---
layout: default
title: 8 Entwurfsmuster
permalink: design-patterns
---

# 8 Entwurfsmuster (Design Patterns)
{: .no_toc }

<details open markdown="block">
  <summary>
    Inhalt
  </summary>
  {: .text-delta }

- TOC
{:toc}

</details>


*2 unterschiedliche Entwurfsmuster aus der Vorlesung (oder nach Absprache auch andere) jeweils sinnvoll einsetzen, begründen und UML-Diagramm*

Entwurfsmuster sind häufig wiederkehrende Lösungsmuster im Software-Umfeld, die dabei helfen können, komplexer werdende Softwaresysteme zu beherrschen. Sie beschreiben unabhängig von konkretem Code wie - auf konzeptioneller Ebene - in bestimmten Situation vorgegangen werden kann, um ein Problem zu lösen. Das Muster biete quasi eine "Generallösung", die dann auf den eigenen Code adaptiert werden kann. Eine schöne Übersicht über Design Pattern bietet [refactoring.guru](https://refactoring.guru/design-patterns).


## Entwurfsmuster 1: Builder

Mithilfe eines "Erbauers" (Builder) kann die Konstruktion eines Objekts von der eigenen Klasse in eine andere, speziell dafür vorgesehene Klasse (Builder) ausgelagert werden. Dieses Entwurfsmuster habe ich für die Konstruktion von Action Cards angewandt. Vor Commit [`dfbfa`](https://github.com/Splines/dominion-cli/commit/dfbfaf38d7a3518c143d811b5f0956fe03b1e1f1) wurden in der Klasse `CardPool` Aktionskarten [wie folgt](https://github.com/Splines/dominion-cli/blob/712ff9c0a9b24148b865dc312d4871c72659e18e/2-dominion-application/src/main/java/me/splines/dominion/Card/CardPool.java#L18-L46) angelegt:

```java
new ActionCard("Jahrmarkt", CardType.ACTION, 5, new Action(
					new EarnActionsInstruction(2),
					new EarnBuyingsInstruction(1),
					new EarnMoneyInstruction(2))),
```

Das UML-Diagramm für die `ActionCard` sah wie folgt aus:

![ActionCard UML](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/design-patterns/builder-before.puml&fmt=svg)

Um den großen Konstruktor der `ActionCard` bei der Konstruktion von Karten zu umgehen, wurde die Klasse [`ActionCardBuilder`](https://github.com/Splines/dominion-cli/blob/dfbfaf38d7a3518c143d811b5f0956fe03b1e1f1/3-dominion-domain/src/main/java/me/splines/dominion/Card/ActionCardBuilder.java) eingeführt. Standardmäßig wird nun eine Aktionskarte mit dem Kartentyp `CardType.ACTION` erstellt, dies kann jedoch durch Aufruf von `asAttack()` bzw. `asReaction()` geändert werden. Mithilfe der Methode `with(Action action)` kann die Aktion für die Aktionskarte festgelegt. Damit Method-Chaining beim Aufruf möglich wird, geben alle Methoden des `ActionCardBuilder`s ein Objekt vom selben Typ zurück. Ausnahme dieser Regel ist die Methode `ActionCard build()`, die am Ende aufgerufen werden muss, um tatsächlich eine neue Aktionskarte zu erhalten. Das Ergebnis ist am Ende immer eine vollständige Aktionskarte: hat die Benutzerin des Builders vergessen, die Methode `with(Action action)` aufzurufen, wird die Konstruktion der `ActionCard` fehlschlagen, da dann `null` als Parameter übergeben wird und dieser Check von der `ActionCard` selbst abgefangen wird.

Das UML-Diagramm nach Anwendung dieses Entwurfsmusters sieht wie folgt aus:

![ActionCard UML mit Erbauer](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/design-patterns/builder-after.puml&fmt=svg)

Dementsprechend lässt sich nun [folgender Code](https://github.com/Splines/dominion-cli/blob/dfbfaf38d7a3518c143d811b5f0956fe03b1e1f1/2-dominion-application/src/main/java/me/splines/dominion/Card/CardPool.java#L20-L57) im `CardPool` schreiben:

```java
new ActionCardBuilder("Jahrmarkt", 5).with(
            new Action(
                    new EarnActionsInstruction(2),
                    new EarnBuyingsInstruction(1),
                    new EarnMoneyInstruction(2)))
            .build()
```

Hier ist nun der Parameter für den `CardType` weggefallen, da die meisten Karten den Kartentyp `CardType.ACTION` und nicht `CardType.ACTION_ATTACK` oder `CardType.ACTION_REACTION` haben. Leider war dies bereits der einzige Vorteil, den der doch etwas unnötige Einsatz dieses Patterns bewirkt hat. Der Code zum Konstruieren einer `ActionCard` sah vorher nicht viel schlimmer aus; hier kommt sogar noch die Zeile `.build()` hinzu. Auch konnten bereits vorher ausschließlich vollständig fertig "gebaute" `ActionCard`s konstruiert werden; dass diese Eigenschaft erhalten bleibt ist also kein Zugewinn. Das Potential dieses Entwurfsmusters hätte besser ausgeschöpft werden können, wenn die `ActionCard` noch mehr Parameter im Konstruktor verlangt hätte, beispielsweise 10 Stück. Nichtsdestotrotz konnte so zumindest das Pattern im eigenen Code erprobt werden.


## Entwurfsmuster 2: Strategy

Eine gute Übersicht über das Strategy-Pattern ist [hier](https://refactoring.guru/design-patterns/strategy) zu finden. Dabei werden ähnliche Aufgabe, die sich jedoch in der konkreten Implementierung unterscheiden, in unterschiedliche Klassen ausgelagert, die jeweils das selbe Interface implementieren. Von außen kann dann eine andere Klasse mit diesem Interface agieren, ohne die Implementierungsdetails kennen zu müssen. Außerdem kann die konkrete Klasse einfach ausgewechselt werden.

Mithilfe dieses Patterns wurden die Anweisungen einer Aktionskarte umgesetzt. Dazu wurde im Domain-Code das Interface [`Instruction`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/action/Instruction.java) eingeführt, das lediglich die Methode `void execute(...)` sowie `String getName()` definiert:

![Instruction UML](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/design-patterns/strategy-instruction.puml&fmt=svg)

Konkret implementiert wird das Interface im Application-Layer im [`instruction`-Paket](https://github.com/Splines/dominion-cli/tree/main/2-dominion-application/src/main/java/me/splines/dominion/instruction), z.B. von der Klasse [`DiscardAndDrawCardsInstruction`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/instruction/DiscardAndDrawCardsInstruction.java).

Im Domain-Code gibt es außerdem die Klasse [`Action`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/action/Action.java), die mehrere `Instruction`s in einer Aktion bündelt. Diese wiederum wird der [`ActionCard`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/card/ActionCard.java) im Konstruktor übergeben. Die `ActionCard` stellt dann nach außen einen Getter `Action getAction()` zur Verfügung.

Während der Aktionsphase kann [`GamePlayer`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/GamePlayer.java) nun seine Aktionskarten ausspielen. Dazu erhält die Methode [`doActionPhase` in `PlayerMove`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/PlayerMove.java#L24-L50) für jede zu spielende Aktionskarte mittels `getAction()` die `Action`, kann daran `getInstructions()` aufrufen und dann für jede Instruktion die `execute(...)`-Methode. Für eine Aktionskarte sieht der Aufruf dann wie folgt aus:

```java
actionCard.get().getAction().getInstructions()
        .forEach(instruction -> instruction.execute(player, moveState, stock));
```

Wie die Instruktionen zu einer Aktion gebündelt und dann damit eine `ActionCard` erstellt wird, haben wir bereits oben beim ersten Entwurfsmuster gesehen. Schließlich ergibt sich nun das folgende Gesamtbild:

![Strategy Entwurfsmuster UML](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/design-patterns/strategy-full.puml&fmt=svg)

Die `ActionCard` hat eine `Action`, diese besitzt eine oder mehrere `Instruction`s, deren konkgit rete Implementierungen sich im Application-Layer befinden. Dadurch kann die `PlayerMove`-Klasse die Instruktionen erhalten und nacheinander in der Aktionsphase ausführen. Durch das Strategy-Pattern wurde es möglich, die Business-Logik (während der Aktionsphase für jede ausgewählte Aktionskarte die Instruktionen dieser Karte ausführen) von der konkreten Implementierung der einzelnen Instruktionen getrennt zu halten. Auf diese Weise wäre es sogar möglich (jedoch nicht gefordert), Instruktionen von Karten zur Runtime auszuwechseln. Dies ändert nichts daran, dass weiterhin einfach nur `execute(...)` aufgerufen werden muss. Außerdem berücksichtigt dieses Konzept das Open-Closed Principle: für zukünftige Erweiterungen des Spiels kann man einfach neue Anweisungen erstellen, indem man das `Instruction`-Interface in einer neuen Klasse implementiert. Mit der neuen `Instruction` können dann im `CardPool` neue Aktionskarten angelegt werden und diese Anweisung auch mit beliebigen anderen `Instruction`s in einer `Action` kombiniert werden.

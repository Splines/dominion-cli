---
layout: default
title: 6 Domain Driven Design
permalink: domain-driven-design
---

# 6 Domain Driven Design
{: .no_toc }

<details open markdown="block">
  <summary>
    Inhalt
  </summary>
  {: .text-delta }

- TOC
{:toc}

</details>


## Ubiquitous Language
*Vier Beispiele für die Ubiquitous Language; jeweils Bezeichnung, Bedeutung und kurze Begründung, warum es zur Ubiquitous Language gehört*

Die Ubiquitous Language ist die allgegenwärtige Fachsprache, die in der Domäne gesprochen wird und daher auch im Sourcecode verwendet werden soll. Auf diese Weise kann die "Verständniskluft" zwischen Entwickler:innen und Domänenexpert:innen verringert werden und es wird einfacher, im Modell der Realität (Code) die Domäne gut abzubilden.

Auch für Dominion wird eine eigene Sprache mit besonderem, spiel-spezifischem Vokabular gesprochen. Dies wird besonders in der Spielanleitung deutlich, wo man beispielsweise in der Sektion [Häufige Anweisungen auf den Aktionskarten](https://www.spielkarten.com/wp-content/uploads/2019/07/22501413_Dominion-2nd-Edition_SR1.pdf#page=6) fast schon eine Art Glossar angelegt findet.

Hier fünf Beispiele:

| Bezeichnung | Bedeutung | Begründung |
|-------------|-----------|------------|
| *Ablegen*   | "Karten werden immer von der Hand abgelegt, sofern nicht anders auf der Karte angegeben. Abgelegte Karten kommen offen auf den eigenen Ablagestapel. [...] Lediglich die oberste Karte des Ablagestapels muss immer sichtbar sein." | Im Code wurde *Ablegen* als [`void discard(Card card)`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/GamePlayer.java#L93-L97) modelliert. Die Bezeichnung wird durchgängig durch die gesamte Spielanleitung hinweg verwendet und ist Bestandteil des Grundvokabulars, um das Spiel verstehen zu können. Daher gehört es zur Ubiquitous Language. |
| *Entsorgen* | "Entsorgt der Spieler Karten, legt er sie offen auf den Müllstapel bzw. auf das Müll-Tableau, falls noch keine Karte entsorgt wurde. Entsorgte Karten können nicht wieder gekauft oder genommen werden." | Im Code wurde *Entsorgen* als [`void dispose(Card card)`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/GamePlayer.java#L99-L102) modelliert. Die Bezeichnung wird durchgängig durch die gesamte Spielanleitung hinweg verwendet und ist Bestandteil des Grundvokabulars, um das Spiel verstehen zu können. Daher gehört es zur Ubiquitous Language. |
| *Nehmen* | "Karten werden aus dem Vorrat genommen und ungenutzt auf den Ablagestapel gelegt – dies ist kein Kauf. Karten, die ohne Angabe der Herkunft genommen werden müssen oder dürfen, werden vom Vorrat genommen." | Im Code wurde *Nehmen* als [`void take(Card card)`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/GamePlayer.java#L110-L113) modelliert. Die Bezeichnung wird durchgängig durch die gesamte Spielanleitung hinweg verwendet und ist Bestandteil des Grundvokabulars, um das Spiel verstehen zu können. Daher gehört es zur Ubiquitous Language. |
| *+ X Aktionen* | "Der Spieler **darf** X weitere Aktionskarten ausspielen. Zunächst muss er jedoch alle Anweisungen der aktuellen Aktionskarte (soweit möglich) erfüllen. Darf er weitere Aktionen ausführen (z.B. durch das Ausspielen von mehreren Aktionskarten), sollte die Anzahl der Aktionen der Übersichtlichkeit halber laut mitgezählt werden." | Die Bezeichnung wird durchgängig durch die gesamte Spielanleitung hinweg verwendet, insbesondere ist sie  auf vielen Aktionskarten aufgedruckt. In der Anleitung wird bei solchen Anweisungen klar darauf geachtet wird, "müssen" und "dürfen" korrekt zu verwenden (diese Bezeichnungen sind sogar fett gedruckt). Da die Bezeichnung Bestandteil des Grundvokabular ist, um erfolgreich Aktionskarten auslegen und spielen zu können, gehört *+ X Aktionen* zur Ubiquitous Language. |
| *Deck* | Ein *Deck* ist ein Kartenstapel im Spiel, auf den Karten abgelegt und von dem Karten wieder gezogen werden können. | Nicht nur in Dominion ist die Idee des Kartenstapels bekannt. Im Englischen habe ich mich, unter anderem nach [diesem Post](https://boardgamegeek.com/thread/1237320/card-game-terminology-deck-stack-pile), entschieden, den Kartenstapel als *Deck* zu bezeichnen. Im Code ist das Kartendeck in der Domäne als [`Deck`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/game/Deck.java) realisiert. In der Spielanleitung ist dieses Wort allgegenwärtig, zum Beispiel auch bei den Begriffen "Nachziehstapel" oder "Ablagestapel" (siehe Seite 5 unten). Der Begriff ist Bestandteil des Grundvokabulars, um das Spiel verstehen zu können und ist daher Teil der Ubiquitous Language. |


## Entities
*UML, Beschreibung und Begründung des Einsatzes einer Entity; falls keine Entity vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist*

![Entity Player (reused from mock section)](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/mock/2-mock.puml&fmt=svg)

Die Klasse [`Player`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/game/Player.java) bzw. [`GamePlayer`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/GamePlayer.java) kann als Entity angesehen werden und modelliert die tatsächlichen Mitspieler von Dominion. Dabei werden die geltenden Domänenregeln forciert, indem nach außen Methoden zum Verändern des Zustands (Lebenszyklus) einer Spielerin bereitgestellt werden, mit denen es nicht möglich ist, die Entity nach der Konstruktion in einen ungültigen Zustand zu versetzen. Beispiele für solche Methoden sind `void takeToHand(Card card)`, `void discard(Card card)` oder `void dispose(Card card)`. Hier ist auch zu erkennen, dass das Value Object `Card` bzw. konkrete Ausprägungen davon (wie z.B. bei `List<ActionCard> getActionCardsOnHand()`) eingesetzt werden, um so viel Verhalten wie möglich auszulagern und die Entity (trotz ihres Umfangs) möglichst schlank zu halten.

Die Klasse `Player` hat zudem eine eigene Identität in der Domäne: der Name eines Spielers ist eindeutig. Wir forcieren diese Regel im Code allerdings nicht und überlassen es über das CLI dem Nutzer, die Namen frei zu wählen. Da zwei `Player` in der Domäne nie auf Gleichheit überprüft werden müssen, ist es aus Sicht des Codes unproblematisch, wenn zwei Spielerinnen denselben Namen haben. Nur für die Nutzer unseres Programms könnte es dann verwirrend werden, weil man eventuell nicht mehr weiß, wer nun an der Reihe ist. Dies ist jedoch für uns nicht weiter relevant und kann einfach behoben werden, indem die Benutzer eindeutige Namen vergeben. Sollten sich Benutzer über dieses "Feature" beschweren, ist die Anpassung in wenigen Handgriffen erledigt (überschreiben der `equals()`-Methode und beim Anlegen der `Player` überprüfen, dass es keine zwei gleiche Spieler gibt).


## Value Objects
*UML, Beschreibung und Begründung des Einsatzes eines Value Objects; falls kein Value Object vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist*

![Value Object Card](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/domain-driven-design/value-object.puml&fmt=svg)

Die abstrakte Klasse [`Card`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/card/Card.java) modelliert eine beliebige Karte im Spiel. Alle Karten von Dominion haben gemeinsam, dass sie einen Namen (z.B. "Jahrmarkt"), einen Kartentyp (z.B. "AKTION") und Kartenkosten (z.B. Karte kostet 5 "Geld") haben. Die Karte ist unveränderlich, das heißt es gibt beispielsweise keine Setter in der Klasse und alle Felder sind als "blank final" markiert. Jedoch ist die Klasse selbst nicht final, sondern abstract, sodass genau genommen die Unterklassen (wie [`MoneyCard`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/card/MoneyCard.java), [`PointCard`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/card/PointCard.java) und [`ActionCard`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/card/ActionCard.java)) als eigentliche Value Objects bezeichnet werden müssten.

In der Klasse [`Card`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/card/Card.java#L37-L55) wurde zudem die `hashCode()` und `equals()`-Methode überschrieben, denn da das Value Object ein Wertkonzept kapselt, sollten zwei Value Objects gleich sein, wenn sie die selben Werte haben. Diese Forderung wurde jedoch gelockert, da es keine zwei Karten in Dominion gibt, die denselben Namen haben. Dementsprechend wurde nur auf Namensgleichheit überprüft, obwohl streng genommen eigentlich neben Name, auch Typ und Kosten sowie bei Subklassen auch auf deren zusätzliche Attribute mit einfließen müssten. In diesem Zusammenhang könnte man die `Card` also auch als Entity bezeichnen, weil sie mit dem Namen eine eindeutige ID innerhalb der Domäne hat. Nichtsdestotrotz hat eine Karten *keinen* eigenen Lebenszyklus und verändert sich nie während ihrer Lebenszeit (immutable), weshalb wir sie trotzdem als Value Object klassifizieren.

## Repositories
*UML, Beschreibung und Begründung des Einsatzes eines Repositories; falls kein Repository vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist*

Repositories bieten eine Schnittstelle, um von der Domäne aus Daten aus dem Persistenzspeicher, z.B. einer Datenbank zu lesen, ohne die technischen Details einer Datenbankverbindung kennen zu müssen. Damit vermitteln Repositories zwischen der Domäne und dem Datenmodell. Im Kern wird dazu ein Interface mit Methoden wie `findById(...)` definiert, das dann in äußeren Schichten (z.B. der Plugin-Schicht) implementiert werden kann. Auf diese Weise wird der Kern nicht mit unnötiger "accidental complexity" belastet.

In diesem Projekt kamen keine Repositories zum Einsatz, da bislang noch kein Zugriff auf Persistenzspeicher benötigt wurde. Das Programm agiert mit dem Benutzer über die Konsole, wobei alle Daten im Hauptspeicher vorgehalten sind und nicht von persistentem Speicher gelesen werden müssen. Daher wurde auch kein expliziter Zugriff auf ein Datenmodell benötigt.

Allerdings hat sich bereits ein zukünftiger Einsatzbereich für Repositories aufgetan: der [`CardPool`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/card/CardPool.java) definiert zurzeit alle sich im Spiel befindlichen Karten als `public static final` Member der Klasse:

```java
// Action cards
public static final List<ActionCard> actionCards = List.of(

    new ActionCardBuilder("Jahrmarkt", 5).with(
            new Action(
                    new EarnActionsInstruction(2),
                    new EarnBuyingsInstruction(1),
                    new EarnMoneyInstruction(2)))
            .build(),

    new ActionCardBuilder("Markt", 5).with(
            new Action(
                    new DrawCardsInstruction(1),
                    new EarnActionsInstruction(1),
                    new EarnBuyingsInstruction(1),
                    new EarnMoneyInstruction(1)))
            .build(),

    ...
);

// Money cards
public static final MoneyCard copperCard = new MoneyCard("Kupfer", 0, 1);
public static final MoneyCard silverCard = new MoneyCard("Silber", 3, 2);
public static final MoneyCard goldCard = new MoneyCard("Gold", 6, 3);
public static final List<MoneyCard> moneyCards = List.of(copperCard, silverCard, goldCard);

...
```

Da das Spiel Dominion von zahlreichen Erweiterungen lebt und individuell Aktionskarten für ein Spiel kombiniert werden können (bei Kombination aller Schachteln können [über 10 Trillionen Kombinationen gezogen werden](https://de.wikipedia.org/wiki/Dominion_(Spiel)#Erweiterungen)), wäre eine Erweiterung des Programms sinnvoll, damit Spieler selbst die Spielkarten zu Beginn auswählen können. Dies könnte beispielsweise über eine Textdatei geschehen, in denen die Namen der zu verwendenden Karten aufgelistet werden. Aber auch ohne eine solche persistente Datei könnte dann ein Repository sinnvoll sein, um die Definition der Karten besser vom Rest des Codes auszulagern. Negativ sticht zum Beispiel der [`GameStock`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/GameStock.java) heraus, der sowohl Methoden zur Verwaltung des Kartenvorrats implementiert, als auch die Karten auf diesem Vorrat selbst. Mit einem Repository könnte dies verbessert werden.


## Aggregates
*UML, Beschreibung und Begründung des Einsatzes eines Aggregates; falls kein Aggregate vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist*

Aggregate gruppieren die Entities und Value Objects zu gemeinsam verwalteten Einheiten. Dadurch können die Objektbeziehungen untereinander gekapselt werden, von außerhalb wird dann nur mit dem Aggregate gearbeitet, indem ausschließlich auf das Aggregate Root zugegriffen wird. Dadurch kann besser die Einhaltung von Domänenregeln kontrolliert werden.

Im Code findet sich in diesem Projekt kein klassisches Aggregate, das Entities oder Value Objects kapselt. Am ehesten könnte man noch die Klasse [`PlayerInteraction`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/interaction/PlayerInteraction.java) als Aggregate bezeichnen.

![Aggregate PlayerInteraction](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/domain-driven-design/aggregate.puml&fmt=svg)

Wie im UML-Diagramm zu erkennen, hat die `PlayerInteraction` jeweils eine Member-Variable für ein Objekt vom Typ `PlacerDecision` bzw. `PlayerInformation`. Die Klasse `PlayerInteraction` wurde jedoch nur aus Bequemlichkeitsgründen angelegt und nicht, um Domänenregeln zu forcieren. `PlayerDecision` und `PlayerInformation` wurden häufig zusammen in Parametern übergeben, sodass es Sinn ergab, sie mithilfe der `PlayerInteraction` zu kapseln. Der [Konstruktur von `Player`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/game/Player.java#L23) erwartet beispielsweise eine `PlayerInteraction`:

```java
protected Player(String name, PlayerInteraction playerInteraction, Deck drawDeck, Stock stock) {
    ...
}
```

Die Klasse [`Player`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/game/Player.java#L23) wiederum stellt dann ähnlich zu `PlayerInteraction` die Methoden `PlayerDecision decide()` sowie `PlayerDecision inform()` zur Verfügung. Dadurch sind [Aufrufe der folgenden Art] möglich (siehe [`PlayerMove`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/PlayerMove.java)):

```java
player.inform().startActionPhase();
player.decide().chooseOptionalActionCard(...);
```

Nicht zuletzt ist die `PlayerInteraction` auch deshalb kein klassisches Aggregate, weil gar keine Entities oder Value Objects zusammengefasst werden, sondern konkrete Implementierungen der Interfaces `PlayerDecision` und `PlayerInformation`. Im allgemeinen Sprachgebrauch könnte man aber trotzdem von einem "Aggregat" sprechen. Abseits davon ist der Einsatz von Aggregates für dieses Projekt nicht erforderlich, wahrscheinlich auch deshalb, weil die Beziehungen zwischen Entities und Value Objects nicht komplex genug sind, um eine Gruppierung als Aggregate zu rechtfertigen bzw. diese Komplexität bereits durch geeignete Abstraktion reduziert wurde.

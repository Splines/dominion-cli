---
layout: default
title: 4 Weitere Prinzipien
permalink: further-principles
---

# 4 Weitere Prinzipien
{: .no_toc }

<details open markdown="block">
  <summary>
    Inhalt
  </summary>
  {: .text-delta }

- TOC
{:toc}

</details>


## Analyse GRASP: Geringe Kopplung
*Jeweils eine bis jetzt noch nicht behandelte Klasse als positives und negatives Beispiel geringer Kopplung; jeweils UML Diagramm mit zusammenspielenden Klassen, Aufgabenbeschreibung und Begründung für die Umsetzung der geringen Kopplung bzw. Beschreibung, wie die Kopplung aufgelöst werden kann*

GRASP ist ein Akronym für "General Responsibility Assignment Software Patterns" und umfasst Entwurfsprinzipien, die sich auf Zuständigkeiten von Objekten beziehen, d.h. sie beschreiben, welche Objekte und Klassen wofür zuständig sein sollen. Eins dieser Prinzipien ist die geringe Kopplung (Low Coupling), wobei Kopplung den Grad der Abhängigkeit/Verknüpfung zwischen verschiedenen Klassen, Softwaremodulen oder gar verschiedenen Systemen bezeichnet. Durch geringe Kopplung sind die einzelnen Komponenten leichter anpassbar (Änderungen an einer Klasse haben nur lokale Auswirkungen), lassen sich besser alleine testen sowie besser warten, da weniger Kontext benötigt wird, um die Klasse zu verstehen (weniger Cognitive Load beim Lesen des Codes). Zudem wird die Wiederverwendbarkeit verbessert.


**Positiv-Beispiel ▶ Geringe Kopplung**

![Refactoring CardFormatter](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/grasp/low-coupling.puml&fmt=svg)

Die Klasse `PlayerMove` bekommt in ihrem Konstruktor ein Objekt vom Typ `Stock` übergeben. Ein `Stock` stellt den Kartenvorrat dar, von dem alle Karten im Spiel stammen. Wenn der Spieler einen Zug durchführt (siehe Klasse `PlayerMove`), muss eine Referenz auf einen `Stock` vorhanden sein, damit beispielsweise in der Kaufphase (Methode `void doBuyPhase()`) eine Karte vom Vorrat genommen und dann auf den Ablagestapel des Spieler hinzugefügt werden kann. Auch bringen wir vom `Stock` durch die Methode `List<Card> getAvailableCardsWithMaxCosts(int maxCosts)` in Erfahrung, welche Karten der Spieler in der Kaufphase überhaupt mit seinem Geld kaufen kann.

Positiv im Sinne der Kopplung ist hier hervorzuheben, dass der `PlayerMove` nur eine schwache Abhängigkeit vom Kartenstapel hat. Denn `PlayerMove` hängt nur von `Stock`, nicht jedoch von dem konkreten `GameStock` ab. Dadurch müssen dem `PlayerMove` die Interna von `GameStock` nicht bekannt sein. In Zukunft könnte deshalb ein komplett anderer `Stock` an `PlayerMove` übergeben werden, ohne dass in `PlayerMove` selbst etwas angepasst werden müsste. Ob dieser Fall jedoch realistisch gesehen benötigt wird, ist fraglich, da der Vorrat in Dominion bislang in allen Editionen auf die gleiche Art und Weise funktioniert hat. Trotzdem ist dies ein gutes Beispiel für eine geringe Kopplung, indem nur schwache Abhängigkeiten mittels Interfaces umgesetzt werden.


**Negativ-Beispiel ▶ Hohe Kopplung**

![Refactoring CardFormatter](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/grasp/high-coupling.puml&fmt=svg)

Die abstrakte Klasse [`Player`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/game/Player.java) liefert ein Beispiel für hohe Kopplung. In diesem Fall besitzt der `Player` eine Member-Variable zu der Klasse `PlayerInteraction`, die wiederum Objekte von Typ `PlayerDecision` und `PlayerInformation` aggregiert. Diese beiden Objekte werden nach außen durch die Methoden `decision()` und `information()` zur Verfügung gestellt. Problematisch ist nun, dass der `Player` diese Objekte ebenso nach außen reicht. Damit ist für `PlayerMove`, die in der konkreten Implementierung von `Player` mit jedem Zug neu instantiiert wird, folgender Aufruf möglich:

```java
player.decide().performMethodOnPlayerDecisionObject()
```

Die Klasse `PlayerMove` bekommt also über den `Player` die Objekte durchgereicht, die eigentlich zu `PlayerInteraction` gehören. Damit sind `Player` und `PlayerMove` stark miteinander gekoppelt, denn `PlayerMove` ist direkt von `Player` abhängig und bekommt diesen sogar als Parameter übergeben. Positiv ist hier jedoch anzuführen, dass bewusst nicht ein Aufruf der folgenden Art erlaubt wurde:

```java
player.playerInteraction.decision().performMethodOnPlayerDecisionObject()
```

Dadurch ist es zumindest nicht notwendig, dass `PlayerMove` über den inneren Aufbau von `Player` Bescheid weiß. Es ist jedoch weiterhin davon abhängig, dass `Player` die Methoden `decide()` und `inform()` bereitstellt, um auf die Objekte vom Typ `PlayerDecision` bzw. `PlayerInformation` zuzugreifen. Als mögliche Lösung könnte der Klasse `PlayerMove` direkt im Konstruktor das `PlayerInteraction`-Objekt von `Player` mit übergeben werden (Dependency Injection). Hier war jedoch die Schreibweise `player.decide().<method>` zu "verlockend", da sie sich nah an der englischen Sprache orientiert und deshalb einfach zu verstehen ist.


## Analyse GRASP: Hohe Kohäsion
*Eine Klasse als positives Beispiel hoher Kohäsion; UML Diagramm und Begründung, warum die Kohäsion hoch ist*

Mit hoher Kohäsion ist gemeint, dass eine Klasse eine Sache "kann" und dies sehr gut. Damit ist das Konzept eng verwandt mit dem Single Responsibility Prinzip. Bei Kohäsion geht es um die Frage, wie stark die Elemente (z.B. Methoden) eines Ganzen (z.B. einer Klasse) inhaltlich/logisch zusammengehören und funktional eine Aufgabe erledigen. Stark verwandte Dinge sollten zusammengehalten werden, dann ist die Kohäsion hoch. Eine gute Erklärung bietet diese [diese Antwort von StackOverflow](https://stackoverflow.com/a/29523753/9655481).

Ein positives Beispiel für hohe Kohäsion ist das Interface [`PlayerDecision`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/interaction/PlayerDecision.java) bzw. dessen konkrete Implementierung [`PlayerDecisionCLI`](https://github.com/Splines/dominion-cli/blob/main/0-dominion-plugin-cli/src/main/java/me/splines/dominion/interaction/PlayerDecisionCLI.java).

![PlayerDecision Hohe Kohäsion](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/grasp/high-cohesion.puml&fmt=svg)

Alle Methoden von `PlayerDecision` beziehen sich auf die klare Aufgabe, der Benutzerin eine Entscheidung zu überlassen. Dementsprechend tragen auch alle Methoden das Wort *choose* in ihrem Name. Die Methoden sind also eng miteinander verwandt und gehören inhaltlich zusammen. Ausnahme ist die Methode `getPlayerNames()` im `PlayerDecisionCLI`, die aus Bequemlichkeit zu dieser Klasse hinzugefügt wurde. Dennoch ist die Kohäsion insgesamt sehr hoch und gleichzeitig auch die Kopplung extrem gering, z.B. hat das `PlayerDecisionCLI` keinen einzigen privaten Member.


## Don’t Repeat Yourself (DRY)
*Einen Commit angeben, bei dem duplizierter Code/duplizierte Logik aufgelöst wurde; Code-Beispiele (vorher/nachher); begründen und Auswirkung beschreiben*

Durch kopierten Code wird das Refactoring enorm erschwert, denn ein kopierter Code kann auch kopierte Fehler bedeuten. Fehler und evtl. auch Sicherheitsschwachstellen müssen dann an mehreren Stellen gleichzeitig behoben werden, was die Wartung eine schwierige Aufgabe macht. Aber nicht nur bei Fehlern, auch bei normalen Anpassungen darf der duplizierte Code nicht vergessen werden, ansonsten drohen Inkonsistenzen im Programm. Code-Duplikationen sind daher unerwünscht, oftmals können sie durch geeignete Abstraktion oder auch ein einfaches Refactoring wie "Extract Method" behoben werden.

Im Commit [`0140d`](https://github.com/Splines/dominion-cli/commit/0140dbf30d5b3351385d7584bd8d59f664185945) wurde duplizierter Code für das Ausgeben einer Spielkarte auf der Konsole in eine eigene Methode ausgelagert. Allerdings wird dieser Commit bereits im Refactoring-Kapitel betrachtet.

Deshalb dient hier der Commit [`3ba15`](https://github.com/Splines/dominion-cli/commit/3ba15bc09b8be403c3dc49bfd0e72ca22ac77073) als Beispiel. Der Commit bezieht sich auf den `CardFormatter`, der für das Formatieren der Spielkarten als String unter Verwendung von Unicode-Zeichen zuständig ist. Um Text mittig zu platzieren mussten an zwei Stellen jeweils berechnet werden, wie viel Platz noch nach links und nach rechts zur Verfügung steht. Diese Berechnung wurde nun in eine eigene Methode ausgelagert. Vorher sah der `CardFormatter` [so aus](https://github.com/Splines/dominion-cli/blob/946ae754aa2b81df345481b97f065d09aefae09c/0-dominion-plugin-cli/src/main/java/me/splines/dominion/card/CardFormatter.java#L67-L68):

```java
public final class CardFormatter {
    ...

    private static String getHeader(Card card, int index) {
        ...
        // Calculate spaces
        int spacesLeft = (int) Math.floor(spaceLeft / 2.0);
        int spacesRight = (int) Math.ceil(spaceLeft / 2.0);
        ...
    }

    private static String getFooter(Card card) {
        ...
        // Calculate spaces
        int spacesToTheLeft = (int) Math.floor(spacesBothSides / 2.0);
        int spacesToTheRight = (int) Math.ceil(spacesBothSides / 2.0);
        ...
    }

}
```

Der Code wurde nun durch die Einführung der neuen Methode [`Spaces getRemainingSpaces(int spacesRemainingOnBothSides)`](https://github.com/Splines/dominion-cli/blob/3ba15bc09b8be403c3dc49bfd0e72ca22ac77073/0-dominion-plugin-cli/src/main/java/me/splines/dominion/card/CardFormatter.java#L121-L129) vereinfacht:

```java
public final class CardFormatter {
    ...

    private static String getHeader(Card card, int index) {
        ...
        Spaces spaces = getRemainingSpaces(spacesBothSides);
        // und später dann spaces.spacesToTheLeft sowie spaces.spacesToTheRight
        ...
    }

    private static String getFooter(Card card) {
        ...
        Spaces spaces = getRemainingSpaces(spacesBothSides);
        // und später dann spaces.spacesToTheLeft sowie spaces.spacesToTheRight
        ...
    }

    private static record Spaces(int spacesToTheLeft, int spacesToTheRight) {};

    private static Spaces getRemainingSpaces(int spacesRemainingOnBothSides) {
        int spacesToTheLeft = (int) Math.floor(spacesRemainingOnBothSides / 2.0);
        int spacesToTheRight = (int) Math.ceil(spacesRemainingOnBothSides / 2.0);
        return new Spaces(spacesToTheLeft, spacesToTheRight);
    }

}
```

Während dieses Refactorings wurden auch die Variabelnamen entsprechend angepasst. Die Variable [`spacesLeft`](https://github.com/Splines/dominion-cli/blob/946ae754aa2b81df345481b97f065d09aefae09c/0-dominion-plugin-cli/src/main/java/me/splines/dominion/card/CardFormatter.java#L63) war zuvor sehr ungünstig gewählt, weil hier mit "left" nicht links, sondern "übrig" gemeint war. Gleichzeitig wurde derselbe Namen [`spacesLeft`](https://github.com/Splines/dominion-cli/blob/946ae754aa2b81df345481b97f065d09aefae09c/0-dominion-plugin-cli/src/main/java/me/splines/dominion/card/CardFormatter.java#L67) verwendet, um die Freiräume anzuzeigen, die nach "links" noch verfügbar sind. Im neuen Code ist nun die Rede von `spacesRemainingOnBothSides` sowie von `spacesToTheLeft` (jeweils anstelle von `spacesLeft`).

Durch das Auslagern in die Methode könnte nun die Berechnung an diesem *einen* Orten angepasst werden, z.B. wenn bei ungerader Breite der Karte der Text eher weiter nach links oder nach rechts geschoben werden soll. Vorher hätte dies an zwei Stellen geändert werden müssen. Sicherlich wären gerade in dieser Klasse `CardFormatter` noch weitere Verbesserungen mit ein wenig Aufwand möglich, gerade weil die Methoden `getHeader(...)` und `getFooter(...)` sehr ähnlich bezüglich ihres Aufbaus sind. Zugegebenermaßen ist der Code in der Plugin-Schicht auch insgesamt der am wenigsten schönste geworden. Die inneren Schichten sind deutlich ausgefeilter, besser wartbar und entsprechen eher dem Prinzip von Clean Code. Nichtsdestotrotz: auch die gezeigte, kleine Anpassung hat Duplicate Code vermieden.

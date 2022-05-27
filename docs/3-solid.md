---
layout: default
title: III SOLID
permalink: solid
---

# III SOLID
{: .no_toc }

<details open markdown="block">
  <summary>
    Inhalt
  </summary>
  {: .text-delta }

- TOC
{:toc}

</details>


## Analyse Single-Responsibility-Principle (SRP)
*Jeweils eine Klasse als positives und negatives Beispiel für SRP;  jeweils UML der Klasse und Beschreibung der Aufgabe bzw. der Aufgaben und möglicher Lösungsweg des Negativ-Beispiels (inkl. UML)*

Eine gute und knappe Erklärung für dieses Prinzip stammt von Robert C. Martin und kann [hier](https://web.archive.org/web/20140407020253/http://www.objectmentor.com/resources/articles/srp.pdf) eingesehen werden. Demnach sollte es "nie mehr als einen Grund geben, eine Klasse zu ändern". Eine Verantwortlichkeit (Responsibility) einer Klasse wird definiert, als den Grund, die Klasse zu ändern. Wenn es mehrere Gründe gibt, warum eine Klasse zukünftig geändert werden könnte, hat diese Klasse mehrere Verantwortlichkeiten. In diesem Fall sollte sie in mehrere Klassen aufgeteilt werden, die sich jeweils nur um eine Aufgabe kümmern. Dadurch wird der Code insgesamt robuster: wenn es nun Änderungen gibt, bleiben andere Klassen, die nichts mit dieser Änderung zu tun haben, unberührt und "zerbrechen" nicht.


**Positiv-Beispiel**

![Single Responsibility Principle Positiv-Beispiel](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/single-responsibility/single-responsibility-positive.puml&fmt=svg)

Die Klasse `Deck` modelliert einen beliebigen Kartenstapel des Spiels, auf dem `Card`s gelegt werden könne. Das `Deck` hat als einzige Aufgabe, Methoden zur Interaktion mit einem Kartenstapel bereit zu stellen. Dazu zählt beispielsweise:

- Eine Karte ziehen mittels `Card draw()`
- Eine Karte auf den Stapel legen mittels `void put(Card card)`
- Den Kartenstapel mischen mittels `void shuffle()`

Es gibt nur einen Grund, warum sich die Klasse ändern müsste: wenn sich die grundlegenden Prinzipien eines Kartenstapels ändern, z.B. wenn plötzlich nicht mehr von oben, sondern fortan immer nur Karten aus der Mitte des Stapels gezogen werden sollen. Dies ist unwahrscheinlich, da auch Spielehersteller darauf aus sind, nicht mit jeder neuen Edition solche grundlegenden Regeln  umzuwerfen. Eventuell kann noch als weiteren Grund für eine Veränderung nicht die Interaktion mit dem Kartenstapel selbst, sondern die Funktionsweise des Randomisierungs-Generators in `void shuffle()` angeführt werden, z.B. wenn dieser gegen eine neuere Implementierung ausgetauscht werden muss. Diese würde aber dank Abstraktion innerhalb der Collections API von Java stattfinden, sodass dieser Fall hier nicht betrachtet wird und gefolgert werden kann: das `Deck` erfüllt das Single Responsibility Principle voll und ganz.


**Negativ-Beispiel**

![Single Responsibility Principle Negativ-Beispiel](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/single-responsibility/single-responsibility-negative.puml&fmt=svg)

Zunächst die anzumerken, dass die Lage in Wirklichkeit nicht ganz so schlimm ist, wie das obige UML-Diagramm anmuten lässt. Viele der Methoden belaufen sich auf eine oder zwei Zeilen Code. Nichtsdestotrotz hat der `GamePlayer` definitiv mehr als eine Verantwortlichkeit. Mit "Player" bezeichnen wir eine Mitspielerin von Dominion. Sie verwaltet jeweils verschiedene Spielstapel und "Sammelstellen" für Karten:

- Nachziehstapel: drawDeck
- Ablagestapel: discardDeck
- Handkarten: hand
- Ausgespielte Karten während des aktuellen Zuges: table

Dementsprechend gibt es Methoden wie `taketoHand(Card card)`, um eine beliebige Karte auf die Hand zu nehmen (von wo die Karte stammt ist hier egal), auf den Müllplatz zu entsorgen mittels `void dispose(Card card)` (und dadurch von der Hand zu entfernen) oder um ganz einfach eine Karte vom Ablagestapel zu ziehen mit der Methode `Card draw()`. Auch können bestimmte Karten von der Hand abgefragt werden, z.B. mit `List<ActionCard> getActionCardsOnHand()`. Die Spielerin ist hier also für die Verwaltung der verschiedenen "Kartensammelstellen" verantwortlich.

Darüber hinaus ist der `GamePlayer` aber auch dafür zuständig, beim Nachziehen dafür zu sorgen, dass aus dem Ablagestapel ein neuer Nachziehstapel gemischt wird, falls letzterer leer sein sollte (siehe `void makeDrawDeckFromDiscardDeck()`). Dies ist definitiv eine neue Verantwortlichkeit und sollte eigentlich gar nicht Aufgabe des Spielers selbst sein. Hier hat die Analogie aus dem realen Leben beim Programmieren "zugeschlagen", denn tatsächlich müssen die Spieler diese Aufgabe selbst angehen und ihre Fähigkeiten beim Mischen des Stapels beweisen. Dies heißt jedoch nicht, dass der `GamePlayer` diese Aufgabe auch im Programmcode selbst übernehmen muss.

Damit die Methode `void makeDrawDeckFromDiscardDeck()` ausgelagert werden kann, müssten `drawDeck` und `discardDeck` zusammen als eine Einheit verwaltet werden, da auf beide Kartenstapel zugegriffen werden muss.

```java
/**
 * Makes a new draw deck from the discard deck
 * by shuffling the cards and putting them on the draw deck.
 *
 * Afterwards the discardDeck is empty and the drawDeck has all the cards
 * from the discardDeck.
 */
private void makeDrawDeckFromDiscardDeck() {
    discardDeck.shuffle();
    while (!discardDeck.isEmpty()) {
        Card card = discardDeck.draw();
        drawDeck.put(card);
    }
}
```

Eine einfache Lösung, wie diese Funktion vom `GamePlayer` ausgelagert werden kann, ist mir leider nicht bekannt. Schließlich weiß momentan nur die Spielerin selbst über ihre Kartenstapel Bescheid und verwaltet diese. Es könnte höchstens eine Klasse `DeckManager` eingeführt werden, die als Member das draw- und discard deck besitzt. Die entsprechenden Methoden von `GamePlayer` würden dann an diesen (im Konstruktor initialisierten) `DeckManager` delegieren. Auf ein weiteres UML-Diagramm soll an dieser Stelle verzichtet werden, da sich nur wenig ändern würde.

Eventuell könnte auch die Methode `int calculatePoints()` ausgelagert werden. Sie wird für alle `GamePlayer` am Ende des Spiels aufgerufen, um die gesammelten Punkte zu berechnen. Dazu werden die Punkte von allen Punktekarten und Fluchkarten, die der Spieler auf irgendwelchen Kartenstapeln oder in der Hand hat addiert. Einem entsprechenden `PointCalculator` könnten alle Karten von `drawDeck`, `discardDeck` und der `hand` als Liste übergeben werden und dieser könnte dann die Punkte anhand dieser Karten berechnen. Soll dieser `PointCalculator` jedoch außerhalb von `GamePlayer` aufgerufen werden, müssten entsprechende Getter für das `drawDeck` und `discardDeck` definiert werden, obwohl diese Member eigentlich gekapselt bleiben sollten. Auch hierfür wurde deshalb noch keine zufriedenstellende Lösung gefunden, ich bin aber offen für Vorschläge 😉.


## Analyse Open-Closed-Principle (OCP)
*Jeweils eine Klasse als positives und negatives Beispiel für OCP;  jeweils UML der Klasse und Analyse mit Begründung, warum das OCP erfüllt/nicht erfüllt wurde – falls erfüllt: warum hier sinnvoll/welches Problem gab es? Falls nicht erfüllt: wie könnte man es lösen (inkl. UML)?*

Das Open-Closed-Principle in Kürze lautet: “Software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification” ([Bertrand Meyer, 1988](https://en.wikipedia.org/wiki/Object-Oriented_Software_Construction)), d.h. offen für Erweiterungen, aber geschlossen für Veränderungen. Code soll so geschrieben werden, dass wir einfach neue Funktionalitäten hinzufügen können, ohne bestehenden Code verändern zu müssen. Die polymorphe Variante dieses Prinzips wird mittels Interfaces umgesetzt, die geschlossen sind für Veränderungen, jedoch von von anderen Klassen implementiert werden können. Diese Klassen können dann später einfach ausgetauscht werden, sodass der bestehende Code erweitert werden kann.

**Positiv-Beispiel**

![Open-Closed-Principle Positiv-Beispiel](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/open-closed-principle/open-closed-positive.puml&fmt=svg)

<!-- https://stackoverflow.com/a/69549641 -->
{% capture link_with_anchor %}{{ site.baseurl }}{% link docs/2-clean-architecture.md %}#analyse-der-schichten{% endcapture %}

Die Aufgabe des Interface `Instruction` wurde bereits bei der [Analyse der Schichten]({{link_with_anchor}}) behandelt. Im Rahmen des Open-Closed-Principles ist nun hervorzuheben, dass das Interface die einfache Erweiterung des Codes um neue Instruktionen ermöglicht, während das Interface selbst geschlossen für Veränderungen ist. Außerdem ist hier im Sinne des "Information Experts" die konkrete Logik der Instruktionen an die Klassen delegiert, da diese am besten wissen, wie ihre Instruktion auszuführen ist. Dafür implementieren sie die Methoden `void execute(...)` und `String getName()`. Letztere soll eine Repräsentation der Instruction als String zurückgeben, z.B. "+1 Karten" oder "+2💰". Die `execute(...)`-Methode arbeitet dann beispielsweise mit dem MoveState-Objekt und fügt dort zwei "Geld" hinzu oder instruiert die Spielerin, eine neue Karte zu ziehen.


**Negativ-Beispiel**

![Open-Closed-Principle Negativ-Beispiel](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/open-closed-principle/open-closed-negative.puml&fmt=svg)

Im Beispiel des `CardFormatter`s ist positiv im Sinne des Open-Closed-Principles anzuführen, dass die abstrakte Klasse `CardBodyFormatter` mit der abstrakten Methode `String getBody(T card)` einfach von Unterklassen erweitert werden kann — in unserem Fall von `ActionCardFormatter`, `MoneyCardFormatter` sowie `PointCardFormatter`, die jeweils `getBody()` überschreiben.

Um einen neuen Kartentyp einzuführen, erstellen wir einen neuen Kartentyp, indem wir von der abstrakten Klasse `Card` erben und anschließend mit diesem Typ einen neuen `CardBodyFormatter` erstellen. Problematisch ist nun zunächst, dass der `CardFormatter` auf Grundlage einer statischen Map für eine konkrete `Card` einen entsprechenden `CardFormatter` auswählt. Dementsprechend müssten wir die Map nun anpassen und verletzen damit das "Open" Prinzip. Hier könnte man jedoch noch argumentieren, dass zumindest der `CardBodyFormatter` weiterhin "closed" bleibt; diesen mussten wir für die Erweiterung nicht antasten.

Ein weiteres Problem könnte sich ergeben, wenn der neue Formatter für die neue Karte ein weiteres Argument in `getBody()` benötigt, z.B. ein Config-Objekt, das jedoch nur für diesen neuen Kartentyp zum Einsatz kommen soll. Hier müsste der Value-Typ der Map (bisher: `Function<Card, String>`) angepasst werden, wodurch wir jedoch auch die Signatur von `getBody()` in jedem Formatter entsprechend ändern müssten ("Closed"-Prinzip verletzt). Dies könnte man umgehen, indem ein eigener Konstruktor für den neuen Formatter definiert und in diesen das zusätzliche Argument mit übergeben wird.

Des Weiteren ist der `CardFormatter` als `final` deklariert und besteht aus statischen Methoden, damit er beispielsweise so aufgerufen werden kann:

```java
CardFormatter.getFormatted(card);
```

Dies bedingt natürlich, dass der Formatter selbst nicht einfach erweitert werden kann, zum Beispiel wenn die Kopf- oder die Fußzeile anders ausgegeben werden sollten. Diese Einschränkung wurde jedoch hingenommen. Auch die bisher erwähnten Verletzungen des Prinzips sind nicht weiter relevant angesichts der konstanten Spielregeln, die sich selbst über mehrere Editionen hinweg nicht ändern, das heißt es werden mit großer Wahrscheinlichkeit keine neuen grundsätzlichen Kartentypen neben `ActionCard`, `MoneyCard` und `PointCard` hinzukommen. Selbst in zahlreichen Erweiterungen des Spiels sind diese Typen bislang konstant geblieben.


## Analyse Liskov-Substitution-Principle (LSP), Interface-Segreggation-Principle (ISP), Dependency-Inversion-Principle (DIP)
*Jeweils eine Klasse als positives und negatives Beispiel für entweder LSP oder ISP oder DIP);  jeweils UML der Klasse und Begründung, warum man hier das Prinzip erfüllt/nicht erfüllt wird*

*Anm.: es darf nur ein Prinzip ausgewählt werden; es darf NICHT z.B. ein positives Beispiel für LSP und ein negatives Beispiel für ISP genommen werden*




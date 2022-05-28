---
layout: default
title: 7 Refactoring
permalink: refactoring
---

# 7 Refactoring
{: .no_toc }

<details open markdown="block">
  <summary>
    Inhalt
  </summary>
  {: .text-delta }

- TOC
{:toc}

</details>


## Code Smells
*Jeweils ein Code-Beispiel zu zwei Code Smells aus der Vorlesung; jeweils Code-Beispiel und einen möglichen Lösungsweg bzw. den genommen Lösungsweg beschreiben (inkl. (Pseudo-)Code)]*

> "If it stinks, change it."

[![SonarCloud Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Splines_dominion-cli&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Splines_dominion-cli)

Mithilfe von [SonarCloud](https://sonarcloud.io/summary/new_code?id=Splines_dominion-cli) wird der Code kontinuierlich auf Code Smells untersucht und diese gemeldet. Zwei davon werden hier mit dem jeweiligen Lösungsweg zum Beheben vorgestellt.

**Code Smell 1: Long Method**

Die Methode `void makeMove()` des `GamePlayer`s (dieser hieß bei den angegebenen Commits nur `Player`), wurde mit der Zeit immer länger, da sie sowohl die Logik für Aktionsphase, Kaufphase und Aufräumphase umfasst. Dadurch wurde die Methode immer unhandlicher. Da Code mehr gelesen als geschrieben wird, sollte auf eine gute Lesbarkeit geachtet werden, die bei langen Methoden nicht gewährleistet ist. Außerdem ist der "Cognitive load" dann beim Lesen des Codes geringer, wenn die Methode kürzer ist und ihre zentrale Aufgabe besser kapselt. Eine gute Erklärung für dieses Prinzip findet sich [hier](https://refactoring.guru/smells/long-method).

Als Lösung wurde entsprechend die lange Methode in kleinere, deutlich aussagekräftigere Methoden aufgetrennt (Refactoring: Extract Method). Vor Commit [`712ff`](https://github.com/Splines/dominion-cli/commit/712ff9c0a9b24148b865dc312d4871c72659e18e) sah die Methode `makeMove()` [so aus](https://github.com/Splines/dominion-cli/blob/88051c3e84f2d51ace11b139d762b01a832edf93/2-dominion-application/src/main/java/me/splines/dominion/Game/Player.java#L58-L103):

```java
@Override
public void makeMove() {
    Move move = new Move();

    // 1st PHASE - Action phase
    // player MAY play as many action card
    while (move.getActionsCount() > 0 && playerDecision.checkWantToPlayActionCard()) {
        move.looseAction();
        // Choose action card to play
        ActionCard actionCard = playerDecision.chooseActionCard(hand);
        // Execute all instructions of action card
        List<Instruction> instructions = actionCard.getAction().getInstructions();
        instructions.forEach((i) -> i.execute(this, move, this.playerDecision, GameState.stock));
    }

    // 2nd PHASE - Buy phase
    // player MAY buy cards according to money available
    // TODO: check whether there are cards that can be bought for just 1 "money"
    List<Card> buyableCards = GameState.stock.getAvailableCardsWithMaxCosts(move.getMoney());
    if (!buyableCards.isEmpty()) {
        while (!buyableCards.isEmpty() && move.getMoney() >= 1 &&
                playerDecision.checkWantToBuy()) {
            List<Card> boughtCards = new ArrayList<>();
            playerDecision.informAboutBuyableCards(buyableCards);
            Card boughtCard = playerDecision.chooseCard(buyableCards);
            boughtCards.add(boughtCard);

            move.looseBuying();
            move.looseMoney(boughtCard.getCost());

            buyableCards = GameState.stock.getAvailableCardsWithMaxCosts(move.getMoney());
        }
    }

    // 3rd PHASE - "Clean up" phase
    // player MUST put all hand & table cards to the discard deck
    hand.forEach(h -> discardDeck.put(h));
    hand = new ArrayList<>();

    // player MUST draw 5 new cards for the next move
    for (int i = 0; i < 5; i++) {
        Card card = drawDeck.draw();
        hand.add(card);
    }
}
```

Nach dem Auftrennen hat sich [folgendes, deutlich aufgeräumteres Bild](https://github.com/Splines/dominion-cli/blob/712ff9c0a9b24148b865dc312d4871c72659e18e/2-dominion-application/src/main/java/me/splines/dominion/Game/Player.java#L58-L123) ergeben:

```java
@Override
public void makeMove() {
    Move move = new Move();
    doActionPhase(move);
    doBuyPhase(move);
    doCleanUpPhase(move);
}

/**
 * 1st PHASE - Action phase
 * Player MAY play as many action card as he/she wants.
 *
 * @param move
 */
private void doActionPhase(Move move) {
    while (move.getActionsCount() > 0 && playerDecision.checkWantToPlayActionCard()) {
        move.looseAction();
        // Choose action card to play
        ActionCard actionCard = playerDecision.chooseActionCard(hand);
        // Execute all instructions of action card
        List<Instruction> instructions = actionCard.getAction().getInstructions();
        instructions.forEach((i) -> i.execute(this, move, this.playerDecision, GameState.stock));
    }
}

/**
 * 2nd PHASE - Buy phase
 * Player MAY buy cards according to his/her available money.
 *
 * @param move
 */
private void doBuyPhase(Move move) {
    // TODO: check whether there are cards that can be bought for just 1 "money"
    List<Card> buyableCards = GameState.stock.getAvailableCardsWithMaxCosts(move.getMoney());
    if (!buyableCards.isEmpty()) {
        while (!buyableCards.isEmpty() && move.getMoney() >= 1 &&
                playerDecision.checkWantToBuy()) {
            List<Card> boughtCards = new ArrayList<>();
            playerDecision.informAboutBuyableCards(buyableCards);
            Card boughtCard = playerDecision.chooseCard(buyableCards);
            boughtCards.add(boughtCard);

            move.looseBuying();
            move.looseMoney(boughtCard.getCost());

            buyableCards = GameState.stock.getAvailableCardsWithMaxCosts(move.getMoney());
        }
    }
}

/**
 * 3rd PHASE - "Clean up" phase
 * Player MUST put all hand & table cards to the discard deck.
 * Player MUST draw 5 new cards for the next move.
 *
 * @param move
 */
private void doCleanUpPhase(Move move) {
    hand.forEach(h -> discardDeck.put(h));
    hand = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
        Card card = drawDeck.draw();
        hand.add(card);
    }
}
```

Gleichzeitig konnte dadurch der Code Smell "Code Comments" behoben werden. Die Kommentare haben einen guten "Auftrennpunkt" (Code Seams) für das Aufspalten in einzelne Methoden geboten und wurden nun in die Java Doc Strings aufgenommen. Zudem boten die Kommentare bereits Namen für die neuen Methoden wie `doCleanUpPhase`. Kommentare haben dementsprechend durchaus ihre Berechtigung, um für weniger Cognitive Load zu sorgen und besser stellen zu identifizieren, die aufgetrennt werden können.


**Code Smell 2: Large Class**

Das Interface `PlayerDecision` hat mit der Zeit immer mehr Methoden bekommen, die entsprechend alle in einer einzigen Klasse implementiert wurden. Vor Commit [`1cfef`](https://github.com/Splines/dominion-cli/commit/1cfefc5c016d81c9f8a5cf85fd7b28b27b93bb43) sah das Interface entsprechend [so aus](https://github.com/Splines/dominion-cli/blob/422a4e9bd11313e077a4c094513184e06c16ce41/3-dominion-domain/src/main/java/me/splines/dominion/game/PlayerDecision.java):

```java
public interface PlayerDecision {

    Card chooseCard(List<Card> cards);
    void informStartActionPhase();
    void informStartBuyingPhase();
    void informNoActionCardsPlayable();
    void informNoCardsBuyableWithMoney(int money);
    void announceResults(List<PlayerResult> results);
    void announceWinners(String... names);
    void informYourTurn(String name);
    List<Card> chooseCards(List<Card> cards);
    ActionCard chooseActionCard(List<ActionCard> cards);
    Optional<ActionCard> chooseOptionalActionCard(List<ActionCard> cards);
    MoneyCard chooseMoneyCard(List<MoneyCard> cards);
    List<Card> chooseCardsToBuy(List<Card> cards);
    Optional<Card> chooseOptionalCardToBuy(List<Card> cards);
    Optional<MoneyCard> chooseOptionalMoneyCard(List<MoneyCard> cards);

}
```

Die Klasse hat dabei zwei Verantwortlichkeiten vereint:

- den Spieler informieren über den Spielzustand bzw. über ihn selbst, z.B. seine Karten<br/>
(`void informNoCardsBuyableWithMoney(int money)`)
- die Spielerin Entscheidungen treffen lassen über ihren Spielzug<br/>
(`MoneyCard chooseMoneyCard(List<MoneyCard> cards)`)

Um den Code Smell zu beheben wurde das Interface aufgeteilt auf zwei neue Interfaces [`PlayerInformation`](https://github.com/Splines/dominion-cli/blob/1cfefc5c016d81c9f8a5cf85fd7b28b27b93bb43/3-dominion-domain/src/main/java/me/splines/dominion/interaction/PlayerInformation.java) sowie [`PlayerDecision`](https://github.com/Splines/dominion-cli/blob/1cfefc5c016d81c9f8a5cf85fd7b28b27b93bb43/3-dominion-domain/src/main/java/me/splines/dominion/interaction/PlayerDecision.java):

```java
public interface PlayerInformation {

    void startActionPhase();
    void startBuyingPhase();
    void noActionCardsPlayable();
    void noCardsBuyableWithMoney(int money);
    void yourTurn(String name);
    void results(List<PlayerResult> results);
    void winners(String... names);

}

public interface PlayerDecision {

    Card chooseCard(List<Card> cards);
    List<Card> chooseCards(List<Card> cards);
    ActionCard chooseActionCard(List<ActionCard> cards);
    Optional<ActionCard> chooseOptionalActionCard(List<ActionCard> cards);
    MoneyCard chooseMoneyCard(List<MoneyCard> cards);
    List<Card> chooseCardsToBuy(List<Card> cards);
    Optional<Card> chooseOptionalCardToBuy(List<Card> cards);
    Optional<MoneyCard> chooseOptionalMoneyCard(List<MoneyCard> cards);

}
```

{% capture aggregates_link %}{{ site.baseurl }}{% link docs/6-domain-driven-design.md %}#aggregates{% endcapture %}
Das bisherige Interface wurde von `PlayerDecision` zu [`PlayerInteraction`](https://github.com/Splines/dominion-cli/blob/1cfefc5c016d81c9f8a5cf85fd7b28b27b93bb43/3-dominion-domain/src/main/java/me/splines/dominion/interaction/PlayerInteraction.java) umbenannt, das wie bereits [hier bei den Aggregates beschrieben]({{aggregates_link}}) zwei Methoden `PlayerDecision decision()` und `PlayerInformation information()` anbietet, um das jeweilige Objekt vom richtigen Typ für die entsprechende Aufgabe zu erhalten:

```java
public final class PlayerInteraction {

    private PlayerDecision decision;
    private PlayerInformation information;

    public PlayerInteraction(PlayerDecision decision, PlayerInformation information) {
        this.decision = decision;
        this.information = information;
    }

    public PlayerDecision decision() {
        return decision;
    }

    public PlayerInformation information() {
        return information;
    }

}
```

Damit ergibt sich nun dieses UML-Diagramm:

![Aggregate PlayerInteraction](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/domain-driven-design/aggregate.puml&fmt=svg)


## 2 Refactorings
*Zwei unterschiedliche Refactorings aus der Vorlesung anwenden, begründen, sowie UML vorher/nachher liefern; jeweils auf die Commits verweisen*

**Refactoring 1: Extract method**

Der Code zum Ausgeben einer Karte auf die Konsole (inklusive Kartennummern) in der Klasse `GameCLI` wurde vor Commit [`0140db`](https://github.com/Splines/dominion-cli/commit/0140dbf30d5b3351385d7584bd8d59f664185945) [an mehreren Stellen verwendet](https://github.com/Splines/dominion-cli/blob/67eb63284781b30f7fb238b1687edebc42e5f0a3/0-dominion-plugin-database/src/main/java/GameCLI.java):

```java
@Override
public Card chooseCard(List<Card> cards) {
    ...
    for (int i = 0; i < cards.size(); i++) {
        Card card = cards.get(i);
        System.out.print((i + 1) + ": " + card.getName());
    }
    ...
}

private Optional<Card> chooseOptionalCard(List<Card> cards) {
    ...
    for (int i = 0; i < cards.size(); i++) {
        Card card = cards.get(i);
        System.out.print((i + 1) + ": " + card.getName());
    }
    ...
}

@Override
public List<Card> chooseCards(List<Card> cards) { {
    ...
    for (int i = 0; i < cards.size(); i++) {
        Card card = cards.get(i);
        System.out.print((i + 1) + ": " + card.getName());
    }
    ...
}
```

Auch dieser relativ kleine, aber dennoch duplizierte "Codeschnipsel" macht den Code schwerer wartbar, da er an mehreren Stellen verwendet wird. Möchte man nun die Ausgabe von mehreren Karten ändern, müsste man alle Codestellen finden, an denen die Ausgabe stattfindet und darf dabei keine Methode übersehen. Mittels "Extract Method" wurde diese Funktionalität entsprechend in eine eigene Method innerhalb derselben Klasse ausgelagert, sodass der Code nach dem Commit [so aussah](https://github.com/Splines/dominion-cli/blob/0140dbf30d5b3351385d7584bd8d59f664185945/0-dominion-plugin-database/src/main/java/GameCLI.java#L13-L18):

```java
private void printCardsWithNumbers(List<Card> cards) {
    for (int i = 0; i < cards.size(); i++) {
        Card card = cards.get(i);
        System.out.print((i + 1) + ": " + card.getName());
    }
}

@Override
public Card chooseCard(List<Card> cards) {
    ...
    printCardsWithNumbers(cards);
    ...
}

private Optional<Card> chooseOptionalCard(List<Card> cards) {
    ...
    printCardsWithNumbers(cards);
    ...
}

@Override
public List<Card> chooseCards(List<Card> cards) { {
    ...
    printCardsWithNumbers(cards);
    ...
}
```

Auf ein UML-Diagramm soll an diese Stelle verzichtet werden, da dieses lediglich zeigen würde, dass eine neue Methode `print CardsWithNumbers(List<Card> cards)` hinzugekommen ist.


**Refactoring 2: Replace Conditional with Polymorphism**

Das Verhalten zum Ausgeben eines Kartenrumpfes (Body) wurde vor Commit [`2098d`](https://github.com/Splines/dominion-cli/commit/2098d44a735d0108b84c9065a3dce520ae563025) mithilfe von If-Else-Statements gesteuert, wie [hier](https://github.com/Splines/dominion-cli/blob/90d2a19e37e16417aee801a0e0276a6c6517a22f/0-dominion-plugin-database/src/main/java/me/splines/dominion/card/CardPrinter.java#L27-L35) zu sehen:

```java
public final class CardPrinter {

    public static void printCard(Card card) {
        printHeader(card);

        if (card instanceof ActionCard) {
            ActionCardPrinter.printBody((ActionCard) card);
        } else if (card instanceof MoneyCard) {
            MoneyCardPrinter.printBody((MoneyCard) card);
        } else if (card instanceof PointCard) {
            PointCardPrinter.printBody((PointCard) card);
        } else {
            printDefaultCardBody();
        }

        printFooter(card);
    }

}
```

Problematisch ist hier, dass die einzelnen Printer teilweise dieselben Funktionalitäten benötigen. Außerdem müsste hier bei Erweiterung ein hässliches und klobiges if-else-Konstrukt angepasst werden, das darüber hinaus mit `instanceof` arbeitet und damit bereits auf einen besseren Ansatz mit Polymorphie hindeutet.

Zur Lösung des Problems wurde die abstrakte und generische Klasse [`CardBodyFormatter<T extends Card>`](https://github.com/Splines/dominion-cli/blob/2098d44a735d0108b84c9065a3dce520ae563025/0-dominion-plugin-database/src/main/java/me/splines/dominion/card/body/CardBodyFormatter.java) eingeführt, die unter anderem die Methode `public abstract String getBody(T card)` bereitstellt. Ein [`MoneyCardFormatter`](https://github.com/Splines/dominion-cli/blob/2098d44a735d0108b84c9065a3dce520ae563025/0-dominion-plugin-database/src/main/java/me/splines/dominion/card/body/MoneyCardFormatter.java) kann dann beispielsweise von dieser Klasse erben und die Methode `String getBody(MoneyCard card)` überschreiben. Entsprechendes ist beim [`PointCardFormatter`](https://github.com/Splines/dominion-cli/blob/2098d44a735d0108b84c9065a3dce520ae563025/0-dominion-plugin-database/src/main/java/me/splines/dominion/card/body/PointCardFormatter.java) und dem [`ActionCardFormatter`](https://github.com/Splines/dominion-cli/blob/2098d44a735d0108b84c9065a3dce520ae563025/0-dominion-plugin-database/src/main/java/me/splines/dominion/card/body/ActionCardFormatter.java) der Fall. Von großem Vorteil ist nun, dass beispielsweise der [`MoneyCardFormatter`](https://github.com/Splines/dominion-cli/blob/2098d44a735d0108b84c9065a3dce520ae563025/0-dominion-plugin-database/src/main/java/me/splines/dominion/card/body/MoneyCardFormatter.java) Methoden wie [`getEmptyLineWithBorder()`](https://github.com/Splines/dominion-cli/blob/2098d44a735d0108b84c9065a3dce520ae563025/0-dominion-plugin-database/src/main/java/me/splines/dominion/card/body/CardBodyFormatter.java#L20-L29) des `CardBodyFormatter`s verwenden kann. Methoden, die der `CardFormatter` implementiert sind also für alle Unterklassen wiederverwendbar.

Statt des If-Else-Konstrukts wird jetzt [eine HashMap verwendet](https://github.com/Splines/dominion-cli/blob/2098d44a735d0108b84c9065a3dce520ae563025/0-dominion-plugin-database/src/main/java/me/splines/dominion/card/CardFormatter.java#L22-L25):

```java
public final class CardFormatter {

    private static final Map<Class<? extends Card>, Function<Card, String>> formatterMap = Map.of(
            ActionCard.class, c -> new ActionCardFormatter().getBody((ActionCard) c),
            MoneyCard.class, c -> new MoneyCardFormatter().getBody((MoneyCard) c),
            PointCard.class, c -> new PointCardFormatter().getBody((PointCard) c));

    public static String getFormatted(Card card) {
        StringBuilder str = new StringBuilder();

        // Header
        str.append(getHeader(card));

        // Body
        // will fail if class not in map, which is intended
        Function<Card, String> formatFunction = formatterMap.get(card.getClass());
        str.append(formatFunction.apply(card));

        // Footer
        str.append(getFooter(card));

        return str.toString();
    }

}
```

Der `CardPrinter` wurde in diesem Commit übrigens zu `CardFormatter` umbenannt, da die Karten nicht mehr direkt auf die Konsole mittels `System.out.println(...)` ausgegeben werden sollen, sondern die Methoden entsprechende Strings zurückgeben. Diese können dann unabhängig von der Formatierung der Karten in anderen Klassen auf die Konsole gebracht werden.

Das UML-Diagramm sieht nach dem Refactoring wie untenstehend gezeigt aus. Vor dem Refactoring war nur die rote Klasse `CardPrinter` (sie heißt nun `CardFormatter`) vorhanden, jedoch ohne die `formatterMap` sowie mit leicht geänderten Methodensignaturen (z.B. wurde aus `void printBody(Card card)` nun `String getFormatted(Card card)`).

![Refactoring CardFormatter](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/open-closed-principle/open-closed-negative.puml&fmt=svg)

---
layout: default
title: 5 Unit Tests
permalink: unit-tests
---

# 5 Unit Tests
{: .no_toc }

<details open markdown="block">
  <summary>
    Inhalt
  </summary>
  {: .text-delta }

- TOC
{:toc}

</details>


## 10 Unit Tests
*Nennung von 10 Unit-Tests und Beschreibung, was getestet wird*

Insgesamt wurden bislang 88 Tests implementiert. Eine Auswahl von 13 Tests soll hier vorgestellt werden:

| Unit Test | Beschreibung |
|-----------|--------------|
| [CardTest::cardInvalidCost](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/test/java/me/splines/dominion/card/CardTest.java#L30-L38)      | Testet, ob tatsächlich eine `IllegalArgumentException` mit einer bestimmten Nachricht geworfen wird, wenn die Kartenkosten kleiner als 0 sind bei der Initialisierung. |
| [MoneyCardTest::moneyValue](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/test/java/me/splines/dominion/card/MoneyCardTest.java#L10-L16) | Testet, ob die `moneyCard.getMoney()` Methode das richtige Ergebnis nach Initialisierung einer `MoneyCard` liefert. |
| [MoneyCardTest::noNegativeMoney](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/test/java/me/splines/dominion/card/MoneyCardTest.java#L18-L26) | Testest, ob tatsächlich eine `IllegalArgumentException` mit einer bestimmten Nachricht geworfen wird, wenn das Geld auf einer Karte einen Wert kleiner als 0 hat bei der Initialisierung. |
| [DeckTest::putDrawSameCard](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/test/java/me/splines/dominion/game/DeckTest.java#L28-L40) | Testet, ob bei einem Deck das Legen einer Karte und anschließenden Nehmen vom Deck wieder die ursprüngliche Karte ergibt. |
| [DeckTest::drawFromEmptyDeck](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/test/java/me/splines/dominion/game/DeckTest.java#L72-L84) | Testet, ob eine `EmptyDeckException` geworfen wird, wenn von einem leeren Kartenstapel eine Karte gezogen wird. |
| [DeckTest::putAndDrawSeveralCards](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/test/java/me/splines/dominion/game/DeckTest.java#L42-L70) | Testet, ob das Hinzufügen und Ziehen von mehreren Mock-Karten in verschiedenen Konstellationen funktioniert. |
| [DeckTest::putMultipleCardsOnDeck](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/test/java/me/splines/dominion/game/DeckTest.java#L86-L97) | Testet, ob die Methode `deck.putCardSeveralTimes(card, count)` entsprechend ihrer Aufgabe korrekt funktioniert und tatsächlich dieselbe Karte mehrfach auf den Stapel gelegt wird. |
| [DeckTest::shuffleDeck](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/test/java/me/splines/dominion/game/DeckTest.java#L99-L138) | Testet, ob der verwendete Algorithmus zum Mischen gut genug ist und kein ganz schlechter Pseudo-Zufallsgenerator. |
| [DisposeMoneyCardTakeMoneyCardTest<br/>::mayChooseACardToDisposeNoMust](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/instruction/DisposeMoneyCardTakeMoneyCardTest.java#L80-L88) | Testet, ob tatsächlich nichts an den Handkarten geändert wird, wenn bei dieser Instruktion keine Karte zum Entsorgen ausgewählt wird. |
| [DisposeMoneyCardTakeMoneyCardTest<br/>::newCardOnHandOldCardDisposed](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/instruction/DisposeMoneyCardTakeMoneyCardTest.java#L102-L114) | Testet, ob der Mechanismus zum Auswählen einer Karte zum Entsorgen und dem Nachziehen einer Karte korrekt funktioniert. |
| [DisposeMoneyCardTakeMoneyCardTest<br/>::canOnlyTakeMoneyCards](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/instruction/DisposeMoneyCardTakeMoneyCardTest.java#L116-L127) | Testet, ob nur Geldkarten zum Entsorgen ausgewählt werden können. |
| [EarnMoneyInstructionTest::earn42Money](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/instruction/EarnMoneyInstructionTest.java#L56-L66) | Testet, ob der Mechanismus zum Verdienen von Geld in einer Anweisung (`Instruction`) korrekt funktioniert und sich der `MoveState` entsprechend ändert. |
| [EarnMoneyInstructionTest::earnNoNegativeMoney](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/instruction/EarnMoneyInstructionTest.java#L68-L76) | Testet, ob kein negatives Geld verdient werden kann und in diesem Fall eine `IllegalArgumentException` geworfen wird. |


## ATRIP: Automatic
*Begründung/Erläuterung, wie ‘Automatic’ realisiert wurde*

Damit Tests auch genutzt werden, sollten sie während der Entwicklung auf Knopfdruck ausführbar sein und automatisch ablaufen. Dazu wurde eine Extension für Visual Studio Code namens ["Test Runner for Java"](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-test) verwendet, die einwandfrei funktionierte. Tests können damit direkt mittels eines Klicks auf einen neben der Signatur befindlichen Button ausgeführt und sogar debugged werden. Alle Tests können zudem auf einmal im Test Runner ausgeführt werden. Die Screenshots geben einen kleinen Einblick:

![Java Test Runner in VSCode](https://raw.githubusercontent.com/splines/dominion-cli/docs/media/tests/java-test-runner.jpg)

![Java Test Runner in VSCode](https://raw.githubusercontent.com/splines/dominion-cli/docs/media/tests/java-test-runner-context-menu.jpg)

Darüber hinaus wurden die Tests in den [GitHub Actions Workflow](https://github.com/Splines/dominion-cli/blob/main/.github/workflows/build.yml#L47-L67) eingebunden. Zunächst werden die Tests auch bei `mvn verify` ausgeführt. Schlägt hier ein Test fehl, dann stoppt der gesamte Build und in GitHub wird ein entsprechender Hinweis angezeigt. Anschließend wird der von Jacoco generierte XML-Coverage-Report auf Codecov hochgeladen. Dieses Tool erlaubt es, die Test und insbesondere die Test Coverage detaillierter zu untersuchen. Die Code Coverage wird anschließend auch auf SonarQube (SonarCloud) hochgeladen.

[![Java CI with Maven](https://github.com/Splines/dominion-cli/actions/workflows/build.yml/badge.svg)](https://github.com/Splines/dominion-cli/actions/workflows/build.yml)

Am Beispiel des Commits [`29459`](https://github.com/Splines/dominion-cli/commit/294593c76cbf1dec7e16e653dbf6ee87b3fd16f4) sieht man hier das Modal, das sich bei Klick auf den Haken im GitHub UI öffnet:

![Java Test Runner in VSCode](https://raw.githubusercontent.com/splines/dominion-cli/docs/media/tests/github-workflow-test-checks.jpg)

Per Klick auf "Details" gelangt man dort zu folgenden Seiten:

- [Automatischer Codecov Report für diesen Commit](https://codecov.io/gh/Splines/dominion-cli/commit/294593c76cbf1dec7e16e653dbf6ee87b3fd16f4)
- [Automatischer SonarCloud Report für diesen Commit](https://github.com/Splines/dominion-cli/runs/6563227374)


## ATRIP: Thorough
*Jeweils ein positives und negatives Beispiel zu ‘Thorough’; jeweils Code-Beispiel, Analyse und Begründung, was professionell/nicht professionell ist*

Tests sollten vollständig sein und alles Notwendige überprüfen. Was notwendig ist liegt dabei im Ermessen der Entwicklerin.

**Positiv-Beispiel**

Für die [`DisposeMoneyCardTakeMoneyCardToHandInstruction`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/instruction/DisposeMoneyCardTakeMoneyCardToHandInstruction.java) wurde die `execute()`-Methoden umfangreich im [`DisposeMoneyCardTakeMoneyCardTest`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/instruction/DisposeMoneyCardTakeMoneyCardTest.java) getestet.

Da der Source Code für die Tests dieser Instruktion 156 Zeilen lang ist, werden hier nur die hoffentlich selbsterklärenden Testnamen aufgeführt:

- mayChooseACardToDisposeNoMust()
- noMoneyCardsOnHand()
- newCardOnHandOldCardDisposed()
- canOnlyTakeMoneyCards()
- canOnlyTakeMoneyCardsThatCostMaxThreeMore()
- noMoneyCardsOnStock()

"Professionell" ist, dass durch diese Tests alle 20 Codezeilen von `execute()` getestet werden konnten. Die assert-Statements wurden sorgfältig angelegt und testen beispielsweise, ob die neuen Handkarten des Spielers nach Ausführung der Anweisung auch tatsächlich den erwarteten Karten entsprechen.

```java
@Test
void newCardOnHandOldCardDisposed() {
    when(decision.chooseOptionalMoneyCard(any())).thenReturn(
            Optional.of(CardPool.silverCard)); // dispose this card
    when(decision.chooseMoneyCard(anyList())).thenReturn(
            CardPool.goldCard); // take this card

    instruction.execute(player, new MoveState(), new GameStock());

    assertThat(player.getHand())
            .doesNotContain(CardPool.silverCard)
            .contains(CardPool.goldCard);
}
```

Wie hier zu erkennen ist wurden in manchen Test-Methoden sogar die Arrange/Act/Assert-Phasen visuell durch eine leere Zeile getrennt. Dass ausführlich getestet wurde, zeigt beispielsweise auch der [`PlayerMoveActionPhaseTest`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/game/PlayerMoveActionPhaseTest.java). Insbesondere wurden hier im Zusammenspiel mit Mock-Objekten ausführliche Tests angestellt (siehe `verify()` statements).

```java
@Test
void playTwoActionCards() {
    Instruction instr = mock(Instruction.class);
    Instruction instr2 = spy(earnActionInstruction);
    ActionCard playCard = new ActionCard("action card 1",
            CardType.ACTION, 1, new Action(instr, instr2));

    Instruction instr3 = mock(Instruction.class);
    Instruction instr4 = mock(Instruction.class);
    ActionCard otherPlayCard = new ActionCard("action card 2",
            CardType.ACTION, 2, new Action(instr3, instr4));

    when(player.getActionCardsOnHand())
            .thenReturn(List.of(playCard, otherPlayCard))
            .thenReturn(List.of(otherPlayCard)); // stub consecutive call
    when(decision.chooseOptionalActionCard(anyList()))
            .thenReturn(Optional.of(playCard))
            .thenReturn(Optional.of(otherPlayCard));
    PlayerMove move = new PlayerMove(player, new GameStock());
    move.doActionPhase();

    verify(player, times(2)).getActionCardsOnHand();
    verify(decision, times(2)).chooseOptionalActionCard(any());
    verify(information, never()).noActionCardsPlayable();
    verify(instr, only()).execute(any(), any(), any());
    verify(instr2, only()).execute(any(), any(), any());
    verify(instr3, only()).execute(any(), any(), any());
    verify(instr4, only()).execute(any(), any(), any());
}
```


**Negativ-Beispiel**

Ein Negativ-Beispiel für vollständiges Testen liefert der [`GameTest`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/game/GameTest.java). Da die Klasse [`Game`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/Game.java) hauptsächlich aus privaten Methoden besteht, konnte hier leider nur überprüft werden, ob sich ein Objekt ohne Fehler erstellen lässt:

```java
@Test
void gameInitializationWithoutError() {
    List<String> playerNames = List.of("Player1", "Player2");
    PlayerInteraction interaction = new PlayerInteraction(decision, information);

    new Game(interaction, playerNames);
    verifyNoMoreInteractions(decision);
    verifyNoMoreInteractions(information);
}
```

Das Design der Klasse `Game` könnte sicherlich angepasst werden, sodass beispielsweise private Methoden in eigene Klassen ausgelagert werden und dann besser testbar sind. Dies hätte jedoch die kleine `Game`-Klasse unnötig "entzerrt" und für ein schlechteres Verständnis des Codes geführt. Mithilfe von [Powermock](https://github.com/powermock/powermock) hätten auch private Methoden getestet werden können; leider ist die Library aber [noch nicht für JUnit5 verfügbar](https://github.com/powermock/powermock/issues/929).


## ATRIP: Professional
*Jeweils ein positives und negatives Beispiel zu ‘Professional’; jeweils Code-Beispiel, Analyse und Begründung, was professionell/nicht professionell ist*

Tests sollten den gleichen Qualitätsstandards wie "Produktivcode" unterliegen, da auch hier Fehler teuer sind.


**Positiv-Beispiel**

Als Positiv-Beispiel soll erneut der [`DisposeMoneyCardTakeMoneyCardTest`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/instruction/DisposeMoneyCardTakeMoneyCardTest.java) dienen. Insbesondere zeugen [diese Zeilen](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/instruction/DisposeMoneyCardTakeMoneyCardTest.java#L51-L78) von "professionellem" Code:

```java
@BeforeEach
void prepare() {
    instruction = new DisposeMoneyCardTakeMoneyCardToHandInstruction();

    drawDeck = new Deck();
    drawDeck.put(CardPool.provinceCard);
    drawDeck.put(CardPool.duchyCard); // ↑ other cards on bottom of draw deck
    drawDeck.put(CardPool.goldCard); // 5 cards until here
    drawDeck.put(CardPool.silverCard);
    drawDeck.put(CardPool.copperCard);
    drawDeck.put(CardPool.curseCard);
    drawDeck.put(CardPool.estateCard);

    MockitoAnnotations.openMocks(this);

    PlayerInteraction interaction = new PlayerInteraction(decision, information);
    player = new GamePlayer("awesome player", interaction, drawDeck, new GameStock());
}

private void expectNoChangesToHand(Player player) {
    assertThat(player.getHand()).containsExactlyInAnyOrderElementsOf(
            List.of( // no changes to hand
                    CardPool.estateCard,
                    CardPool.curseCard,
                    CardPool.copperCard,
                    CardPool.silverCard,
                    CardPool.goldCard));
}
```

Hier fällt zunächst positiv auf, dass die von JUnit bereitgestellte Annotation `@BeforeEach` Verwendung bei der Methode `void prepare()` findet. Hier werden Vorbereitungen getroffen, die ansonsten in jedem Test eigenständig notwendig gewesen wären und zu viel dupliziertem Code geführt hätten. Beispielsweise wird hier das `drawDeck` (Nachziehstapel) des Spielers mit Karten populiert sowie die Mock-Objekte initialisiert. Ferner fällt positiv auf, dass eine sehr häufig benutzte Assertion in eine eigene Methode `private void expectNoChangesToHand(Player player)` ausgelagert wurde, um erneut Code-Duplikationen zu vermeiden.


**Negativ-Beispiel**

Im Test [`buyOneCard()`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/game/PlayerMoveBuyingPhaseTest.java#L50-L83) in [`PlayerMoveBuyingPhaseTest`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/game/PlayerMoveBuyingPhaseTest.java) sind diese Zeilen anzutreffen:

```java
Deck drawDeck = new Deck();
drawDeck.put(CardPool.copperCard);
drawDeck.put(CardPool.copperCard);
drawDeck.put(CardPool.copperCard);
drawDeck.put(CardPool.copperCard); // ↑ 4 additional cards for next round
drawDeck.put(CardPool.duchyCard);
drawDeck.put(CardPool.copperCard);
drawDeck.put(CardPool.copperCard);
drawDeck.put(CardPool.copperCard);
drawDeck.put(CardPool.copperCard);
```

Hier hätte man gleiche Statements durch eine For-Schleife vereinfachen können und dadurch Line Duplications vermeiden können. Dazu habe ich mich hier jedoch bewusst dagegen entschieden, um deutlicher zu machen, welche Karten nun auf dem Nachziehstapel liegen.

Darüber hinaus hätte man den Test [`playTwoActionCards()`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/game/PlayerMoveActionPhaseTest.java#L113-L141) sicherlich etwas vereinfachen oder in einzelne, kleinere Methoden auslagern können, da bei dieser Größe teilweise schon die Übersichtlichkeit verloren geht.


## Code Coverage
*Code Coverage im Projekt analysieren und begründen*

Für die Analyse der Code Coverage wurde [JaCoCo (Java Code Coverage Library)](https://www.eclemma.org/jacoco/) eingesetzt, indem ein eigenes Modul [`dominion-report`](https://github.com/Splines/dominion-cli/tree/main/dominion-report) erstellt und darin das Ziel [`report-aggregate`](https://github.com/Splines/dominion-cli/blob/main/dominion-report/pom.xml#L66) ausgeführt wurde. Der auf diese Weise entstandene Report wurde — wie bereits bei ["ATRIP: Automatic"](#atrip-automatic) erwähnt — mittels [GitHub Actions](https://github.com/features/actions) zu [Codecov](https://app.codecov.io/gh/Splines/dominion-cli) gepushed. Dadurch können wir mit jedem Commit nachvollziehen, wie viel Prozent unseres gesamten Codes sowie des Diffs (neuer Code) abgedeckt wird.

[![codecov](https://codecov.io/gh/Splines/dominion-cli/branch/main/graph/badge.svg?token=kG7xahROjx)](https://codecov.io/gh/Splines/dominion-cli)


**Grid-Graph**

Anhand des Grid-Graphen ist bereits ersichtlich, dass große Teile des Codes von Tests abgedeckt sind. Der große rote Block steht für die schwer zu testende Klasse [`Game`](https://app.codecov.io/gh/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/Game.java). Andere orangene oder rote Blöcke bezeichnen häufig Value Objects, die nur aus einfachen Gettern/Settern bestehen und daher keiner Tests bedürfen.

[![Codecov Grid Coverage Graph](https://codecov.io/gh/Splines/dominion-cli/branch/main/graphs/tree.svg)](https://app.codecov.io/gh/Splines/dominion-cli)


**Code-Coverage pro Modul**

Explizit aus der Code-Coverage herausgenommen wurde (auch im obigen Grid-Graph) die äußerste Schicht "Plugin-CLI". Diese beinhaltet den kurzlebigsten Code, sodass es im Rahmen dieses Projekts keinen Sinn ergab, die Schicht mit zu testen.

| Modul | Coverage |
|-------|----------|
| ~~0-dominion-plugin-cli~~ | ~~0% (von der<br/>Gesamtcoverage ausgeschlossen)~~ |
| ~~1-dominion-adapters~~ | ~~kein Code vorhanden~~ |
| 2-dominion-application | ca. 87% |
| 3-dominion-domain | ca. 93% |
| Total | ca. 89% |

Besonders in der innersten Schicht, dem Domänen-Code, wurde mit 93% eine hohe Testabdeckung erreicht, die wichtig ist, um Kernfunktionalitäten und grundlegende Bausteine des Spiels wie Karten und Kartenstapel umfassend zu testen. In der Applikationsschicht wurden beispielsweise die Anweisungen (`Instruction`s) auf ihre Korrektheit getestet oder auch die verschiedenen Phasen eines Zuges (`PlayerMove`: Action Phase, Buying Phase und Clean Up Phase).

## Fakes und Mocks
*Analyse und Begründung des Einsatzes von 2 Fake/Mock-Objekten; zusätzlich jeweils UML Diagramm der Klasse*

[Mockito](https://site.mockito.org/) ist eine Mock-Library für Java, mit der  Stellvertreter für Objekte definiert werden können. Dadurch ist es möglich, bei Unit-Test tatsächlich nur die "Unit" zu testen ohne Abhängigkeiten zu anderen Komponenten. Mock-Objekte ersetzen komplexe Objekte durch gleichartige Objekte mit minimaler Funktion, die jedoch für die Tests ausreichend sind.


**1. Mock**

![1. Mock PlayerDecision](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/mock/1-mock.puml&fmt=svg)

Das Interface `PlayerDecision` wurde in den Unit Tests sehr häufig "gemockt", so zum Beispiel auch im [`PlayerMoveActionPhaseTest`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/game/PlayerMoveActionPhaseTest.java#L37-L38).

```java
@Test
void cantPlaySameActionCardMultipleTimes() {
    Instruction instr = mock(Instruction.class);
    Instruction instr2 = spy(earnActionInstruction);
    ActionCard playCard = new ActionCard("a",
            CardType.ACTION, 1, new Action(instr, instr2));

    Deck drawDeck = new Deck();
    drawDeck.put(playCard);
    drawDeck.put(CardPool.copperCard);
    drawDeck.put(CardPool.copperCard);
    drawDeck.put(CardPool.copperCard);
    drawDeck.put(CardPool.copperCard);
    PlayerInteraction interaction = new PlayerInteraction(decision, information);
    GamePlayer ourPlayer = spy(new GamePlayer("our player", interaction, drawDeck, new GameStock()));

    when(decision.chooseOptionalActionCard(anyList()))
            .thenReturn(Optional.of(playCard));
    PlayerMove move = new PlayerMove(ourPlayer, new GameStock());
    move.doActionPhase();

    verify(ourPlayer, times(2)).getActionCardsOnHand();
    verify(decision, times(1))
            .chooseOptionalActionCard(actionCardListCaptor.capture());
    assertThat(actionCardListCaptor.getValue()).isEqualTo(List.of(playCard));
}
```

Im Test [`cantPlaySameActionCardMultipleTimes()`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/game/PlayerMoveActionPhaseTest.java#L143-L168) wird dann der Return-Wert bei Aufruf der Funktion `chooseOptionalActionCard(...)` festgelegt:

```java
when(decision.chooseOptionalActionCard(anyList()))
        .thenReturn(Optional.of(playCard));
```

Anschließend wird überprüft, ob diese Methode genau einmal aufgerufen wurde. Dabei wird gleichzeitig mithilfe eines [Argument Captors](https://site.mockito.org/javadoc/current/org/mockito/ArgumentCaptor.html) der an die Funktion übergebene Parameter abgefangen und einem Test unterzogen:

```java
verify(ourPlayer, times(2)).getActionCardsOnHand();
verify(decision, times(1))
        .chooseOptionalActionCard(actionCardListCaptor.capture());
assertThat(actionCardListCaptor.getValue()).isEqualTo(List.of(playCard));
```

Der Vorteil von "Mocking" mittels Mockito wird hier sehr deutlich. Andernfalls hätten wir eine `DummyPlayerDecision` manuell implementieren, das heißt alle Methoden des Interface `PlayerDecision` überschreiben müssen. Mockito nimmt uns diesen Schritt ab, sodass wir mit `when(functionCall).thenReturn(...)` unkompliziert nur die eine Methode explizit näher spezifizieren, die wir auch tatsächlich im Test benötigen.


**2. Mock**

![2. Mock Player](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/mock/2-mock.puml&fmt=svg)

In derselben Klasse [`PlayerMoveActionPhaseTest`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/game/PlayerMoveActionPhaseTest.java#L43-L44) wurde auch der `Player` gemockt, um dann beispielsweise im Test [`playTwoActionCards()`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/test/java/me/splines/dominion/game/PlayerMoveActionPhaseTest.java#L113-L141) Verwendung zu finden:

```java
@Test
void playTwoActionCards() {
    Instruction instr = mock(Instruction.class);
    Instruction instr2 = spy(earnActionInstruction);
    ActionCard playCard = new ActionCard("action card 1",
            CardType.ACTION, 1, new Action(instr, instr2));

    Instruction instr3 = mock(Instruction.class);
    Instruction instr4 = mock(Instruction.class);
    ActionCard otherPlayCard = new ActionCard("action card 2",
            CardType.ACTION, 2, new Action(instr3, instr4));

    when(player.getActionCardsOnHand())
            .thenReturn(List.of(playCard, otherPlayCard))
            .thenReturn(List.of(otherPlayCard)); // stub consecutive call
    when(decision.chooseOptionalActionCard(anyList()))
            .thenReturn(Optional.of(playCard))
            .thenReturn(Optional.of(otherPlayCard));
    PlayerMove move = new PlayerMove(player, new GameStock());
    move.doActionPhase();

    verify(player, times(2)).getActionCardsOnHand();
    verify(decision, times(2)).chooseOptionalActionCard(any());
    verify(information, never()).noActionCardsPlayable();
    verify(instr, only()).execute(any(), any(), any());
    verify(instr2, only()).execute(any(), any(), any());
    verify(instr3, only()).execute(any(), any(), any());
    verify(instr4, only()).execute(any(), any(), any());
}
```

Hier wurde der Aufruf von `player.getActionCardsOnHand()` gemockt, und zwar so, dass zwei nacheinander stattfindende Methodenaufrufe unterschiedliche Werte (hier Listen) zurückgeben.

```java
when(player.getActionCardsOnHand())
        .thenReturn(List.of(playCard, otherPlayCard))
        .thenReturn(List.of(otherPlayCard)); // stub consecutive call
```

Das entsprechende Verhalten wird dann mit `verify()` unter Verwendung des Verification Modes `times()` überprüft:

```java
verify(player, times(2)).getActionCardsOnHand();
```

Der `Player` wurde hier gemockt, damit wir überprüfen konnten, ob eine seiner Methoden (`getActionCardsOnHand`) tatsächlich so oft aufgerufen wurde wie erwartet (hier: zwei mal). Das Mock-Objekt hat (wie vorhin auch) nur das aktuell notwendige Verhalten des Interfaces / der abstrakten Klasse (hier: `Player`) abgebildet. Mockito hat uns hierbei geholfen: wir mussten lediglich die Methode `when(...)` aufrufen.
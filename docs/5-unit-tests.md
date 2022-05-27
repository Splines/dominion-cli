---
layout: default
title: V Unit Tests
permalink: unit-tests
---

# V Unit Tests
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

Am Beispiel des Commits [`29459`](https://github.com/Splines/dominion-cli/commit/294593c76cbf1dec7e16e653dbf6ee87b3fd16f4) sieht man hier das Modal, das sich bei Klick auf den Haken im GitHub UI öffnet:

![Java Test Runner in VSCode](https://raw.githubusercontent.com/splines/dominion-cli/docs/media/tests/github-workflow-test-checks.jpg)

Per Klick auf "Details" gelangt man dort zu folgenden Seiten:

- [Automatischer Codecov Report für diesen Commit](https://codecov.io/gh/Splines/dominion-cli/commit/294593c76cbf1dec7e16e653dbf6ee87b3fd16f4)
- [Automatischer SonarCloud Report für diesen Commit](https://github.com/Splines/dominion-cli/runs/6563227374)




## ATRIP: Thorough
*Jeweils ein positives und negatives Beispiel zu ‘Thorough’; jeweils Code-Beispiel, Analyse und Begründung, was professionell/nicht professionell ist*


## ATRIP: Professional
*Jeweils ein positives und negatives Beispiel zu ‘Professional’; jeweils Code-Beispiel, Analyse und Begründung, was professionell/nicht professionell ist*


## Code Coverage
*Code Coverage im Projekt analysieren und begründen*


## Fakes und Mocks
*Analyse und Begründung des Einsatzes von 2 Fake/Mock-Objekten; zusätzlich jeweils UML Diagramm der Klasse*

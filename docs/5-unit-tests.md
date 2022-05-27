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

Insgesamt wurden bislang 91 Tests implementiert. Eine Auswahl von 13 Tests soll hier vorgestellt werden:

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


## ATRIP: Thorough
*Jeweils ein positives und negatives Beispiel zu ‘Thorough’; jeweils Code-Beispiel, Analyse und Begründung, was professionell/nicht professionell ist*


## ATRIP: Professional
*Jeweils ein positives und negatives Beispiel zu ‘Professional’; jeweils Code-Beispiel, Analyse und Begründung, was professionell/nicht professionell ist*


## Code Coverage
*Code Coverage im Projekt analysieren und begründen*


## Fakes und Mocks
*Analyse und Begründung des Einsatzes von 2 Fake/Mock-Objekten; zusätzlich jeweils UML Diagramm der Klasse*

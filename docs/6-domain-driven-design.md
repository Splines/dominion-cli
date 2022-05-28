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

Hier fünft Beispiele:

| Bezeichnung | Bedeutung | Begründung |
|-------------|-----------|------------|
| *Ablegen*   | "Karten werden immer von der Hand abgelegt, sofern nicht anders auf der Karte angegeben. Abgelegte Karten kommen offen auf den eigenen Ablagestapel. [...] Lediglich die oberste Karte des Ablagestapels muss immer sichtbar sein." | Im Code wurde *Ablegen* als [`void discard(Card card)`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/GamePlayer.java#L93-L97) modelliert. Die Bezeichnung wird durchgängig durch die gesamte Spielanleitung hinweg verwendet und ist Bestandteil des Grundvokabulars, um das Spiel verstehen zu können. Daher gehört es zur Ubiquitous Language. |
| *Entsorgen* | "Entsorgt der Spieler Karten, legt er sie offen auf den Müllstapel bzw. auf das Müll-Tableau, falls noch keine Karte entsorgt wurde. Entsorgte Karten können nicht wieder gekauft oder genommen werden." | Im Code wurde *Entsorgen* als [`void dispose(Card card)`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/GamePlayer.java#L99-L102) modelliert. Die Bezeichnung wird durchgängig durch die gesamte Spielanleitung hinweg verwendet und ist Bestandteil des Grundvokabulars, um das Spiel verstehen zu können. Daher gehört es zur Ubiquitous Language. |
| *Nehmen* | "Karten werden aus dem Vorrat genommen und ungenutzt auf den Ablagestapel gelegt – dies ist kein Kauf. Karten, die ohne Angabe der Herkunft genommen werden müssen oder dürfen, werden vom Vorrat genommen." | Im Code wurde *Nehmen* als [`void take(Card card)`](https://github.com/Splines/dominion-cli/blob/main/2-dominion-application/src/main/java/me/splines/dominion/game/GamePlayer.java#L110-L113) modelliert. Die Bezeichnung wird durchgängig durch die gesamte Spielanleitung hinweg verwendet und ist Bestandteil des Grundvokabulars, um das Spiel verstehen zu können. Daher gehört es zur Ubiquitous Language. |
| *+ X Aktionen* | "Der Spieler **darf** X weitere Aktionskarten ausspielen. Zunächst muss er jedoch alle Anweisungen der aktuellen Aktionskarte (soweit möglich) erfüllen. Darf er weitere Aktionen ausführen (z.B. durch das Ausspielen von mehreren Aktionskarten), sollte die Anzahl der Aktionen der Übersichtlichkeit halber laut mitgezählt werden." | Die Bezeichnung wird durchgängig durch die gesamte Spielanleitung hinweg verwendet, insbesondere ist sie  auf vielen Aktionskarten aufgedruckt. In der Anleitung wird bei solchen Anweisungen klar darauf geachtet wird, "müssen" und "dürfen" korrekt zu verwenden (diese Bezeichnungen sind sogar fett gedruckt). Da die Bezeichnung Bestandteil des Grundvokabular ist, um erfolgreich Aktionskarten auslegen und spielen zu können, gehört *+ X Aktionen* zur Ubiquitous Language. |
| *Deck* | Ein *Deck* ist ein Kartenstapel im Spiel, auf den Karten abgelegt und von dem Karten wieder gezogen werden können. | Nicht nur in Dominion ist die Idee des Kartenstapels bekannt. Im Englischen habe ich mich, unter anderem nach [diesem Post](https://boardgamegeek.com/thread/1237320/card-game-terminology-deck-stack-pile), entschieden, den Kartenstapel als *Deck* zu bezeichnen. Im Code ist das Kartendeck in der Domäne als [`Deck`](https://github.com/Splines/dominion-cli/blob/main/3-dominion-domain/src/main/java/me/splines/dominion/game/Deck.java) realisiert. In der Spielanleitung ist dieses Wort allgegenwärtig, zum Beispiel auch bei den Begriffen "Nachziehstapel" oder "Ablagestapel" (siehe Seite 5 unten). Der Begriff ist Bestandteil des Grundvokabulars, um das Spiel verstehen zu können und ist daher Teil der Ubiquitous Language. |


## Entities
*UML, Beschreibung und Begründung des Einsatzes einer Entity; falls keine Entity vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist*


## Value Objects
*UML, Beschreibung und Begründung des Einsatzes eines Value Objects; falls kein Value Object vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist*

## Repositories
*UML, Beschreibung und Begründung des Einsatzes eines Repositories; falls kein Repository vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist*


## Aggregates
*UML, Beschreibung und Begründung des Einsatzes eines Aggregates; falls kein Aggregate vorhanden: ausführliche Begründung, warum es keines geben kann/hier nicht sinnvoll ist*
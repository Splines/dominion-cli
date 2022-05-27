---
layout: default
title: 2 Clean Architecture
permalink: clean-architecture
---

# 2 Clean Architecture
{: .no_toc }

<details open markdown="block">
  <summary>
    Inhalt
  </summary>
  {: .text-delta }

- TOC
{:toc}

</details>


## Was ist Clean Architecture?
*Allgemeine Beschreibung der Clean Architecture in eigenen Worten*

**Hintergrund**

Seit Anbeginn der Menschheit wurden Technologien eingesetzt, um Ziele effektiv und effizient zu erreichen. Waffen zum Jagen von Tieren wurden aus Stein und Holz gewonnen und diese immer weiter verbessert. Doch die Entwicklungsfortschritte in den letzten Jahrhundert sind zeitlich gesehen enorm und verdichten sich immer weiter. Nicht zuletzt durch die Synchronisation der Uhren gegen Ende des 19. Jahrhunderts, die [Geschwindigkeitsrevolution der Gesellschaft](https://www.grin.com/document/380316) durch neue Transportmittel wie der Eisenbahn sowie die [Erfindung des Internets 1969](https://open.hpi.de/courses/internetworking2019?locale=de) kommt immer mehr ein [Gefühl der Beschleunigung der modernen Geschichte](https://journals.openedition.org/trivium/4034) auf.

Besonders in der Informatik sind die Entwicklungen immens und als Entwickler:in ist es teils sehr schwierig, mit den neuen Technologien mitzuhalten und vom Zug des Fortschritts nicht abgehängt zu werden. Dies mag zum Teil daran liegen, dass die Kosten zum Erstellen von Prototypen, aber auch zum Ausliefern eines finalen Produkts extrem gering sind, wenn man die Informatik mit anderen Disziplinen vergleicht. Zwar müssen Entwickler:innen und Serverinfrastruktur bezahlt werden — doch im Vergleich zum Bau eines Hauses, bei dem Hunderttausende Euro allein für Baukosten aufkommen können, scheinen die Kosten für eine Anwendung in der Informatik vergleichsweise gering.

Um beim Hausbau zu bleiben: als Informatiker:innen genießen wir einen weiteren, bedeutenden Vorteil. Wir können kostengünstig Prototypen entwickeln, die eventuell sogar zu einem ausgereiften Produkt weiterentwickelt werden. Als Architekt sind sicherlich Prototypen auch hilfreich, doch diese werden im Modellformat à la DIN-A4-Blatt angefertigt und nicht anhand eines "echten" Hauses mit Rohstoffen, die auch beim richtigen Hausbau verwendet werden.

**Privilegien und Herausforderungen im IT-Sektor**

Ein weiteres Privileg der Informatiker:innen ist die Anpassbarkeit unserer Software. Beim Hausbau muss bereits beim Entwurf einer Wohnung entschieden werden, an welchen Stellen Steckdosen in der Wand angebracht werden sollen, damit die Stromleitungen entsprechend geplant werden können. Im Nachhinein wäre es nur noch mit (größerem) Aufwand möglich, die Position der Steckdosen anzupassen.

In der Software-Branche hingegen können wir — bei geeigneter Architektur — unser Produkt in der Art gestalten, dass es auch noch nachträglich einfach anpassbar und erweiterbar ist. In Hinblick auf den vorher angeführten, rasenden Technologiewandel ist dies enorm wichtig, da Technologien ständig aussterben und neue entstehen. In den seltensten Fällen basiert heutzutage ein Programm nur noch auf einer Technologie. Stattdessen treffen wir eine Entscheidung und wählen aus einer Myriade von Optionen die für uns am besten geeignetste heraus — zumindest die geeignetste nach unserem Empfinden zu einem bestimmten Zeitpunkt. Ein Jahr später hätte diese Wahl ganz anders ausfallen können. Im schlimmsten Fall ist bis dahin eine Technologie vom Aussterben bedroht, auf die wir vor einem Jahr gesetzt haben. Wenn unsere Architektur nicht gut genug durchdacht ist, könnte dies das Ende unserer gesamten Lösung bedeuten, da der Aufwand zu groß ist, die bisherige Codebasis zu migrieren.


**Was ist nun Clean Architecture?**

Clean Architecture bezeichnet ein Architektur-Prinzip, das eine nachhaltige Architektur von Programmcode ermöglichen soll. Nachhaltig in diesem Sinne bedeutet, dass nachträglich Entscheidungen von Technologiewahlen revidiert werden können, ohne eine Unmenge von Änderungen im Code nach sich zu ziehen. Dazu wird der Code in Schichten (Layers) aufgeteilt, die jeweils für sich stehen und eine ganz bestimmte Aufgabe übernehmen ("Separation of Concerns").

Insbesondere beim Zwiebelmodell (Onion Architecture) wird bildlich klar, was Clean Architecture auszeichnet. Im Kern ist der zentrale, langlebige Code angesiedelt, der sich möglichst wenig ändert und unabhängig von den äußeren Schichten kompiliert werden kann. Dies wird nur dadurch erzielt, dass der Domänen-Code in der innersten Schicht keine Abhängigkeit zu einer äußeren Schicht hat. Stattdessen werden im Kern häufig Interfaces definiert, die dann von äußeren Schichten implementiert werden. Äußere Schichten dürfen also Abhängigkeiten zu inneren Schichten haben, jedoch dürfen innere Schichten nicht von äußeren abhängen. Dies beschreibt die Dependency Rule: Abhängigkeitspfeile zeigen immer *von außen nach innen*.

Dieser Aufbau hat einen großen Vorteil: der Kern ist — abgesehen von der verwendeten Programmiersprache — komplett unabhängig von jeglichen Technologien, das heißt zum Beispiel unabhängig von einem konkreten UI oder einer konkreten Datenbank. Dies impliziert, dass äußere Schichten einfach ausgetauscht werden können, ohne die Implementierung der Domäne bzw. die Business-Logik zu gefährden. Die inneren Schichten sind im besten Fall komplett von der Außenwelt abgeschirmt. Äußere Schichten beeinflussen innere Schichten in keinster Weise.

Ein weiterer Vorteil von Clean Architecture ist die Unabhängigkeit von "schweren" Frameworks, die oftmals eine bestimmte Arbeitsweise verlangen und uns limitieren. Mit Clean Architecture programmieren wir nicht mehr *für* die Frameworks, sondern können sie gezielt eher in der Funktionsweise von Libraries verwenden. Darüber hinaus ist durch "Separation of Concerns" und die "Dependency Rule" eine einfache Testbarkeit unseres Codes gewährleistet: Business-Regeln stehen alleine für sich und können also auch eigenständig getestet werden, unabhängig von UI, Datenbank oder sonstigen externen Komponenten. Äußere Schichten können sich zwar auf innere beziehen, diese Abhängigkeiten können aber einfach "gemockt" werden, sodass auch hier das Testen einfacher fällt (im Vergleich zu einer Architektur, die den Code nicht in Schichten aufteilt und die "Dependency Rule" nicht einhält).

**Clean Architecture - TLDR**

Clean-Architecture bezeichnet ein Architektur-Prinzip, bei dem Code ähnlich wie bei einer Zwiebel in Schichten aufgeteilt wird. Technologie-unabhängiger Code wird im Kern angesiedelt und weist keinerlei Abhängigkeiten zu äußeren Schichten auf ("Dependency Rule"). Durch diese Trennung können äußere Schichten mit kurzlebigerem (da technologie-abhängigerem) Programmcode ausgewechselt werden, ohne die Business Logik zu gefährden. Gerade im schnelllebigen IT-Sektor ist dies von großer Bedeutung, um beim Absterben von Technologien mit moderatem Aufwand auf andere umzusteigen zu können. Auf die Zwiebel bezogen: wenn äußere Schichten faulen, kann der Kern noch wohlauf sein.

Eine schöne Übersicht zu Clean Architecture von Robert C. Martin ist [hier](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) zu finden.



## Analyse der Dependency Rule
*Eine Klasse, die die Dependency Rule einhält und eine Klasse, die die Dependency Rule verletzt;   jeweils UML der Klasse und Analyse der Abhängigkeiten in beide Richtungen (d.h., von wem hängt die Klasse ab und wer hängt von der Klasse ab) in Bezug auf die Dependency Rule*

*In den folgenden UML-Diagrammen werden zur besseren Übersichtlichkeit die für die Analyse der Dependency Rule unnötigen Details ausgelassen, beispielsweise Methoden von anderen Klassen. Abhängigkeiten werden ausschließlich ausgehend von der zentralen Klasse eingezeichnet, die gerade diskutiert wird.*


**1. Positiv-Beispiel**

![Dependency Rule 1. Positiv-Beispiel](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/dependency-rule/1-positive.puml&fmt=svg)

Die Klasse `Game` befindet sich im Application-Layer. Gemäß der Dependency Rule darf sie also nur Abhängigkeiten zu inneren Schichten (Domain) haben. Wie im UML-Diagramm zu sehen, hängt die Klasse von anderen Klassen in ihrer eigenen Schicht (z.B. von `GamePlayer`) als auch von Klassen aus der Domain-Schicht (z.B. von `Deck`) ab. Es gibt jedoch keinen Abhängigkeitspfeil, der von `Game` in eine Klasse aus der Plugin-CLI-Schicht zeigt. Stattdessen wird `Game` von der Klasse `Dominion` in ihrer `main()`-Methode verwendet.


**2. Positiv-Beispiel**

Die von den Dozenten vorgegeben Projektstruktur mit vorpopulierten `pom.xml`-Dateien für einzelne Maven-Module schließt ein Verletzen der Dependency Rule aus. Es ist schlichtweg nicht möglich, in einer inneren Schicht eine Klasse einer äußeren Schicht zu verwenden, da innere Schichten in dieser Projektstruktur nichts von äußeren wissen und dadurch auch Symbole, d.h. zum Beispiel Klassennamen, nicht aufgelöst werden können. Es kommt dann bereits beim Kompilieren zu Fehlern. Statt eines Negativ-Beispiels soll hier deshalb ein weiteres Positiv-Beispiel vorgestellt werden.

![Dependency Rule 2. Positiv-Beispiel](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/dependency-rule/2-positive.puml&fmt=svg)

Die abstrakte Klasse `Move` aus dem Domänen-Kern ist ausschließlich von Klassen innerhalb ihrer eigenen Schicht abhängig (z.B. von der abstrakten Klasse `Player`). `PlayerMove` im Application-Layer erbt von `Move` und überschreibt die mit `protected` versehenen Methoden. Abhängigkeitspfeile zeigen auch in diesem Beispiel stets von außen nach innen, niemals von innen nach außen.


## Analyse der Schichten
*Jeweils eine Klasse zu zwei unterschiedlichen Schichten der Clean-Architecture: jeweils UML der Klasse (ggf. auch zusammenspielenden Klassen), Beschreibung der Aufgabe, Einordnung in die Clean-Architecture (mit Begründung).*


**`Action` in der Domain-Schicht**

![Domain-Schicht Action](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/clean-architecture-layers/action-domain.puml&fmt=svg)

Die Klasse `Action` innerhalb der Domain stellt eine Art "Container" für mehrere Anweisungen einer Karte zur Verfügung. Diese Anweisungen werden von dem Interface `Instruction` modelliert, wobei dieses im Wesentlichen nur eine `execute(...)`-Methode umfasst. Beim Spielen einer Karte führt der/die Spieler:in während der Aktionsphase die Anweisungen "von oben nach unten" (wie auf der Karte aufgeschrieben) aus. Diese Reihenfolge wird durch eine einfache Liste von `Instruction`s innerhalb der Klasse `Action` modelliert.

Die Klasse `Action` befindet sich in der Domänen-Schicht, da sie zur Domäne des Spiels Dominion zählt: der Begriff "Anweisung" (Instruction) wird durchgehend in der Spielanleitung verwendet, außerdem wird von "Aktionskarten" geredet. Dementsprechend liegt es nahe, eine Klasse `Action` einzuführen, die die `Instruction`s einer Karte zu einer Aktion bündelt, die dann ausgeführt werden kann. Dass eine Aktion aus Anweisungen besteht ist eine Invariante, die für jede Aktion berücksichtigt werden muss, auch dies begründet die Position von `Action` im Domain-Code. Dass die Anweisungen "von oben nach unten" ausgeführt werden, ist durch die Reihenfolge der Anweisungen in der Liste implizit gegeben. Die Klasse ´Action` wird vom `CardPool` verwendet, um die Karten im Spiel aufzubauen.


**`CardFormatter` in der Plugin-CLI-Schicht**

![Plugin-CLI-Schicht CardFormatter](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/splines/dominion-cli/docs/uml/clean-architecture-layers/card-formatter-plugin-cli.puml&fmt=svg)

Der `CardFormatter` befindet sich in der Plugin-CLI-Schicht und ist dafür zuständig Karten für den/die Benutzer:in ansprechend in der Konsole mithilfe von Unicode-Zeichen darzustellen, zum Beispiel so:

```
┏━━━━━━ KUPFER ━━━━━━┓
┃                    ┃
┃             💰💰💰 ┃
┃             💰1 💰 ┃
┃             💰💰💰 ┃
┃                    ┃
┃                    ┃
┃                    ┃
┃                    ┃
┃                    ┃
┃                    ┃
┃                    ┃
┗ 0💰 ━━ Geld ━━━━━━━┛
```

Um den Body der Karte zu "rendern" (im Fall der Geldkarte die "1" umgeben von Geldsäcken), nutzt der `CardFormatter` eine konkrete Implementierung der abstrakten Klasse `CardBodyFormatter`, je nachdem von welchem Typ die Karte bei `getFormatted(Card card)` ist. In diesem Beispiel wird demnach der `MoneyCardFormatter` zum Einsatz kommen.

Die Plugin-CLI-Schicht wurde als Ort für den `CardFormatter` gewählt, da dieser lediglich für die Anzeige von Karten, jedoch für keinerlei Anwendungslogik zuständig ist. Normalerweise greift diese Schicht nur auf den Adapter zu. Im Rahmen dieses Projekts wurde jedoch auf einen Adapter verzichtet, da dieser zur jetzigen Zeit keinen großen Mehrwert liefern und den Code nur unnötig aufblähen würde. Stattdessen ruft der `CardFormatter` für die Ausgabe direkt Methoden der Karte auf, zum Beispiel `card.getName()` oder `card.getMoney()`.

Trotz des herausfordernden Codes, Karten mit Unicode in einem Grid anzuordnen und auszugeben, ist die emotionale Bindung an diesen Code sehr gering: der `CardFormatter` könnte jederzeit ausgetauscht werden, beispielsweise mit einem "richtigen" UI für eine Web-Applikation. Die Business Logik und der Kern unserer Anwendung wäre dadurch nicht betroffen; die innersten Schichten bekämen von diesen Änderungen in der Tat überhaupt nichts mit.

---
layout: default
title: I Einführung
permalink: einfuehrung
---

# I Einführung
{: .no_toc }

<details open markdown="block">
  <summary>
    Inhalt
  </summary>
  {: .text-delta }

- TOC
{:toc}

</details>

## Übersicht über die Applikation
*Was macht die Applikation? Wie funktioniert sie? Welches Problem löst sie/welchen Zweck hat sie?*

Dominion-CLI setzt das Kartenspiel "Dominion" als Command Line Interface um, das heißt mehrere Spieler können das Spiel gegeneinander an einer Konsole spielen. Hier eine Kurzbeschreibung des Spiels:

> Dominion ist ein Kartenspiel, mit dem Donald X. Vaccarino das Deck-Building-Genre begründete. Aus einem von Spiel zu Spiel variierenden Kartenangebot bauen die Spieler ihren persönlichen Kartensatz auf. Durch geschickten Einsatz von Aktions- und Geldkarten versuchen sie, ihr anfänglich schwaches Deck so zu stärken, dass sie am Schluss die meisten Punktekarten haben. Das Grundprinzip kann durch die in zahlreichen Erweiterungen enthaltenen Mechaniken vielfältig ausgebaut werden. — [Wikipedia](https://de.wikipedia.org/wiki/Dominion_(Spiel))

**Die Spielanleitung der zweiten Edition kann [hier](https://www.spielkarten.com/spielregeln/dominion-basisspiel-2-edition/) eingesehen werden. Besonders interessant ist hier wahrscheinlich [Seite 6 des ersten Teils](https://www.spielkarten.com/wp-content/uploads/2019/07/22501413_Dominion-2nd-Edition_SR1.pdf#page=6).** *(Bitte erstelle ein [GitHub Issue](https://github.com/Splines/dominion-cli/issues), falls dieser Link nicht mehr funktioniert.)*

Durch das CLI wird dem Spieler / der Spielerin der gesamte Spielspaß im Kreise von Freunden und Familie genommen. Stattdessen kann man nun auch alleine an der Konsole sitzen und einfach für alle Personen spielen. Außerdem entfallen lästige Handgriffe wie das Ziehen und Ablegen von Karten. Karten selbst werden mit Unicode-Zeichen ausgegeben und sind auf das Wesentliche reduziert, z.B. sind Bilder Fehlanzeige. Statt eines lebendigen und bunten Spiels beschränkt sich die Interaktion mit den Spielern auf Zahlen-Eingaben im Terminal. So geht Spielen heute.

Der eigentliche Grund für das CLI liegt in meinem Bestreben, eine künstliche Intelligenz (KI) für das Spiel zu programmieren. Für das Training muss das komplette Spiel simuliert werden, das CLI ist hier der erste Meilenstein auf dem Weg zur KI. Nichtsdestotrotz wurde für dieses Projekt im CLI-Layer (äußerste Schicht) auch auf ein ansprechendes Design Wert gelegt, obwohl dieses für das Training einer KI natürlich nicht notwendig gewesen wäre.

Dieses Projekt fokussiert sich auf die Implementierung der wesentlichen Spielabläufe. Durch die Umsetzung von "Clean Architecture" ist der Code vielseitig einsetzbar, z.B. könnte das CLI einfach ausgetauscht werden durch ein ansprechenderes UI in einer Desktop- oder Web-Applikation.


## Wie startet man die Applikation (eigener Build)?
*Wie startet man die Applikation? Welche Voraussetzungen werden benötigt? Schritt-für-Schritt-Anleitung*

**Voraussetzungen**

- [Java SE Development Kit 17 (LTS)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) — Objektorientierte Programmiersprache
- [Maven](https://maven.apache.org/) — Build und package management tool

**Projekt herunterladen**

```
git clone https://github.com/Splines/dominion-cli.git
cd dominion-cli
```

**Projekt bauen**

```
mvn clean package -DskipTests
```

**CLI (JAR-Datei) ausführen**

```
mv .\0-dominion-plugin-cli\target\dominion-cli.jar .
java -jar dominion-cli.jar
```

**Unicode Code Page (Optional)**

⚠ Falls Unicode-Zeichen nicht richtig angezeigt werden ("?" anstatt von Symbolen), kann mit folgendem Command versucht werden, die Code Page auf Unicode umzustellen (chcp steht für "Change Codepage"). Getestet wurde der Befehl in Windows PowerShell, wo per default die Code Page 850 aktiv war.

```
chcp 65001
```


## Wie testet man die Applikation?
*Wie testet man die Applikation? Welche Voraussetzungen werden benötigt? Schritt-für-Schritt-Anleitung*

Es gelten die selben Voraussetzungen wie beim Starten der Applikation. Nachdem das Projekt heruntergeladen wurde, können die Unit Tests wie folgt ausgeführt werden:

**Projekt testen (Unit tests)**

```
mvn test
```

Alle Tests sollten erfolgreich sein. Ein einziger Testfall basiert auf Zufall (Shuffle Card Deck), dieser Test könnte daher mit sehr geringer Wahrscheinlichkeit nicht erfolgreich sein. In diesem Fall den Command zum Testen einfach erneut ausführen.
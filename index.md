---
layout: default
title: Home
nav_order: 1
---

# Dominion-CLI
{: .fs-9 }

Herzlich willkommen zur Doku von `dominon-cli`, einem Command Line Interface (CLI), mit dem du eins der ersten Deck-Building-Kartenspiele — ["Dominion"](https://de.wikipedia.org/wiki/Dominion_(Spiel)) — nun auch in der Konsole spielen kannst.
{: .fs-6 .fw-300 }

{% capture intro_link %}{{ site.baseurl }}{% link docs/1-introduction.md %}{% endcapture %}
[Zur Einführung]({{intro_link}}){: .btn .btn-primary .fs-5 .mb-4 .mb-md-0 .mr-2 } [GitHub](https://github.com/Splines/dominion-cli){: .btn .fs-5 .mb-4 .mb-md-0 }

---

## Über das Projekt

Dieses Studentenprojekt ist im Rahmen der Vorlesung *Advanced Software Engineering* an der Dualen Hochschule Baden-Württemberg (DHBW) entstanden. **Die Dokumentation ist keine Benutzerdokumentation, sondern dokumentiert technische Details der Implementierung und der getroffenen architektonischen Design-Entscheidungen.** In der Vorlesung haben wir das Konzept von Clean Architecture und Domain-Driven Design kennengelernt und konnten dieses anhand eines praktischen Projekts praktisch erproben. Zahlreiche weitere Prinzipien, wie z.B. SOLID und GRASP, sowie erprobte Entwurfsmuster waren ebenfalls Teil des Vorlesungsstoffes.

## Tools

- Um die UML-Diagramme zu generieren wurde das tolle Tool [`PlantUML`](https://plantuml.com/) verwendet. Die Bilder wurden mithilfe deren ["Proxy-Service"](https://plantuml.com/de/server) in die Dokumentation eingebunden.
- Die Dokumentation wird auf [`GitHub Pages`](https://pages.github.com/) gehostet. Der Source-Code kann [hier](https://github.com/Splines/dominion-cli/tree/docs) eingesehen werden. Verwendet wurde der static site generator [`Jekyll`](https://jekyllrb.com/) mit Markdown-Dateien unter Verwendung des fantastischen Themes [`Just the Docs`](https://just-the-docs.github.io/just-the-docs/).


# SnowParticles
![Spigot](https://img.shields.io/badge/Spigot-1.21.1+-yellow.svg)
![Version](https://img.shields.io/badge/Version-1.0c-lightgray.svg)

**SnowParticles** ist ein Minecraft-Spigot-Plugin (1.21.1), das Schneepartikel in bestimmten Bereichen spawnt. Es unterstützt dynamische Effekte, die sich an die Umgebung anpassen – einschließlich der Möglichkeit, Schnee in offenen Bereichen zu erzeugen und ihn in überdachten Bereichen zu unterdrücken.



## Features

- **Setzen von Schneepunkteffekten:** Definiere Bereiche, in denen Schneepartikel sichtbar sind.
- **Benutzerdefinierter Radius und Höhenbereich:** Stelle sicher, dass Effekte nur in gewünschten Zonen auftreten.
- **Block-Überprüfung:** Keine Partikel unter Blöcken (z. B. in Gebäuden), außer die Blöcke werden als "transparent" definiert.
- **Speicherung und automatische Aktivierung:** Punkte werden in einer points.yml-Datei gespeichert und bei einem Neustart automatisch geladen.
- **Flexibel über die Config anpassbar:** Ignorierte Blöcke können in der config.yml definiert werden (z. B. Glas oder Blätter).
## Commands

The Medieval Brewery plugin provides the following commands:

- `/snowparticle setpoint <name> <radius> <lowestpoint> <highestpoint>`: Setzt einen neuen Schneepunkt mit einem bestimmten Radius und Höhenbereich.
- `/snowparticle removepoint <name>`: Entfernt einen bestehenden Schneepunkt.

Für alle Commands ist die Permission `snowparticles.admin` nötig.

### Beispiele
- **Schneepunkt setzen**:
  /snowparticle setpoint SnowArea 10 50 100
  → Erstellt einen Schneepunkt namens SnowArea mit einem Radius von 10 Blöcken und einem Höhenbereich von 50 bis 100.

- **Schneepunkt entfernen**:
  /snowparticle removepoint SnowArea
  → Entfernt den Schneepunkt SnowArea.
## Anforderungen

- **JAVA** 21+
- **Spigot** 1.21.1+
## Installation

- Lade die neueste Version des Plugins von GitHub Releases herunter.
- Platziere die .jar-Datei in deinem plugins-Ordner.
- Starte deinen Server neu.
- Passe die config.yml nach Bedarf an.


## Configuration
Die Datei `config.yml` enthält Einstellungen für das Verhalten des Plugins.
### Ignorierte Blöcke
Definiere, welche Blöcke bei der Überprüfung ignoriert werden sollen (z. B. Glas oder Blätter).
```yaml
ignored-blocks:
  - GLASS
  - GLASS_PANE
  - LEAVES
  - LEAVES2
  - ICE
````
## Dateien
`config.yml`: Konfiguration des Plugins (z. B. ignorierte Blöcke).
`points.yml`: Speichert alle Schneepunkte (automatisch verwaltet).
## Lizenz
Dieses Plugin steht unter der [MIT LIZENZ](LICENSE).
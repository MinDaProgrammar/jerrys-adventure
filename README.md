# Jerry's Adventure — Text-Based Dungeon Game (Java)

A terminal dungeon crawler written for the **Procedural Programming** course (BSc Data Science & AI, Maastricht University, 2025). You play Jerry's apprentice, dropped into a random room of a 5×5 dungeon, and have to collect gear and treasure strong enough to beat the Goblin Boss.

## Gameplay

- The dungeon is a 5×5 grid; each room may hold a **monster**, an **upgrade** (armour/potion or weapon), a **treasure**, or nothing
- Room contents and difficulty are encoded in one integer per cell. The digit type sets the event (`1` monster, `3` upgrade, `7` treasure) and the number of digits sets the level (`7` → level 1 treasure, `777` → level 3), decoded with a small recursive function
- Fights are turn-based rock-paper-scissors: **Slash** beats Magic, **Magic** beats Block, **Block** beats Slash, against a randomly choosing monster
- Used upgrades and treasure disappear from the map, so the order in which you explore matters
- Typewriter-style text output with natural pauses at punctuation, plus screen clearing between rooms

## Running

Requires Java 17+ (any recent JDK). Run it in a real terminal so the screen clearing works.

```bash
javac JerrysAdventure.java
java JerrysAdventure
```

## Concepts practised

Loops and control flow, 2D arrays, methods and recursion, input validation with `Scanner`, randomness.

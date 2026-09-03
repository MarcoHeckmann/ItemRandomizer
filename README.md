# DropRandomizer

A Minecraft [Paper](https://papermc.io) plugin that randomizes both **block drops** and **crafting recipes**, turning your world and your survival experience upside down. Every break and every craft is a surprise until you discover what a block actually drops.

## Features

- **Randomized block drops** – Every breakable block in the game is randomly assigned a drop. Technical and unbreakable blocks (command blocks, barriers, bedrock, etc.) are automatically excluded.
- **Randomized crafting** – Every crafting recipe returns a random item instead of its normal result.
- **Discovery system** – Look at any block to see its current drop in your action bar. When a new drop is discovered, the whole server is notified.
- **Periodic re-randomization** – All mappings are shuffled again every **30 minutes** (by default), so the game keeps changing.
- **`/find` command** – Search for which block currently drops the item you need.
- **Fully English**, colored chat messages built with MiniMessage-free legacy color codes.

## Requirements

| Requirement | Version |
|-------------|---------|
| Minecraft | 1.21.x |
| Paper (or compatible fork) | 1.21.10+ |
| Java | 21 |

## Installation

1. Download the latest `droprandomizer-1.0-SNAPSHOT.jar` from the [Releases](../../releases) page.
2. Drop the `.jar` file into your server's `plugins/` folder.
3. Restart the server (or use a plugin manager).
4. The plugin is now ready. Start the randomizer with `/randomizer start`.

## Commands

| Command | Permission | Description |
|---------|-----------|-------------|
| `/randomizer start` | `randomizer.start` | Start the randomizer game. |
| `/randomizer stop` | `randomizer.stop` | Stop the randomizer game and reset all mappings. |
| `/find <material>` | default | Search which block currently drops the given material. |

## Permissions

| Permission | Default | Description |
|-----------|---------|-------------|
| `randomizer.start` | `false` | Allows starting the randomizer. |
| `randomizer.stop` | `false` | Allows stopping the randomizer. |

## How it works

1. Start the game with `/randomizer start`.
2. Every breakable block in `Material.values()` is paired with a randomly shuffled list of every item in the game.
3. When a player breaks a block, its natural drops are replaced by the assigned random drop.
4. When a player crafts an item, they receive a random item instead of the expected recipe result.
5. The `DiscoverManager` shows players, in real time, what the block they're looking at currently drops, along with a running game timer.
6. Every 30 minutes all mappings are reshuffled and players are notified.

### Discovery message

When any player breaks a block with an undiscovered drop, the whole server sees a message like:

```
✦ Steve discovered STONE → Drop: DIAMOND
```

### `/find` example

Search which block drops DIAMOND:

```
/find diamond
```
```
The block for DIAMOND is STONE
```

## Configuration

There is currently no configuration file. All behavior is controlled directly through the commands and source code:

- **Re-randomization interval** is defined in `ResetManager.startResetTimer()` as `20 * 60 * 30` ticks (30 minutes).
- **Excluded blocks** are listed in `DropRandomizeManager.EXCLUDED_BLOCKS`.

## Building from source

The project uses Maven. Java 21 and Maven are required.

```bash
mvn clean package
```

The resulting jar will be located at `target/droprandomizer-1.0-SNAPSHOT.jar`.

## Project structure

```
src/main/java/jp/softrain/dropRandomizer/
├── Main.java                        # Plugin entry point
├── commands/
│   ├── FindCommand.java             # /find <material>
│   └── RandomizerCommand.java       # /randomizer start|stop
├── listeners/
│   ├── BlockBreakListener.java      # Replaces block drops & discovery
│   └── CraftListener.java           # Replaces crafted items
└── manager/
    ├── CraftRandomizeManager.java   # Maps recipes -> random results
    ├── DiscoverManager.java         # Action-bar info per player + timer
    ├── DropRandomizeManager.java    # Maps blocks -> random drops
    └── ResetManager.java            # Start/stop/reset + re-randomization timer
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

# FoodEffects

Food effects is a forge mod for minecraft 1.16.4 that can help you modify the effects of foods.

## Installation

Download the mod jar and throw it into mods directory.

## Usage

If you're not familiar with data packs, read [MinecraftWiki](https://minecraft.gamepedia.com/Tutorials/Creating_a_data_pack) first.

You need to create json files in `data/foodeffects/food_effects/` directory.

### Example

The following example will add two effects to apple:

- Speed I effect with 10s duration and 100% chance
- Haste II effect with 10s duration and 50% chance

```json
{
  "minecraft:apple": [
    {
      "effect": "minecraft:speed",
      "amplifier": 0,
      "duration": 200,
      "possibility": 1.0
    },
    {
      "effect": "minecraft:haste",
      "amplifier": 1,
      "duration": 200,
      "possibility": 0.5
    }
  ]
}
```
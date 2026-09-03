package jp.softrain.dropRandomizer.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.*;

public class DropRandomizeManager {

    public static HashMap<Material, Material> randomMap = new HashMap<>();


    // Blocks that have isItem()=true but cannot be broken in Survival
    private static final Set<Material> EXCLUDED_BLOCKS = EnumSet.of(
            Material.BEDROCK,
            Material.BARRIER,
            Material.COMMAND_BLOCK,
            Material.CHAIN_COMMAND_BLOCK,
            Material.REPEATING_COMMAND_BLOCK,
            Material.STRUCTURE_BLOCK,
            Material.STRUCTURE_VOID,
            Material.JIGSAW,
            Material.END_PORTAL_FRAME,
            Material.LIGHT
    );

    private static boolean isBreakableBlock(Material material) {
        // Must be a block AND an item → excludes all technical block states
        // (POTTED_*, *_WALL_SIGN, WHEAT, BUBBLE_COLUMN, FIRE, etc.)
        if (!material.isBlock() || !material.isItem()) return false;
        // Manually excluded unbreakable blocks
        if (EXCLUDED_BLOCKS.contains(material)) return false;
        return true;
    }

    public static void setup() {
        List<Material> blocks = new ArrayList<>();
        for (Material material : Material.values()) {

            if (isBreakableBlock(material)) {
                blocks.add(material);
            }
        }

        List<Material> drops = new ArrayList<>();
        for (Material material : Material.values()) {
            if (material.isItem()) {
                drops.add(material);
            }
        }

        Collections.shuffle(drops);

        for (int i = 0; i < blocks.size(); i++){
            Material block = blocks.get(i);
            Material drop = drops.get(i);
            randomMap.put(block, drop);
        }

        for (Map.Entry<Material, Material> entry : randomMap.entrySet()) {
            Bukkit.getConsoleSender().sendMessage(entry.getKey() + " §a→ §f" + entry.getValue());
        }

        Bukkit.getConsoleSender().sendMessage("§e=== DropRandomizer Debug ===");
        Bukkit.getConsoleSender().sendMessage("§7Breakable blocks: §f" + blocks.size());
        Bukkit.getConsoleSender().sendMessage("§7All items as drops: §f" + drops.size());
        Bukkit.getConsoleSender().sendMessage("§7Surplus items (never obtainable): §c" + Math.max(0, drops.size() - blocks.size()));
    }

    public static Material getDrops(Material block) {
        return randomMap.get(block);
    }
    public static void reset(){
        randomMap.clear();
    }
}
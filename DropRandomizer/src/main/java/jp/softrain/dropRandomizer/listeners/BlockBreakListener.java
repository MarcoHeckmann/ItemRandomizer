package jp.softrain.dropRandomizer.listeners;

import jp.softrain.dropRandomizer.manager.DiscoverManager;
import jp.softrain.dropRandomizer.manager.DropRandomizeManager;
import jp.softrain.dropRandomizer.manager.ResetManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

public class BlockBreakListener implements Listener {
    @EventHandler
    public static void onBreak(BlockBreakEvent event){

        if (!ResetManager.isGameRunning) return;

        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        Material drop = DropRandomizeManager.getDrops(blockType);

        if (drop == null) return;

        event.setDropItems(false);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(drop));

        if (!DiscoverManager.discovered.containsKey(blockType)) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendMessage("§6✦ §e" + player.getName() + " §7discovered §a" + blockType + " §8→ §7Drop: " + getDropName(drop));
                players.playSound(players.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.3f, 2);
            }
        }

        DiscoverManager.discovered.put(blockType, drop);

    }

    private static @NonNull String getDropName(Material drop) {
        String name = drop.name();
        if (name.startsWith("LEATHER"))       return "§x§A§0§5§2§2§D" + name;
        if (name.startsWith("CHAINMAIL"))     return "§x§C§0§C§0§C§0" + name;
        if (name.startsWith("GOLD"))          return "§x§F§F§D§7§0§0" + name;
        if (name.startsWith("DIAMOND"))       return "§x§0§0§E§5§F§F" + name;
        if (name.startsWith("NETHERITE"))     return "§x§8§A§2§B§E§2" + name;
        return "§f" + name;
    }
}

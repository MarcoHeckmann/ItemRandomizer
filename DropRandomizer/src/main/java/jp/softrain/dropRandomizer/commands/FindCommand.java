package jp.softrain.dropRandomizer.commands;

import jp.softrain.dropRandomizer.manager.DropRandomizeManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FindCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§cUsage: /find <block>");
            return true;
        }

        Material dropMaterial;

        try {
            dropMaterial = Material.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage("§cThis item does not exist.");
            return true;
        }

        boolean found = false;

        for (Map.Entry<Material, Material> entry : DropRandomizeManager.randomMap.entrySet()) {
            if (entry.getValue() == dropMaterial) {
                player.sendMessage("§7The block for §a" + dropMaterial + " §7is §f" + entry.getKey());
                found = true;
            }
        }

        if (!found) {
            player.sendMessage("§cNo block with the drop §f" + dropMaterial + " §cfound!");
        }

        return true;
    }
}

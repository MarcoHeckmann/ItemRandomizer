package jp.softrain.dropRandomizer.commands;

import jp.softrain.dropRandomizer.manager.ResetManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RandomizerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length < 1){
            player.sendMessage("§5Randomizer §8→ §7Please use /Randomizer start");
            return true;
        }

        if (args[0].equalsIgnoreCase("start")){
            if(!player.hasPermission("randomizer.start")){
                player.sendMessage("§5Randomizer §8→ §cYou don't have the permission do perform this command.");
                return true;
            }

            if(ResetManager.isGameRunning){
               player.sendMessage("§5Randomizer §8→ §cThe game is already running. Please stop it with §7/Randomizer stop");
               return true;
            }
            ResetManager.startRandomizer();

            for (Player players : Bukkit.getOnlinePlayers()){
                players.sendMessage("§5Randomizer §8→ §aThe game has started!");
            }
            return true;
        } else if (args[0].equalsIgnoreCase("stop")){
            if(!player.hasPermission("randomizer.stop")){
                player.sendMessage("§5Randomizer §8→ §cYou don't have the permission do perform this command.");
                return true;
            }

            if(!ResetManager.isGameRunning){
                player.sendMessage("§5Randomizer §8→ §cThe game is not running. Please start it with §7/Randomizer start");
                return true;
            }

            ResetManager.stopRandomizer();

            for (Player players : Bukkit.getOnlinePlayers()){
                players.sendMessage("§5Randomizer §8→ §aThe game has been stopped!");
            }
            return true;
        }
        return false;
    }
}

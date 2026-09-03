package jp.softrain.dropRandomizer.manager;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class DiscoverManager extends BukkitRunnable {

    public static HashMap<Material, Material> discovered = new HashMap<>();
    private int tickCounter = 0;
    private int totalSeconds = 0;
    private int totalMinutes = 0;
    private int totalHours = 0;
    private int totalDays = 0;

    @Override
    public void run() {

        if(!ResetManager.isGameRunning){
            this.cancel();
            return;
        }

        tickCounter++;

        if (tickCounter >= 20){
            tickCounter = 0;
            totalSeconds++;
        }
        if (totalSeconds >= 60){
            totalSeconds = 0;
            totalMinutes++;
        }
        if (totalMinutes >= 60){
            totalMinutes = 0;
            totalHours++;
        }
        if (totalHours >= 24){
            totalHours = 0;
            totalDays++;
        }

        String time = String.format(
                "%02d:%02d:%02d:%02d",
                totalDays,
                totalHours,
                totalMinutes,
                totalSeconds);

        for (Player player : Bukkit.getOnlinePlayers()) {

            //Get Reach | Looked block
            double reach = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getValue();
            Block block = player.getTargetBlockExact((int) reach, FluidCollisionMode.NEVER);

            if (block == null) {
                player.sendActionBar("§d" + time);
                continue;
            }

            Material blockType = block.getType();
            Material drop = discovered.get(blockType);

            if (drop == null) {
                player.sendActionBar(blockType + " §a→ §fUnbekannt" + " §8┃ §d" + time);
            } else {
                player.sendActionBar(blockType + " §a→ §f" + drop + " §8┃ §d" + time);
            }
        }
    }
    public static void reset(){
        discovered.clear();
    }
}

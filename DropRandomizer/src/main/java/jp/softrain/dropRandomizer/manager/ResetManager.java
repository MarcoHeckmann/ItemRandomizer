package jp.softrain.dropRandomizer.manager;

import jp.softrain.dropRandomizer.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ResetManager {

    public static boolean isGameRunning = false;

    public static void stopRandomizer() {
        isGameRunning = false;
        DiscoverManager.reset();
        DropRandomizeManager.reset();
        CraftRandomizeManager.reset();
    }

    public static void startRandomizer() {
        isGameRunning = true;
        DropRandomizeManager.setup();
        CraftRandomizeManager.setup();
        startResetTimer();
        new DiscoverManager().runTaskTimer(Main.getInstance(), 0L, 1L);
    }

    public static void resetRandomizer() {
        DiscoverManager.reset();
        DropRandomizeManager.reset();
        CraftRandomizeManager.reset();
        DropRandomizeManager.setup();
        CraftRandomizeManager.setup();
    }

    public static void startResetTimer() {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (!isGameRunning) return;
            ResetManager.resetRandomizer();
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendMessage("§dItems have been re-randomized!");
            }
            startResetTimer();
        }, 20 * 60 * 30);
    }
}
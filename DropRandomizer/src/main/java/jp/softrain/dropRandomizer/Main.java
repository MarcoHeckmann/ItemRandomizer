package jp.softrain.dropRandomizer;

import jp.softrain.dropRandomizer.commands.FindCommand;
import jp.softrain.dropRandomizer.commands.RandomizerCommand;
import jp.softrain.dropRandomizer.listeners.BlockBreakListener;
import jp.softrain.dropRandomizer.listeners.CraftListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(),this);
        Bukkit.getPluginManager().registerEvents(new CraftListener(),this);
        getCommand("find").setExecutor(new FindCommand());
        getCommand("randomizer").setExecutor(new RandomizerCommand());


        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }
}

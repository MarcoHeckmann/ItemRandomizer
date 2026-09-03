package jp.softrain.dropRandomizer.listeners;

import jp.softrain.dropRandomizer.manager.CraftRandomizeManager;
import jp.softrain.dropRandomizer.manager.ResetManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CraftListener implements Listener {

    @EventHandler
    public static void onCrafting(CraftItemEvent event){

        if (!ResetManager.isGameRunning) return;
        Bukkit.getConsoleSender().sendMessage("Event fired!");

        Recipe recipe = event.getRecipe();


        Material craftedItemRandom = CraftRandomizeManager.getCraftedItem(recipe);

        if (craftedItemRandom == null) return;

        event.setCurrentItem(new ItemStack(craftedItemRandom));

        Bukkit.getConsoleSender().sendMessage("Crafted: " + recipe + " §eReceived: §6" + craftedItemRandom);
    }
}

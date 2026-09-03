package jp.softrain.dropRandomizer.manager;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

import java.util.*;

public class CraftRandomizeManager {

    private static HashMap<NamespacedKey, Material> randomMap = new HashMap<>();

    public static void setup() {
        if (!ResetManager.isGameRunning) return;

        Iterator<Recipe> iterator = Bukkit.recipeIterator();

        List<NamespacedKey> keyList = new ArrayList<>();
        List<Material> craftedMaterial = new ArrayList<>();

        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if (!(recipe instanceof Keyed keyed)) continue;

            keyList.add(keyed.getKey());
            craftedMaterial.add(recipe.getResult().getType());
        }

        Collections.shuffle(craftedMaterial);

        for (int i = 0; i < keyList.size(); i++) {
            randomMap.put(keyList.get(i), craftedMaterial.get(i));
        }

        Bukkit.getConsoleSender().sendMessage("§e=== CraftingRandomizer Debug ===");
        Bukkit.getConsoleSender().sendMessage("§7Rezepte: §f" + keyList.size());
    }

    public static Material getCraftedItem(Recipe recipe) {
        if (!(recipe instanceof Keyed keyed)) return null;
        return randomMap.get(keyed.getKey());
    }

    public static void reset() {
        randomMap.clear();
    }
}
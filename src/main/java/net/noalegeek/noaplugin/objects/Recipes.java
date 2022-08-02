package net.noalegeek.noaplugin.objects;

import fr.sunderia.sunderiautils.recipes.AnvilRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

/**
 * @author <a href="https://github.com/Minemobs/MinemobsUtils/blob/main/src/main/java/fr/minemobs/minemobsutils/objects/Recipes.java">minemobs</a>
 */

public enum Recipes {

    ;

    private final ShapedRecipe recipe;
    private final ShapelessRecipe shapelessRecipe;
    private final AnvilRecipe anvilRecipe;

    Recipes(ShapedRecipe recipe) {
        this.recipe = recipe;
        this.shapelessRecipe = null;
        this.anvilRecipe = null;
    }

    Recipes(ShapelessRecipe shapelessRecipe) {
        this.shapelessRecipe = shapelessRecipe;
        this.recipe = null;
        this.anvilRecipe = null;
    }

    Recipes(AnvilRecipe anvilRecipe) {
        this.shapelessRecipe = null;
        this.recipe = null;
        this.anvilRecipe = anvilRecipe;
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    public ShapelessRecipe getShapelessRecipe() {
        return shapelessRecipe;
    }

    public AnvilRecipe getAnvilRecipe() {
        return anvilRecipe;
    }

    public boolean matches(ShapedRecipe recipe1, ShapedRecipe recipe2) {
        return recipe1.getKey().equals(recipe2.getKey());
    }

}
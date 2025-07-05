package dev.spagurder.echoeye.fabric.datagen;

//? fabric {
import dev.spagurder.echoeye.EchoEyeMod;
import dev.spagurder.echoeye.block.ModBlocks;
import dev.spagurder.echoeye.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.ECHO_EYE)
                .pattern("eee")
                .pattern("eoe")
                .pattern("eee")
                .define('e', Items.ECHO_SHARD)
                .define('o', Items.ENDER_EYE)
                .unlockedBy("has_echo_shard", has(Items.ECHO_SHARD))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(EchoEyeMod.MOD_ID, "echo_eye"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ECHO_DEEPSLATE)
                .pattern(" e ")
                .pattern("ede")
                .pattern(" e ")
                .define('e', Items.ECHO_SHARD)
                .define('d', Ingredient.of(
                        Items.DEEPSLATE,
                        Items.COBBLED_DEEPSLATE
                ))
                .unlockedBy("has_echo_shard", has(Items.ECHO_SHARD))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(EchoEyeMod.MOD_ID, "echo_deepslate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.RECOVERY_EYE)
                .requires(Items.COMPASS)
                .requires(ModItems.ECHO_EYE)
                .unlockedBy("has_echo_eye", has(ModItems.ECHO_EYE))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(EchoEyeMod.MOD_ID, "recovery_eye"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.RECOVERY_EYE)
                .requires(Items.RECOVERY_COMPASS)
                .requires(Items.ENDER_EYE)
                .unlockedBy("has_recovery_compass", has(Items.RECOVERY_COMPASS))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(EchoEyeMod.MOD_ID, "recovery_eye_alt"));
    }
}
//?}

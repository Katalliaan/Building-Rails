package building_rails;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import building_rails.item.ItemBlastingCap;
import building_rails.item.ItemDynamite;
import building_rails.item.ItemBlastingCap.CapMaterial;

public class BRItems {
	public static Item itemDynamite = new ItemDynamite();
	public static Item blastingCap_iron = new ItemBlastingCap(CapMaterial.IRON).setTextureName(BuildingRails.modid + ":blastingCap_iron");
	
	public static void registerItems() {
		GameRegistry.registerItem(BRItems.itemDynamite, "itemDynamite");
		GameRegistry.registerItem(BRItems.blastingCap_iron, "blastingCap_iron");
	}
	
	public static void registerRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemDynamite), " s ", "pgp", "pgp", 's', Items.string, 'p', Items.paper, 'g', Items.gunpowder));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blastingCap_iron), " f ", "iri", " i ", 'f', Items.flint_and_steel, 'r', "dustRedstone", 'i', "ingotIron"));
	}
}

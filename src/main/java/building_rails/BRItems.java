package building_rails;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.oredict.ShapedOreRecipe;
import building_rails.item.*;
import building_rails.item.ItemBlastingCap.CapMaterial;

public class BRItems {
	public static Item itemDynamite = new ItemDynamite();
	public static Item blastingCap_iron = new ItemBlastingCap(CapMaterial.IRON).setTextureName(BuildingRails.modid + ":blastingCap_iron");
	public static Item blastingCap_steel = new ItemBlastingCap(CapMaterial.STEEL).setTextureName(BuildingRails.modid + ":blastingCap_steel");
	
	public static Item itemBatWing = (new ItemBatWing(1, false).setUnlocalizedName("building_rails.item.batWing").setTextureName(BuildingRails.modid + ":batwing"));
	public static Item itemDough = new Item().setUnlocalizedName("building_rails.item.dough").setTextureName(BuildingRails.modid + ":dough").setCreativeTab(BuildingRails.creativeTab);;
	public static Item itemFlour = new Item().setUnlocalizedName("building_rails.item.flour").setTextureName(BuildingRails.modid + ":flour").setCreativeTab(BuildingRails.creativeTab);
	public static Item itemFruitSalad = new ItemFood(1, false).setUnlocalizedName("building_rails.item.fruitSalad").setTextureName(BuildingRails.modid + ":fruitSalad");
	public static Item itemMashedPotatoes = new ItemFood(1, false).setUnlocalizedName("building_rails.item.mashedPotatoes").setTextureName(BuildingRails.modid + ":mashedPotatoes");
	public static Item itemMutton = new ItemFood(1, true).setUnlocalizedName("building_rails.item.mutton").setTextureName(BuildingRails.modid + ":mutton");
	public static Item itemMuttonRaw = new ItemFood(1, true).setUnlocalizedName("building_rails.item.muttonRaw").setTextureName(BuildingRails.modid + ":muttonRaw");
	public static Item itemPotEmpty = new Item().setUnlocalizedName("building_rails.item.potEmpty").setTextureName(BuildingRails.modid + ":potEmpty").setCreativeTab(BuildingRails.creativeTab);
	public static Item itemPotMonsterStew = new ItemMonsterStew(1, false).setUnlocalizedName("building_rails.item.potMonsterStew").setTextureName(BuildingRails.modid + ":potMonsterStew");
	public static Item itemPotMushsterStew = new ItemMushsterStew(1, false).setUnlocalizedName("building_rails.item.potMushsterStew").setTextureName(BuildingRails.modid + ":potMushsterStew");
	public static Item itemPotRawMonsterStew = new ItemRawMonsterStew(1, false).setUnlocalizedName("building_rails.item.potRawMonsterStew").setTextureName(BuildingRails.modid + ":potRawMonsterStew");
	public static Item itemPotRawMushsterStew = new ItemRawMushsterStew(1, false).setUnlocalizedName("building_rails.item.potRawMushsterStew").setTextureName(BuildingRails.modid + ":potRawMushsterStew");
	public static Item itemPotWater = new Item().setUnlocalizedName("building_rails.item.potWater").setTextureName(BuildingRails.modid + ":potWater").setCreativeTab(BuildingRails.creativeTab);
	public static Item itemRoastedPumpkin = new ItemFood(1, false).setUnlocalizedName("building_rails.item.roastedPumpkin").setTextureName(BuildingRails.modid + ":roastedPumpkin");
	public static Item itemSalad = new ItemFood(1, false).setUnlocalizedName("building_rails.item.salad").setTextureName(BuildingRails.modid + ":salad");
	public static Item itemSashimi = new ItemFood(1, false).setUnlocalizedName("building_rails.item.sashimi").setTextureName(BuildingRails.modid + ":sashimi");
	public static Item itemSucklingPorkchop = new ItemFood(1, true).setUnlocalizedName("building_rails.item.sucklingPorkchop").setTextureName(BuildingRails.modid + ":sucklingPorkchop");
	
	public static void registerItems() {
		GameRegistry.registerItem(BRItems.itemDynamite, "itemDynamite");
		GameRegistry.registerItem(BRItems.blastingCap_iron, "blastingCap_iron");
		GameRegistry.registerItem(BRItems.blastingCap_steel, "blastingCap_steel");
		
		GameRegistry.registerItem(BRItems.itemBatWing, "itemBatWing");
		GameRegistry.registerItem(BRItems.itemDough, "itemDough");
		GameRegistry.registerItem(BRItems.itemFlour, "itemFlour");
		GameRegistry.registerItem(BRItems.itemFruitSalad, "itemFruitSalad");
		GameRegistry.registerItem(BRItems.itemMashedPotatoes, "itemMashedPotatoes");
		GameRegistry.registerItem(BRItems.itemMutton, "itemMutton");
		GameRegistry.registerItem(BRItems.itemMuttonRaw, "itemMuttonRaw");
		GameRegistry.registerItem(BRItems.itemPotEmpty, "itemPotEmpty");
		GameRegistry.registerItem(BRItems.itemPotMonsterStew, "itemPotMonsterStew");
		GameRegistry.registerItem(BRItems.itemPotMushsterStew, "itemPotMushsterStew");
		GameRegistry.registerItem(BRItems.itemPotRawMonsterStew, "itemPotRawMonsterStew");
		GameRegistry.registerItem(BRItems.itemPotRawMushsterStew, "itemPotRawMushsterStew");
		GameRegistry.registerItem(BRItems.itemPotWater, "itemPotWater");
		GameRegistry.registerItem(BRItems.itemRoastedPumpkin, "itemRoastedPumpkin");
		GameRegistry.registerItem(BRItems.itemSalad, "itemSalad");
		GameRegistry.registerItem(BRItems.itemSashimi, "itemSashimi");
		GameRegistry.registerItem(BRItems.itemSucklingPorkchop, "itemSucklingPorkchop");
	}
	
	public static void registerRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemDynamite), " s ", "pgp", "pgp", 's', Items.string, 'p', Items.paper, 'g', Items.gunpowder));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blastingCap_iron), " f ", "iri", " i ", 'f', Items.flint_and_steel, 'r', "dustRedstone", 'i', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blastingCap_steel), " f ", "iri", " i ", 'f', Items.flint_and_steel, 'r', "dustRedstone", 'i', "ingotSteel"));
	}
}

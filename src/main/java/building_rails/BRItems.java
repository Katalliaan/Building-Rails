package building_rails;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import building_rails.entity.EntityThrownDynamite;
import building_rails.item.*;
import building_rails.item.ItemBlastingCap.CapMaterial;
import building_rails.item.ItemMaterial.Material;

public class BRItems {
	// Crafting Items
	public static Item itemMaterials = new ItemMaterial();

	// Explosives
	public static Item itemDynamite = new ItemDynamite();
	public static Item blastingCap_iron = new ItemBlastingCap(CapMaterial.IRON)
			.setTextureName(BuildingRails.modid
					+ ":explosives/blastingCap_iron");
	public static Item blastingCap_steel = new ItemBlastingCap(
			CapMaterial.STEEL).setTextureName(BuildingRails.modid
			+ ":explosives/blastingCap_steel");

	// Food (ingredients)
	public static Item itemBatWing = (new ItemBatWing(1, false)
			.setUnlocalizedName("building_rails.item.batWing")
			.setTextureName(BuildingRails.modid + ":food/batwing"));
	public static Item itemDough = new Item()
			.setUnlocalizedName("building_rails.item.dough")
			.setTextureName(BuildingRails.modid + ":food/dough")
			.setCreativeTab(BuildingRails.creativeTab);;
	public static Item itemFlour = new Item()
			.setUnlocalizedName("building_rails.item.flour")
			.setTextureName(BuildingRails.modid + ":food/flour")
			.setCreativeTab(BuildingRails.creativeTab);
	public static Item itemMuttonRaw = new ItemFood(1, true)
			.setUnlocalizedName("building_rails.item.muttonRaw")
			.setTextureName(BuildingRails.modid + ":food/muttonRaw");
	public static Item itemMutton = new ItemFood(1, true).setUnlocalizedName(
			"building_rails.item.mutton").setTextureName(
			BuildingRails.modid + ":food/mutton");

	// Food (bowl)
	public static Item itemFruitSalad = new ItemBowlFood(1, false)
			.setUnlocalizedName("building_rails.item.fruitSalad")
			.setTextureName(BuildingRails.modid + ":food/fruitSalad");
	public static Item itemMashedPotatoes = new ItemBowlFood(1, false)
			.setUnlocalizedName("building_rails.item.mashedPotatoes")
			.setTextureName(BuildingRails.modid + ":food/mashedPotatoes");
	public static Item itemSalad = new ItemBowlFood(1, false)
			.setUnlocalizedName("building_rails.item.salad").setTextureName(
					BuildingRails.modid + ":food/salad");

	// Food (pot)
	public static Item itemPotEmpty = new Item()
			.setUnlocalizedName("building_rails.item.potEmpty")
			.setTextureName(BuildingRails.modid + ":food/potEmpty")
			.setCreativeTab(BuildingRails.creativeTab);
	public static Item itemPotMonsterStew = new ItemMonsterStew(1, false)
			.setUnlocalizedName("building_rails.item.potMonsterStew")
			.setTextureName(BuildingRails.modid + ":food/potMonsterStew");
	public static Item itemPotMushsterStew = new ItemMushsterStew(1, false)
			.setUnlocalizedName("building_rails.item.potMushsterStew")
			.setTextureName(BuildingRails.modid + ":food/potMushsterStew");
	public static Item itemPotRawMonsterStew = new ItemRawMonsterStew(1, false)
			.setUnlocalizedName("building_rails.item.potRawMonsterStew")
			.setTextureName(BuildingRails.modid + ":food/potRawMonsterStew");
	public static Item itemPotRawMushsterStew = new ItemRawMushsterStew(1,
			false).setUnlocalizedName("building_rails.item.potRawMushsterStew")
			.setTextureName(BuildingRails.modid + ":food/potRawMushsterStew");
	public static Item itemPotWater = new Item()
			.setUnlocalizedName("building_rails.item.potWater")
			.setTextureName(BuildingRails.modid + ":food/potWater")
			.setCreativeTab(BuildingRails.creativeTab);
	public static Item itemStock = new ItemPotFood(1, false)
			.setUnlocalizedName("building_rails.item.stock").setTextureName(
					BuildingRails.modid + ":food/potStock");

	// Food (others)
	public static Item itemRoastedPumpkin = new ItemFood(1, false)
			.setUnlocalizedName("building_rails.item.roastedPumpkin")
			.setTextureName(BuildingRails.modid + ":food/roastedPumpkin");
	public static Item itemSashimi = new ItemFood(1, false).setUnlocalizedName(
			"building_rails.item.sashimi").setTextureName(
			BuildingRails.modid + ":food/sashimi");
	public static Item itemSucklingPorkchop = new ItemFood(1, true)
			.setUnlocalizedName("building_rails.item.sucklingPorkchop")
			.setTextureName(BuildingRails.modid + ":food/sucklingPorkchop");

	public static void registerItems() {
		// Crafting Items
		GameRegistry.registerItem(BRItems.itemMaterials, "itemMaterials");
		for (Material m : ItemMaterial.Material.values()) {
			OreDictionary.registerOre(m.oreDict, new ItemStack(BRItems.itemMaterials, 1, m.ordinal()));
		}

		// Explosives
		GameRegistry.registerItem(BRItems.itemDynamite, "itemDynamite");
		GameRegistry.registerItem(BRItems.blastingCap_iron, "blastingCap_iron");
		GameRegistry.registerItem(BRItems.blastingCap_steel,
				"blastingCap_steel");

		// Food (ingredients)
		GameRegistry.registerItem(BRItems.itemBatWing, "itemBatWing");
		GameRegistry.registerItem(BRItems.itemDough, "itemDough");
		GameRegistry.registerItem(BRItems.itemFlour, "itemFlour");
		GameRegistry.registerItem(BRItems.itemMuttonRaw, "itemMuttonRaw");
		GameRegistry.registerItem(BRItems.itemMutton, "itemMutton");

		// Food (bowl)
		GameRegistry.registerItem(BRItems.itemFruitSalad, "itemFruitSalad");
		GameRegistry.registerItem(BRItems.itemMashedPotatoes,
				"itemMashedPotatoes");

		// Food (pot)
		GameRegistry.registerItem(BRItems.itemPotEmpty, "itemPotEmpty");
		GameRegistry.registerItem(BRItems.itemPotMonsterStew,
				"itemPotMonsterStew");
		GameRegistry.registerItem(BRItems.itemPotMushsterStew,
				"itemPotMushsterStew");
		GameRegistry.registerItem(BRItems.itemPotRawMonsterStew,
				"itemPotRawMonsterStew");
		GameRegistry.registerItem(BRItems.itemPotRawMushsterStew,
				"itemPotRawMushsterStew");
		GameRegistry.registerItem(BRItems.itemPotWater, "itemPotWater");
		GameRegistry.registerItem(BRItems.itemStock, "itemStock");

		// Food (others)
		GameRegistry.registerItem(BRItems.itemRoastedPumpkin,
				"itemRoastedPumpkin");
		GameRegistry.registerItem(BRItems.itemSalad, "itemSalad");
		GameRegistry.registerItem(BRItems.itemSashimi, "itemSashimi");
		GameRegistry.registerItem(BRItems.itemSucklingPorkchop,
				"itemSucklingPorkchop");
	}

	public static void registerRecipes() {
		// standard dynamite
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemDynamite),
				" s ", "pgp", "pgp", 's', Items.string, 'p', Items.paper, 'g',
				Items.gunpowder));
		// sticky dynamite
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemDynamite,
				8, 2), "ddd", "dtd", "ddd", 'd', new ItemStack(itemDynamite, 1,
				0), 't', new ItemStack(itemMaterials, 1,
				ItemMaterial.Material.ITEM_TAR.ordinal())));
		// ender dynamite
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemDynamite,
				8, 1), "ddd", "ded", "ddd", 'd', new ItemStack(itemDynamite, 1,
				0), 'e', Items.ender_pearl));
		// combo dynamite
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemDynamite,
				8, 3), "ddd", "ded", "ddd", 'd', new ItemStack(itemDynamite, 1,
				2), 'e', Items.ender_pearl));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemDynamite,
				8, 3), "ddd", "dtd", "ddd", 'd', new ItemStack(itemDynamite, 1,
				1), 't', new ItemStack(itemMaterials, 1,
						ItemMaterial.Material.ITEM_TAR.ordinal())));

		// blasting caps
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				blastingCap_iron), " f ", "iri", " i ", 'f',
				Items.flint_and_steel, 'r', "dustRedstone", 'i', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(
				blastingCap_steel), " f ", "iri", " i ", 'f',
				Items.flint_and_steel, 'r', "dustRedstone", 'i', "ingotSteel"));
	}

	public static void registerDispenserBehaviors() {
		BlockDispenser.dispenseBehaviorRegistry.putObject(BRItems.itemDynamite,
				ItemDynamite.dispenserDynamiteBehavior);
	}
}

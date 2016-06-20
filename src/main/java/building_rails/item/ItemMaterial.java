package building_rails.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import building_rails.BuildingRails;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class ItemMaterial extends Item {
	private final IIcon[] icons;
	
	public ItemMaterial() {
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(BuildingRails.creativeTab);
		
		icons = new IIcon[Material.values().length];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		damage = MathHelper.clamp_int(damage, 0, Material.values().length);
		return icons[damage];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		int numParts = Material.values().length;
		
		for (int i = 0; i < numParts; i++) {
			icons[i] = register.registerIcon(Material.values()[i].iconKey);
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = MathHelper.clamp_int(stack.getItemDamage(), 0, Material.values().length);
		return Material.values()[i].unlocalizedName;
	}
	
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List lst) {
		for (int i = 0; i < Material.values().length; i++) {
			lst.add(new ItemStack(item, 1, i));
		}
	}

	public enum Material {
		ITEM_TAR("itemTar"),
		DYNAMO_REDSTONE("dynamoRedstone"),
		REDSTONE_ALLOY_INGOT("ingotRedstoneAlloy"),
		PLASTIC_INGOT("ingotPlastic"),
		ITEM_GREEN_SLUDGE("sludgeGreen"),
		BRONZE_NUGGET("nuggetBronze");
		
		public final String unlocalizedName;
		public final String oreDict;
		public final String iconKey;
		
		private Material(String unlocalizedName) {
			this.unlocalizedName = "building_rails.item." + unlocalizedName;
			this.iconKey = "building_rails:" + unlocalizedName;
			this.oreDict = unlocalizedName;
		}
	}
}
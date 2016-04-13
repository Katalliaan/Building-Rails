package building_rails.item;

import building_rails.BuildingRails;
import building_rails.entity.EntityThrownDynamite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;

import java.util.List;

public class ItemDynamite extends Item {
	private int subtypeCount;
	private IIcon[] icons;
	
	public ItemDynamite() {
		this.setHasSubtypes(true);
		this.subtypeCount = 2;
		this.setMaxStackSize(64);
		this.setCreativeTab(BuildingRails.creativeTab);
		this.setTextureName(BuildingRails.modid + ":itemDynamite");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItemDamage() == 1)
			return "building_rails.item.itemDynamite.ender";
		
		return "building_rails.item.itemDynamite";
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < subtypeCount; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta];
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.icons = new IIcon[this.subtypeCount];
		
		for (int i = 0; i < subtypeCount; i++) {
			if (i == 0)
				this.icons[i] = iconRegister.registerIcon(BuildingRails.modid + ":itemDynamite");
			if (i == 1)
				this.icons[i] = iconRegister.registerIcon(BuildingRails.modid + ":itemDynamiteEnder");
		}
	}
	
	private static boolean isEnder(ItemStack stack) {
		return (stack.getItemDamage() == 1);
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer entityPlayer) {
		boolean hasLighter = false;
		int lighterSlot = -1;

		for (int i = 0; i < 8 && hasLighter == false; i++) {
			if (entityPlayer.inventory.mainInventory[i] != null) {
				Item checkedItem = entityPlayer.inventory.mainInventory[i]
						.getItem();

				if (checkedItem instanceof ItemFlintAndSteel
						|| checkedItem == Items.fire_charge) {
					hasLighter = true;
					lighterSlot = i;
				}
			}
		}

		if (hasLighter) {
			ItemStack lighter = entityPlayer.inventory.mainInventory[lighterSlot];

			if (lighter.getItem() instanceof ItemFlintAndSteel) {
				lighter.setItemDamage(lighter.getItemDamage() + 1);
				if (lighter.getItemDamage() > lighter.getMaxDamage())
				{
					entityPlayer.renderBrokenItemStack(lighter);
					entityPlayer.addStat(StatList.objectBreakStats[Item.getIdFromItem(lighter.getItem())], 1);
					entityPlayer.inventory.mainInventory[lighterSlot] = null;
				}
			} else if (Math.random() <= 0.25) {
				if (--lighter.stackSize <= 0)
	            {
					entityPlayer.inventory.mainInventory[lighterSlot] = null;
	            }
			}

			itemStack.stackSize--;
			
			world.playSoundAtEntity(entityPlayer, "game.tnt.primed", 1.0F, 1.0F);
			
			EntityThrownDynamite thrownDynamite = new EntityThrownDynamite(world, entityPlayer, isEnder(itemStack));
				world.spawnEntityInWorld(thrownDynamite);
		}

		return itemStack;
	}
}

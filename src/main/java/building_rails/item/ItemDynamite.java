package building_rails.item;

import building_rails.BuildingRails;
import building_rails.entity.EntityThrownDynamite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemDynamite extends Item {

	public ItemDynamite() {
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setCreativeTab(BuildingRails.creativeTab);
		this.setTextureName(BuildingRails.modid + ":itemDynamite");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "building_rails.item.itemDynamite";
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
			
			EntityThrownDynamite thrownDynamite = new EntityThrownDynamite(world, entityPlayer);
				world.spawnEntityInWorld(thrownDynamite);
		}

		return itemStack;
	}
}

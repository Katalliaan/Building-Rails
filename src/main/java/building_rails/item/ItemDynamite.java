package building_rails.item;

import building_rails.BuildingRails;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDynamite extends Item {

	public ItemDynamite() {
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		this.setCreativeTab(BuildingRails.creativeTab);
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
				lighter.damageItem(1, entityPlayer);
			} else if (Math.random() <= 0.25) {
				lighter.stackSize--;
			}

			itemStack.stackSize--;
		}

		return itemStack;
	}
}

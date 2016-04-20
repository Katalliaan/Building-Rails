package building_rails.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemContainerFood extends ItemFood {
	public ItemContainerFood(int p_i45340_1_, boolean p_i45340_2_) {
		super(p_i45340_1_, p_i45340_2_);
	}
	
	public ItemContainerFood(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
	}

	@Override
	protected void onFoodEaten(ItemStack itemStack, World world,
			EntityPlayer entityPlayer) {
		super.onFoodEaten(itemStack, world, entityPlayer);
		if (!world.isRemote) {
			ItemStack emptyStack = this.getContainer();
			
			if (!entityPlayer.inventory.addItemStackToInventory(emptyStack)) {
				world.spawnEntityInWorld(new EntityItem(world,
						entityPlayer.posX, entityPlayer.posY,
						entityPlayer.posZ, emptyStack));
			}
		}
	}
	
	abstract ItemStack getContainer();
}
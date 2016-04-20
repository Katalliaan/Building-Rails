package building_rails.item;

import building_rails.BRItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemBowlFood extends ItemFood {

	public ItemBowlFood(int p_i45340_1_, boolean p_i45340_2_) {
		super(p_i45340_1_, p_i45340_2_);
	}

	public ItemBowlFood(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
	}
	
	@Override
	protected void onFoodEaten(ItemStack itemStack, World world,
			EntityPlayer entityPlayer) {
		if (!world.isRemote) {
			if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(
					Items.bowl))) {
				world.spawnEntityInWorld(new EntityItem(world,
						entityPlayer.posX, entityPlayer.posY,
						entityPlayer.posZ, new ItemStack(Items.bowl)));
			}
		}
	}
}

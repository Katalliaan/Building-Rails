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

public class ItemRawMonsterStew extends ItemFood {
	public ItemRawMonsterStew(int i, boolean b) {
		this(i, 0.6f, b);
	}

	public ItemRawMonsterStew(int p_i45339_1_, float p_i45339_2_,
			boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
	}

	@Override
	protected void onFoodEaten(ItemStack itemStack, World world,
			EntityPlayer entityPlayer) {
		if (!world.isRemote) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id,
					30 * 20, 0));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id,
					5 * 20, 0));

			if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(
					BRItems.itemPotEmpty))) {
				world.spawnEntityInWorld(new EntityItem(world,
						entityPlayer.posX, entityPlayer.posY,
						entityPlayer.posZ, new ItemStack(BRItems.itemPotEmpty)));
			}
		}
	}
}

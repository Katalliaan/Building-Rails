package building_rails.item;

import building_rails.BRItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemMonsterStew extends ItemPotFood {
	public ItemMonsterStew(int i, boolean b) {
		this(i, 0.6f, b);
	}

	public ItemMonsterStew(int p_i45339_1_, float p_i45339_2_,
			boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
	}

	@Override
	protected void onFoodEaten(ItemStack itemStack, World world,
			EntityPlayer entityPlayer) {
		super.onFoodEaten(itemStack, world, entityPlayer);
		if (!world.isRemote) {
			if (world.rand.nextFloat() < 0.5f) {
				entityPlayer.addPotionEffect(new PotionEffect(
						Potion.confusion.id, 10 * 20, 0));
			}
			if (world.rand.nextFloat() < 0.2f) {
				entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id,
						5 * 20, 0));
			}
		}
	}
}
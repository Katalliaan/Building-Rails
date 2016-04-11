package building_rails.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemMushsterStew extends ItemFood {
	public ItemMushsterStew(int i, boolean b) {
		this(i, 0.6f, b);
	}

	public ItemMushsterStew(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
	}

	@Override
	protected void onFoodEaten(ItemStack itemStack, World world,
			EntityPlayer entityPlayer) {
		if (!world.isRemote && world.rand.nextFloat() < 0.3f) {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id,
					10 * 20, 0));
		}
	}
}

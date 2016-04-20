package building_rails.item;

import building_rails.BRItems;
import net.minecraft.item.ItemStack;

public class ItemPotFood extends ItemContainerFood {

	public ItemPotFood(int p_i45340_1_, boolean p_i45340_2_) {
		super(p_i45340_1_, p_i45340_2_);
	}

	public ItemPotFood(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
	}

	@Override
	ItemStack getContainer() {
		return new ItemStack(BRItems.itemPotEmpty);
	}
}
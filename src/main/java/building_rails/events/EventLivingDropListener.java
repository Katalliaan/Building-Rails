package building_rails.events;

import java.util.Random;

import building_rails.BRItems;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventLivingDropListener {

	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event) {
		Random rand = new Random();
		int quantity;
		
		if (event.entityLiving instanceof EntityBat) {
			quantity = rand.nextInt(2) + 1;
			event.entityLiving.dropItem(BRItems.itemBatWing, quantity);
		}
		
		if (event.entityLiving instanceof EntitySheep && !event.entityLiving.isChild()) {
			quantity = rand.nextInt(3) + 1;
			event.entityLiving.dropItem(BRItems.itemMuttonRaw, quantity);
		}
	}
}

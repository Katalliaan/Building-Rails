package building_rails.events;

import building_rails.block.BlockOreFissure;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.ExplosionEvent;

public class ExplosionListener {

	@SubscribeEvent
	public void onExplosionDetonate(ExplosionEvent.Detonate event) {
		if (!event.world.isRemote) {
			int range = 2;

			int xMin = (int) (event.explosion.explosionX - range);
			int xMax = (int) (event.explosion.explosionX + range);
			int yMin = (int) (event.explosion.explosionY - range);
			int yMax = (int) (event.explosion.explosionY + range);
			int zMin = (int) (event.explosion.explosionZ - range);
			int zMax = (int) (event.explosion.explosionZ + range);

			if (yMin < 0)
				yMin = 0;

			for (int x = xMin; x < xMax; x++) {
				for (int y = yMin; y < yMax; y++) {
					for (int z = zMin; z < zMax; z++) {
						if (event.world.getBlock(x, y, z) instanceof BlockOreFissure) {
							BlockOreFissure.crack(event.world, x, y, z);
						}
					}
				}
			}
		}
	}
}
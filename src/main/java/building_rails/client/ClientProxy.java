package building_rails.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import building_rails.CommonProxy;
import building_rails.entity.EntityThrownDynamite;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(
				EntityThrownDynamite.class, new RenderThrownDynamite());
	}
}

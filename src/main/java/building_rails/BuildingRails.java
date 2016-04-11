package building_rails;

import java.util.logging.Logger;

import building_rails.entity.EntityThrownDynamite;
import building_rails.events.EventLivingDropListener;
import building_rails.item.ItemDynamite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = BuildingRails.modid, name = BuildingRails.name, version = BuildingRails.version, dependencies = BuildingRails.dependencies)
public class BuildingRails {
	public static final String modid = "building_rails";
	public static final String name = "@NAME@";
	public static final String version = "@VERSION@";
	public static final String dependencies = "";

	// The instance of your mod that Forge uses.
	@Instance(value = "building_rails")
	public static BuildingRails instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "building_rails.client.ClientProxy", serverSide = "building_rails.CommonProxy")
	public static CommonProxy proxy;

	// Shared logger
	public static final Logger logger = Logger.getLogger("Building_Rails");

	public static final CreativeTabs creativeTab = new CreativeTabs(
			"Building Rails") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Blocks.rail);
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		BRItems.registerItems();

		EntityRegistry.registerModEntity(EntityThrownDynamite.class,
				"ThrownDynamite", 0, this, 64, 1, true);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		BRItems.registerRecipes();
		
		proxy.registerRenderers();
		
		MinecraftForge.EVENT_BUS.register(new EventLivingDropListener());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}

package building_rails;

import cpw.mods.fml.common.registry.GameRegistry;
import building_rails.block.*;
import building_rails.tileentity.*;
import net.minecraft.block.Block;

public class BRBlocks {
	public static Block blockOreFissure = new BlockOreFissure();
	
	public static void registerBlocks() {
		GameRegistry.registerBlock(blockOreFissure, "blockOreFissure");
	}
	
	public static void registerTEs() {
		GameRegistry.registerTileEntity(TileEntityOreFissure.class, "tileEntityOreFissure");
	}
}

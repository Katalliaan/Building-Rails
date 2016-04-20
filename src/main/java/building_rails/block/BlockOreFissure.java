package building_rails.block;

import building_rails.BuildingRails;
import building_rails.tileentity.TileEntityOreFissure;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockOreFissure extends Block implements ITileEntityProvider {
	public BlockOreFissure() {
		super(Material.rock);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
		this.setStepSound(soundTypePiston);
		this.disableStats();
		this.setCreativeTab(BuildingRails.creativeTab);
	}
	
	public static void crack(World world, int posX, int posY, int posZ) {
		int meta = world.getBlockMetadata(posX, posY, posZ);
		
		if (meta < 3) {
			world.setBlockMetadataWithNotify(posX, posY, posZ, meta + 1, 2);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityOreFissure();
	}
}
package building_rails.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityOreFissure extends TileEntity {
	FissureBiome biome;

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (biome == null) {
			biome = FissureBiome.getBiome(worldObj.getBiomeGenForCoords(xCoord,
					zCoord));
		}
		if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 3 && !worldObj.isRemote) {
			ItemStack stack;

			switch (this.biome) {
			case COPPER:
				stack = OreDictionary.getOres("oreCopper").get(0);
				break;
			case GOLD:
				stack = new ItemStack(Blocks.gold_ore);
				break;
			case IRON:
				stack = new ItemStack(Blocks.iron_ore);
				break;
			case TIN:
				stack = OreDictionary.getOres("oreTin").get(0);
				break;
			default:
				stack = new ItemStack(Blocks.cobblestone);
				break;
			}

			worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord + 0.5F,
					yCoord + 1, zCoord + 0.5F, stack));
		}
	}

	public static enum FissureBiome {
		IRON, COPPER, TIN, GOLD, DEFAULT;

		public static FissureBiome getBiome(BiomeGenBase biome) {
			if (BiomeDictionary.isBiomeOfType(biome, Type.MOUNTAIN)) {
				return FissureBiome.IRON;
			} else if (BiomeDictionary.isBiomeOfType(biome, Type.COLD)) {
				return FissureBiome.COPPER;
			} else if (BiomeDictionary.isBiomeOfType(biome, Type.HOT)
					&& !BiomeDictionary.isBiomeOfType(biome, Type.NETHER)) {
				return FissureBiome.TIN;
			} else if (BiomeDictionary.isBiomeOfType(biome, Type.NETHER)) {
				return FissureBiome.GOLD;
			} else {
				return FissureBiome.DEFAULT;
			}
		}
	}
}

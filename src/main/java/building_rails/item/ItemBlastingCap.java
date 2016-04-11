package building_rails.item;

import building_rails.BRItems;
import building_rails.BuildingRails;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlastingCap extends Item {
	protected CapMaterial capMat;
	
	public ItemBlastingCap(CapMaterial mat) {
		this.maxStackSize = 1;
		this.capMat = mat;
		this.setMaxDamage(mat.maxUses);
		this.setCreativeTab(BuildingRails.creativeTab);
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		return "building_rails.item.blastingCap_" + capMat.unlocName;
	}
	
    public boolean onItemUse(ItemStack stack, EntityPlayer entityPlayer, World world, int xCoord, int yCoord, int zCoord, int side, float px, float py, float pz) {
		boolean hasDynamite = false;
		int dynamiteSlot = -1;
    	
		for (int i = 0; i < 8 && hasDynamite == false; i++) {
			if (entityPlayer.inventory.mainInventory[i] != null) {
				ItemStack checkedStack = entityPlayer.inventory.mainInventory[i];

				if (checkedStack.getItem() == BRItems.itemDynamite && checkedStack.getItemDamage() == 0) {
					hasDynamite = true;
					dynamiteSlot = i;
				}
			}
		}
		
		if (hasDynamite) {
			int xOffset = 0;
			int yOffset = 0;
			int zOffset = 0;
			
			switch(side) {
			case 0:
			case 1:
				xOffset = 1;
				zOffset = 1;
				break;
			case 2:
			case 3:
				xOffset = 1;
				yOffset = 1;
				break;
			case 4:
			case 5:
				yOffset = 1;
				zOffset = 1;
			}
			
			for (int xIter = xCoord - xOffset; xIter <= xCoord + xOffset; xIter++) {
				for (int yIter = yCoord - yOffset; yIter <= yCoord + yOffset; yIter++) {
					for (int zIter = zCoord - zOffset; zIter <= zCoord + zOffset; zIter++) {
						Block block = world.getBlock(xIter, yIter, zIter);
						int metadata = world.getBlockMetadata(xIter, yIter, zIter);
						
						if (block.getMaterial() != Material.air && block.getExplosionResistance(entityPlayer) <= 56.0F) {
							block.dropBlockAsItemWithChance(world, xIter, yIter, zIter, metadata, 1.0F, 0);
							world.setBlock(xIter, yIter, zIter, Blocks.air);
						}
					}
				}
			}
			
			world.playSoundEffect(xCoord, yCoord, zCoord, "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
			world.spawnParticle("largeexplode", xCoord, yCoord + 1, zCoord, 1.0D, 0.0D, 0.0D);
			
			stack.damageItem(1, entityPlayer);
			if (--entityPlayer.inventory.mainInventory[dynamiteSlot].stackSize <= 0)
            {
				entityPlayer.inventory.mainInventory[dynamiteSlot] = null;
            }
			
			return true;
		}
		else {
			return false;
		}
    }
    
    public int getItemEnchantability() {
    	return capMat.getEnchantability();
    }
    
    public static enum CapMaterial {
    	IRON(64, 14, "iron"),
    	STEEL(128, 9, "steel");
    	
    	private final int maxUses;
    	private final int enchantability;
    	private final String unlocName;
    	
    	CapMaterial(int dur, int ench, String unloc) {
    		this.maxUses = dur;
    		this.enchantability = ench;
    		this.unlocName = unloc;
    	}
    	
    	public int getMaxUses() {
    		return this.maxUses;
    	}
    	
    	public int getEnchantability() {
    		return this.enchantability;
    	}
    }
}

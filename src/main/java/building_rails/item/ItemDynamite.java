package building_rails.item;

import building_rails.BuildingRails;
import building_rails.entity.EntityThrownDynamite;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;

import java.util.List;

public class ItemDynamite extends Item {
	private int subtypeCount;
	private IIcon[] icons;
	
	public ItemDynamite() {
		this.setHasSubtypes(true);
		this.subtypeCount = 2;
		this.setMaxStackSize(64);
		this.setCreativeTab(BuildingRails.creativeTab);
		this.setTextureName(BuildingRails.modid + ":itemDynamite");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItemDamage() == 1)
			return "building_rails.item.itemDynamite.ender";
		
		return "building_rails.item.itemDynamite";
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < subtypeCount; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta];
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		this.icons = new IIcon[this.subtypeCount];
		
		for (int i = 0; i < subtypeCount; i++) {
			if (i == 0)
				this.icons[i] = iconRegister.registerIcon(BuildingRails.modid + ":itemDynamite");
			if (i == 1)
				this.icons[i] = iconRegister.registerIcon(BuildingRails.modid + ":itemDynamiteEnder");
		}
	}
	
	private static boolean isEnder(ItemStack stack) {
		return (stack.getItemDamage() == 1);
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world,
			EntityPlayer entityPlayer) {
		boolean hasLighter = false;
		int lighterSlot = -1;

		for (int i = 0; i < 8 && hasLighter == false; i++) {
			if (entityPlayer.inventory.mainInventory[i] != null) {
				Item checkedItem = entityPlayer.inventory.mainInventory[i]
						.getItem();

				if (checkedItem instanceof ItemFlintAndSteel
						|| checkedItem == Items.fire_charge) {
					hasLighter = true;
					lighterSlot = i;
				}
			}
		}

		if (hasLighter) {
			ItemStack lighter = entityPlayer.inventory.mainInventory[lighterSlot];

			if (lighter.getItem() instanceof ItemFlintAndSteel) {
				lighter.setItemDamage(lighter.getItemDamage() + 1);
				if (lighter.getItemDamage() > lighter.getMaxDamage())
				{
					entityPlayer.renderBrokenItemStack(lighter);
					entityPlayer.addStat(StatList.objectBreakStats[Item.getIdFromItem(lighter.getItem())], 1);
					entityPlayer.inventory.mainInventory[lighterSlot] = null;
				}
			} else if (Math.random() <= 0.25) {
				if (--lighter.stackSize <= 0)
	            {
					entityPlayer.inventory.mainInventory[lighterSlot] = null;
	            }
			}

			itemStack.stackSize--;
			
			world.playSoundAtEntity(entityPlayer, "game.tnt.primed", 1.0F, 1.0F);
			
			EntityThrownDynamite thrownDynamite = new EntityThrownDynamite(world, entityPlayer, isEnder(itemStack));
				world.spawnEntityInWorld(thrownDynamite);
		}

		return itemStack;
	}
	
	public static final IBehaviorDispenseItem dispenserDynamiteBehavior = new BehaviorDefaultDispenseItem() {
		private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
		
		/**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack dispenseStack(IBlockSource blockSource, ItemStack stack)
        {
            EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
            World world = blockSource.getWorld();
            double posX = blockSource.getX() + (double)((float)enumfacing.getFrontOffsetX() * 1.125F);
            double posY = blockSource.getY() + (double)((float)enumfacing.getFrontOffsetY() * 1.125F);
            double posZ = blockSource.getZ() + (double)((float)enumfacing.getFrontOffsetZ() * 1.125F);
            int i = blockSource.getXInt() + enumfacing.getFrontOffsetX();
            int j = blockSource.getYInt() + enumfacing.getFrontOffsetY();
            int k = blockSource.getZInt() + enumfacing.getFrontOffsetZ();
            Block block = world.getBlock(i, j, k);

            if (block instanceof BlockFire && !isEnder(stack))
            {
            	world.playSoundEffect(posX, posY, posZ, "game.tnt.primed", 1.0F, 1.0F);
            	
            	EntityThrownDynamite thrownDynamite = new EntityThrownDynamite(world, posX, posY, posZ, enumfacing);
                world.spawnEntityInWorld(thrownDynamite);

            }
            else
            {
            	return this.behaviourDefaultDispenseItem.dispense(blockSource, stack);
            }
            
            stack.splitStack(1);
            return stack;
        }
        /**
         * Play the dispense sound from the specified block.
         */
        protected void playDispenseSound(IBlockSource p_82485_1_)
        {
            p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
        }
	};
}

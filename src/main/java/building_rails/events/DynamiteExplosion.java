package building_rails.events;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import building_rails.entity.EntityThrownDynamite;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class DynamiteExplosion extends Explosion {

	boolean ender;
	private World world;
	private Random explosionRNG = new Random();

	public DynamiteExplosion(World world, Entity exploder, double explosionX,
			double explosionY, double explosionZ, float explosionSize) {
		this(world, exploder, explosionX, explosionY, explosionZ,
				explosionSize, false);
	}

	public DynamiteExplosion(World world, Entity exploder, double explosionX,
			double explosionY, double explosionZ, float explosionSize,
			boolean ender) {
		super(world, exploder, explosionX, explosionY, explosionZ,
				explosionSize);
		this.world = world;
		this.explosionSize = explosionSize;
		this.ender = ender;
	}

	@Override
	public void doExplosionB(boolean bool) {
		super.doExplosionB(bool);

		if (!world.isRemote) {
			if (!this.isSmoking) {
				this.affectedBlockPositions.clear();
			}

			Iterator iterator = world.playerEntities.iterator();

			while (iterator.hasNext()) {
				EntityPlayer entityplayer = (EntityPlayer) iterator.next();

				if (entityplayer.getDistanceSq(this.explosionX,
						this.explosionY, this.explosionZ) < 4096.0D) {
					((EntityPlayerMP) entityplayer).playerNetServerHandler
							.sendPacket(new S27PacketExplosion(this.explosionX,
									this.explosionY, this.explosionZ,
									this.explosionSize,
									this.affectedBlockPositions, (Vec3) this
											.func_77277_b().get(entityplayer)));
				}
			}
		}

		if (ender) {
			double offset = 2.0D;

			int minX = MathHelper.floor_double(this.explosionX
					- (double) this.explosionSize - offset);
			int minY = MathHelper.floor_double(this.explosionX
					+ (double) this.explosionSize + offset);
			int minZ = MathHelper.floor_double(this.explosionY
					- (double) this.explosionSize - offset);
			int maxX = MathHelper.floor_double(this.explosionY
					+ (double) this.explosionSize + offset);
			int maxY = MathHelper.floor_double(this.explosionZ
					- (double) this.explosionSize - offset);
			int maxZ = MathHelper.floor_double(this.explosionZ
					+ (double) this.explosionSize + offset);

			List list = this.world.getEntitiesWithinAABBExcludingEntity(
					this.exploder, AxisAlignedBB.getBoundingBox((double) minX,
							(double) minZ, (double) maxY, (double) minY,
							(double) maxX, (double) maxZ));

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof EntityItem) {
					EntityItem item = (EntityItem) list.get(i);

					item.setLocationAndAngles(exploder.posX, exploder.posY,
							exploder.posZ, item.rotationYaw, item.rotationPitch);
				}

				if (list.size() > 0) {
					world.playSoundAtEntity(exploder, "mob.endermen.portal",
							1.0F, 1.0F);
				}
			}
		}
	}

	@Override
	public EntityLivingBase getExplosivePlacedBy() {
		return this.exploder == null ? null
				: (this.exploder instanceof EntityThrownDynamite ? ((EntityThrownDynamite) this.exploder).shootingEntity
						: (this.exploder instanceof EntityLivingBase ? (EntityLivingBase) this.exploder
								: null));
	}
}

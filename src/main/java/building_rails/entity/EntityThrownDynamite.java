package building_rails.entity;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import building_rails.BuildingRails;
import building_rails.events.DynamiteExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityThrownDynamite extends EntityThrowable implements IEntityAdditionalSpawnData {
	private int fuse;
	private boolean inGround;
	public EntityLivingBase shootingEntity;

	public boolean ender;
	public boolean sticky;
	public boolean stuck;
	private int stuckEntityID;
	private double xHit = 0.0D;
	private double yHit = 0.0D;
	private double zHit = 0.0D;

	private final float _speed = 0.75f;
	private final float _power = 3.0f;

	public EntityThrownDynamite(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
		this.renderDistanceWeight = 10.0D;
		this.fuse = 80;
		this.yOffset = 0.0F;

		this.stuck = false;
	}

	public EntityThrownDynamite(World world, double posX, double posY,
			double posZ, EnumFacing enumFacing, boolean sticky) {
		this(world);

		this.ender = false;
		this.sticky = sticky;
		this.setPosition(posX, posY, posZ);

		this.setThrowableHeading(enumFacing.getFrontOffsetX(),
				enumFacing.getFrontOffsetY(), enumFacing.getFrontOffsetZ(),
				_speed, 1.0F);
	}

	public EntityThrownDynamite(World world, EntityLivingBase entityLiving,
			boolean ender, boolean sticky) {
		this(world);
		this.shootingEntity = entityLiving;
		this.ender = ender;
		this.sticky = sticky;

		this.setLocationAndAngles(entityLiving.posX, entityLiving.posY
				+ (double) entityLiving.getEyeHeight(), entityLiving.posZ,
				entityLiving.rotationYaw, entityLiving.rotationPitch);
		this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F
				* (float) Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F
				* (float) Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F
				* (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F
				* (float) Math.PI));
		this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F
				* (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F
				* (float) Math.PI));
		this.motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F
				* (float) Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ,
				_speed, 1.0F);
	}

	@Override
	protected void entityInit() {
	}

	public void onUpdate() {
		if (this.sticky) {
			if (this.stuck) {
				Entity stuckEntity = this.worldObj
						.getEntityByID(this.stuckEntityID);

				if (this.stuckEntityID != 0 && stuckEntity == null) {
					this.stuck = false;
					this.stuckEntityID = 0;
				} else if (this.stuckEntityID != 0 && stuckEntity != null) {
					this.setPosition(stuckEntity.posX, stuckEntity.posY + stuckEntity.yOffset, stuckEntity.posZ);
				}
			} else {
				super.onUpdate();
			}
		}
		else {
			this.prevPosX = this.posX;
	        this.prevPosY = this.posY;
	        this.prevPosZ = this.posZ;
	        this.motionY -= 0.03999999910593033D;
	        this.moveEntity(this.motionX, this.motionY, this.motionZ);
	        this.motionX *= 0.9800000190734863D;
	        this.motionY *= 0.9800000190734863D;
	        this.motionZ *= 0.9800000190734863D;

	        if (this.onGround)
	        {
	            this.motionX *= 0.699999988079071D;
	            this.motionZ *= 0.699999988079071D;
	            this.motionY *= -0.5D;
	        }
		}

		if (this.fuse-- <= 0) {
			this.setDead();

			if (!this.worldObj.isRemote) {
				this.explode();
			}
		} else {
			this.worldObj.spawnParticle("smoke", this.posX, this.posY,
					this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	protected void onImpact(MovingObjectPosition mop) {
		if (sticky) {
			if (!this.stuck && mop.typeOfHit == MovingObjectType.ENTITY) {
				if (mop.entityHit != this.shootingEntity) {
					mop.entityHit.attackEntityFrom(
							DamageSource.causeThrownDamage(this, getThrower()),
							0.0F);

					this.stuckEntityID = mop.entityHit.getEntityId();

					this.motionX = mop.entityHit.motionX;
					this.motionY = mop.entityHit.motionY;
					this.motionZ = mop.entityHit.motionZ;

					this.stuck = true;
				}
			} else if (!this.stuck && mop.typeOfHit == MovingObjectType.BLOCK) {
				this.xHit = this.posX;
				this.yHit = this.posY;
				this.zHit = this.posZ;

				this.motionX = 0.0D;
				this.motionY = 0.0D;
				this.motionZ = 0.0D;

				this.stuck = true;
			}
		}
	}

	private void explode() {
		DynamiteExplosion explosion = new DynamiteExplosion(this.worldObj,
				this.shootingEntity, this.posX, this.posY, this.posZ, _power,
				ender);
		explosion.isFlaming = false;
		explosion.isSmoking = true;
		if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(
				this.worldObj, explosion))
			return;
		explosion.doExplosionA();
		explosion.doExplosionB(true);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);

		this.ender = nbttagcompound.getBoolean("ender");
		this.sticky = nbttagcompound.getBoolean("sticky");

		this.fuse = nbttagcompound.getShort("fuse");

		if (nbttagcompound.hasKey("direction")) {
			NBTTagList nbttaglist = nbttagcompound.getTagList("direction", 6);
			this.motionX = nbttaglist.func_150309_d(0);
			this.motionY = nbttaglist.func_150309_d(1);
			this.motionZ = nbttaglist.func_150309_d(2);
		} else {
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);

		nbttagcompound.setTag(
				"direction",
				this.newDoubleNBTList(new double[] { this.motionX,
						this.motionY, this.motionZ }));

		nbttagcompound.setShort("fuse", (short) this.fuse);
		nbttagcompound.setBoolean("ender", ender);
		nbttagcompound.setBoolean("sticky", sticky);
	}

	@Override
	public void setThrowableHeading(double x, double y, double z, float force,
			float coneOfFire) {
		float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
		x /= (double) f2;
		y /= (double) f2;
		z /= (double) f2;
		x += this.rand.nextGaussian()
				* (double) (this.rand.nextBoolean() ? -1 : 1)
				* 0.007499999832361937D * (double) coneOfFire;
		y += this.rand.nextGaussian()
				* (double) (this.rand.nextBoolean() ? -1 : 1)
				* 0.007499999832361937D * (double) coneOfFire;
		z += this.rand.nextGaussian()
				* (double) (this.rand.nextBoolean() ? -1 : 1)
				* 0.007499999832361937D * (double) coneOfFire;
		x *= (double) force;
		y *= (double) force;
		z *= (double) force;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f3 = MathHelper.sqrt_double(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y,
				(double) f3) * 180.0D / Math.PI);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeBoolean(this.ender);
		buffer.writeBoolean(this.sticky);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.ender = additionalData.readBoolean();
		this.sticky = additionalData.readBoolean();
		
	}
}

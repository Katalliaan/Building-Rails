package building_rails.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityThrownDynamite extends Entity implements IProjectile {
	private int xTile;
	private int yTile;
	private int zTile;
	private int inTile;
	private int fuse;
	private boolean inGround;
	public EntityLivingBase shootingEntity;
	private int ticksAlive;
	private int ticksInAir;

	public EntityThrownDynamite(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
	}

	public EntityThrownDynamite(World world, EntityLivingBase entityLiving) {
		super(world);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = entityLiving;
		float speed = 1.5F;
		fuse = 80;

		this.setSize(1.0F, 1.0F);
		this.setLocationAndAngles(entityLiving.posX, entityLiving.posY
				+ (double) entityLiving.getEyeHeight(), entityLiving.posZ,
				entityLiving.rotationYaw, entityLiving.rotationPitch);
		this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F
				* (float) Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F
				* (float) Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F
				* (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F
				* (float) Math.PI));
		this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F
				* (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F
				* (float) Math.PI));
		this.motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F
				* (float) Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ,
				speed, 1.0F);
	}

	@Override
	protected void entityInit() { }
	
	public void onUpdate() {
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

        if (this.fuse-- <= 0)
        {
            this.setDead();

            if (!this.worldObj.isRemote)
            {
                this.explode();
            }
        }
        else
        {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
	}
	
	private void explode()
    {
        float f = 4.0F;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, f, true);
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		this.xTile = nbttagcompound.getShort("xTile");
		this.yTile = nbttagcompound.getShort("yTile");
		this.zTile = nbttagcompound.getShort("zTile");
		this.inTile = nbttagcompound.getByte("inTile") & 255;
		this.inGround = nbttagcompound.getByte("inGround") == 1;
		
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
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setShort("xTile", (short) this.xTile);
		nbttagcompound.setShort("yTile", (short) this.yTile);
		nbttagcompound.setShort("zTile", (short) this.zTile);
		nbttagcompound.setByte("inTile", (byte) this.inTile);
		nbttagcompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		nbttagcompound.setTag(
				"direction",
				this.newDoubleNBTList(new double[] { this.motionX,
						this.motionY, this.motionZ }));
		
		nbttagcompound.setShort("fuse", (short) this.fuse);
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
}

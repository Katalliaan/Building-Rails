package building_rails.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import building_rails.BRItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import building_rails.entity.EntityThrownDynamite;
import net.minecraft.init.Items;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderThrownDynamite extends Render {

	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		int meta;
		
		if (!((EntityThrownDynamite) par1Entity).ender && !((EntityThrownDynamite) par1Entity).sticky)
			meta = 0;
		else if (((EntityThrownDynamite) par1Entity).ender && !((EntityThrownDynamite) par1Entity).sticky)
			meta = 1;
		else if (!((EntityThrownDynamite) par1Entity).ender && ((EntityThrownDynamite) par1Entity).sticky)
			meta = 2;
		else
			meta = 3;
		
		IIcon icon = BRItems.itemDynamite.getIconFromDamage(meta);

		if (icon != null) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float) par2, (float) par4, (float) par6);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			this.bindEntityTexture(par1Entity);
			Tessellator tessellator = Tessellator.instance;
			this.func_77026_a(tessellator, icon);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return TextureMap.locationItemsTexture;
	}

	private void func_77026_a(Tessellator p_77026_1_, IIcon p_77026_2_) {
		float f = p_77026_2_.getMinU();
		float f1 = p_77026_2_.getMaxU();
		float f2 = p_77026_2_.getMinV();
		float f3 = p_77026_2_.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F,
				0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		p_77026_1_.startDrawingQuads();
		p_77026_1_.setNormal(0.0F, 1.0F, 0.0F);
		p_77026_1_.addVertexWithUV((double) (0.0F - f5), (double) (0.0F - f6),
				0.0D, (double) f, (double) f3);
		p_77026_1_.addVertexWithUV((double) (f4 - f5), (double) (0.0F - f6),
				0.0D, (double) f1, (double) f3);
		p_77026_1_.addVertexWithUV((double) (f4 - f5), (double) (f4 - f6),
				0.0D, (double) f1, (double) f2);
		p_77026_1_.addVertexWithUV((double) (0.0F - f5), (double) (f4 - f6),
				0.0D, (double) f, (double) f2);
		p_77026_1_.draw();
	}
}

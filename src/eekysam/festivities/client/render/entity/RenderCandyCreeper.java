package eekysam.festivities.client.render.entity;

import org.lwjgl.opengl.GL11;

import eekysam.festivities.Festivities;
import eekysam.festivities.entity.EntityCandyCreeper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderCandyCreeper extends RenderLiving
{
	private static final ResourceLocation candyCreeperTextures = new ResourceLocation(Festivities.ID, "textures/entity/candy_creeper.png");  

    /** The creeper model. */
    private ModelBase creeperModel = new ModelCreeper(2.0F);

    public RenderCandyCreeper()
    {
        super(new ModelCreeper(), 0.5F);
    }

    /**
     * Updates creeper scale in prerender callback
     */
    protected void updateCreeperScale(EntityCandyCreeper par1EntityCreeper, float par2)
    {
        float f1 = par1EntityCreeper.getCreeperFlashIntensity(par2);
        float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        f1 *= f1;
        f1 *= f1;
        float f3 = (1.0F + f1 * 0.4F) * f2;
        float f4 = (1.0F + f1 * 0.1F) / f2;
        GL11.glScalef(f3, f4, f3);
    }

    /**
     * Updates color multiplier based on creeper state called by getColorMultiplier
     */
    protected int updateCreeperColorMultiplier(EntityCandyCreeper par1EntityCreeper, float par2, float par3)
    {
        float f2 = par1EntityCreeper.getCreeperFlashIntensity(par3);

        if ((int)(f2 * 10.0F) % 2 == 0)
        {
            return 0;
        }
        else
        {
            int i = (int)(f2 * 0.2F * 255.0F);

            if (i < 0)
            {
                i = 0;
            }

            if (i > 255)
            {
                i = 255;
            }

            short short1 = 255;
            short short2 = 255;
            short short3 = 255;
            return i << 24 | short1 << 16 | short2 << 8 | short3;
        }
    }

    /**
     * A method used to render a creeper's powered form as a pass model.
     */
    protected int renderCreeperPassModel(EntityCandyCreeper par1EntityCreeper, int par2, float par3)
    {
        if (par1EntityCreeper.getPowered())
        {
            if (par1EntityCreeper.isInvisible())
            {
                GL11.glDepthMask(false);
            }
            else
            {
                GL11.glDepthMask(true);
            }

            if (par2 == 2)
            {
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        return -1;
    }

    protected int func_77061_b(EntityCandyCreeper par1EntityCreeper, int par2, float par3)
    {
        return -1;
    }

    protected ResourceLocation getCreeperTextures(EntityCandyCreeper par1EntityCreeper)
    {
        return candyCreeperTextures;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.updateCreeperScale((EntityCandyCreeper)par1EntityLivingBase, par2);
    }

    /**
     * Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime
     */
    protected int getColorMultiplier(EntityLivingBase par1EntityLivingBase, float par2, float par3)
    {
        return this.updateCreeperColorMultiplier((EntityCandyCreeper)par1EntityLivingBase, par2, par3);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.renderCreeperPassModel((EntityCandyCreeper)par1EntityLivingBase, par2, par3);
    }

    protected int inheritRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.func_77061_b((EntityCandyCreeper)par1EntityLivingBase, par2, par3);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getCreeperTextures((EntityCandyCreeper)par1Entity);
    }
}

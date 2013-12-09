package eekysam.festivities.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityStrongItem extends EntityItem
{
	public EntityStrongItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
	{
		super(par1World, par2, par4, par6, par8ItemStack);
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (par1DamageSource.isExplosion())
		{
			return false;
		}
		else
		{
			return super.attackEntityFrom(par1DamageSource, par2);
		}
	}

	@Override
	public boolean combineItems(EntityItem par1EntityItem)
	{
		return false;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (this.age > 30)
		{
			this.setDead();
			EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, this.getEntityItem());
			this.worldObj.spawnEntityInWorld(entityitem);
		}
	}

	@Override
	public boolean writeMountToNBT(NBTTagCompound par1NBTTagCompound)
	{
		String s = super.getEntityString();

		if (!this.isDead && s != null)
		{
			par1NBTTagCompound.setString("id", s);
			this.writeToNBT(par1NBTTagCompound);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean writeToNBTOptional(NBTTagCompound par1NBTTagCompound)
	{
		String s = super.getEntityString();

		if (!this.isDead && s != null && this.riddenByEntity == null)
		{
			par1NBTTagCompound.setString("id", s);
			this.writeToNBT(par1NBTTagCompound);
			return true;
		}
		else
		{
			return false;
		}
	}
}

package eekysam.festivities.tile;

import net.minecraft.tileentity.TileEntity;

public class TileEntityFireplace extends TileEntity implements ISimpleTile
{
	protected int anim = 0;

	protected long ticks = 0;

	public static final int frames = 7;
	private static final int cut = ((frames * 2) - 2);

	@Override
	public void updateEntity()
	{
		this.ticks++;

		if (this.ticks % 6 == 0)
		{
			this.anim++;
			this.anim = this.anim % cut;
		}
	}

	@Override
	public int getAnimNum()
	{
		if (this.anim < frames)
		{
			return this.anim % frames;
		}
		else
		{
			return (cut - this.anim) % frames;
		}
	}
}

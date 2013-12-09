package eekysam.utils.draw;

public class SideDrawFakeShade extends SideDrawBasic
{
	public SideDrawFakeShade(IRenderer parent)
	{
		super(parent);
	}

	@Override
	protected void XUp()
	{
		if (!this.doubleSided)
		{
			this.setBrightnessF(0.7F);
		}
		else
		{
			this.setBrightnessF(0.9F);
		}
		super.XUp();
		this.reTess();
	}

	@Override
	protected void XDown()
	{
		if (!this.doubleSided)
		{
			this.setBrightnessF(0.9F);
		}
		else
		{
			this.setBrightnessF(0.9F);
		}
		super.XDown();
		this.reTess();
	}

	@Override
	protected void YUp()
	{
		if (!this.doubleSided)
		{
			this.setBrightnessF(0.85F);
		}
		else
		{
			this.setBrightnessF(0.6F);
		}
		super.YUp();
		this.reTess();
	}

	@Override
	protected void YDown()
	{
		if (!this.doubleSided)
		{
			this.setBrightnessF(0.6F);
		}
		else
		{
			this.setBrightnessF(0.6F);
		}
		super.YDown();
		this.reTess();
	}

	@Override
	protected void ZUp()
	{
		if (!this.doubleSided)
		{
			this.setBrightnessF(0.8F);
		}
		else
		{
			this.setBrightnessF(0.8F);
		}
		super.ZUp();
		this.reTess();
	}

	@Override
	protected void ZDown()
	{
		if (!this.doubleSided)
		{
			this.setBrightnessF(0.8F);
		}
		else
		{
			this.setBrightnessF(0.8F);
		}
		super.ZDown();
		this.reTess();
	}

	protected void setBrightnessF(float f)
	{
		this.tess.setColorOpaque_F(f, f, f);
	}
}

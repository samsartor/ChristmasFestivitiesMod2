package eekysam.utils.draw;

public class SideDrawFakeShade extends SideDrawBasic
{
	public SideDrawFakeShade(IRenderer parent)
	{
		super(parent);
	}
	
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

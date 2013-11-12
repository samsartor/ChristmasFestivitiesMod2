package eekysam.utils;

import org.lwjgl.util.vector.Vector3f;

public enum EnumDirection
{
	XUp,
	XDown,
	YUp,
	YDown,
	ZUp,
	ZDown;
	
	public EnumDirection getOpposite()
	{
		switch (this)
		{
			case XUp:
				return XDown;
			case XDown:
				return XUp;
			case YUp:
				return YDown;
			case YDown:
				return YUp;
			case ZUp:
				return ZDown;
			case ZDown:
				return ZUp;
		}
		return null;
	}
	
	public Vector3f getVector(float mag)
	{
		switch (this)
		{
			case XUp:
				return new Vector3f(1 * mag, 0.0F, 0.0F);
			case XDown:
				return new Vector3f(-1 * mag, 0.0F, 0.0F);
			case YUp:
				return new Vector3f(0.0F, 1 * mag, 0.0F);
			case YDown:
				return new Vector3f(0.0F, -1 * mag, 0.0F);
			case ZUp:
				return new Vector3f(0.0F, 0.0F, 1 * mag);
			case ZDown:
				return new Vector3f(0.0F, 0.0F, -1 * mag);
		}
		return new Vector3f(0.0F, 0.0F, 0.0F);
	}
	
	public Vector3f getBadPlaneVector(float u, float v)
	{
		switch (this)
		{
			case XUp:
				return new Vector3f(0.0F, v, u);
			case XDown:
				return new Vector3f(0.0F, v, u);
			case YUp:
				return new Vector3f(u, 0.0F, v);
			case YDown:
				return new Vector3f(u, 0.0F, v);
			case ZUp:
				return new Vector3f(u, v, 0.0F);
			case ZDown:
				return new Vector3f(u, v, 0.0F);
		}
		return new Vector3f(0.0F, 0.0F, 0.0F);
	}
}

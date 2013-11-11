interface Sketcher
{
	public void setPos(int x, int y, int z);
	
	public void setDomain(int width, int height, int length);
	
	public void setTexture(String id, String texture, int width, int height);
	
	public void selectUV(int u, int v);
	
	public void XUp();
	
	public void XDown();
	
	public void YUp();
	
	public void YDown();
	
	public void ZUp();
	
	public void ZDown();
	
	public void drawAll();
	
	public void drawSidesSameTexture();
	
	public void drawSidesGroupedTexture();
	
	public void drawAllNormalTextureShape();
}

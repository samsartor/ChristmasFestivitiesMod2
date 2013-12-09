package eekysam.festivities.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiMismatch extends GuiOk
{
	private String msg;

	public GuiMismatch(String msg, GuiScreen parent)
	{
		super(parent, "ID mismatch", "Should I continue?");
		this.msg = msg;
	}

	@Override
	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		String[] msgs = this.msg.split("\n");
		int offset = Math.max(85 - msgs.length * 10, 30);
		for (int i = 0; i < msgs.length; i++)
		{
			this.drawString(this.fontRenderer, msgs[i], 10, 10 + i * 9, 0xFFFFFF);
		}
		int maxLines = 20;
		// super.super. Grrr
		for (int var4 = 0; var4 < this.buttonList.size(); ++var4)
		{
			GuiButton var5 = (GuiButton) this.buttonList.get(var4);
			var5.yPosition = this.height - 20;
			var5.drawButton(this.mc, par1, par2);
		}
	}
}

package eekysam.festivities.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.resources.I18n;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiOk extends GuiScreen
{
	/**
	 * A reference to the screen object that created this. Used for navigating
	 * between screens.
	 */
	protected GuiScreen parentScreen;

	/** First line of text. */
	protected String message1;

	/** Second line of text. */
	private String message2;

	protected String buttonText;

	public GuiOk(GuiScreen par1GuiScreen, String par2Str, String par3Str)
	{
		this.parentScreen = par1GuiScreen;
		this.message1 = par2Str;
		this.message2 = par3Str;
		this.buttonText = I18n.getString("gui.done");
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui()
	{
		this.buttonList.add(new GuiSmallButton(0, this.width / 2 - 75, this.height / 6 + 96, this.buttonText));
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of
	 * ActionListener.actionPerformed(ActionEvent e).
	 */
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		System.out.println("Done " + this.parentScreen);
		Minecraft.getMinecraft().displayGuiScreen(this.parentScreen);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 70, 16777215);
		this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 90, 16777215);
		super.drawScreen(par1, par2, par3);
	}
}

package net.launcher.components;

import net.launcher.utils.BaseUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ServerSelectStyle
{
	public int x = 0;
	public int y = 0;
	public String fontName = BaseUtils.empty;
	public float fontSize = 1F;
	public Color textColor;
	public BufferedImage texture;
	public int rowHeight = 0;

	public ServerSelectStyle(int x, int y, int rowHeight, String fontName, String texture, float fontSize, Color textColor)
	{
		this.x = x;
		this.y = y;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.textColor = textColor;
		this.texture = BaseUtils.getLocalImage(texture);
		this.rowHeight = rowHeight;
	}

	public void apply(ServerSelect serverSelect)
	{
		serverSelect.setBounds(x, y, serverSelect.width, serverSelect.height);
		serverSelect.setFont(BaseUtils.getFont(fontName, fontSize));
		serverSelect.setBackground(textColor);
		serverSelect.setForeground(textColor);
		serverSelect.rowHeight = rowHeight;
		serverSelect.sss = this;
	}
}
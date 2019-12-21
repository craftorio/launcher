package net.launcher.components;

public class AvatarStyle
{
	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;

	/**
	 *
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public AvatarStyle(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void apply(Avatar avatar)
	{
		avatar.setBounds(x, y, w, h);
		avatar.at = this;
	}
}
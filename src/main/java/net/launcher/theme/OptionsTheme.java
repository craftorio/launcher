package net.launcher.theme;

import java.awt.Color;
import javax.swing.border.EmptyBorder;

import net.launcher.components.Align;
import net.launcher.components.ButtonStyle;
import net.launcher.components.CheckboxStyle;
import net.launcher.components.ComponentStyle;
import net.launcher.components.TextfieldStyle;

public class OptionsTheme {

    public static ComponentStyle panelOpt = new ComponentStyle(48, 108, 400, 300, "font", 14F, Color.decode("0xA67A53"), true);

    public static CheckboxStyle loadnews = new CheckboxStyle(16, 130, 300, 23, "font", "checkbox", 14F, Color.decode("0xBBBBBB"), true);
    public static CheckboxStyle Music = new CheckboxStyle(16, 162, 300, 23, "font", "checkbox", 14F, Color.decode("0xBBBBBB"), true);
    public static CheckboxStyle updatepr = new CheckboxStyle(16, 187, 300, 23, "font", "checkbox", 14F, Color.decode("0xBBBBBB"), true);
    public static CheckboxStyle cleandir = new CheckboxStyle(16, 212, 300, 23, "font", "checkbox", 14F, Color.decode("0xBBBBBB"), true);
    public static CheckboxStyle fullscrn = new CheckboxStyle(44, 220, 300, 23, "font", "checkbox", 14F, Color.decode("0xBBBBBB"), true);
    public static CheckboxStyle offline = new CheckboxStyle(28, 262, 300, 23, "font", "checkbox", 14F, Color.decode("0xBBBBBB"), true);
    public static TextfieldStyle memory = new TextfieldStyle(48, 270, 265, 32, "input-def", "font", 14F, Color.decode("0xBBBBBB"), Color.decode("0xBBBBBB"), new EmptyBorder(0, 10, 0, 10));
    public static ButtonStyle close = new ButtonStyle(48, 320, 127, 37, "font", "button2-craftorio", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);

    public static FontBundle memoryDesc = new FontBundle("font", 16F, Color.DARK_GRAY);

    public static int titleX = 48;
    public static int titleY = 210;
}
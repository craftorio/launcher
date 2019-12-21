package net.launcher.theme;

import java.awt.Color;
import javax.swing.border.EmptyBorder;

import net.launcher.components.Align;
import net.launcher.components.ButtonStyle;
import net.launcher.components.CheckboxStyle;
import net.launcher.components.ComponentStyle;
import net.launcher.components.TextfieldStyle;

public class OptionsTheme {

    public static ComponentStyle panelOpt = new ComponentStyle(26, 108, 400, 300, "font", 14F, Color.decode("0xA67A53"), true);

    public static CheckboxStyle loadnews = new CheckboxStyle(26, 130, 300, 23, "font", "checkbox", 14F, Color.decode("0xFFFFFF"), true);
    public static CheckboxStyle Music = new CheckboxStyle(26, 162, 300, 23, "font", "checkbox", 14F, Color.decode("0xFFFFFF"), true);
    public static CheckboxStyle updatepr = new CheckboxStyle(26, 187, 300, 23, "font", "checkbox", 14F, Color.decode("0xFFFFFF"), true);
    public static CheckboxStyle cleandir = new CheckboxStyle(26, 212, 300, 23, "font", "checkbox", 14F, Color.decode("0xFFFFFF"), true);
    public static CheckboxStyle fullscrn = new CheckboxStyle(22, 180, 300, 23, "font", "checkbox", 14F, Color.decode("0xFFFFFF"), true);
    public static CheckboxStyle offline = new CheckboxStyle(28, 262, 300, 23, "font", "checkbox", 14F, Color.decode("0xFFFFFF"), true);
    public static TextfieldStyle memory = new TextfieldStyle(26, 230, 308, 48, "input-def-craftorio", "font", 14F, Color.decode("0xFFFFFF"), Color.decode("0xBBBBBB"), new EmptyBorder(0, 10, 0, 10));
    public static ButtonStyle close = new ButtonStyle(26, 412, 308, 48, "font", "button2-craftorio", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);

    public static FontBundle memoryDesc = new FontBundle("font", 16F, Color.DARK_GRAY);

    public static int titleX = 26;
    public static int titleY = 160;
}
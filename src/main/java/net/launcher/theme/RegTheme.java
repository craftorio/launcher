package net.launcher.theme;

import java.awt.Color;

import javax.swing.border.EmptyBorder;

import net.launcher.components.Align;
import net.launcher.components.ButtonStyle;
import net.launcher.components.ComponentStyle;
import net.launcher.components.PassfieldStyle;
import net.launcher.components.TextfieldStyle;

public class RegTheme {
    public static TextfieldStyle loginReg = new TextfieldStyle(20, 141, 265, 32, "input-def", "font", 14F, Color.decode("0xFFFFFF"), Color.decode("0xFFFFFF"), new EmptyBorder(0, 10, 0, 10));
    public static PassfieldStyle passwordReg = new PassfieldStyle(20, 182, 265, 32, "input-def", "font", 14F, Color.decode("0xFFFFFF"), Color.decode("0xFFFFFF"), "1", new EmptyBorder(0, 10, 0, 10));
    public static PassfieldStyle password2Reg = new PassfieldStyle(20, 223, 265, 32, "input-def", "font", 14F, Color.decode("0xFFFFFF"), Color.decode("0xFFFFFF"), "1", new EmptyBorder(0, 10, 0, 10));
    public static TextfieldStyle mailReg = new TextfieldStyle(20, 264, 265, 32, "input-def", "font", 14F, Color.decode("0xFFFFFF"), Color.decode("0xFFFFFF"), new EmptyBorder(0, 10, 0, 10));

    public static ComponentStyle textloginReg = new ComponentStyle(145, 168, -1, -1, "font", 16F, Color.WHITE, true);
    public static ComponentStyle textpasswordReg = new ComponentStyle(145, 212, -1, -1, "font", 16F, Color.WHITE, true);
    public static ComponentStyle textpassword2Reg = new ComponentStyle(145, 258, -1, -1, "font", 16F, Color.WHITE, true);
    public static ComponentStyle textmailReg = new ComponentStyle(145, 304, -1, -1, "font", 16F, Color.WHITE, true);

    public static ButtonStyle okreg = new ButtonStyle(20, 305, 126, 34, "font", "button1", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    public static ButtonStyle closereg = new ButtonStyle(159, 305, 126, 34, "font", "button2", 14F, Color.decode("0x03d942"), true, Align.CENTER);


    public static int titleRegX = 190;
    public static int titleRegY = 150;
}
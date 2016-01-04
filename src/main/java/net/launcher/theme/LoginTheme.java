package net.launcher.theme;

import java.awt.Color;

import javax.swing.border.EmptyBorder;

import net.launcher.components.Align;
import net.launcher.components.ButtonStyle;
import net.launcher.components.ComboboxStyle;
import net.launcher.components.ComponentStyle;
import net.launcher.components.LinklabelStyle;
import net.launcher.components.PassfieldStyle;
import net.launcher.components.ServerbarStyle;
import net.launcher.components.TextfieldStyle;

public class LoginTheme
{
    public static int             frameW      = 305;
    public static int             frameH      = 408;

    public static ButtonStyle     loginBtn    = new ButtonStyle(20, 253, 126, 34, "font", "button1", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    public static ButtonStyle     logoutBtn   = new ButtonStyle(20, 253, 126, 34, "font", "button2", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    public static ButtonStyle     helpBtn     = new ButtonStyle(159, 253, 126, 34, "font", "button2", 14F, Color.decode("0x03d942"), true, Align.CENTER);
    public static ButtonStyle     settingsBtn = new ButtonStyle(259, 106, 26, 26, "font", "settings", 14F, Color.decode("0x03d942"), true, Align.CENTER);

    public static ButtonStyle     toGame      = new ButtonStyle(20, 200, 265, 32, "font", "button1", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    public static ButtonStyle     toPersonal  = new ButtonStyle(262, 367, 150, 47, "font", "button", 14F, Color.decode("0x03d942"), true, Align.CENTER);

    public static ButtonStyle     registerBtn = new ButtonStyle(20, 344, 265, 45, "font", "button3", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);

    public static TextfieldStyle  login       = new TextfieldStyle(20, 141, 265, 32, "input-def", "font", 14F, Color.decode("0xFFFFFF"), Color.decode("0xFFFFFF"), new EmptyBorder(0, 10, 0, 10));
    public static PassfieldStyle  password    = new PassfieldStyle(20, 182, 265, 32, "input-def", "font", 14F, Color.decode("0xFFFFFF"), Color.decode("0xFFFFFF"), "1", new EmptyBorder(0, 10, 0, 10));


    //public static TextfieldStyle  login       = new TextfieldStyle(128, 200, 250, 38, "textfield", "font", 16F, Color.decode("0xA67A53"), Color.decode("0xA67A53"), new EmptyBorder(0, 10, 0, 10));
    //public static PassfieldStyle  password    = new PassfieldStyle(128, 250, 250, 38, "textfield", "font", 19F, Color.decode("0xA67A53"), Color.decode("0xA67A53"), "1", new EmptyBorder(0, 10, 0, 10));
    
    //public static ComponentStyle  newsBrowser = new ComponentStyle(20, 60, 660, 60, "font", 0F, Color.WHITE, true);
    //public static LinklabelStyle  links       = new LinklabelStyle(420, 280, 0, "font", 16F, Color.WHITE, Color.LIGHT_GRAY);
    
    public static ButtonStyle     update_exe  = new ButtonStyle(96, 440, 150, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
    public static ButtonStyle     update_jar  = new ButtonStyle(262, 440, 150, 47, "font", "button", 16F, Color.decode("0xd4dc7b"), true, Align.CENTER);
    public static ButtonStyle     update_no   = new ButtonStyle(600, 0, 0, 0, "font", "button", 0F, Color.decode("0xd4dc7b"), true, Align.CENTER);
    
    public static ComboboxStyle   servers     = new ComboboxStyle(20, 106, 126, 26, "font", "servers_combo", 12F, Color.decode("0x778981"), true, Align.CENTER, new EmptyBorder(0, 10, 0, 10));
    public static ServerbarStyle  serverbar   = new ServerbarStyle(159, 112, 80, 16, "font", 12F, Color.decode("0xcccccc"), true);

    public static LinklabelStyle  projectLinkStyle = new LinklabelStyle(20, 305, 265, 26, "font", 12F, Color.decode("0x778981"), Color.decode("0x03d942"), Align.CENTER);
    
    public static float           fontbasesize  = 14F;
    public static float           fonttitlesize = 20F;
}
package net.launcher.theme;

import java.awt.Color;

import javax.swing.border.EmptyBorder;

import net.launcher.components.*;

public class LoginTheme
{
    public static int             frameW      = 361;
    public static int             frameH      = 500;

    public static ButtonStyle     loginBtn    = new ButtonStyle(26, 274, 148, 48, "font", "button1-craftorio", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    public static ButtonStyle     logoutBtn   = new ButtonStyle(26, 274, 148, 48, "font", "button2-craftorio", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    //public static ButtonStyle     helpBtn     = new ButtonStyle(159, 253, 127, 34, "font", "button2", 14F, Color.decode("0x03d942"), true, Align.CENTER);
    public static LinklabelStyle  helpBtn     = new LinklabelStyle(26, 461, 133, 17, "font", 12F, Color.decode("0xFFFFFF"), Color.decode("0xE47134"), Align.CENTER);
    public static ButtonStyle     settingsBtn = new ButtonStyle(185, 274, 148, 48, "font", "button2-craftorio", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);

    public static ButtonStyle     toGame      = new ButtonStyle(26, 212, 308, 50, "font", "button3-craftorio-2", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    public static ButtonStyle     toPersonal  = new ButtonStyle(26, 320, 148, 50, "font", "button3-craftorio-2", 14F, Color.decode("0x03d942"), true, Align.CENTER);

    public static ButtonStyle     registerBtn = new ButtonStyle(26, 412, 308, 50, "font", "button3-craftorio", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    public static ButtonStyle     accountBtn = new ButtonStyle(26, 412, 308, 50, "font", "button3-craftorio", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);

    public static AvatarStyle     avatar        = new AvatarStyle(26, 150, 48, 48);
    public static TextfieldStyle  login        = new TextfieldStyle(26, 150, 308, 48, "input-def-craftorio", "font", 14F, Color.decode("0xFFFFFF"), Color.decode("0xFFFFFF"), new EmptyBorder(0, 10, 0, 10));
    public static TextfieldStyle  loginCurrent = new TextfieldStyle(86, 150, 248, 48, "input-def-craftorio", "font", 14F, Color.decode("0xFFFFFF"), Color.decode("0xFFFFFF"), new EmptyBorder(0, 10, 0, 10));
    public static PassfieldStyle  password     = new PassfieldStyle(26, 212, 308, 48, "input-def-craftorio", "font", 14F, Color.decode("0xFFFFFF"), Color.decode("0xFFFFFF"), "1", new EmptyBorder(0, 10, 0, 10));


    //public static TextfieldStyle  login       = new TextfieldStyle(128, 200, 250, 38, "textfield", "font", 16F, Color.decode("0xA67A53"), Color.decode("0xA67A53"), new EmptyBorder(0, 10, 0, 10));
    //public static PassfieldStyle  password    = new PassfieldStyle(128, 250, 250, 38, "textfield", "font", 19F, Color.decode("0xA67A53"), Color.decode("0xA67A53"), "1", new EmptyBorder(0, 10, 0, 10));
    
    //public static ComponentStyle  newsBrowser = new ComponentStyle(20, 60, 660, 60, "font", 0F, Color.WHITE, true);
    //public static LinklabelStyle  links       = new LinklabelStyle(420, 280, 0, "font", 16F, Color.WHITE, Color.LIGHT_GRAY);
    
    public static ButtonStyle     update_exe  = new ButtonStyle(96, 440, 150, 47, "font", "button", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    public static ButtonStyle     update_jar  = new ButtonStyle(20, 253, 126, 34, "font", "button1", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    public static ButtonStyle     update_no   = new ButtonStyle(159, 253, 126, 34, "font", "button2", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    
    public static ComboboxStyle   servers     = new ComboboxStyle(20, 106, 126, 26, "font", "servers_combo", 12F, Color.decode("0x778981"), true, Align.CENTER, new EmptyBorder(0, 10, 0, 10));
    public static ServerbarStyle  serverbar   = new ServerbarStyle(159, 112, 80, 16, "font", 12F, Color.decode("0xcccccc"), true);

    public static LinklabelStyle  projectLinkStyle = new LinklabelStyle(26, 355, 308, 20, "font", 14F, Color.decode("0xFFFFFF"), Color.decode("0xE47134"), Align.CENTER);
    public static ServerSelectStyle serverSelectStyle = new ServerSelectStyle(26, 170, 47, "font", "button3-craftorio-1x", 14F, Color.decode("0xcccccc"));
    public static ButtonStyle serverSelectBack = new ButtonStyle(26, 412, 308, 48, "font", "button2-craftorio", 14F, Color.decode("0xFFFFFF"), true, Align.CENTER);
    
    public static float           fontbasesize  = 12F;
    public static float           fonttitlesize = 18F;
}
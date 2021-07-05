package net.launcher.components;

import net.launcher.run.Settings;
import net.launcher.utils.BaseUtils;
import net.launcher.utils.EncodingUtils;
import net.launcher.utils.GuardUtils;
import net.launcher.utils.java.eURLClassLoader;
import net.minecraft.Launcher;

import javax.swing.*;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GameLegacy extends JFrame {

    private static final long serialVersionUID = 1L;
    public static Launcher mcapplet;
    private static eURLClassLoader cl;
    static String Cl = null;
    Timer timer = null;
    int i = 0;
    static List<String> params = new ArrayList<String>();
    private static String minepath;
    public static GameThread gameThread;

    public GameLegacy(final String answer) {
        GuardUtils.prepareLogDirs(new File(BaseUtils.getAssetsDir().getAbsolutePath() + File.separator + BaseUtils.getClientName()));
        GuardUtils.prepareLogDirs(new File(BaseUtils.getAssetsDir().getAbsolutePath() + File.separator + "logs"));
        String bin = BaseUtils.getMcDir() + File.separator;
        cl = new eURLClassLoader(GuardUtils.url.toArray(new URL[0]));
        String user = answer.split("<br>")[1].split("<:>")[0];
        String session = EncodingUtils.xorencode(EncodingUtils.inttostr(answer.split("<br>")[1].split("<:>")[1]), Settings.protectionKey);

        Thread ch = new Thread(() -> {
            while (true) {
                GuardUtils.check();
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ch.start();

        Thread check = new Thread(new Runnable() {
            @Override
            public void run() {
                int z = 0;
                while (z < Settings.useModCheckerint) {
                    GuardUtils.checkMods(answer, false);
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    z++;
                }
            }
        });
        check.start();
        try {
            System.out.println("Running Minecraft");
            minepath = BaseUtils.getMcDir().toString();
            String jarpath = BaseUtils.getMcDir() + File.separator;
            String assets = BaseUtils.getAssetsDir() + File.separator;
            System.setProperty("fml.ignoreInvalidMinecraftCertificates", "true");
            System.setProperty("fml.ignorePatchDiscrepancies", "true");
            System.setProperty("org.lwjgl.librarypath", jarpath + "natives");
            System.setProperty("net.java.games.input.librarypath", jarpath + "natives");
            System.setProperty("java.library.path", jarpath + "natives");
            if (BaseUtils.getPropertyBoolean("fullscreen")) {
                params.add("--fullscreen");
                params.add("true");
            } else {
                params.add("--width");
                params.add(String.valueOf(Settings.width));
                params.add("--height");
                params.add(String.valueOf(Settings.height));
            }
            if (Settings.useAutoenter) {
                params.add("--server");
                params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[1]);
                params.add("--port");
                params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[2]);
            }
            try {
                cl.loadClass("com.mojang.authlib.Agent");
                params.add("--accessToken");
                params.add(session);
                params.add("--uuid");
                params.add(EncodingUtils.xorencode(EncodingUtils.inttostr(answer.split("<br>")[0].split("<:>")[1]), Settings.protectionKey));
                params.add("--userProperties");
                params.add("{}");
                params.add("--assetIndex");
                params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[3]);
            } catch (ClassNotFoundException e2) {
                params.add("--session");
                params.add(session);
            }
            params.add("--username");
            params.add(user);
            params.add("--version");
            params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[3]);
            params.add("--gameDir");
            params.add(minepath);

            params.add("--assetsDir");


            if (Integer.parseInt(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[3].replace(".", "")) < 173) {
                BaseUtils.send("==================================================================");
                BaseUtils.send("Load resources (legacy):" + assets + "assets/virtual/legacy");
                BaseUtils.send("==================================================================");
                params.add(assets + "assets/virtual/legacy");
            } else {
                BaseUtils.send("==================================================================");
                BaseUtils.send("Load resources:" + assets + "assets");
                BaseUtils.send("==================================================================");
                params.add(assets + "assets");
            }
            boolean tweakClass = false;
            try {
                BaseUtils.send("Lookup for com.mumfrey.liteloader.launch.LiteLoaderTweaker");
                cl.loadClass("com.mumfrey.liteloader.launch.LiteLoaderTweaker");
                params.add("--tweakClass");
                params.add("com.mumfrey.liteloader.launch.LiteLoaderTweaker");
                tweakClass = true;
            } catch (ClassNotFoundException e) {
                BaseUtils.send("Class not found: com.mumfrey.liteloader.launch.LiteLoaderTweaker");
            }
            try {
                BaseUtils.send("Lookup for cpw.mods.fml.common.launcher.FMLTweaker");
                cl.loadClass("cpw.mods.fml.common.launcher.FMLTweaker");
                params.add("--tweakClass");
                params.add("cpw.mods.fml.common.launcher.FMLTweaker");
                tweakClass = true;
            } catch (ClassNotFoundException e) {
                BaseUtils.send("Class not found: cpw.mods.fml.common.launcher.FMLTweaker");
            }
            try {
                BaseUtils.send("Lookup for net.minecraftforge.fml.common.launcher.FMLTweaker");
                cl.loadClass("net.minecraftforge.fml.common.launcher.FMLTweaker");
                params.add("--tweakClass");
                params.add("net.minecraftforge.fml.common.launcher.FMLTweaker");
                tweakClass = true;
            } catch (ClassNotFoundException e) {
                BaseUtils.send("Class not found: net.minecraftforge.fml.common.launcher.FMLTweaker");
            }
            if (tweakClass) {
                Cl = "net.minecraft.launchwrapper.Launch";
            } else {
                Cl = "net.minecraft.client.main.Main";
            }

            Frame.main.setVisible(false);
            GuardUtils.delete(new File(assets + "resources/skins"));
            gameThread = getGameThread();
            gameThread.start();
        } catch (Exception e) {
        }
    }

    private GameThread getGameThread() {
        return new GameThread(() -> {
            try {
                Class<?> start = cl.loadClass(Cl);
                params.forEach((n) -> BaseUtils.send("param: " + n));
                Method main = start.getMethod("main", String[].class);
                main.invoke(null, new Object[]{params.toArray(new String[0])});
            } catch (Exception e) {
                e.printStackTrace(new PrintStream(System.out));
                JOptionPane.showMessageDialog(Frame.main, e, "Ошибка запуска", JOptionPane.ERROR_MESSAGE, null);
                try {
                    Class<?> af = Class.forName("java.lang.Shutdown");
                    Method m = af.getDeclaredMethod("halt0", int.class);
                    m.setAccessible(true);
                    m.invoke(null, 1);
                } catch (Exception x) {
                }
            }
        });
    }
}

package net.launcher.components;

import net.launcher.run.Settings;
import net.launcher.utils.BaseUtils;
import net.launcher.utils.EncodingUtils;
import net.launcher.utils.GuardUtils;
import net.launcher.utils.java.eURLClassLoader;

import javax.swing.*;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static eURLClassLoader classLoader;
    static String Klass = null;
    static List<String> params = new ArrayList<String>();
    private static String minePath;
    public static GameThread gameThread;

    public GameFrame(final String answer) {
        GuardUtils.prepareLogDirs(new File(BaseUtils.getAssetsDir().getAbsolutePath() + File.separator + BaseUtils.getClientName()));
        GuardUtils.prepareLogDirs(new File(BaseUtils.getAssetsDir().getAbsolutePath() + File.separator + "logs"));
        GuardUtils.updateMods(answer);
        classLoader = new eURLClassLoader(GuardUtils.url.toArray(new URL[0]));
        String user = answer.split("<br>")[1].split("<:>")[0];
        String session = EncodingUtils.xorencode(EncodingUtils.inttostr(answer.split("<br>")[1].split("<:>")[1]), Settings.protectionKey);
        Thread checkThread = new Thread(() -> {
            while (true) {
                GuardUtils.check();
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        checkThread.start();

        Thread check = new Thread(() -> {
            int checkCounter = 0;
            while (checkCounter < Settings.useModCheckerint) {
                GuardUtils.checkMods(answer, false);
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                checkCounter++;
            }
        });
        check.start();
        try {
            System.out.println("Running Minecraft");
            minePath = BaseUtils.getMcDir().toString();
            String jarPath = minePath + File.separator;
            String assets = BaseUtils.getAssetsDir() + File.separator;
            System.setProperty("fml.ignoreInvalidMinecraftCertificates", "true");
            System.setProperty("fml.ignorePatchDiscrepancies", "true");
            System.setProperty("org.lwjgl.librarypath", jarPath + "natives");
            System.setProperty("net.java.games.input.librarypath", jarPath + "natives");
            System.setProperty("java.library.path", jarPath + "natives");
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
                classLoader.loadClass("com.mojang.authlib.Agent");
                params.add("--accessToken");
                params.add(session);
                params.add("--uuid");
                params.add(EncodingUtils.xorencode(EncodingUtils.inttostr(answer.split("<br>")[0].split("<:>")[1]), Settings.protectionKey));
                params.add("--userProperties");
                params.add("{}");
                params.add("--assetIndex");
                String version = Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[3];
                params.add(version.split("\\.")[0] + "." + version.split("\\.")[1]);
            } catch (ClassNotFoundException e2) {
                params.add("--session");
                params.add(session);
            }
            params.add("--username");
            params.add(user);
            params.add("--version");
            //params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[3]);
            params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[3]);
            params.add("--gameDir");
            params.add(minePath);

            params.add("--assetsDir");
            BaseUtils.send("==================================================================");
            BaseUtils.send("Load resources:" + assets + "assets");
            BaseUtils.send("==================================================================");
            params.add(assets + "assets");

            boolean tweakClass = false;
            try {
                BaseUtils.send("Lookup for com.mumfrey.liteloader.launch.LiteLoaderTweaker");
                classLoader.loadClass("com.mumfrey.liteloader.launch.LiteLoaderTweaker");
                params.add("--tweakClass");
                params.add("com.mumfrey.liteloader.launch.LiteLoaderTweaker");
                tweakClass = true;
            } catch (ClassNotFoundException e) {
                BaseUtils.send("Class not found: com.mumfrey.liteloader.launch.LiteLoaderTweaker");
            }
            try {
                BaseUtils.send("Lookup for cpw.mods.fml.common.launcher.FMLTweaker");
                classLoader.loadClass("cpw.mods.fml.common.launcher.FMLTweaker");
                params.add("--tweakClass");
                params.add("cpw.mods.fml.common.launcher.FMLTweaker");
                tweakClass = true;
            } catch (ClassNotFoundException e) {
                BaseUtils.send("Class not found: cpw.mods.fml.common.launcher.FMLTweaker");
            }
            try {
                BaseUtils.send("Lookup for net.minecraftforge.fml.common.launcher.FMLTweaker");
                classLoader.loadClass("net.minecraftforge.fml.common.launcher.FMLTweaker");
                params.add("--tweakClass");
                params.add("net.minecraftforge.fml.common.launcher.FMLTweaker");
                tweakClass = true;
            } catch (ClassNotFoundException e) {
                BaseUtils.send("Class not found: net.minecraftforge.fml.common.launcher.FMLTweaker");
            }

            try {
                BaseUtils.send("Lookup for cpw.mods.modlauncher.Launcher");
                classLoader.loadClass("cpw.mods.modlauncher.Launcher");
                params.add("--launchTarget");
                params.add("fmlclient");

                params.add("--fml.forgeGroup");
                params.add("net.minecraftforge");

                params.add("--fml.forgeVersion");
                params.add("36.1.31");

                params.add("--fml.mcVersion");
                params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[3]);

                params.add("--fml.mcpVersion");
                params.add("20210115.111550");

                Klass = "cpw.mods.modlauncher.Launcher";


            } catch (ClassNotFoundException e) {
                BaseUtils.send("Class not found: cpw.mods.modlauncher.Launcher");
            }

            if (null == Klass) {
                if (tweakClass) {
                    Klass = "net.minecraft.launchwrapper.Launch";
                } else {
                    Klass = "net.minecraft.client.main.Main";
                }
            }

            Frame.main.setVisible(false);
            GuardUtils.delete(new File(assets + "resources/skins"));
            gameThread = getGameThread();
            gameThread.start();
        } catch (Exception ignored) {
        }
    }

    private GameThread getGameThread() {
        return new GameThread(() -> {
            try {
                //System.setProperty("user.dir", minePath);
                Class<?> entrypoint = classLoader.loadClass(Klass);

                ArrayList<String> cp = new ArrayList<>();
                cp.add(entrypoint.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

                GuardUtils.url.forEach((URL u) -> {
                    cp.add(u.getPath());
                });

                String path = new File(entrypoint.getProtectionDomain().getCodeSource().getLocation()
                        .toURI()).getPath();
                BaseUtils.send("Main class path: " + path);
                ArrayList<String> args = new ArrayList<String>();

                args.add(System.getProperty("java.home") + "/bin/java");
                int memory = BaseUtils.getPropertyIntMax("memory", Settings.defaultmemory);
                args.add("-XX:+UnlockExperimentalVMOptions");
                args.add("-XX:+UseG1GC");
                args.add("-XX:G1NewSizePercent=20");
                args.add("-XX:G1ReservePercent=20");
                args.add("-XX:MaxGCPauseMillis=50");
                args.add("-XX:G1HeapRegionSize=32M");
                args.add("-Xms" + memory + "m");
                args.add("-Xmx" + memory + "m");
                args.add("-XstartOnFirstThread");
                args.add("-Xss1M");
                args.add("-DMinecraft");
                args.add("-Duser.dir=" + minePath);

                String jarpath = minePath + File.separator;
                args.add("-Dfml.ignoreInvalidMinecraftCertificates=true");
                args.add("-Dfml.ignorePatchDiscrepancies=true");
                args.add("-Dorg.lwjgl.librarypath=" + jarpath + "natives");
                args.add("-Dnet.java.games.input.librarypath=" + jarpath + "natives");
                args.add("-Djava.library.path=" + jarpath + "natives");
                args.add("-cp");
                args.add(String.join(System.getProperty("os.name").startsWith("Windows") ? ";" : ":", cp));
                args.add(entrypoint.getCanonicalName());
                args.addAll(params);
                BaseUtils.send("command: " + String.join(" ", args));
                ProcessBuilder pb = new ProcessBuilder(args);
                File log = new File("/tmp/mc.log");
                File err = new File("/tmp/mc.err");
                pb.directory(new File(minePath));
                pb.redirectOutput(log);
                pb.redirectError(err);
                pb.start();

                BaseUtils.send("finished!");
//                if (pb.start().exitValue() > 0) {
//                    JOptionPane.showMessageDialog(Frame.main, "", "Ошибка запуска", JOptionPane.ERROR_MESSAGE, null);
//                }



//                Method main = start.getMethod("main", String[].class);
//                main.invoke(null, new Object[]{params.toArray(new String[0])});
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
            System.exit(0);
        });
    }
}

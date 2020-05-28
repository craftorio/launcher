package net.launcher.run;

import net.launcher.components.Frame;
import net.launcher.utils.BaseUtils;
import net.launcher.utils.ProcessUtils;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Starter {
    public static void main(String[] args) throws Exception {
        try {
            String jarpath = Starter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            int memory = BaseUtils.getPropertyIntMax("memory", Settings.defaultmemory);

            ArrayList<String> params = new ArrayList<String>();
            params.add(System.getProperty("java.home") + "/bin/java");

            // Check if it's 32 bit java, and fix memory limit
            if (System.getProperty("sun.arch.data.model").equals("32") && (memory > 1024)) {
                memory = 1024;
            }

            params.add("-Xms256m");
            // Command options
            params.add("-Xmx" + memory + "m");
            //params.add("-XX:MaxPermSize=128m");

            if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {
                params.add("-Xdock:name=Minecraft");
                params.add("-Xdock:icon=" + BaseUtils.getAssetsDir().toString() + "/favicon.png");
            }
            params.add("-classpath");
            params.add(jarpath);
            params.add(Launcher.class.getCanonicalName());
            params.add("true");

            ProcessBuilder pb = new ProcessBuilder(params);
            pb.directory(new File(BaseUtils.getAssetsDir().toString()));
            BaseUtils.send("Starting launcher: \"" + String.join("\" \"", params) + "\"");
            Process process = pb.start();
            if (null == process) {
                throw new Exception("Launcher can't be started!");
            }
            ProcessUtils processUtil = new ProcessUtils(process);
            processUtil.print();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(Frame.main, e, "Ошибка запуска", javax.swing.JOptionPane.ERROR_MESSAGE, null);
            try {
                Class<?> af = Class.forName("java.lang.Shutdown");
                Method m = af.getDeclaredMethod("halt0", int.class);
                m.setAccessible(true);
                m.invoke(null, 1);
            } catch (Exception x) {
            }
        }
    }
}

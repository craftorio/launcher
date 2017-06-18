package net.launcher.run;

import net.launcher.MusPlay;
import net.launcher.components.Frame;
import net.launcher.utils.BaseUtils;

import java.io.*;

/**
 * Launcher class
 */
public class Launcher {
    /**
     * Main
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        boolean isRenderUi;
        isRenderUi = BaseUtils.isDebugMode() ? true : (args.length > 0 && args[0].equals("true")) ? true : false;
        if (isRenderUi) {
            File dir = new File(BaseUtils.getAssetsDir().toString());
            if (!dir.exists()) {
                dir.mkdirs();
            }

            InputStream stream = BaseUtils.getResourceAsStream("/assets/textures/gui/favicon.png");
            OutputStream resStreamOut = null;
            int readBytes;
            byte[] buffer = new byte[4096];
            try {
                resStreamOut = new FileOutputStream(new File(BaseUtils.getAssetsDir().toString() + "/favicon.png"));
                while ((readBytes = stream.read(buffer)) > 0) {
                    resStreamOut.write(buffer, 0, readBytes);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (null != stream) {
                    stream.close();
                }
                if (null != resStreamOut) {
                    resStreamOut.close();
                }
            }
            Frame.start();
            if (BaseUtils.getPropertyBoolean("Music", true)) {
                new MusPlay(Settings.iMusicname);
            }
        } else {
            Starter.main(null);
        }
    }
}



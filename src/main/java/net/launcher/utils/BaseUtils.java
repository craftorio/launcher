package net.launcher.utils;

import net.launcher.components.Files;
import net.launcher.components.Frame;
import net.launcher.run.Settings;
import net.launcher.run.Starter;
import net.launcher.theme.Message;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.*;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseUtils {
    public static String texturesPath = "/resources/assets/textures";
    public static final String empty = "";
    public static int logNumber = 0;
    public static ConfigUtils config = new ConfigUtils("/launcher.config", getConfigName());

    public static Map<String, Font> fonts = new HashMap<String, Font>();
    public static Map<String, BufferedImage> resourceCache = new HashMap<String, BufferedImage>();

    protected static String workDir;

    protected static Boolean isJar = null;
    public static ArrayList<HashMap> serversStatus = null;
    public static HashMap<String, String> currentServer;
    private static String clientName = null;

    public static ArrayList<HashMap> getServersStatus()
    {
        if (null == serversStatus) {
            ThreadUtils.pollServersStatus();
        }
        return serversStatus;
    }

    public static Boolean isDebugMode() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().
                getInputArguments().toString().contains("jdwp");
    }

    /**
     * Check is run in Jar
     *
     * @return
     */
    public static Boolean isJar() {
        if (null == isJar) {
            isJar = false;
            String className = BaseUtils.class.getName().replace('.', '/');
            String classJar = BaseUtils.class.getClass().getResource("/" + className + ".class").toString();
            if (classJar.startsWith("jar:")) {
                isJar = true;
            }
        }

        return isJar;
    }

    /**
     * Resolve assets path
     *
     * @param path
     * @return
     */
    public static InputStream getResourceAsStream(String path) throws FileNotFoundException {
        InputStream stream;
        if (!isJar()) {
            path = BaseUtils.getAssetsDir() + "/../../../resources/main" + path;
            path = path.replace("\\", "/");
            stream = new FileInputStream(path);
            //stream = BaseUtils.class.getClassLoader().getResourceAsStream(path);
        } else {
            stream = BaseUtils.class.getResourceAsStream(path);
        }

        return stream;

    }

    /**
     * Get the Gui Image
     *
     * @param filename
     * @return BufferedImage
     */
    public static BufferedImage getResourceGuiImage(String filename) {
        String path = texturesPath + "/gui/" + filename + ".png";

        if (resourceCache.containsKey(path)) {
            return (BufferedImage) resourceCache.get(path);
        }

        try {
            send("Opened local image: " + path);
            BufferedImage image = ImageIO.read(getResourceAsStream(path));
            resourceCache.put(path, image);

            return image;
        } catch (Exception e) {
            BufferedImage image = getEmptyImage();
            resourceCache.put(path, image);
            sendErr("Fail to open local image: " + path);

            return image;
        }

    }

    /**
     * @param imageUrl
     * @param FallbackImage
     * @return
     */
    public static BufferedImage getRemoteImage(String imageUrl, String FallbackImage) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url.openStream());
            send("Opened remote image: " + imageUrl);
            return image;
        } catch (Exception e) {
            send("Can't open remote image: " + imageUrl);
            return getLocalImage(FallbackImage);
        }
    }

    public static BufferedImage getLocalImage(String name) {
        return getResourceGuiImage(name);

        //        try
        //        {
        //            if(resourceCache.containsKey(name)) return (BufferedImage) resourceCache.get(name);
        //
        //            BufferedImage img = ImageIO.read(BaseUtils.class.getResource("/java/net/launcher/theme/" + name + ".png"));
        //            resourceCache.put(name, img);
        //            send("Opened local image: " + name + ".png");
        //            return img;
        //        }
        //        catch(Exception e)
        //        {
        //            sendErr("Fail to open local image: " + name + ".png");
        //            return getEmptyImage();
        //        }
    }

    public static BufferedImage getEmptyImage() {
        return new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB);
    }

    public static void send(String msg) {
        if (Settings.debug)
            System.out.println(date() + "[Launcher thread/INFO]: " + msg);
    }

    public static void sendErr(String err) {
        if (Settings.debug)
            System.err.println(date() + "[Launcher thread/WARN]: " + err);
    }

    public static void sendp(String msg) {
        if (Settings.debug)
            System.out.println(msg);
    }

    public static void sendErrp(String err) {
        if (Settings.debug)
            System.err.println(err);
    }

    public static boolean contains(int x, int y, int xx, int yy, int w, int h) {
        return (x >= xx) && (y >= yy) && (x < xx + w) && (y < yy + h);
    }

    /**
     * @return File
     */
    public static File getConfigName() {

        File config = new File(getWorkDir() + File.separator + "launcher.config");

        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Can't create file: \"" + config.getAbsolutePath() + "\", error:" + e.getMessage());
            }
        }

        return config;
    }

    /**
     * @return String
     * @throws URISyntaxException
     */
    protected static String getWorkDir() {

        try {
            if (null == workDir) {
                File file = new File(BaseUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                File dir = new File(file.getParentFile().getAbsolutePath() + File.separator + "craftorio");

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                workDir = dir.getAbsolutePath();

                BaseUtils.send("===> Initialize in work dir: " + workDir);
            }
        } catch (Exception e) {
            throw new RuntimeException("Can't initialize work dir: " + e.getMessage());
        }

        return workDir;
    }

    /**
     * Retrieve assets directory
     *
     * @return File
     */
    public static File getAssetsDir() {

        return new File(getWorkDir());
    }

    /**
     * Retrieve client directory
     *
     * @return File
     */
    public static File getMcDir() {

        return new File(getWorkDir() + File.separator + getClientName());
    }

    /**
     * Retrieve current OS ID
     *
     * @return int
     */
    public static int getPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win"))
            return 2;
        if (osName.contains("mac"))
            return 3;
        if (osName.contains("solaris"))
            return 1;
        if (osName.contains("sunos"))
            return 1;
        if (osName.contains("linux"))
            return 0;
        if (osName.contains("unix"))
            return 0;

        return 4;
    }

    public static String buildUrl(String s) {
        return Settings.http + Settings.domain + "/" + Settings.siteDir + "/" + s;
    }

    static {
        config.load();
    }

    public static void setCurrentServer(int index)
    {
        setCurrentServer(getServersStatus().get(index));
    }

    public static void setCurrentServer(HashMap<String, String> name)
    {
        currentServer = name;
    }

    public static void setProperty(String s, Object value) {
        if (config.checkProperty(s))
            config.changeProperty(s, value);
        else
            config.put(s, value);
    }

    public static String getPropertyString(String s) {
        if (config.checkProperty(s))
            return config.getPropertyString(s);
        return null;
    }

    public static boolean getPropertyBoolean(String s) {
        if (config.checkProperty(s))
            return config.getPropertyBoolean(s);
        return false;
    }

    public static int getPropertyInt(String s) {
        if (config.checkProperty(s))
            return config.getPropertyInteger(s);
        return 0;
    }

    public static int getPropertyInt(String s, int d) {
        File dir = new File(BaseUtils.getAssetsDir().toString());
        if (!dir.exists())
            dir.mkdirs();
        if (config.checkProperty(s))
            return config.getPropertyInteger(s);
        setProperty(s, d);
        return d;
    }

    public static int getPropertyIntMax(String s, int d) {
        File dir = new File(BaseUtils.getAssetsDir().toString());
        if (!dir.exists())
            dir.mkdirs();
        if (config.checkProperty(s)) {
            Integer v = config.getPropertyInteger(s);
            if (v >= d) {
                return v;
            }
        }
        setProperty(s, d);
        return d;
    }

    public static boolean getPropertyBoolean(String s, boolean b) {
        if (config.checkProperty(s))
            return config.getPropertyBoolean(s);
        return b;
    }

    static String boundary = PostUtils.randomString() + PostUtils.randomString() + PostUtils.randomString();

    public static String runHTTP(String URL, String params, boolean send) {
        HttpURLConnection ct = null;
        try {

            URL url = new URL(URL + params);
            ct = (HttpURLConnection) url.openConnection();
            ct.setRequestMethod("GET");
            ct.setRequestProperty("User-Agent", "Launcher/64.0");
            ct.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
            ct.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            ct.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            ct.setUseCaches(false);
            ct.setDoInput(true);
            ct.setDoOutput(true);

            ct.connect();

            InputStream is = ct.getInputStream();
            StringBuilder response;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                }
            }

            String str = response.toString();

            return str;
        } catch (Exception e) {
            return null;
        } finally {
            if (ct != null)
                ct.disconnect();
        }
    }

    public static String getURLSc(String script) {
        return getURL("/" + Settings.siteDir + "/" + script);
    }

    public static String[] getServerNames() {
        String[] error = {"Offline"};
        try {
            String url = runHTTP(getURLSc("servers.php"), "", false);

            if (url == null) {
                return error;
            } else if (url.contains(", ")) {
                Settings.servers = url.replaceAll("<br>", "").split("<::>");
                String[] serversNames = new String[Settings.servers.length];

                for (int a = 0; a < Settings.servers.length; a++) {
                    serversNames[a] = Settings.servers[a].split(", ")[0];
                }

                return serversNames;
            } else {
                return error;
            }
        } catch (Exception e) {
            return error;
        }
    }

    public static String getURL(String path) {
        return Settings.http + Settings.domain + path;
    }

    public void setClientName(String name) {
        clientName = name;
    }

    public static String getClientName() {
        if (null == clientName) {
            if (Settings.useMulticlient) {
                clientName = BaseUtils.currentServer.get("name").replaceAll(" ", empty);
            } else {
                clientName = "default";
            }
        }

        return clientName;
    }

    public static void openURL(String url) {
        try {
            Object o = Class.forName("java.awt.Desktop").getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            o.getClass().getMethod("browse", new Class[]{URI.class}).invoke(o, new Object[]{new URI(url)});
        } catch (Throwable e) {
        }
    }

    public static void deleteDirectory(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                deleteDirectory(f);
            file.delete();
        } else
            file.delete();
    }

    public static BufferedImage getSkinImage(String name) {
        try {
            BufferedImage img = ImageIO.read(new URL(buildUrl(Settings.skins + name + ".png")));
            send("Skin loaded: " + buildUrl(Settings.skins + name + ".png"));
            return img;
        } catch (Exception e) {
            sendErr("Skin not found: " + buildUrl(Settings.skins + name + ".png"));
            return getLocalImage("skin");
        }
    }

    public static BufferedImage getCloakImage(String name) {
        try {
            BufferedImage img = ImageIO.read(new URL(buildUrl(Settings.cloaks + name + ".png")));
            send("Cloak loaded: " + buildUrl(Settings.cloaks + name + ".png"));
            return img;
        } catch (Exception e) {
            sendErr("Cloak not found: " + buildUrl(Settings.cloaks + name + ".png"));
            return new BufferedImage(64, 32, 2);
        }
    }

    public static String execute(String surl, Object[] params) {
        try {
            send("Openning stream: " + surl);
            for(int i = 0; i < params.length - 1; i += 2) {
                send("With param: " + params[i] + "=" + params[i+1]
                        + "(" + ThreadUtils.decrypt(params[i+1].toString(), Settings.key2) + ")");
            }

            URL url = new URL(surl);

            InputStream is = PostUtils.post(url, params);
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuffer response = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            String str1 = response.toString().trim();
            send("Stream opened for " + surl + " completed, return answer: ");
            return str1;
        } catch (Exception e) {
            sendErr("Stream for " + surl + " not ensablished, return null");
            return null;
        }
    }

    public static Font getFont(String name, float size) {
        try {
            if (fonts.containsKey(name))
                return (Font) fonts.get(name).deriveFont(size);
            Font font = null;
            send("Creating font: " + name);
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, BaseUtils.getResourceAsStream("/java/net/launcher/theme/" + name + ".ttf"));
            } catch (Exception e) {
                try {
                    font = Font.createFont(Font.TRUETYPE_FONT, BaseUtils.getResourceAsStream("/java/net/launcher/theme/" + name + ".otf"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            fonts.put(name, font);
            return font.deriveFont(size);
        } catch (Exception e) {
            send("Failed create font!");
            throwException(e, Frame.main);
            return null;
        }
    }

    public static void throwException(Exception e, Frame main) {
        e.printStackTrace();
        main.panel.removeAll();
        main.addFrameComp();
        StackTraceElement[] el = e.getStackTrace();
        main.panel.tmpString = empty;
        main.panel.tmpString += e.toString() + "<:>";
        for (StackTraceElement i : el) {
            main.panel.tmpString += "at " + i.toString() + "<:>";
        }
        main.panel.type = 3;
        main.repaint();
    }

    public static int servtype = 2;

    public static String[] pollServer(String ip, int port) {
        Socket soc = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        try {
            soc = new Socket();
            soc.setSoTimeout(6000);
            soc.setTcpNoDelay(true);
            soc.setTrafficClass(18);
            soc.connect(new InetSocketAddress(ip, port), 6000);
            dis = new DataInputStream(soc.getInputStream());
            dos = new DataOutputStream(soc.getOutputStream());
            dos.write(254);

            if (dis.read() != 255) {
                throw new IOException("Bad message");
            }
            String servc = readString(dis, 256);
            servc.substring(3);
            if (servc.substring(0, 1).equalsIgnoreCase("§") && servc.substring(1, 2).equalsIgnoreCase("1")) {
                servtype = 1;
                return servc.split("\u0000");

            } else {
                servtype = 2;
                return servc.split("§");
            }

        } catch (Exception e) {
            return new String[]{null, null, null};
        } finally {
            try {
                dis.close();
            } catch (Exception e) {
            }
            try {
                dos.close();
            } catch (Exception e) {
            }
            try {
                soc.close();
            } catch (Exception e) {
            }
        }
    }

    public static int parseInt(String integer, int def) {
        try {
            return Integer.parseInt(integer.trim());
        } catch (Exception e) {
            return def;
        }
    }

    public static String readString(DataInputStream is, int d) throws IOException {
        short word = is.readShort();
        if (word > d)
            throw new IOException();
        if (word < 0)
            throw new IOException();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < word; i++) {
            res.append(is.readChar());
        }
        return res.toString();
    }

    public static String genServerStatus(String[] args) {
        if (servtype == 1) {
            if (args[0] == null && args[1] == null && args[2] == null)
                return Message.serveroff;
            if (args[4] != null && args[5] != null) {
                if (args[4].equals(args[5]))
                    return Message.serveroff.replace("%%", args[4]);
                return Message.serveron.replace("%%", args[4]).replace("##", args[5]);
            }
        } else if (servtype == 2) {


            if (args[0] == null && args[1] == null && args[2] == null)
                return Message.serveroff;
            if (args[1] != null && args[2] != null) {
                int i = args.length;
                if (args[i - 2].equals(args[i - 1]))
                    return Message.serveroff.replace("%%", args[i - 1]);
                return Message.serveron.replace("%%", args[i - 2]).replace("##", args[i - 1]);
            }
        }
        return Message.servererr;
    }

    public static BufferedImage getServerOnlineImage() {
        return Files.iconServerOnline;
    }

    public static BufferedImage getServerOfflineImage() {
        return Files.iconServerOffline;
    }

    public static BufferedImage genServerIcon(String[] args) {
        if (args[0] == null && args[1] == null && args[2] == null)
            return Files.light.getSubimage(0, 0, Files.light.getHeight(), Files.light.getHeight());
        if (args[1] != null && args[2] != null) {
            if (args[1].equals(args[2]))
                return Files.light.getSubimage(Files.light.getHeight(), 0, Files.light.getHeight(), Files.light.getHeight());
            return Files.light.getSubimage(Files.light.getHeight() * 2, 0, Files.light.getHeight(), Files.light.getHeight());
        }
        return Files.light.getSubimage(Files.light.getHeight() * 3, 0, Files.light.getHeight(), Files.light.getHeight());
    }

    public static void restart() {
        send("Restarting launcher...");
        try {
            ArrayList<String> params = new ArrayList<String>();
            params.add(System.getProperty("java.home") + "/bin/java");
            params.add("-classpath");
            params.add(Starter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            params.add(Starter.class.getCanonicalName());
            ProcessBuilder pb = new ProcessBuilder(params);
            pb.start();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.exit(0);
    }

    public static String unix2hrd(long unix) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(unix * 1000));
    }

    public void delete(File file) {
        if (!file.exists())
            return;

        if (file.isDirectory()) {
            for (File f : file.listFiles())
                delete(f);
            file.delete();
        } else {
            file.delete();
        }
    }

    public static boolean checkLink(String l) {
        if (l.contains("#")) {
            return false;
        }
        return true;
    }

    public static boolean existLink(String l) {
        if (l.contains("@")) {
            return true;
        }
        return false;
    }

    /**
     * @param cl URLClassLoader
     * @author D_ART
     */
    public static void patchDir(URLClassLoader cl) {
        if (!Settings.patchDir)
            return;

        try {
            Class<?> c = cl.loadClass("net.minecraft.client.Minecraft");

            send("Changing client dir...");

            for (Field f : c.getDeclaredFields()) {
                if (f.getType().getName().equals("java.io.File") & Modifier.isPrivate(f.getModifiers()) & Modifier.isStatic(f.getModifiers())) {
                    f.setAccessible(true);
                    f.set(null, getMcDir());
                    send("Patching succesful, herobrine removed.");
                    return;
                }
            }
        } catch (Exception e) {
            sendErr("Client not patched");
        }
    }

    public static void updateLauncher() throws Exception {
        send("Launcher updater started...");
        send("Downloading file: " + Settings.updateFile + Frame.jar);

        InputStream is = new BufferedInputStream(new URL(Settings.updateFile + Frame.jar).openStream());
        FileOutputStream fos = new FileOutputStream(Starter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

        int bs = 0;
        byte[] buffer = new byte[65536];
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        while ((bs = is.read(buffer, 0, buffer.length)) != -1) {
            fos.write(buffer, 0, bs);
            md5.update(buffer, 0, bs);
        }
        is.close();
        fos.close();
        BaseUtils.send("File downloaded: " + Settings.updateFile + Frame.jar);
        restart();
    }

    private static String date() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("[HH:mm:ss]");
        String s = formatter.format(now) + " ";
        return s;
    }
}

package net.launcher.run;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import net.launcher.components.Frame;
import net.launcher.utils.BaseUtils;
import net.launcher.utils.ProcessUtils;

public class Starter
{
	public static void main(String[] args) throws Exception
	{
		try {
			String jarpath = Starter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			int memory = BaseUtils.getPropertyInt("memory", 768);
			
			ArrayList<String> params = new ArrayList<String>();
			params.add(System.getProperty("java.home")+"/bin/java");
			if(System.getProperty("sun.arch.data.model").equals("32") && (memory>1024)) {
				memory = 1024;
			}
			params.add("-Xmx"+memory+"m");
			params.add("-XX:MaxPermSize=128m");
			if(System.getProperty("os.name").toLowerCase().startsWith("mac"))
			{
				params.add("-Xdock:name=Minecraft");
				params.add("-Xdock:icon="+BaseUtils.getAssetsDir().toString()+"/favicon.png");
			}
			params.add("-classpath");
			params.add(jarpath);
			params.add(Mainclass.class.getCanonicalName());
			params.add("true");
			
			ProcessBuilder pb = new ProcessBuilder(params);
			pb.directory(new File(BaseUtils.getAssetsDir().toString()));
			Process process = pb.start();
			if (process == null) throw new Exception("Launcher can't be started!");
			new ProcessUtils(process).print();
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(Frame.main, e, "Ошибка запуска", javax.swing.JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		}
	}
}

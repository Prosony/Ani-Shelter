package test;
/**
 * @author Prosony
 * @since 0.1.0
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CheckProperties {
	private boolean isTest = false;

	private CheckProperties(){
		try {
			Properties properties = new Properties();
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String appConfigPath = rootPath + "test.properties";
			properties.load(new FileInputStream(appConfigPath));
			isTest = Boolean.parseBoolean(properties.getProperty("test"));
			System.out.println("it is test? -" +isTest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static CheckProperties instance = new CheckProperties();
	public static CheckProperties getInstance() {
		return instance;
	}

	public boolean getProperties(){
		return isTest;
	}
}

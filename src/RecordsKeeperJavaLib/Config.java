package RecordsKeeperJavaLib;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
	public static String getProperty(String key) throws IOException {
        Properties prop = new Properties();
       
        InputStream inputStream = new FileInputStream("config.properties");
        prop.load(inputStream);
      
        inputStream.close();

        String value = prop.getProperty(key);
        return value;
    }
}

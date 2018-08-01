/*
package package2.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.json.JSONException;

import org.junit.Test;

import package2.Config;
import package2.Permissions;

public class PermissionsTest {

    String validaddress ;
	
	
	Properties prop;
	
	 public boolean getPropert() throws IOException {

	         prop = new Properties();

	        String path = "config.properties";
	        File file = new File(path);
	        if (file.exists()) {
	            FileInputStream fs = new FileInputStream(path);
	            prop.load(fs);
	            fs.close();
	            return true;
	        } else {
	            return false;
	        }
	    }
	
	
	public PermissionsTest() throws IOException{
	
	if (getPropert() == true) {
           
            validaddress = Config.getProperty("validaddress");
            
        } else {
           
            validaddress = System.getenv("validaddress");
           
        }
	
	
	}
	
	@Test
	public void grantPermission() throws Exception{
		
		String result=Permissions.grantPermission(validaddress, "create,connect");
        int len = result.length();
        assertEquals(len, 64);
	    }
	
	@Test
	    public void failgrantpermissions() throws IOException, JSONException {
	        String result = Permissions.grantPermission(validaddress, "create,connect");
	        assertEquals(result, "e3bba87d1f0a980b65f12388d31c734ea38b08d11d00aaab1004e470ca419556");
	    }
	
	 @Test
	    public void revokepermissions() throws IOException, JSONException {

	        String result = Permissions.revokePermission(validaddress, "send");
	        int len = result.length();
	        assertEquals(len, 64);
	    }

	    @Test
	    public void failrevokepermissions() throws IOException, JSONException {

	        String result = Permissions.revokePermission(validaddress, "create,connect");
	        assertEquals(result, "e3bba87d1f0a980b65f12388d31c734ea38b08d11d00aaab1004e470ca419556");
	    }
	
	
	
}
*/

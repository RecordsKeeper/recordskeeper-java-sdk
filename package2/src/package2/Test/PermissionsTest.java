package package2.Test;

import static org.junit.Assert.*;

import java.io.IOException;


import org.json.JSONException;

import org.junit.Test;

import package2.Config;
import package2.Permissions;

public class PermissionsTest {

    String validaddress = Config.getProperty("validaddress");
	
	public PermissionsTest() throws IOException{}
	
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

package RecordsKeeperJavaLib.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import org.json.JSONException;
import org.junit.Test;

import RecordsKeeperJavaLib.Config;
import RecordsKeeperJavaLib.address;

public class AddressTest {
	
    String multisigaddress;
	String validaddress;
	String miningaddress;
	String nonminingaddress;
	String qty;
	String invalidaddress;
	String wrongimportaddress;
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
	
	address Address = new address();
	
	public AddressTest() throws Exception{
		
	if (getPropert() == true) {
            multisigaddress = prop.getProperty("multisigaddress");
            validaddress = prop.getProperty("validaddress");
            miningaddress = prop.getProperty("miningaddress");
            nonminingaddress = prop.getProperty("nonminingaddress");
            invalidaddress = prop.getProperty("invalidaddress");
            wrongimportaddress = prop.getProperty("wrongimportaddress");
        } else {
            multisigaddress = System.getenv("multisigaddress");
            validaddress = System.getenv("validaddress");
            miningaddress = System.getenv("miningaddress");
            nonminingaddress = System.getenv("nonminingaddress");
            invalidaddress = System.getenv("invalidaddress");
            wrongimportaddress = System.getenv("wrongimportaddress");
        }	
	
	}

	@Test
	public void getaddresstest() throws IOException, JSONException {
	    String res1 = address.getaddress();
		int address_size = res1.length();
		assertEquals(address_size,34);
	}

	@Test
	public void getMultisigAddress() throws IOException, JSONException{
		String res2 = address.getMultisigAddress(2, "miygjUPKZNV94t9f8FqNvNG9YjCkp5qqBZ, mwDbTVQcATL263JwpoE8AHCMGM5hE1kd7m, mpC8A8Fob9ADZQA7iLrctKtwzyWTx118Q9");
		assertEquals(res2, multisigaddress);	
	}
	
	 @Test
	    public void checkifvalid() throws Exception{

	        String res4 = address.checkifValid(validaddress);
	        assertEquals(res4, "Valid Address" );

	    }
	 
	 @Test
	    public void failcheckifvalid() throws Exception{

	        String res5 = address.checkifValid(invalidaddress);
	        assertEquals(res5, "Invalid Address");

	    }
	
	 @Test
	    public void checkifMineAllowed() throws Exception{

	        String res6 = address.checkifMineAllowed(miningaddress);
	        assertEquals(res6, "Address has mining permission");
	
	 }
	 
	 @Test
	    public void failcheckifMineAllowed() throws Exception{

	        String res7 = address.checkifMineAllowed(nonminingaddress);
	        assertEquals(res7, "Address does not have mining permission");

	    }
	 
	 @Test
	    public void checkBalance() throws Exception{
	        double balance = address.checkBalance(invalidaddress);
	        assertNotNull(balance);
	 } 
	 
	 
	 @Test
	    public void importAddress() throws Exception{
	        String public_address = address.importAddress(miningaddress);
	        assertEquals(public_address, "Address successfully imported");
	    }
	 
	 @Test
	    public void wrongimportAddress() throws Exception{
	        String public_address = address.importAddress(wrongimportaddress);
	        assertEquals(public_address, "Invalid Rk address or script");
	    }

	 
	 }

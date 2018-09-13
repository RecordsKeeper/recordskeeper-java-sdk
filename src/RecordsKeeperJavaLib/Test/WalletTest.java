
package RecordsKeeperJavaLib.Test;


import static org.junit.Assert.*;

import RecordsKeeperJavaLib.Config;
import RecordsKeeperJavaLib.Wallet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import org.json.JSONException;
import org.junit.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WalletTest {

        Properties prop;
	    String validaddress;
	    String privatekey;
	    String testdata;
	    String signedtestdata ;
	    String miningaddress;
	
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


	    public WalletTest() throws IOException {
  
	  if (getPropert() == true) {
          
		    
		    privatekey = Config.getProperty("privatekey");
		    validaddress = Config.getProperty("validaddress");
		    testdata = Config.getProperty("testdata");
		    signedtestdata = Config.getProperty("signedtestdata");
		    miningaddress = Config.getProperty("miningaddress");
   
        } 
	  
	  else {
	  
		  	privatekey = System.getenv("privatekey");
		  	validaddress = System.getenv("validaddress");
		  	testdata = System.getenv("testdata");
		  	signedtestdata = System.getenv("signedtestdata");
		  	miningaddress = System.getenv("miningaddress");
   
        }
	  
	  }

	    @Test
	    public void createWallet() throws Exception {

	    	JSONObject item = Wallet.createWallet();

	        String address = item.getString("public_address");
	        int address_size = address.length();
	        assertEquals(address_size, 34);
	        
	    }

	    @Test
	    public void getPrivatekey() throws Exception{
	    	
	    	String checkprivkey = Wallet.getPrivateKey(miningaddress);
	        assertEquals(checkprivkey, privatekey);
	        
	    }
	   
	    @Test
	    public void retreieveWalletinfo() throws IOException, JSONException {
	    	
	    	JSONObject item = Wallet.retrieveWalletinfo();
	        int tx_count = item.getInt("tx_count");
	        assertNotNull(tx_count);
	        
	    }
	
	    /*   @Test
	    public void importwallet(){
	        String a = wallet.importWallet();
	    }

	    @Test
	    public void dumpwallet(){
	        String a = wallet.dumpWallet();
	    }

	    @Test
	    public void lockwallet(){
	        String a = wallet.lockWallet();
	    }

	    @Test
	    public void unlockwallet(){
	        String a = wallet.unlockWallet();
	    }

	    @Test
	    public void changeWalletPassword(){
	        String a = wallet.changeWalletPassword();
	    }
	*/
	    @Test
	    public void signmessage() throws IOException, JSONException {
	    	String signedMessage = Wallet.signMessage(privatekey, testdata);
	        assertEquals(signedMessage, signedtestdata);
	    }

	    @Test
	    public void verifymessage() throws IOException, JSONException {
	    	String validity = Wallet.verifyMessage(miningaddress, signedtestdata, testdata);
	        assertEquals(validity, "Signed Message is correct");
	    }

}

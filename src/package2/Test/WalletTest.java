/*
package package2.Test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import package2.Config;
import package2.Wallet;

public class WalletTest {

	
	  

	  
	    String validaddress = Config.getProperty("validaddress");
	    String privatekey = Config.getProperty("privatekey");
	    String testdata = Config.getProperty("testdata");
	    String signedtestdata = Config.getProperty("signedtestdata");
	    String miningaddress = Config.getProperty("miningaddress");

	    public WalletTest() throws IOException {}

	    @Test
	    public void createWallet() throws Exception {

	        JSONObject item = Wallet.createWallet();

	        String address = item.getString("public_address");
	        System.out.println(address);
	        int address_size = address.length();
	        assertEquals(address_size, 40);
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
	        assertNotEquals(tx_count, 463757);
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
	
	    
	    @Test
	    public void signmessage() throws IOException, JSONException {
	        String signedMessage = Wallet.signMessage(privatekey, testdata);
	        System.out.println(signedMessage);
	        System.out.println(signedMessage);
	        assertEquals(signedMessage, signedtestdata);
	    }

	    @Test
	    public void verifymessage() throws IOException, JSONException {
	        String validity = Wallet.verifyMessage(miningaddress, signedtestdata, testdata);
	        assertEquals(validity, "Yes, message is verified");
	    }
	
	
	
	
}
*/

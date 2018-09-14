package RecordsKeeperJavaLib.Test;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException;
import java.math.BigInteger;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import RecordsKeeperJavaLib.Config;
import RecordsKeeperJavaLib.transactions;


public class TransactionsTest {

    public Properties prop;
    public String validaddress;
    public String dumptxhex;
    public String miningaddress;
    public String recieveraddress;
    public String checkdata;
    public String privatekey;
    public double amount;
    public String dumpsignedtxhex;
    public String dumptxid;
    public String signedtxhex;
	
	
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

	    public TransactionsTest() throws IOException {
	
         if (getPropert() == true) {
        	 
            validaddress = prop.getProperty("validaddress");
            miningaddress = prop.getProperty("miningaddress");
            amount = Double.parseDouble(Config.getProperty("amount"));
            checkdata = prop.getProperty("checkdata");
            dumptxhex = prop.getProperty("dumptxhex");
            privatekey = prop.getProperty("privatekey");
            dumpsignedtxhex = prop.getProperty("dumpsignedtxhex");
            dumptxid = prop.getProperty("dumptxid");
            signedtxhex = prop.getProperty("signedtxhex");
            recieveraddress = prop.getProperty("recieveraddress");
            
        } else {
        	
            validaddress = System.getenv("validaddress");
            miningaddress = System.getenv("miningaddress");
            amount = Double.parseDouble(System.getenv("amount"));
            checkdata = System.getenv("checkdata");
            dumptxhex = System.getenv("dumptxhex");
            privatekey = System.getenv("privatekey");
            dumpsignedtxhex = System.getenv("dumpsignedtxhex");
            dumptxid = System.getenv("dumptxid");
            signedtxhex = System.getenv("signedtxhex");
            recieveraddress = System.getenv("recieveraddress");
          }         
	    }

	    @Test
	     public void sendTransaction() throws Exception {

	        String txid = transactions.sendTransaction(validaddress, miningaddress, checkdata, amount);	        
	        int tx_size = txid.length();
	        assertEquals(tx_size, 64);
	    }

	    @Test
	     public void createRawTransaction() throws IOException, JSONException {

	        String txhex = transactions.createRawTransaction( validaddress, miningaddress, amount, checkdata);	        
	        int tx_size = txhex.length();
	        assertEquals(tx_size, 294);

	    }

	    @Test
	     public void signrawtransaction() throws IOException, JSONException {

	        String txhex = transactions.signRawTransaction(dumptxhex, privatekey);
	        int tx_size = txhex.length();	        
	        assertEquals(tx_size, 508);
	    }

	    @Test
	     public void sendrawtransaction() throws IOException, JSONException {

	        String txid = transactions.sendRawTransaction(signedtxhex);
	        int tx_size = txid.length();
	        assertEquals(tx_size, 64);
	    }

	    @Test
	     public void sendsignedtransaction() throws IOException, JSONException {

	        String txid = transactions.sendSignedTransaction(validaddress, recieveraddress, amount, privatekey, checkdata);
	        int tx_size = txid.length();
	        assertEquals(tx_size, 64);
	        
	    }

        @Test
	      public void retrievetransaction() throws IOException, JSONException {
	        
        	JSONObject res= transactions.retrieveTransaction(dumptxid);
	        String sentdata = res.getString("sent_data");
	        assertEquals(sentdata, checkdata);

	    }
	            	           
	    @Test
	     public void getfee() throws IOException, JSONException {
	        double fees = transactions.getFee(validaddress, dumptxid);
	        assertEquals(fees, 0.000, 0.001);
	    }
	
}

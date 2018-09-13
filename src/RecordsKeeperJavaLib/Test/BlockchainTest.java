package RecordsKeeperJavaLib.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;


import RecordsKeeperJavaLib.Blockchain;
import RecordsKeeperJavaLib.Config;


public class BlockchainTest {
	
	
    public Properties prop;
    public String chain;
    public String stream;
    public int port;

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

    public BlockchainTest() throws IOException {
    
	    if (getPropert() == true) {
            chain = Config.getProperty("chain");
            stream = Config.getProperty("stream");
            port = Integer.parseInt(Config.getProperty("port"));
        } else {
            chain = System.getenv("chain");
            stream = System.getenv("stream");
            port =Integer.parseInt(System.getenv("port"));
        }

    }

    @Test
    public void getChainInfo() throws Exception {
    	
    	JSONObject res1= Blockchain.getChainInfo();
        String chainname=res1.getString("chain_name");
        assertEquals(chainname, chain);

    }

    @Test
    public void getNodeInfo() throws IOException, JSONException {
       
    	JSONObject res2= Blockchain.getNodeInfo();
    	
    	int info = res2.getInt("synced_blocks");
        assertNotNull(info );

        int balance = res2.getInt("node_balance");
        assertNotNull(balance);
   
    }

    @Test
    public void permissions() throws IOException, JSONException {
        String output = Blockchain.permissions();
        assertEquals(output, "mine,admin,activate,connect,send,receive,issue,create");
    }

    @Test
    public void getpendingTransactions() throws IOException, JSONException {
    	
        JSONObject res3 = Blockchain.getpendingTransactions();
        String pendingtx=res3.getString("tx");
        assertEquals(pendingtx,"Currently, No pending Transactions.");

        int pendingtxcount=res3.getInt("tx_count");
        assertEquals(pendingtxcount, 0);
        
    }

    @Test
     public void checkNodeBalance() throws IOException, JSONException {
        JSONObject balance = Blockchain.checkNodeBalance();
        assertNotEquals(balance, 0);
    
    }
}

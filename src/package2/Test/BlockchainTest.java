package package2.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import package2.Config;
import package2.blockchain;


public class BlockchainTest {

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
   
    String chain;
    String stream;
   
    int port;

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
    	
    	JSONObject res1= blockchain.getChainInfo();
        String chainname=res1.getString("chain_name");
    //    System.out.println(chainname);
        assertEquals(chainname, chain);

        String rootstream = res1.getString("root_stream");
     //   System.out.println(rootstream);
        assertEquals(rootstream, stream);

        int rpcport = res1.getInt("default_rpcport");
      //  System.out.println(rpcport);
     //   System.out.println(port);
        assertEquals(rpcport, port);

        int networkport = res1.getInt("default_networkport");
      //  System.out.println(networkport);
        assertEquals(networkport, 8379);  
    }

    @Test
    public void getNodeInfo() throws IOException, JSONException {
       
    	JSONObject res2= blockchain.getNodeInfo();
    	
    	int info = res2.getInt("synced_blocks");
        assertNotNull(info );

        int balance = res2.getInt("node_balance");
        assertNotNull(balance);

        int difficulty = res2.getInt("difficulty");
        assertNotSame(difficulty, 1);
   
    }

    @Test
        public void permissions() throws IOException, JSONException {
        String output = blockchain.permissions();
      //  System.out.println(output);
        assertEquals(output, "mine,admin,activate,connect,send,receive,issue,create");
    }

    @Test
   public void getpendingTransactions() throws IOException, JSONException {
    	
       JSONObject res3 = blockchain.getpendingTransactions();
        String pendingtx=res3.getString("tx");
        assertEquals(pendingtx,"[]");

        int pendingtxcount=res3.getInt("tx_count");
        assertEquals(pendingtxcount, 0);
    }

    @Test
     public void checkNodeBalance() throws IOException, JSONException {
        int balance = blockchain.checkNodeBalance();
        assertNotEquals(balance, 0);
    
    }
}

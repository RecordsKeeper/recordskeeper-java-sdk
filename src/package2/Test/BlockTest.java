package package2.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import package2.Config;
import package2.block;


public class BlockTest {
	
	  public Properties prop;
	
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
	   
	    

	    public BlockTest() throws IOException {
	    
          if (getPropert() == true) {
            mainaddress = prop.getProperty("mainaddress");
        } else {
            mainaddress = System.getenv("mainaddress");
        }
	    
	    }

	    @Test
	    public void blockinfo() throws Exception {
	        JSONObject res1 = block.blockinfo("100");
	        
	        String miner = res1.getString("miner");
	        assertEquals(miner, mainaddress);

	        int size = res1.getInt("size");	        
	        assertEquals(size, 300);

	        int nonce = res1.getInt("nonce");
	        assertEquals(nonce, 260863);

	      
	    }

	    @Test
	    public void retreiveBlocks() throws Exception{
	        JSONArray res1 = block.retrieveBlocks("10-20");
	        
	        JSONObject a=res1.getJSONObject(0);
	        String miner =a.getString("miner"); 
	        assertEquals(miner, mainaddress);

	        int blocktime=a.getInt("blocktime");
	       // System.out.println(c);
	        assertEquals(blocktime, 1522831655);

	        String blockhash = a.getString("blockhash");
	      //  System.out.println(blockhash);
	        assertEquals(blockhash, "000003533bb68197a881ed2018c66e46a4080a76e070b868cf5d56c276c19019");

	        int txcount = a.getInt("tx_count");
	       // System.out.println(txcount);
	        assertEquals(txcount, 1);
	    }
	

}

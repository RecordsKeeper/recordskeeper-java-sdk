package RecordsKeeperJavaLib.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import RecordsKeeperJavaLib.Config;
import RecordsKeeperJavaLib.block;


public class BlockTest {
	
	  public Properties prop;
	  public String mainaddress;
	  public String testblock;
	  public String blockrange;
	
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
            testblock = prop.getProperty("testblock");
            blockrange = prop.getProperty("blockrange");
            
          	} 
          else {
            mainaddress = System.getenv("mainaddress");
            testblock = System.getenv("testblock");
            blockrange = System.getenv("blockrange");
          }
	    
	    }

	    @Test
	    public void blockinfo() throws Exception {
	        JSONObject res1 = block.blockinfo(testblock);
	        
	        String miner = res1.getString("miner");
	        assertEquals(miner, mainaddress);
	        System.out.println(miner);

	        int size = res1.getInt("size");	 
	        System.out.println(size);
	        assertNotNull(size);     
	    }

	    @Test
	    public void retreiveBlocks() throws Exception{
	        JSONArray res1 = block.retrieveBlocks(blockrange);
	        
	        JSONObject a = res1.getJSONObject(0);

	        int blocktime=a.getInt("blocktime");
	        System.out.println(blocktime);
	        assertNotNull(blocktime);


	        int txcount = a.getInt("tx_count");
	        System.out.println(txcount);
	        assertNotNull(txcount);
	    }
	
}

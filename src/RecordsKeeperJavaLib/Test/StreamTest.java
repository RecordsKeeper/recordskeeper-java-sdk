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
import RecordsKeeperJavaLib.stream;


public class StreamTest {
	
    public Properties prop;
    public String miningaddress;
    public String stream;
    public String testkey;
    public String checkdata;
    public String dumptxid;
    public String validaddress;
    stream Stream = new stream();
	
	
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
	
	    public StreamTest() throws IOException {
	    if (getPropert() == true) {
            miningaddress = prop.getProperty("miningaddress");
            stream = prop.getProperty("stream");
            testkey = prop.getProperty("testkey");
            checkdata = prop.getProperty("checkdata");
            dumptxid = prop.getProperty("dumptxid");
            validaddress = prop.getProperty("validaddress");
            
            
        } else {
            miningaddress = System.getenv("miningaddress");
            validaddress = System.getenv("validaddress");
            stream = System.getenv("stream");
            testkey = System.getenv("testkey");
            dumptxid = System.getenv("dumptxid");
            checkdata = System.getenv("checkdata");
        }
	    
	    
	    }

	    @Test
	    public void test_publish() throws Exception {
	      

	        String txid = Stream.publish(validaddress, stream, testkey, checkdata);
	        int tx_size = txid.length();
	        assertEquals(tx_size, 64);
	        
	    }

	   @Test
	    public void test_retrieve() throws IOException, JSONException {
	        
		   String raw_data = Stream.retrieve(stream, dumptxid);
	       assertEquals(raw_data, checkdata);
	       
	    }

	   @Test
	    public void test_retrieveWithAddress() throws IOException, JSONException {
	    	
	    	JSONObject res=Stream.retrieveWithAddress(stream, validaddress, 100);
	        String data = res.getString("raw_data");
	        assertEquals(data, checkdata);
	        
	    }
		    
	   @Test
	    public void test_retrieveWithKey() throws IOException, JSONException {
	    	
	    	JSONObject res1 = Stream.retrieveWithKey(stream, testkey, 100);	 
	    	String data = res1.getString("raw_data");
	        assertEquals(data, checkdata);
	        
	    }
	    
	   @Test
	    public void test_verifydata() throws IOException, JSONException {
	        
	    	String result = Stream.verifyData(stream, checkdata, 100);
	        assertEquals(result, "Data is successfully verified.");
	        
	    }
	    
	   @Test
	    public void test_retrieveItems() throws IOException, JSONException {
	    	
	    	JSONArray res2= Stream.retrieveItems(stream, 100);
	        JSONObject res3 = res2.getJSONObject(0);
	        
	        String result=res3.getString("key");

	        assertEquals(result, testkey);
	        
	    }
	    

}

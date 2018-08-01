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
import package2.stream;


public class StreamTest {
	
    public Properties prop;
    public String miningaddress;
    public String stream;
    public String testdata;
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
            testdata = prop.getProperty("key");
        } else {
            miningaddress = System.getenv("miningaddress");
            stream = System.getenv("stream");
            testdata = System.getenv("key");
        }
	    
	    
	    }

	    @Test
	    public void publishs() throws Exception {
	      

	        String txid = Stream.publish(miningaddress,stream,testdata,"This is test data");
	        int tx_size = txid.length();
	        assertEquals(tx_size, 64);
	    }

	   @Test
	    public void retrieve() throws IOException, JSONException {
	        String raw_data = Stream.retrieve(stream, "eef0c0c191e663409169db0972cc75ff91e577a072289ee02511b410bc304d90");
	        assertEquals(raw_data,"testdata");
	    }

	    @Test
	    public void retrieveWithAddress() throws IOException, JSONException {
	    	JSONObject res=Stream.retrieveWithAddress(stream, miningaddress);
	        String data = res.getString("data");
	     //   System.out.println(data);
	        assertEquals(data, "5468697320697320746573742064617461");
	    }
		    
	    @Test
	    public void retrieveWithKey() throws IOException, JSONException {
	    	JSONObject res1=Stream.retrieveWithKey(stream, testdata);	 
	    	String data = res1.getString("data");
	        assertEquals(data, "5468697320697320746573742064617461");
	    }
	    
	    @Test
	    public void verifydata() throws IOException, JSONException {
	        String result = Stream.verifyData(stream, testdata, 5);
	        assertEquals(result, "Data is successfully verified.");
	    }
	    
	    @Test
	    public void retrieveItems() throws IOException, JSONException {
	    	
	    	JSONArray res2=Stream.retrieveItems(stream, 5);
	        JSONObject res3 = res2.getJSONObject(0);
	        String result=res3.getString("raw_data");
	        
	        System.out.println(result);
	        assertEquals(result, "This is test data");
	    }
	    

}

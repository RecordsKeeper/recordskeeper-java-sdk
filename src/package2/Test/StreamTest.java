package package2.Test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import package2.Config;
import package2.stream;


public class StreamTest {
	
	
	   stream Stream = new stream();
	    String miningaddress = System.getenv("miningaddress");
	    String stream = System.getenv("stream");
	    String testdata = System.getenv("key");

	    public StreamTest() throws IOException {}

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

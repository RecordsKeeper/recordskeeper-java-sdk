package RecordsKeeperJavaLib;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import RecordsKeeperJavaLib.Config;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import static okio.ByteString.decodeHex;

/**
 * <h1>Stream Class Usage</h1>
 * Stream class to call stream related functions like publish, retrievewithtxid, retrieveWithAddress, retrieveWithKey and verify data functions which are used to publish data into the stream, retrieve data from the stream and verify data from the stream.
 * <p>Library to work with RecordsKeeper streams.</p>
 * You can publish, retrieve and verify stream data by using stream class.<br>
 * You just have to pass parameters to invoke the pre-defined functions.
 * <h2>Libraries</h2>
 * Import these java libraries first to get started with the functionality.<br>
 * <p><code>import okhttp3.Credentials;<br>
 * import okhttp3.MediaType;<br>
 * import okhttp3.OkHttpClient;<br>
 * import okhttp3.Request;<br>
 * import okhttp3.RequestBody;<br>
 * import okhttp3.Response;<br>
 * import RecordsKeeperJavaLib.Config;<br>
 * import org.json.JSONArray;<br>
 * import org.json.JSONException;<br>
 * import org.json.JSONObject;<br>
 * import java.io.IOException;<br>
 * import java.util.Arrays;<br>
 * <h2>Create Connection</h2>
 * Entry point for accessing any class resources. Import values from config file.
 * <p><code>String url = Config.getProperty("url");<br>
 * String chain = Config.getProperty("chain");</code></p>
 * URL: Url to connect to the chain ([RPC Host]:[RPC Port]) <br>
 * Chain-name: chain name <br>
 * <p>Set the url and chain name value in the config file to change the network-type.</p>
 * <h2>Node Authentication</h2>
 * Import values from config file.
 * <p><code>String rkuser = Config.getProperty("rkuser");<br>
 * String passwd = Config.getProperty("passwd");</code></p>
 * User name: The rpc user is used to call the APIs.<br>
 * Password: The rpc password is used to authenticate the APIs.<br>
 * Now we have node authentication credentials.
 */

public class stream {
	
	public static Properties prop;
	public static boolean getPropert() throws IOException {

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

	/**
     * Default Constructor Class
     */
   
    public stream() throws IOException {}

	
    
    
    /**
     * Publish <br>
     * The data is converted into hex value.
     * <p><code>publish(address, stream, key, dataHex); <br>
     * txid = publish(address, stream, key, dataHex); // publish() function call <br>
     * System.out.println(txid)  // prints the transaction id of the data published</code></p>
     * You have to pass these four arguments to the publish function call:
     * @param address Address of the publihser
     * @param stream Stream to which you want your data to be published
     * @param key key Value for the data to be published
     * @param data Data Hex of the data to be published
     * @return It will return a transaction id of the published data, use this information to retrieve the particular data from the stream
     */

    
    
    public static String publish(String address, String stream, String key, String data) throws IOException, JSONException {
    	String datahex;
    	
    	
    	byte[] byt = data.getBytes("UTF-8");
     	BigInteger big = new BigInteger(byt);
     	String hex = big.toString(16);
    	    String rkuser;
			String passwd;
			String chain;
			String url;
	        if (getPropert() == true) {
	            url = Config.getProperty("url");
	            rkuser = Config.getProperty("rkuser");
	            passwd = Config.getProperty("passwd");
	            chain = Config.getProperty("chain");
	        } else {
	            url = System.getenv("url");
	            rkuser = System.getenv("rkuser");
	            passwd = System.getenv("passwd");
	            chain = System.getenv("chain");
	        }
	     
	     String resp;
	     String txid;

         OkHttpClient client = new OkHttpClient();
         MediaType mediaType = MediaType.parse("application/json");
         String credential = Credentials.basic(rkuser, passwd);
        datahex = "\"" + hex + "\"";
        address = "\"" + address + "\"";
        stream = "\"" + stream + "\"";
        key = "\"" + key + "\"";

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"publishfrom\",\"params\":[" + address + "," + stream + "," + key + "," + datahex + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
       // System.out.println(resp);
        JSONObject jsonObject = new JSONObject(resp);
        txid = jsonObject.getString("result");

        return txid;
    }
    
    
    /**
     * Retrieve an existing item from a particular stream against a transaction id.
     * <p><code>retrieve(stream, txid);  // call retrieve function with stream and txid as the required parameters <br>
     * result = retrieve(stream, txid);<br>
     * System.out.println(result);   //prints info of the transaction</code></p>
     * You have to pass these two arguments to the retrieve function call:
     * @param stream which you want to access
     * @param txid id of the data you want to retrieve
     * @return It will return the item's details like publisher address, key value, confirmations, hexdata and transaction id.
     */
    
    

    public static String retrieve(String stream, String txid) throws IOException, JSONException {
  
    	  String rkuser;
			String passwd;
			String chain;
			String url;
	        if (getPropert() == true) {
	            url = Config.getProperty("url");
	            rkuser = Config.getProperty("rkuser");
	            passwd = Config.getProperty("passwd");
	            chain = Config.getProperty("chain");
	        } else {
	            url = System.getenv("url");
	            rkuser = System.getenv("rkuser");
	            passwd = System.getenv("passwd");
	            chain = System.getenv("chain");
	        }
          OkHttpClient client = new OkHttpClient();
          MediaType mediaType = MediaType.parse("application/json");
          String credential = Credentials.basic(rkuser, passwd);
          String raw_data = "";
          String resp;
          String data;
          
       stream = "\"" + stream + "\"";
       txid = "\"" + txid + "\"";

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getstreamitem\",\"params\":[" + stream + "," + txid + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
        JSONObject jsonObject = new JSONObject(resp);
        JSONObject object = jsonObject.getJSONObject("result");
        data = object.getString("data");
        byte[] bytes = decodeHex(data).toByteArray();
        raw_data = new String(bytes);

        return raw_data;
    }

    
    /**
     * Retrieve an item against a particular publisher address.
     * <p><code>retrieveWithAddress(stream, address); <br>
     * key,data, txid = retrieveWithAddress(stream, address);  //retrieveWithAddress() function call <br>
     * byte[] bytes = decodeHex(data).toByteArray();
     * raw_data = new String(bytes);   //convert hex data into raw data <br>
     * System.out.println(key);   //prints key value of the data  <br>
     * System.out.println(data);   //prints hex data <br>
     * System.out.println(txid);   //prints transaction id of the data <br>
     * System.out.println(raw_data);   //prints raw data</code></p>
     * You have to pass these two arguments to the verifyWithAddress function call:
     * @param stream which you want to access
     * @param address address of the data publisher you want to verify
     * @return It will return the key value, hexdata, raw data and transaction id of the published item.
     */

    
    
    
    public static JSONObject retrieveWithAddress(String stream, String address) throws IOException, JSONException {

        stream = "\"" + stream + "\"";
        address = "\"" + address + "\"";
        String txid = "";
        String key = "";
        String data = "";
        String raw_data = "";
        String resp;
                        String rkuser;
			String passwd;
			String chain;
			String url;
	        if (getPropert() == true) {
	            url = Config.getProperty("url");
	            rkuser = Config.getProperty("rkuser");
	            passwd = Config.getProperty("passwd");
	            chain = Config.getProperty("chain");
	        } else {
	            url = System.getenv("url");
	            rkuser = System.getenv("rkuser");
	            passwd = System.getenv("passwd");
	            chain = System.getenv("chain");
	        }
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String credential = Credentials.basic(rkuser, passwd);
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"liststreampublisheritems\",\"params\":[" + stream + "," + address + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
        JSONObject jsonObject = new JSONObject(resp);
        JSONArray array = jsonObject.getJSONArray("result");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            key = object.getString("key");
            data = object.getString("data");
            byte[] bytes = decodeHex(data).toByteArray();
            raw_data = new String(bytes);
            txid = object.getString("txid");
        }
        JSONObject item=new JSONObject();
        item.put("key", key);
        item.put("data", data);
        item.put("raw_data", raw_data);
        item.put("txid", txid); 
        
        return item;
    }
    
    
    /**
     * Retrieve an item against a particular key value.
     * <p><code>verifyWithKey(stream, address); <br>
     * publisher,data, txid = verifyWithKey(stream, address); <br>
     * byte[] bytes = decodeHex(data).toByteArray(); <br>
     * raw_data = new String(bytes);   //convert hex data into raw data <br>
     * System.out.println(publisher);     //prints publisher's address of the published data <br>
     * System.out.println(data);     //prints hex data <br>
     * System.out.println(txid);     //prints transaction id of the data <br>
     * System.out.println(raw_data);     //prints raw data</code></p>
     * You have to pass these two arguments to the verifyWithKey function call:
     * @param stream which you want to access
     * @param key key value of the published data you want to verify
     * @return It will return the key value, hexdata, raw data and transaction id of the published item.
     */
    
    

    public static JSONObject retrieveWithKey(String stream, String key) throws IOException, JSONException {

        stream = "\"" + stream + "\"";
        key = "\"" + key + "\"";

        String txid = "";        
        String data = "";
        String raw_data = "";
        String resp;
                        String rkuser;
			String passwd;
			String chain;
			String url;
	        if (getPropert() == true) {
	            url = Config.getProperty("url");
	            rkuser = Config.getProperty("rkuser");
	            passwd = Config.getProperty("passwd");
	            chain = Config.getProperty("chain");
	        } else {
	            url = System.getenv("url");
	            rkuser = System.getenv("rkuser");
	            passwd = System.getenv("passwd");
	            chain = System.getenv("chain");
	        }
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String credential = Credentials.basic(rkuser, passwd);
         
         
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"liststreamkeyitems\",\"params\":[" + stream + "," + key + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
        JSONObject jsonObject = new JSONObject(resp);
        JSONArray array = jsonObject.getJSONArray("result");
        JSONObject object1 = array.getJSONObject(0);
        JSONArray publishers = object1.getJSONArray("publishers");
        String pubs = publishers.getString(0);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            data = object.getString("data");
            byte[] bytes = decodeHex(data).toByteArray();
            raw_data = new String(bytes);
            txid = object.getString("txid");
        }

        JSONObject item=new JSONObject();
        item.put("publisher", pubs);
        item.put("data", data);
        item.put("raw_data", raw_data);
        item.put("txid", txid); 
        
        return item;
        
    }

 
    
    /**
     * Verify an data item on a particular stream of RecordsKeeper Blockchain.
     * <p><code>verifyData(stream, data, count); <br>
     * result = verifyData(stream, data, count); <br>
     * System.out.println(result);     //prints if verification is successful or not</code></p>
     * You have to pass these three arguments to the verifyWithKey function call:
     * @param stream which you want to access
     * @param data against which you want to make a query
     * @param count count of items which will be queried
     * @return It will return the result if verification is successful or not.
     */
    
    public static String verifyData(String stream, String data, int count) throws IOException, JSONException {

    	
        String txid;        
        String raw_data = "";
        String resp;
                        String rkuser;
			String passwd;
			String chain;
			String url;
	        if (getPropert() == true) {
	            url = Config.getProperty("url");
	            rkuser = Config.getProperty("rkuser");
	            passwd = Config.getProperty("passwd");
	            chain = Config.getProperty("chain");
	        } else {
	            url = System.getenv("url");
	            rkuser = System.getenv("rkuser");
	            passwd = System.getenv("passwd");
	            chain = System.getenv("chain");
	        }
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String credential = Credentials.basic(rkuser, passwd);
        stream = "\"" + stream + "\"";
        data = "\"" + data + "\"";
        
        boolean False = false;

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"liststreamitems\",\"params\":[" + stream + "," + False + "," + count + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
        JSONObject jsonObject = new JSONObject(resp);
        JSONArray array = jsonObject.getJSONArray("result");
        for (int i = 0; i < count; i++) {
            JSONObject object = array.getJSONObject(i);
            data = object.getString("data");
            byte[] bytes = decodeHex(data).toByteArray();
            raw_data = new String(bytes);
        }
        String result;
        if (data == null) {
            result = "Data not found";
        } else {
            result = "Data is successfully verified.";
        }

        return result;
    }

    
    
    /**
     * Retrieve data items on a particular stream of RecordsKeeper Blockchain.
     * <p><code>retrieveItems(stream, count); <br>
     * address, key_value, raw_data, txid = retrieveItems(stream, count); <br>
     * System.out.println(address );   //prints address of the publisher of the item <br>
     * System.out.println(key_value);   //prints key value of the stream item <br>
     * System.out.println(raw_data);   //prints raw data published <br>
     * System.out.println(txid);   //prints tx id of the item published</code></p>
     * You have to pass these two arguments to the verifyWithKey function call:
     * @param stream which you want to access
     * @param count count of items which will be queried
     * @return It will return the address, key value, data and transaction id of the stream item published.
     */
    
    
    public static JSONArray retrieveItems(String stream, int count) throws IOException, JSONException {

        stream = "\"" +stream+ "\"";
        
        String txid = "";        
        String address = ""; 
        String data;
        String raw_data = "";
        String resp;
        String key="";
       String rkuser;
			String passwd;
			String chain;
			String url;
	        if (getPropert() == true) {
	            url = Config.getProperty("url");
	            rkuser = Config.getProperty("rkuser");
	            passwd = Config.getProperty("passwd");
	            chain = Config.getProperty("chain");
	        } else {
	            url = System.getenv("url");
	            rkuser = System.getenv("rkuser");
	            passwd = System.getenv("passwd");
	            chain = System.getenv("chain");
	        }
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String credential = Credentials.basic(rkuser, passwd);
        

        boolean False = false;

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"liststreamitems\",\"params\":[" + stream + "," + False + "," + count + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
        JSONObject jsonObject = new JSONObject(resp);
        JSONArray array = jsonObject.getJSONArray("result");
     
        JSONObject item=new JSONObject();
        JSONArray arr= new JSONArray();
        
        
        for(int i = 0; i<count; i++){
            JSONObject object = array.getJSONObject(i);
            JSONArray array1 = object.getJSONArray("publishers");
            address = array1.getString(0);
            key = object.getString("key");
            data = object.getString("data");
            txid = object.getString("txid");
            
            JSONObject jsonObject1 = array.getJSONObject(0);
            data = jsonObject1.getString("data");
            byte[] bytes = decodeHex(data).toByteArray();
            raw_data = new String(bytes);
            
           
            item.put("raw_data", raw_data);
            item.put("address", address);
            
            item.put("txid", txid);
            item.put("key_value", key);
            arr.put(item);
            
        }

        
        
        return arr;

     
    }





}   

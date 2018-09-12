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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * <h1>Block Class Usage</h1>
 * Block class is used to call block related functions.
 * <p>Library to work with RecordsKeeper block informaion.</p>
 * <p>You can collect block information by using block class.
 * You just have to pass parameters to invoke the pre-defined functions.</p>
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




public class block {
	
	
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
	
        public block() throws IOException{
	    
	}
    
     
        /**
         * Block info to retrieve block information.
         * <p>Block class is used to call block related function like blockinfo which is used to retrieve block details like block's hash value, size, nonce, transaction ids, transaction count, miner address, previous block hash, next block hash, merkleroot, blocktime and difficulty of the block for which you have made the query.</p>
         * <p><code>blockinfo(block_height);<br>
         * tx_count, tx, miner, size, nonce, blockHash, prevblock, nextblock, merkleroot, blocktime, difficulty = blockinfo(block_height); //blockinfo() function call <br>
         * System.out.println(tx_count);      //prints transaction count of the block <br>
         * System.out.println(tx);           //prints transaction ids of the block <br>
         * System.out.println(size);           //prints size of the block <br>
         * System.out.println(blockHash);    //prints hash value of the block <br>
         * System.out.println(nonce);         //prints nonce of the block <br>
         * System.out.println(miner);          //prints miner's address of the block <br>
         * System.out.println(nextblock);      //prints next block's hash <br>
         * System.out.println(prevblock);      //prints previous block's hash <br>
         * System.out.println(merkleroot);     //prints merkle root of the block <br>
         * System.out.println(blocktime);      //prints time at which block is mined <br>
         * System.out.println(difficulty);     //prints difficulty of the block</code></p>
         * You have to pass these block height as the argument to the blockinfo function call:
         * @param block_height height of the block of which you want to collect info
         * @return It will return transaction ids, transaction count, nonce, size, hash value, previous block's hash value, next block hash value, merkle root, difficulty, blocktime and miner address of the block.
         */  
        
        
    public static JSONObject blockinfo(String block_height) throws IOException, JSONException {

        block_height = "\"" +block_height+ "\"";
        MediaType mediaType = MediaType.parse("application/json");
    	                String rkuser;
			String passwd;
			String chain;
			String url;
	        if (getPropert() == true) {
	            url = prop.getProperty("url");
	            rkuser = prop.getProperty("rkuser");
	            passwd = prop.getProperty("passwd");
	            chain = prop.getProperty("chain");
	        } else {
	            url = System.getenv("url");
	            rkuser = System.getenv("rkuser");
	            passwd = System.getenv("passwd");
	            chain = System.getenv("chain");
	        }
    	String credential = Credentials.basic(rkuser, passwd);
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getblock\",\"params\":["+block_height+"],\"id\":1,\"chain_name\":\""+chain+"\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String resp;
        resp = response.body().string();
        
        JSONObject jsonObject = new JSONObject(resp);
        JSONObject object = jsonObject.getJSONObject("result");
        JSONArray array = object.getJSONArray("tx");
       
        int tx_count = array.length();
       
        int size = object.getInt("size");
        int nonce = object.getInt("nonce");
        int blocktime = object.getInt("time");
        int difficulty = object.getInt("difficulty");
        String miner = object.getString("miner");
        String blockHash = object.getString("hash");
        String prevblock = object.getString("previousblockhash");
        String nextblock = object.getString("nextblockhash");
        String merkleroot = object.getString("merkleroot");
        String add = "";
        for (int i = 0; i<tx_count; i++){
             add = array.getString(i);
        }
        
        JSONObject item = new JSONObject();
        item.put("tx_count", tx_count);
        item.put("size", size);
        item.put("nonce", nonce);
        item.put("blockhash", blockHash);
        item.put("prevblock", prevblock);
        item.put("nextblock", nextblock);
        item.put("merkleroot", merkleroot);
        item.put("blocktime", blocktime);
        item.put("difficulty", difficulty);
        item.put("miner", miner);
        item.put("tx", add);
       
        
        return item ;
        
        
    }

    
    /**
     * Retrieve a range of blocks on RecordsKeeper chain.
     * <p><code>retrieveBlocks(block_range);<br>
     * block_hash, miner_add, block_time, txcount = retrieveBlocks(block_range);  //retrieveBlocks() function call<br>
     * System.out.println(block_hash); //prints hash of the blocks<br>
     * System.out.println(miner_add);  //prints miner of the blocks<br>
     * System.out.println(block_time);  //prints block time of the blocks<br>
     * System.out.println(txcount);  //prints transaction count of the blocks</code></p>
     * You have to pass these block height as the argument to the retrieveBlocks function call:
     * @param block_range range of the block of which you want to collect info
     * @return It will return blockhash, miner address, blocktime and transaction count of the blocks.
     */
    
    
    public static JSONArray retrieveBlocks(String block_range) throws IOException, JSONException {

        block_range = "\"" + block_range + "\"";
        MediaType mediaType = MediaType.parse("application/json");
    	                String rkuser;
			String passwd;
			String chain;
			String url;
	        if (getPropert() == true) {
	            url = prop.getProperty("url");
	            rkuser = prop.getProperty("rkuser");
	            passwd = prop.getProperty("passwd");
	            chain = prop.getProperty("chain");
	        } else {
	            url = System.getenv("url");
	            rkuser = System.getenv("rkuser");
	            passwd = System.getenv("passwd");
	            chain = System.getenv("chain");
	        }
    	String credential = Credentials.basic(rkuser, passwd);
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"listblocks\",\"params\":[" + block_range + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String resp;
        
        
        
      
        
        resp = response.body().string();
        JSONObject jsonObject = new JSONObject(resp);
        JSONArray array = jsonObject.getJSONArray("result");
        int block_count = array.length();
        int tx_count = array.length();
        
        JSONObject item = new JSONObject();
        JSONArray arr= new JSONArray();
        
        for (int i = 0; i < block_count; i++) {

            JSONObject object = array.getJSONObject(i);
            int blocktime = object.getInt("time");
            String blockHash = object.getString("hash");
            blockHash = object.getString("hash");
            String miner = object.getString("miner");
            blocktime = object.getInt("time");
            tx_count = object.getInt("txcount");
             
              
            item.put("tx_count", tx_count);
            item.put("miner", miner);
            item.put("blockhash", blockHash);
            item.put("blocktime", blocktime);
            arr.put(item);
         
        }
        
      
		return arr;
           
			
             
    }



}
	
	
	


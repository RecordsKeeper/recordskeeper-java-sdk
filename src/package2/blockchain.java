package package2;


import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSource;
import package2.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Arrays;
  


/**
 * <h1>Blockchain Class Usage</h1>
 * Blockchain class is used to call blockchain related functions like retrieving blockchain parameters, retrieving node's
 information, retrieving mempool's information, retrieving node's permissions and check node's balance functions which are used on the RecordsKeeeper Blockchain.
 * <p>Library to work with Blockchain in RecordsKeeper Blockchain.</p>
 * You can generate new address, check all addresses, check address validity, check address permissions, check address balance
 by using Address class. You just have to pass parameters to invoke the pre-defined functions.
 * <h2>Libraries</h2>
 * Import these java libraries first to get started with the functionality.<br>
 * <p><code>import okhttp3.Credentials;<br>
 * import okhttp3.MediaType;<br>
 * import okhttp3.OkHttpClient;<br>
 * import okhttp3.Request;<br>
 * import okhttp3.RequestBody;<br>
 * import okhttp3.Response;<br>
 * import package2.Config;<br>
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





public class blockchain {
	

	  /**
     * Default Constructor Class
     */

	
    public blockchain() throws IOException {
    }
    
    /**
     * Retrieve Blockchain parameters of RecordsKeeper Blockchain.<br>
     * getChainInfo() function is used to retrieve Blockchain parameters.
     * <p><code>getChainInfo(); <br>
     * chain_protocol, chain_description, root_stream, max_blocksize, default_networkport, default_rpcport, mining_diversity, chain_name = getChainInfo() //getChainInfo() function call <br>
     * System.out.println(chain_protocol); //prints blockchain's protocol <br>
     * System.out.println(chain_description); //prints blockchain's description <br>
     * System.out.println(root_stream); //prints blockchain's root stream <br>
     * System.out.println(max_blocksize); //prints blockchain's maximum block size <br>
     * System.out.println(default_networkport); //prints blockchain's default network port <br>
     * System.out.println(default_rpcport); //prints blockchain's default rpc port <br>
     * System.out.println(mining_diversity); //prints blockchain's mining diversity <br>
     * System.out.println(chain_name); //prints blockchain's name</code></p>
     * @return It will return the information about RecordsKeeper blockchain's parameters.
     */
    

    public static JSONObject getChainInfo() throws IOException, JSONException {

    	    String resp;
    	        String rkuser=System.getenv("rkuser");
    		String passwd=System.getenv("passwd");
    		String chain=System.getenv("chain");
    		String url=System.getenv("url");
    		String credential = Credentials.basic(rkuser, passwd);
    		OkHttpClient client = new OkHttpClient();
    		MediaType mediaType = MediaType.parse("application/json");
    		
    	
    	
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getblockchainparams\",\"params\":[],\"id\":1,\"chain_name\":\""+chain+"\"}\n");
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
        String chain_protocol = object.getString("chain-protocol");
         String chain_name = object.getString("chain-name");
         String chain_description = object.getString("chain-description");
        String root_stream = object.getString("root-stream-name");
        int max_blocksize = object.getInt("maximum-block-size");
        int default_networkport = object.getInt("default-network-port");
        int default_rpcport = object.getInt("default-rpc-port");
        int mining_diversity =  object.getInt("mining-diversity");
    
        JSONObject item = new JSONObject();
        item.put("chain_prot"
        		+ "ocol", chain_protocol);
        item.put("chain_name", chain_name);
        item.put("chain_description", chain_description);
        item.put("root_stream", root_stream);
        item.put("max_blocksize", max_blocksize);
        item.put("default_networkport", default_networkport);
        item.put("default_rpcport", default_rpcport);
        item.put("mining_diversity", mining_diversity);
        
       return item;
    
    }
    
    
    
    /**
     * Retrieve node's information on RecordsKeeper Blockchain. <br>
     * getNodeInfo() function is used to retrieve node's information on RecordsKeeper Blockchain.
     * <p><code>getNodeInfo(); <br>
     * node_balance, synced_blocks, node_address, difficulty = getNodeInfo();       #getNodeInfo() function call <br>
     * System.out.println(node_balance); //prints balance of the node<br>
     * System.out.println(synced_blocks); //prints no of synced blocks<br>
     * System.out.println(node_address); //prints node's address<br>
     * System.out.println(difficulty); //prints node's difficulty </code></p>
     * @return It will return node's balance, no of synced blocks, node's address and node's difficulty.
     */

    public static JSONObject getNodeInfo() throws IOException, JSONException {
        
	    String resp;
	    String rkuser=System.getenv("rkuser");
		String passwd=System.getenv("passwd");
		String chain=System.getenv("chain");
		String url=System.getenv("url");
		String credential = Credentials.basic(rkuser, passwd);
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
    	
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getinfo\",\"params\":[],\"id\":1,\"chain_name\":\""+chain+"\"}\n");
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
        int node_balance = object.getInt("balance");
        int synced_blocks = object.getInt("blocks");
        String node_address = object.getString("nodeaddress");
        int difficulty = object.getInt("difficulty");
        
        
        JSONObject item = new JSONObject();
        item.put("node_balance",node_balance );
        item.put("synced_blocks",synced_blocks );
        item.put("node_address",node_address );
        item.put("difficulty",difficulty );
        

        return item; //node_balance,synced_blocks, node_address, difficulty;
    }

    
    
    
    /**
     * Retrieve permissions given to the node on RecordsKeeper Blockchain.<br>
     * permissions() function is used to retrieve node's permissions.
     * <p><code> permissions();<br>
     * allowed_permissions = permissions();                //permissions() function call<br>
     * System.out.print(allowed_permissions);      //prints permissions available to the node</code></p>
     * @return It will return the permissions available to the node.
     */
    
    public static String permissions() throws IOException, JSONException {
    	
    	
	    String resp;
	    String permission;
	    String rkuser=System.getenv("rkuser");
		String passwd=System.getenv("passwd");
		String chain=System.getenv("chain");
		String url=System.getenv("url");
		String credential = Credentials.basic(rkuser, passwd);
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"listpermissions\",\"params\":[],\"id\":1,\"chain_name\":\""+chain+"\"}\n");
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
        int pms_count = array.length();
        String output = "";
        for (int i = 0; i<pms_count; i++) {
            permission = array.getJSONObject(i).getString("type");
            if(i != pms_count - 1)
                output += permission +",";
            else output += permission;
        }
        return output;
    }

    
    
    
    /**
     * Retrieve pending transaction's information on RecordsKeeper Blockchain. <br>
     * getpendingTransactions() function is used to retrieve pending transaction's information like no of pending transactions and the pending transactions.
     * <p><code>getpendingTransactions();<br>
     * pendingtx, pendingtxcount = getpendingTransactions(address);   //getpendingTransactions() function call<br>
     * System.out.println(pendingtx);             //prints pending transactions<br>
     * System.out.println(pendingtxcount);        //prints pending transaction count</code></p>
     * @return It will return the information of pending transactions on Recordskeeper Blockchain.
     */
    
    public static JSONObject  getpendingTransactions() throws IOException, JSONException {

    	
    	String resp;
 	    String rkuser=System.getenv("rkuser");
 		String passwd=System.getenv("passwd");
 		String chain=System.getenv("chain");
 		String url=System.getenv("url");
 		String credential = Credentials.basic(rkuser, passwd);
 		OkHttpClient client = new OkHttpClient();
 		MediaType mediaType = MediaType.parse("application/json");
 		
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getmempoolinfo\",\"params\":[],\"id\":1,\"chain_name\":\""+chain+"\"}\n");
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
        int tx_count = object.getInt("size");

        RequestBody body1 = RequestBody.create(mediaType, "{\"method\":\"getrawmempool\",\"params\":[],\"id\":1,\"chain_name\":\""+chain+"\"}\n");
        Request request1 = new Request.Builder()
                .url(url)
                .method("POST", body1)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response1 = client.newCall(request1).execute();
        String resp1 = response1.body().string();
        JSONObject jsonObject1 = new JSONObject(resp1);
        JSONArray array = jsonObject1.getJSONArray("result");
        String tx = "";
        JSONObject item = new JSONObject();
        
        for (int i = 0; i<jsonObject1.length(); i++) {
            tx = String.valueOf(jsonObject1.get("result"));
        }
        item.put("tx_count",tx_count);
        item.put("tx", tx);
        return item; 
    }

    
    
    /**
     * Check node's total balance. <br>
     * checkNodeBalance() function is used to check the total balance of the node.
     * <p><code>checkNodeBalance();<br>
     * node_balance = checkNodeBalance();     //checkNodeBalance() function call <br>
     * System.out.println(node_balance);    //prints total balance of the node</code></p>
     * @return It will return the total balance of the node on RecordsKeeper Blockchain.
     */
    
    
    public static int checkNodeBalance() throws IOException, JSONException {

    	String resp;
 	    String rkuser=System.getenv("rkuser");
 		String passwd=System.getenv("passwd");
 		String chain=System.getenv("chain");
 		String url=System.getenv("url");
 		String credential = Credentials.basic(rkuser, passwd);
 		OkHttpClient client = new OkHttpClient();
 		MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getmultibalances\",\"params\":[],\"id\":1,\"chain_name\":\""+chain+"\"}\n");
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
        JSONArray object1 = object.getJSONArray("total");
        JSONObject object2 = object1.getJSONObject(0);
        int qty = object2.getInt("qty");

        return qty;
    }



}

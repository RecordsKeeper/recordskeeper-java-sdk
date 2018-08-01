package package2 ;


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
import static java.lang.Math.abs;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import static okio.ByteString.decodeHex;
import java.util.Properties;


/**
 * <h1>Transaction Class Usage</h1>
 * Transaction class is used to call transaction related functions like create raw transaction, sign transaction, send transaction , retrieve transaction and verify transaction functions which are used to create raw transactions, send transactions, sign transactions, retrieve transactions and verify transactions on the RecordsKeeeper Blockchain.
 * <p>Library to work with RecordsKeeper transactions.</p>
 * You can send transaction, create raw transaction, sign raw transaction, send raw transaction, send signed transaction,
 retrieve transaction information and calculate transaction's fees by using transaction class. You just have to pass
 parameters to invoke the pre-defined functions.
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


public class transactions {

/*     String sender_address;
     String reciever_address;
     String data;
     double amount;
     String datahex;
     String txHex;
     String private_key;
     String signed_txHex;
     String txid;
     String address;
     String resp;
*/
	
	
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
   
    public transactions() throws IOException {}

    /**
     * Send Transaction without signing with private key.<br>
     * sendTransaction() function is used to send transaction by passing reciever's address, sender's address and amount.
     * <p><code>sendTransaction(sender_address, receiver_address, amount); <br>
     * txid = sendTransaction(sender_address, receiver_address, amount); <br>
     * System.out.println(txid);                  //prints transaction id of the sent transaction</code></p>
     * You have to pass these three arguments to the sendTransaction function call:
     * @param sender_address Transaction's sender address
     * @param receiver_address Transaction's receiver address
     * @param data hex value of the data
     * @param amount Amount to be sent in transaction
     * @return It will return the transaction id of the raw transaction.
     */

    
    
    
    public static String sendTransaction(String sender_address, String reciever_address, String data, double amount) throws IOException, JSONException {

    	
    	String datahex;
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
  		String credential = Credentials.basic(rkuser, passwd);
  		OkHttpClient client = new OkHttpClient();
  		MediaType mediaType = MediaType.parse("application/json"); 
  		
  		
  		byte[] byt = data.getBytes("UTF-8");
    	BigInteger big = new BigInteger(byt);
    	String hex = big.toString(16);
    	
        datahex = "\"" + hex + "\"";
        sender_address = "\"" + sender_address + "\"";
        reciever_address = "\"" + reciever_address + "\"";
        
       

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"createrawsendfrom\",\"params\":[" + sender_address + ", {" + reciever_address + " :" + amount + "}, [" + datahex + "],\"send\"],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
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
        String txid = jsonObject.getString("result");

        return txid ;
    }

    
    
    /**
     * Create raw transaction. <br>
     * createRawTransaction() function is used to create raw transaction by passing reciever's address, sender's address and amount.
     * <p><code> createRawTransaction(sender_address, reciever_address, amount); <br>
     * tx_hex = createRawTransaction(sender_address, reciever_address, amount);<br>
     * System.out.println(tx_hex);      //prints transaction hex of the raw transaction</code></p>
     * You have to pass these three arguments to the createRawTransaction function call:
     * @param sender_address Transaction's sender address
     * @param receiver_address Transaction's receiver address
     * @param amount Amount to be sent in transaction
     * @param data hex value of data
     * @return It will return transaction hex of the raw transaction.
     */
    
    
    public static String createRawTransaction(String sender_address, String reciever_address, double amount, String data) throws IOException, JSONException {

        sender_address = "\"" + sender_address + "\"";
        reciever_address = "\"" + reciever_address + "\"";
        String datahex;
        String resp;
        
        byte[] byt = data.getBytes("UTF-8");
    	BigInteger big = new BigInteger(byt);
    	String hex = big.toString(16);
        
        datahex = "\"" + hex + "\"";
        
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
  		String credential = Credentials.basic(rkuser, passwd);
  		OkHttpClient client = new OkHttpClient();
  		MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"createrawsendfrom\",\"params\":[" + sender_address + ", {" + reciever_address + " :" + amount + "}, [" + datahex + "],\"\"],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
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
       
        String txhex = jsonObject.getString("result");

        return txhex;
    }
    
    
    
    /**
     * Sign raw transaction. <br>
     * signRawTransaction() function is used to sign raw transaction by passing transaction hex of the raw transaction and the private key to sign the raw transaction.
     * <p><code>  signRawTransaction(tx_hex, private_key); <br>
     * signed_hex = signRawTransaction(txHex, private_key); <br>
     * System.out.println(signed_hex);      //prints signed transaction hex of the raw transaction</code></p>
     * You have to pass these three arguments to the signRawTransaction function call:
     * @param txHex Transaction hex of the raw transaction
     * @param private_key Private key to sign raw transaction
     * @return It will return signed transaction hex of the raw transaction.
     */
    

    public static String signRawTransaction(String txHex, String private_key) throws IOException, JSONException {

        txHex = "\"" + txHex + "\"";
       
        private_key = "\"" + private_key + "\"";
       

        ArrayList<String> ar = new ArrayList<String>();
        ar.add(private_key);
        
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
  		String credential = Credentials.basic(rkuser, passwd);
  		OkHttpClient client = new OkHttpClient();
  		MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"signrawtransaction\",\"params\":[" + txHex + ",[]," + ar + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
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
      //  System.out.println(jsonObject);
        JSONObject object = jsonObject.getJSONObject("result");
        String signedHex = object.getString("hex");

        return signedHex;
    }

    
    /**
     * Send raw transaction. <br>
     * sendRawTransaction() function is used to send raw transaction by passing signed transaction hex of the raw transaction.
     * <p><code> sendRawTransaction(signed_txHex); <br>
     * tx_id = sendRawTransaction(signed_txHex); <br>
     * System.out.println(tx_id);     //prints transaction id of the raw transaction</code></p>
     * You have to pass these three arguments to the sendRawTransaction function call:
     * @param signed_txHex Signed transaction hex of the raw transaction
     * @return It will return transaction id of the raw transaction sent on to the Blockchain.
     */
    
    
    public static String sendRawTransaction(String signed_txHex) throws IOException, JSONException {

        signed_txHex = "\"" + signed_txHex + "\"";
        String txid;
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
  		String credential = Credentials.basic(rkuser, passwd);
  		OkHttpClient client = new OkHttpClient();
  		MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"sendrawtransaction\",\"params\":[" + signed_txHex + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
        System.out.print(resp);
        JSONObject jsonObject = new JSONObject(resp);
        if (jsonObject.isNull("result")) {
            JSONObject object = jsonObject.getJSONObject("error");
            txid = object.getString("message");
            
        } else {
            txid = jsonObject.getString("result");
            
        }
        return txid;
    }

    
    
    /**
     * Send Transaction by signing with private key.<br>
     * sendSignedTransaction() function is used to send transaction by passing reciever's address, sender's address, private key of sender and amount. In this function private key is required to sign transaction.
     * <p><code>sendSignedTransaction(); <br>
     * transaction_id = sendSignedTransaction(); <br>
     * System.out.println(transaction_id);        //prints transaction id of the signed transaction</code></p>
     * You have to pass these four arguments to the sendSignedTransaction function call:
     * @param sender_address Transaction's sender address
     * @param receiver_address Transaction's receiver address
     * @param amount Amount to be sent in transaction
     * @param private_key Private key of the sender's address
     * @param data hex value of the data
     * @return It will return transaction id of the signed transaction.
     */

    
    
    
    
    public static String sendSignedTransaction(String sender_address, String reciever_address, double amount, String private_key, String data) throws IOException, JSONException {

    	String resp;
    	String txid;
        sender_address = "\"" + sender_address + "\"";
        reciever_address = "\"" + reciever_address + "\"";
      
        byte[] byt = data.getBytes("UTF-8");
    	BigInteger big = new BigInteger(byt);
    	String hex = big.toString(16);
        
        private_key = "\"" + private_key + "\"";
        String datahex = "\"" + hex + "\"";
       
        
        ArrayList<String> ar = new ArrayList<String>();
        ar.add(private_key);
        
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
  		String credential = Credentials.basic(rkuser, passwd);
  		OkHttpClient client = new OkHttpClient();
  		MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"createrawsendfrom\",\"params\":[" + sender_address + ", {" + reciever_address + " :" + amount + "}, [" + datahex + "],\"\"],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
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
        String txhexi;
        txhexi = jsonObject.getString("result");

        RequestBody body1 = RequestBody.create(mediaType, "{\"method\":\"signrawtransaction\",\"params\":[\"" + txhexi + "\",[]," + ar + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
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
        JSONObject object1 = jsonObject1.getJSONObject("result");
        String signedHexi;
              signedHexi  = object1.getString("hex");

        RequestBody body2 = RequestBody.create(mediaType, "{\"method\":\"sendrawtransaction\",\"params\":[\"" + signedHexi + "\"],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request2 = new Request.Builder()
                .url(url)
                .method("POST", body2)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response2 = client.newCall(request2).execute();
        String resp2 = response2.body().string();
        JSONObject jsonObject2 = new JSONObject(resp2);
        if (jsonObject.isNull("result")) {
            JSONObject object = jsonObject2.getJSONObject("error");
            txid = object.getString("message");
            System.out.println(txid);
        } else {
            txid = jsonObject2.getString("result");
        }
        return txid;
    }
    
    
    
    
    /**
     * Retrieve a transaction from the Blockchain. <br>
     * retrieveTransaction() function is used to retrieve transaction's information by passing transaction id to the function.
     * <p><code> retrieveTransaction(tx_id); <br>
     * sent_data, sent_amount, reciever_address = retrieveTransaction(tx_id); <br>
     * System.out.println(sent_data);              //prints sent data <br>
     * System.out.println(sent_amount);                //prints sent amount <br>
     * System.out.println(receiver_address);            //prints receiver's address  </code></p>
     * You have to pass given argument to the retrieveTransaction function call:
     * @param txid Transaction id of the transaction you want to retrieve
     * @return It will return the sent data, sent amount and reciever's address of the retrieved transaction.
     */

    public static JSONObject retrieveTransaction(String txid) throws IOException, JSONException {

        txid = "\"" + txid + "\"";
      
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
  		String credential = Credentials.basic(rkuser, passwd);
  		OkHttpClient client = new OkHttpClient();
  		MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getrawtransaction\",\"params\":[" + txid + ", 1],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
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
       // System.out.println(resp);
        JSONArray data = object.getJSONArray("data");
        byte[] bytes = decodeHex(data.getString(0)).toByteArray();
        String sent_data = new String(bytes);
        JSONArray amount = object.getJSONArray("vout");
        JSONObject value = amount.getJSONObject(0);
       // System.out.println(value);
        double sent_amount =  (double) value.getDouble("value");
      //  System.out.println(sent_amount);
        
        JSONObject item=new JSONObject();
        item.put("sent_data", sent_data);
        item.put("sent_amount", sent_amount);
        return item  ; 
        
    }
    
    
    
    /**
     * Calculate a particular transaction's fee on RecordsKeeper Blockchain. <br>
     * getFee() function is used to calculate transaction's fee by passing transaction id and sender's address to the function.
     * <p><code> getFee(address, tx_id);
     * Fees = getFee(address, tx_id);
     * System.out.println(Fees);                   //prints fees consumed in the verified transaction</code></p>
     * You have to pass these two arguments to the getFee function call:
     * @param address Sender's address
     * @param txid Transaction id of the transaction you want to calculate fee for
     * @return It will return the fees consumed in the transaction.
     */
    
    

    public static double getFee(String address, String txid) throws IOException, JSONException {

        address = "\"" +address+ "\"";
        txid = "\"" +txid+ "\"";
        boolean True = true;
        
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
  		String credential = Credentials.basic(rkuser, passwd);
  		OkHttpClient client = new OkHttpClient();
  		MediaType mediaType = MediaType.parse("application/json");
  		
  		
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getaddresstransaction\",\"params\":[" + address + ","+txid+","+True+"],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();
        
        
        Response response = client.newCall(request).execute();
        resp = response.body().string();
        System.out.print(resp);
        JSONObject jsonObject = new JSONObject(resp);
        JSONObject object = jsonObject.getJSONObject("result");
        JSONArray value = object.getJSONArray("vout");
        JSONObject amount = value.getJSONObject(0);
        double sent_amount = amount.getInt("amount");
        JSONObject balance = object.getJSONObject("balance");
        double balance_amount = (double) balance.getDouble("amount");
        double fees = (abs(balance_amount) - sent_amount);

        return fees;
    }
    

}

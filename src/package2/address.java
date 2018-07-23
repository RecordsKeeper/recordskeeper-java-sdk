
package package2;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import package2.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Arrays;


/**
 * <h1>Address Class Usage</h1>
 * Address class is used to call address related functions like generate new address, list all addresses and no of
 addresses on the node's wallet, check if given address is valid or not, check if given address has mining permission
 or not and check a particular address balance on the node functions which are used on the RecordsKeeeper Blockchain.
 * <p>Library to work with RecordsKeeper addresses.</p>
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





public class address {

 
	 /**
     * Default Constructor Class
     */
	
	public address() throws IOException{
	    
	}
	

	 /**
     * Generate new address on the node's wallet.<br>
     * getAddress() function is used to generate a new wallet address.
     * <p><code>getAddress(); <br>
     * newAddress = getAddress();                 //getAddress() function call <br>
     * System.out.println(newAddress);                  //prints a new address </code></p>
     * @return It will return a new address of the wallet
     */

		
     public static String getaddress() throws IOException, JSONException{
	   	    
    	 
    	 
    	
	   	    OkHttpClient client = new OkHttpClient();
	        MediaType mediaType = MediaType.parse("application/json");
	       
	    
             String rkuser=System.getenv("rkuser");
	     String passwd=System.getenv("passwd");
	     String url= System.getenv("url");
	     String chain=System.getenv("chain");
			String credential = Credentials.basic(rkuser, passwd);
			RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getnewaddress\",\"params\":[],\"id\":1,\"chain_name\":\""+chain+"\"}\n");
	       
		
			
			Request request = new Request.Builder()
	                .url(url)
	                .method("POST", body)
	                .addHeader("Content-Type", "application/json")
	                .addHeader("Cache-Control", "no-cache")
	                .header("Authorization", credential)
	                .build();
        

	       	        
	        Response response = client.newCall(request).execute();       
	        String a = response.body().string();
	        //System.out.println(a);
	        JSONObject object = new JSONObject(a);
	        String result = object.getString("result");
	        //System.out.println(result);
	        return result;
			
	  }
	  
     
     /**
      * Generate a new multisignature address.
      * <p><code>getMultisigAddress(nrequired, key); <br>
      * newAddress = getMultisigAddress(nrequired, key);          //getMultisigAddress() function call<br>
      * System.out.println(newAddress);                           //prints a new address </code> </p>
      * You have to pass these two arguments to the getMultisigAddress function call:<br>
      * @param nrequired to pass the no of signatures that are must to sign a transaction
      * @param key  pass any no of comma-seperated public addresses that are to be used with this multisig address as a single variable
      * @return It will return a new multisignature address on RecordsKeeper Blockchain.
      */
     
    
     

     public static String getMultisigAddress(int nrequired, String key) throws JSONException, IOException
     {     
    	 
    	     String rkuser=System.getenv("rkuser");
	     String passwd=System.getenv("passwd");
	     String url= System.getenv("url");
	     String chain=System.getenv("chain");
    	    String res;
    	    String keys = "";
         String output = "";
         String[] key_list = key.split(",");
         for (int i = 0; i < key_list.length; i++){
             keys = "\"" + key_list[i] + "\"";
             if(i!=key_list.length-1)
                 output += keys +",";
             else output += keys;
         }

         OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
         RequestBody body = RequestBody.create(mediaType, "{\"method\":\"createmultisig\",\"params\":["+nrequired+","+Arrays.asList(output)+"],\"jsonrpc\": \"2.0\", \"id\": \"curltext\", \"chain_name\":\""+chain+"\"}\n");
         Request request = new Request.Builder()
                 .url(url)
                 .method("POST", body)
                 .addHeader("Content-Type", "application/json")
                 .addHeader("Cache-Control", "no-cache")
                 .header("Authorization", credential)
                 .build();

         Response response = client.newCall(request).execute();
         String a = response.body().string();
         
         
         JSONObject jsonObject = new JSONObject(a);
         JSONObject object = jsonObject.getJSONObject("result");
         if (object.isNull("address")) {
             String error = jsonObject.getString("error");
             System.out.println(error);
             res = jsonObject.getString("message");
         }else {
             res = object.getString("address");
            
         }

         return res;
     }
    	 
		
     
     /**
      * Generate a new multisignature address on the node's wallet.
      * <p><code>getMultisigWalletAddress(nrequired, key); <br>
      * newAddress = getMultisigWalletAddress(nrequired, key);          //getMultisigAddress() function call<br>
      * System.out.println(newAddress);                           //prints a new address </code> </p>
      * You have to pass these two arguments to the getMultisigWalletAddress function call:<br>
      * @param nrequired to pass the no of signatures that are must to sign a transaction
      * @param key  pass any no of comma-seperated public addresses that are to be used with this multisig address as a single variable
      * @return It will return a new multisignature address on the wallet.
      */
    	 

     
     public static String getMultisigWalletAddress(int nrequired, String key) throws IOException, JSONException{

    	 String rkuser=System.getenv("rkuser");
	     String passwd=System.getenv("passwd");
	     String url= System.getenv("url");
	     String chain=System.getenv("chain");	   
  	    String res;
  	    String address;

         String keys = "";
         String output = "";
         String[] key_list = key.split(",");
         for (int i = 0; i < key_list.length; i++){
             keys = "\"" + key_list[i] + "\"";
             if(i!=key_list.length-1)
                 output += keys +",";
             else output += keys;
         }

         OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
         RequestBody body = RequestBody.create(mediaType, "{\"method\":\"addmultisigaddress\",\"params\":["+nrequired+","+Arrays.asList(output)+"],\"jsonrpc\": \"2.0\", \"id\": \"curltext\", \"chain_name\":\""+chain+"\"}\n");
         Request request = new Request.Builder()
                 .url(url)
                 .method("POST", body)
                 .addHeader("Content-Type", "application/json")
                 .addHeader("Cache-Control", "no-cache")
                 .header("Authorization", credential)
                 .build();

         Response response = client.newCall(request).execute();
         String a = response.body().string();
         JSONObject jsonObject = new JSONObject(a);
         address = jsonObject.getString("result");

         if (address == null) {
             String error = jsonObject.getString("error");
             System.out.println(error);
             res = jsonObject.getString("message");
         }else {
             res = jsonObject.getString("result");
         }

         return res;
     }
     
     
     /**
      * List all addresses and no of addresses on the node's wallet. <br>
      * retrieveAddresses() function is used to list all addresses and no of addresses on the node's wallet.
      * <p><code>retrieveAddresses();<br>
      * allAddresses, address_count = retrieveAddresses();       //retrieveAddresses() function call<br>
      * System.out.println(allAddresses);       //prints all the addresses of the wallet<br>
      * System.out.println(address_count);     //prints the address count</code></p>
      * @return It will return all the addresses and the count of the addresses on the wallet.
      */
     
     
     
     
     public static String retrieveAddress() throws IOException, JSONException {

     	 String rkuser=System.getenv("rkuser");
	     String passwd=System.getenv("passwd");
	     String url= System.getenv("url");
	     String chain=System.getenv("chain");	   
  	    String address;
    	 
    	 
    	 

         OkHttpClient client = new OkHttpClient();
         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
         RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getaddresses\",\"params\":[],\"jsonrpc\": \"2.0\", \"id\": \"curltext\", \"chain_name\":\"" + chain + "\"}\n");
         Request request = new Request.Builder()
                 .url(url)
                 .method("POST", body)
                 .addHeader("Content-Type", "application/json")
                 .addHeader("Cache-Control", "no-cache")
                 .header("Authorization", credential)
                 .build();

         Response response = client.newCall(request).execute();
         String a = response.body().string();
         JSONObject jsonObject = new JSONObject(a);
         JSONArray array = jsonObject.getJSONArray("result");
         System.out.println(array.length());
         for(int i=0; i<array.length();i++) {
             String ace = array.getString(i);
             System.out.println(ace);
         }
         address = String.valueOf(array);

         return address;
     }
     
     
     
     
     /**
      * Check validity of the address.<br>
      * checkifValid() function is used to check validity of a particular address.
      * <p><code>checkifValid();<br>
      * addressC = checkifValid(address);                //checkifValid() function call<br>
      * System.out.println(addressC);      //prints validity</code></p>
      * You have to pass address as argument to the checkifValid function call:
      * @param address to check the validity
      * @return It will return if an address is valid or not.
      */
     
     
     
     
     public static String checkifValid(String address) throws IOException, JSONException{

    	  String rkuser=System.getenv("rkuser");
	     String passwd=System.getenv("passwd");
	     String url= System.getenv("url");
	     String chain=System.getenv("chain");
            address = "\""+address+"\"";

         OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");
         
         String credential = Credentials.basic(rkuser, passwd);
         RequestBody body = RequestBody.create(mediaType, "{\"method\":\"validateaddress\",\"params\":["+address+"],\"jsonrpc\": \"2.0\", \"id\": \"curltext\", \"chain_name\":\"" + chain + "\"}\n");
         Request request = new Request.Builder()
                 .url(url)
                 .method("POST", body)
                 .addHeader("Content-Type", "application/json")
                 .addHeader("Cache-Control", "no-cache")
                 .header("Authorization", credential)
                 .build();

         Response response = client.newCall(request).execute();
         String a = response.body().string();
         JSONObject jsonObject = new JSONObject(a);
         JSONObject jsonObject1 = jsonObject.getJSONObject("result");
         Boolean valid = jsonObject1.getBoolean("isvalid");
         String add1 = "";
         if (valid) {
             add1 = "The Address is Valid";
         }
         else
             add1 = "The Address is not Valid";
         return add1;

     }
     
     
     
     /**
      * Check if given address has mining permission or not. <br>
      * checkifMineAllowed() function is used to sign raw transaction by passing transaction hex of the raw transaction and the private key to sign the raw transaction.
      * <p><code>checkifMineAllowed(address);<br>
      * permissionCheck = checkifMineAllowed(address);   //checkifMineAllowed() function call <br>
      * System.out.println(permissionCheck);      //prints permission status of the given address</code></p>
      * You have to pass address as argument to the checkifMineAllowed function call:
      * @param address to check the permission status
      * @return It will return if mining permission is allowed or not.
      */
     
     
     
     
     
     public static String checkifMineAllowed(String address) throws IOException, JSONException{

      
    	 
    	 
    	  String rkuser=System.getenv("rkuser");
	     String passwd=System.getenv("passwd");
	     String url= System.getenv("url");
	     String chain=System.getenv("chain");
    	 address = "\""+address+"\"";

         OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
         RequestBody body = RequestBody.create(mediaType, "{\"method\":\"validateaddress\",\"params\":["+address+"],\"jsonrpc\": \"2.0\", \"id\": \"curltext\", \"chain_name\":\"" + chain + "\"}\n");
         Request request = new Request.Builder()
                 .url(url)
                 .method("POST", body)
                 .addHeader("Content-Type", "application/json")
                 .addHeader("Cache-Control", "no-cache")
                 .header("Authorization", credential)
                 .build();

         Response response = client.newCall(request).execute();
         String a = response.body().string();
         JSONObject jsonObject = new JSONObject(a);
         JSONObject jsonObject1 = jsonObject.getJSONObject("result");
         String add = jsonObject1.getString("address");
         Boolean permission = jsonObject1.getBoolean("ismine");

         if (permission) {
             System.out.println(permission + ": The Address has mining permission");
         }
         else
             System.out.println("The Address does not have mining permission");

         return add;

     }
     
     
     
     /**
      * Check address balance on a particular node. <br>
      * checkBalance() function is used to check the balance of the address.
      * <p><code>checkBalance(address);<br>
      * address_balance = checkBalance(address);     //checkBalance() function call<br>
      * System.out.println(address_balance);    //prints balance of the address </code></p>
      * You have to pass address as argument to the checkifMineAllowed function call:
      * @param address to check the balance
      * @return It will return the balance of the address on RecordsKeeper Blockchain.
      */
     
     
     
     
     
     
     public static int checkBalance(String address) throws IOException, JSONException{
    	 
    	 
    	 String rkuser=System.getenv("rkuser");
	     String passwd=System.getenv("passwd");
	     String url= System.getenv("url");
	     String chain=System.getenv("chain");
         address = "\""+address+"\"";

         OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
         RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getaddressbalances\",\"params\":["+address+"],\"jsonrpc\": \"2.0\", \"id\": \"curltext\", \"chain_name\":\"" + chain + "\"}\n");
         Request request = new Request.Builder()
                 .url(url)
                 .method("POST", body)
                 .addHeader("Content-Type", "application/json")
                 .addHeader("Cache-Control", "no-cache")
                 .header("Authorization", credential)
                 .build();

         Response response = client.newCall(request).execute();
         String a = response.body().string();
         JSONObject jsonObject = new JSONObject(a);
         JSONArray array = jsonObject.getJSONArray("result");
         JSONObject object = array.getJSONObject(0);
         int balance = object.getInt("qty");

         return balance;
     }
     
     
     
     /**
      * Import a non-wallet address on RecordsKeeeper Blockchain. <br>
      * importAddress() function is used to check the balance of the address.
      * <p><code>importAddress(public_address); <br>
      * response = importAddress(public_address);     //importAddress() function call <br>
      * System.out.println(response);   //prints response</code></p>
      * You have to pass address as argument to the importAddress function call:
      * @param public_address non-wallet address to import on a particular node
      * @return It will return the response of the importAddress() function call.
      */
     
     
     
     
     public static String importAddress(String public_address) throws IOException, JSONException{
    	 
    	  String rkuser=System.getenv("rkuser");
	     String passwd=System.getenv("passwd");
	     String url= System.getenv("url");
	     String chain=System.getenv("chain");
         public_address = "\""+public_address+"\"";
         boolean False = false;
         OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
         RequestBody body = RequestBody.create(mediaType, "{\"method\":\"importaddress\",\"params\":["+public_address+", \""+null+"\" , "+False+"],\"jsonrpc\": \"2.0\", \"id\": \"curltext\", \"chain_name\":\"" + chain + "\"}\n");
         Request request = new Request.Builder()
                 .url(url)
                 .method("POST", body)
                 .addHeader("Content-Type", "application/json")
                 .addHeader("Cache-Control", "no-cache")
                 .header("Authorization", credential)
                 .build();

         Response response = client.newCall(request).execute();
         String a = response.body().string();
         System.out.println(a);
         String add1="";
         JSONObject jsonObject = new JSONObject(a);
         if (jsonObject.isNull("result")){
             if(jsonObject.isNull("error")){
            	 add1="Address successfully imported";
                 
             }
             else {
                 JSONObject object= jsonObject.getJSONObject("error");
                 String objectString = object.getString("message");
                 System.out.println(objectString);
                 add1="Address not imported successfully ";
             }
         }

         return add1;

         }
 
 
}



 
















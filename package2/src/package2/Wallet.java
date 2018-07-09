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
 * <h1>Wallet Class Usage</h1>
 * Wallet class is used to call wallet related functions like create wallet, retrieve private key of wallet address, retrieve wallet's information, dump wallet, lock wallet, unlock wallet, change wallet's password, create wallet's backup, import wallet's backup, sign message and verify message functions on RecordsKeeeper Blockchain.
 * <p>Library to work with RecordsKeeper wallet functionalities.</p>
 * You can create wallet, dump wallet into a file, backup wallet into a file, import wallet from a file, lock wallet, unlock wallet, change wallet's password, retrieve private key, retrieve wallet's information, sign and verify message by using wallet class. You just have to pass parameters to invoke the pre-defined functions.
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





public class Wallet {

	
	

	 /* private String public_address;
	    private String filename;
	    private String password;
	    private int unlock_time;
	    private String old_password;
	    private String new_password;
	    private String private_key;
	    private String message;
	    private String address;
	    private String signedMessage;
	    private String resp;
	    */
		
    
	/**
     * Default Constructor Class
     */

    
    public Wallet() throws IOException {}
	
	
    
    /**
     *  Create wallet on RecordsKeeper blockchain. <br>
     *  createWallet() function is used to create wallet on RecordsKeeper blockchain.
     *  <p><code>createWallet();
     *  publicaddress, privatekey, publickey = createWallet();
     *  System.out.println(publicaddress);  //prints public address of the wallet
     *  System.out.println(privatekey);  //prints private key of the wallet
     *  System.out.println(publickey);  //prints public key of the wallet</code></p>
     * @return It will return the public address, public key and private key.
     */
    
    
    public static JSONObject createWallet() throws IOException, JSONException {

    	    String resp;
    	
    	
    	    String rkuser=Config.getProperty("rkuser");
    	    String passwd=Config.getProperty("passwd");
    	    String url= Config.getProperty("url");
    	    String chain=Config.getProperty("chain");
    	    
    	    OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");

            String credential = Credentials.basic(rkuser, passwd);
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"createkeypairs\",\"params\":[],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
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
        JSONObject object = array.getJSONObject(0);
        String public_address = object.getString("address");
        String private_key = object.getString("privkey");
        String public_key = object.getString("pubkey");

        public_address = "\"" + public_address + "\"";

        boolean False = false;

        RequestBody body1 = RequestBody.create(mediaType, "{\"method\":\"importaddress\",\"params\":[" + public_address + ",\"\"," + False + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request1 = new Request.Builder()
                .url(url)
                .method("POST", body1)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response1 = client.newCall(request1).execute();
        String resp1 = response1.body().string();
        JSONObject item = new JSONObject(resp1);
        item.put("public_address", public_address);
        item.put("private_key", private_key);
        item.put("public_key", public_key);

        return item;
    }
    
    
    /**
     * Retrieve private key of an address.
     * <p><code> getPrivateKey(); <br>
     * privkey = getPrivateKey(); <br>
     * System.out.println(privkey);        //prints private key of the given address</code></p>
     * You have to pass address argument to the getPrivateKey function call:
     * @param public_address address whose private key is to be retrieved
     * @return It will return private key of the given address.
     */
    
    
    public static String getPrivateKey(String public_address) throws IOException, JSONException {

        public_address = "\"" + public_address + "\"";
        String rkuser=Config.getProperty("rkuser");
	    String passwd=Config.getProperty("passwd");
	    String url= Config.getProperty("url");
	    String chain=Config.getProperty("chain");
	    
	    OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        String credential = Credentials.basic(rkuser, passwd);

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"dumpprivkey\",\"params\":[" + public_address + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        String resp;
        Response response = client.newCall(request).execute();
        resp = response.body().string();
        System.out.println(resp);
        JSONObject jsonObject = new JSONObject(resp);
        String private_key = "";
        if (jsonObject.isNull("result")) {
            private_key = jsonObject.getString("error");
        } else {
            private_key = jsonObject.getString("result");
        }
        return private_key;
    }

    
    /**
     * Retrieve node wallet's information.<br>
     * retrieveWalletinfo() function is used to retrieve node wallet's information.
     * <p><code>retrieveWalletinfo(); <br>
     * balance, tx_count, unspent_tx = retrieveWalletinfo(); <br>
     * System.out.println(balance); //prints wallet's balance <br>
     * System.out.println(tx_count); //prints wallet transaction count  <br>
     * System.out.println(unspent_tx); //prints unspent wallet transactions</code></p>
     * @return It will return wallet's balance, transaction count and unspent transactions.
     */
    
    
    
    public static JSONObject retrieveWalletinfo() throws IOException, JSONException {

    	String resp;
    	String rkuser=Config.getProperty("rkuser");
 	    String passwd=Config.getProperty("passwd");
 	    String url= Config.getProperty("url");
 	    String chain=Config.getProperty("chain");
 	    
 	    OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"getwalletinfo\",\"params\":[],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
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
        int balance = object.getInt("balance");
        int tx_count = object.getInt("txcount");
        int unspent_tx = object.getInt("utxocount");

        JSONObject item = new JSONObject();
        item.put("balance", balance);
        item.put("tx_count", tx_count);
        item.put("unspent_tx", unspent_tx);

        return item;
    }
    
   
    /**
     * Create wallet's backup.<br>
     * backupWallet() function is used to create backup of the wallet.dat file.
     * <p><code>backupWallet(filename); <br>
     * result = backupWallet(filename); <br>
     * System.out.println(result);      //prints result</code></p>
     * You have to pass these three arguments to the backupWallet function call:
     * @param filename wallet's backup file name
     * @return It will return the response of the backup wallet function. The backup of the wallet is created in your chain's directory and you can simply access your file by using same filename that you have passed with the backupwallet function. Creates a backup of the wallet.dat file in which the node�s private keys and watch-only addresses are stored. The backup is created in file filename. Use with caution � any node with access to this file can perform any action restricted to this node�s addresses.
     */

    
    
    public static String backupWallet(String filename) throws IOException, JSONException {

    	String resp;
    	String rkuser=Config.getProperty("rkuser");
 	    String passwd=Config.getProperty("passwd");
 	    String url= Config.getProperty("url");
 	    String chain=Config.getProperty("chain");
 	    
 	    OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
    	
        filename = "\"" + filename + "\"";
        

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"backupwallet\",\"params\":[" + filename + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
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
        String message = "";

        if (jsonObject.isNull("result")) {
            message = "Backup Successful";
        } else {
            JSONArray array = jsonObject.getJSONArray("error");
            JSONObject object = array.getJSONObject(0);
            message = object.getString("message");
        }
        return message;
    }
   
    
    
    /**
     * Import backup wallet.<br>
     * importWallet() function is used to import wallet's backup file.
     * <p><code>importWallet(filename); <br>
     * result = importWallet(filename); <br>
     * System.out.println(result);    //prints result</code></p>
     * You have to pass these three arguments to the importWallet function call:
     * @param filename wallet's backup file name
     * @return It will return the response of the import wallet function. It will import the entire set of private keys which were dumped (using dumpwallet) into file filename.
     */
    
    
    public static String importWallet(String filename) throws IOException, JSONException {

    	String resp;
    	String rkuser=Config.getProperty("rkuser");
 	    String passwd=Config.getProperty("passwd");
 	    String url= Config.getProperty("url");
 	    String chain=Config.getProperty("chain");
 	    
 	    OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
    	
        filename = "\"" + filename + "\"";

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"importwallet\",\"params\":[" + filename + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
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
        String result = "";
        if (jsonObject.isNull("result"))
            result = "Wallet is successfully imported";
        else {
            JSONArray array = jsonObject.getJSONArray("error");
            JSONObject object = array.getJSONObject(0);
            result = object.getString("message");
        }
        return result;
    }
    
    
    /**
     * Dump wallet on RecordsKeeper blockchain.<br>
     * dumpWallet() function is used to retrieve transaction's information by passing transaction id to the function.
     * <p><code>dumpWallet(filename); <br>
     * result = dumpWallet(filename);  <br>
     * System.out.println(result);                   //prints result</code></p>
     * You have to pass these three arguments to the dumpWallet function call:
     * @param filename file name to dump wallet in
     * @return It will return the response of the dump wallet function. Dumps the entire set of private keys in the wallet into a human-readable text format in file filename. Use with caution � any node with access to this file can perform any action restricted to this node�s addresses.
     */
    
    
    
    public static String dumpWallet(String filename) throws IOException, JSONException {

    	String resp;
    	String rkuser=Config.getProperty("rkuser");
 	    String passwd=Config.getProperty("passwd");
 	    String url= Config.getProperty("url");
 	    String chain=Config.getProperty("chain");
 	    
 	    OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
    	
    	filename = "\"" + filename + "\"";

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"dumpwallet\",\"params\":[" + filename + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
        JSONObject object = new JSONObject(resp);
        String res = "";
        if (object.isNull("result"))
            res = "Wallet is successfully dumped";
        else {
            JSONArray array = object.getJSONArray("error");
            JSONObject jsonObject = array.getJSONObject(0);
            res = jsonObject.getString("message");
        }
        return res;
    }
    
    
    /**
     * Locking wallet with a password on RecordsKeeper Blockchain.<br>
     * lockWallet() function is used to verify transaction's information by passing transaction id and sender's address to the function.
     * <p><code>  lockWallet(password); <br>
     * result = lockWallet(password);  <br>
     * System.out.println(result);                    //prints result</code></p>
     * You have to pass password as an argument to the lockWallet function call:
     * @param password password to lock the wallet
     * @return It will return the the response of the lock wallet function. This encrypts the node�s wallet for the first time, using passphrase as the password for unlocking. Once encryption is complete, the wallet�s private keys can no longer be retrieved directly from the wallet.dat file on disk, and chain will stop and need to be restarted. Use with caution � once a wallet has been encrypted it cannot be permanently unencrypted, and must be unlocked for signing transactions with the unlockwallet function.
     */

    
      
    public static String lockWallet(String password) throws IOException, JSONException {

    	String resp;
    	String rkuser=Config.getProperty("rkuser");
 	    String passwd=Config.getProperty("passwd");
 	    String url= Config.getProperty("url");
 	    String chain=Config.getProperty("chain");
 	    
 	    OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
    	
    	password = "\"" + password + "\"";

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"dumpwallet\",\"params\":[" + password + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
        JSONObject object = new JSONObject(resp);
        String res = "";
        if (object.isNull("result")) {
            res = "Wallet is successfully encrypted.";
        } else {
            JSONArray array = object.getJSONArray("error");
            JSONObject jsonObject = array.getJSONObject(0);
            res = jsonObject.getString("message");
        }
        return res;
    }
    
    
    /**
     * Unlocking wallet with the password on RecordsKeeper Blockchain.<br>
     * unlockWallet() function is used to verify transaction's information by passing transaction id and sender's address to the function.
     * <p><code>unlockWallet(password, unlock_time); <br>
     * result = unlockWallet(password, unlock_time); <br>
     * System.out.println(result);                    //prints result</code></p>
     * You have to pass these two arguments to the unlockWallet function call:
     * @param password password to unlock the wallet
     * @param unlock_time seconds for which wallet remains unlock
     * @return It will return the response of the unlock wallet function. This uses passphrase to unlock the node�s wallet for signing transactions for the next timeout seconds. This will also need to be called before the node can connect to other nodes or sign blocks that it has mined.
     */
    
    
    public static String unlockWallet(String password, int unlock_time) throws IOException, JSONException {

    	String resp;
    	String rkuser=Config.getProperty("rkuser");
 	    String passwd=Config.getProperty("passwd");
 	    String url= Config.getProperty("url");
 	    String chain=Config.getProperty("chain");
 	    
 	    OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
    	
        password = "\"" + password + "\"";
        

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"walletpassphrase\",\"params\":[" + password + "," + unlock_time + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
        JSONObject object = new JSONObject(resp);
        String res = "";
        if (object.isNull("result")) {
            res = "Wallet is successfully encrypted.";
        } else {
            JSONArray array = object.getJSONArray("error");
            JSONObject jsonObject = array.getJSONObject(0);
            res = jsonObject.getString("message");
        }
        return res;
    }
    
    
    
    /**
     * Change wallet's password. <br>
     * changeWalletPassword() function is used to change wallet's password and set new password.
     * <p><code>changeWalletPassword(old_password, new_password); <br>
     * result = changeWalletPassword(password, new_password); <br>
     * System.out.println(result);                    //prints result</code></p>
     * You have to pass these two arguments to the changeWalletPassword function call:
     * @param old_password old password of the wallet
     * @param new_password new password of the wallet
     * @return This changes the wallet�s password from old-password to new-password.
     */
    
    
    public static String changeWalletPassword(String old_password, String new_password) throws IOException, JSONException {

    	
    	String resp;
    	String rkuser=Config.getProperty("rkuser");
 	    String passwd=Config.getProperty("passwd");
 	    String url= Config.getProperty("url");
 	    String chain=Config.getProperty("chain");
 	    
 	    OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);
        old_password = "\"" + old_password + "\"";
        new_password = "\"" + new_password + "\"";

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"walletpassphrasechange\",\"params\":[" + old_password + "," +new_password + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
        JSONObject object = new JSONObject(resp);
        String res = "";

        if (object.isNull("result")) {
            res = "Password successfully changed!";
        } else {
            JSONArray array = object.getJSONArray("error");
            JSONObject jsonObject = array.getJSONObject(0);
            res = jsonObject.getString("message");
        }
        return res;
    }

    /**
     * Sign Message on RecordsKeeper Blockchain.<br>
     * signMessage() function is used to change wallet's password and set new password.
     * <p><code>signMessage(private_key, message);  <br>
     * signedMessage = signMessage(priavte_key, message);    <br>
     * System.out.println(signedMessage);                 //prints signed message</code></p>
     * You have to pass these two arguments to the signMessage function call:
     * @param private_key private key of the sender's wallet address
     * @param message message to send
     * @return It will return the signed message.
     */
    
    public static String signMessage(String private_key, String message) throws IOException, JSONException {

    	String resp;
    	String signedMessage;
    	String rkuser=Config.getProperty("rkuser");
 	    String passwd=Config.getProperty("passwd");
 	    String url= Config.getProperty("url");
 	    String chain=Config.getProperty("chain");
 	    
 	    OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);	
    	
       private_key = "\"" + private_key + "\"";
       message = "\"" + message + "\"";

        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"signmessage\",\"params\":[" + private_key + "," + message + "],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        resp = response.body().string();
        JSONObject object = new JSONObject(resp);
        signedMessage = object.getString("result");

        return signedMessage;
    }

    /**
     * Verify Message on RecordsKeeper Blockchain.<br>
     * verifyMessage() function is used to change wallet's password and set new password.
     * <p><code>verifyMessage(address, signedMessage, message);
     * validity = verifyMessage(address, signedMessage, message);
     * System.out.println(validity);                 //prints validity of the message</code></p>
     * You have to pass these three arguments to the verifyMessage function call:
     * @param address address to be verified
     * @param signedMessage signed message
     * @param message message to send
     * @return It will return the validity of the message.
     */
    
    
    public static String verifyMessage(String address, String signedMessage, String message) throws IOException, JSONException {

    	String resp;
        address = "\"" +address+ "\"";
        signedMessage = "\"" +signedMessage+ "\"";
        message = "\"" +message+ "\"";

        String rkuser=Config.getProperty("rkuser");
 	    String passwd=Config.getProperty("passwd");
 	    String url= Config.getProperty("url");
 	    String chain=Config.getProperty("chain");
 	    
 	    OkHttpClient client = new OkHttpClient();

         MediaType mediaType = MediaType.parse("application/json");

         String credential = Credentials.basic(rkuser, passwd);	
        
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"verifymessage\",\"params\":[" + address+ "," + signedMessage + ","+message+"],\"id\":1,\"chain_name\":\"" + chain + "\"}\n");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .header("Authorization", credential)
                .build();

        
        
        Response response = client.newCall(request).execute();
        resp = response.body().string();
        System.out.println(resp);
        JSONObject jsonObject = new JSONObject(resp);
        boolean verifiedMessage = jsonObject.getBoolean("result");
        String validity = "";
        if(verifiedMessage == true)
            validity = "Yes, message is verified";
        else
            validity = "No, signedMessage is not correct";

        return validity;
    }
    
	
    
    
    
    public static void main(String[] args) throws IOException, JSONException
    {
    	 // backupwallet, importwallet, dumpwallet, lockwallet, unlockwallet, changewalletpassword
    	
    	JSONObject res1= createWallet();
    	System.out.print(res1);
    	
    	String res2=getPrivateKey("1P1oadCfFodEC755yvu4yUfcBFEes82V8vyQPf");
    	System.out.println(res2);
    	System.out.println(res2);
    	
    	JSONObject res3= retrieveWalletinfo();
    	System.out.println(res3);
    	
    	String  res4=signMessage("1P1oadCfFodEC755yvu4yUfcBFEes82V8vyQPf","fhaesjkhfkw");
    	System.out.println(res4);
    	
     	String res5=verifyMessage("1P1oadCfFodEC755yvu4yUfcBFEes82V8vyQPf","IPcmyAKio2WS8qHJ819TN7F1A+aViGW9dMxIuT0mHWfLWZ29ooggvm9KzFJ4BDT4/pCytCO0hpvP0qQpTyiWPHE=", "fhaesjkhfkw");
    	System.out.println(res5);
    	
    	  }
	
    
    
}

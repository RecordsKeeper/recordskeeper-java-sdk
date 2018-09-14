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

import RecordsKeeperJavaLib.Assets;
import RecordsKeeperJavaLib.Config;


public class AssetTest {

    String multisigaddress;
	String validaddress;
	String miningaddress;
	String nonminingaddress;
	String qty;
	String invalidaddress;
	String wrongimportaddress;
	String testasset;
	Properties prop;
	
	
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
     
	 Assets Assets = new Assets();
	 
	public AssetTest() throws IOException {
	 
	 if (getPropert() == true) {
            
		 	validaddress = prop.getProperty("validaddress");
            testasset = prop.getProperty("testasset");
            miningaddress = prop.getProperty("miningaddress");
        
	 } 
	 
	 else  {
          
		    validaddress = System.getenv("validaddress");
            testasset = System.getenv("testasset");
            miningaddress = System.getenv("miningaddress");
          
        } 
	 }

    @Test
    public void createAsset() throws Exception {

        String txid = Assets.createAsset(validaddress, testasset, 1000);
        assertEquals(txid, "Asset or stream with this name already exists");

    }

    @Test
    public void retrieveasset() throws IOException, JSONException {

        JSONObject item = Assets.retrieveAssets();    
        JSONArray value = item.getJSONArray("Assets");
 
        JSONObject asset = value.getJSONObject(0);
        String asset_name = asset.getString("name");
        assertEquals(asset_name, testasset);

      }
     
    @Test
    public void sendAsset() throws Exception {

        String txid = Assets.sendAssets(miningaddress, testasset, 1);
        System.out.println(txid);
        int txid_len = txid.length();
        assertEquals(txid_len, 64);

    }

}


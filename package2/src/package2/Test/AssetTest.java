package package2.Test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import package2.Assets;
import package2.Config;


public class AssetTest {
	 Assets Assets = new Assets();
	 String validaddress = Config.getProperty("validaddress");
  
	 public AssetTest() throws IOException {}
	 
  

    @Test
    public void createAsset() throws Exception {

        String txid = Assets.createAsset(validaddress, "BAAT", 100);
        System.out.println(txid);
        assertEquals(txid, "Asset or stream with this name already exists.");

    }

    @Test
  
    
    public void retrieveasset() throws IOException, JSONException {

        JSONObject item = Assets.retrieveAssets();
        //System.out.println(item);
        JSONArray value = item.getJSONArray("Output");
        System.out.println(value);
        String asset_name="";
        String issue_id="";
        String issue_qty="";
        for(int i=0;i<value.length();i++)
        {
        	JSONObject asset = value.getJSONObject(i);
        	asset_name=asset.getString("name");
        //	System.out.println(asset_name);
        	if (asset_name.equals("BAAT"))
        	{   
        		
        		System.out.println(asset_name);
        		break;
        	}
         }
          assertEquals(asset_name, "BAAT");
          
          
          for(int i=0;i<value.length();i++)
          {
          	JSONObject res = value.getJSONObject(i);
          	issue_id=res.getString("issue_id");
          //	System.out.println(asset_name);
          	if (issue_id.equals("7d6e52a1d0cfa9662709a7b2724c2ffc03701defc618481b59da4cd18fa20ddd"))
          	{   
          		
          		System.out.println(issue_id);
          		break;
          	}
           }
            assertEquals(issue_id, "7d6e52a1d0cfa9662709a7b2724c2ffc03701defc618481b59da4cd18fa20ddd");
            
            
            
            
            for(int i=0;i<value.length();i++)
            {
            	JSONObject res1 = value.getJSONObject(i);
            	issue_qty=res1.getString("issue_qty");
            //	System.out.println(asset_name);
            	if (issue_qty.equals("100"))
            	{   
            		
            		System.out.println(issue_qty);
            		break;
            	}
             }
              assertEquals(issue_qty, "100");
        
    }
  
    
       
    @Test
    public void sendAsset() throws Exception {

        String txid = Assets.sendAssets(validaddress, "BAAAT", 10);
        int txid_len = txid.length();
        assertEquals(txid_len, 64);

    }









}

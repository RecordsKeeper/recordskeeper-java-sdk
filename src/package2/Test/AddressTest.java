package package2.Test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Test;

import package2.Config;
import package2.address;

 

public class AddressTest {
	
	
	    String multisigaddress = Config.getProperty("multisigaddress");
	    String validaddress = Config.getProperty("validaddress");
	    String miningaddress = Config.getProperty("miningaddress");
	    String nonminingaddress = Config.getProperty("nonminingaddress");
	    String qty = Config.getProperty("qty");
	    String invalidaddress = Config.getProperty("invalidaddress");
	    String wrongimportaddress = Config.getProperty("wrongimportaddress");
	
	address Address = new address();
	
	public AddressTest() throws Exception{
		
	}

	@Test
	public void getaddresstest() throws IOException, JSONException {
	   String res1 = address.getaddress();
	//	System.out.println(res1);
		int address_size = res1.length();
	//	System.out.println(address_size);
		assertEquals(address_size,34 );
	}

	@Test
	public void getMultisigAddress() throws IOException, JSONException{
		String res2 = address.getMultisigAddress(2, "miygjUPKZNV94t9f8FqNvNG9YjCkp5qqBZ, mwDbTVQcATL263JwpoE8AHCMGM5hE1kd7m, mpC8A8Fob9ADZQA7iLrctKtwzyWTx118Q9");
	//	System.out.println(res2);
		assertEquals(res2, multisigaddress);	
	}
	
	@Test
	public void retrieveAddresses() throws Exception{
		
		String res3=address.retrieveAddress();
		System.out.println(res3);
	}
	
	 @Test
	    public void checkifvalid() throws Exception{

	        String res4 = address.checkifValid(validaddress);
	        System.out.println(res4 + "dfgh");
	        assertEquals(res4, "The Address is Valid" );

	    }
	 
	 @Test
	    public void failcheckifvalid() throws Exception{

	        String res5 = address.checkifValid(invalidaddress);
	        System.out.println(res5 + "yayyyyyyyy");
	        assertEquals(res5, "The Address is Valid");

	    }
	
	 @Test
	    public void checkifMineAllowed() throws Exception{

	        String res6 = address.checkifMineAllowed(miningaddress);
	        assertEquals(res6, miningaddress);
	
	 }
	 
	 @Test
	    public void failcheckifMineAllowed() throws Exception{

	        String res7 = address.checkifMineAllowed(nonminingaddress);
	        assertEquals(res7, nonminingaddress);

	    }
	 
	 @Test
	    public void checkBalance() throws Exception{

	        int balance = address.checkBalance(nonminingaddress);
	        System.out.println(balance);
	        assertEquals(balance, 7);

	 
	 } 
	 
	 
	 @Test
	    public void importAddress() throws Exception{
	        String public_address = address.importAddress(miningaddress);
	        assertEquals(public_address, "Address successfully imported");
	    }
	 
	 @Test
	    public void wrongimportAddress() throws Exception{
	        String public_address = address.importAddress(wrongimportaddress);
	        assertEquals(public_address, "Address not successfully ");
	    }

	 
	 }

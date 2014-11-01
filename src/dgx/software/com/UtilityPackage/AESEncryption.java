package dgx.software.com.UtilityPackage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.print.DocFlavor.STRING;

import org.apache.commons.codec.binary.Base64;

public class AESEncryption {

	// Declare instance Variables
	private AESEncryption AESEncryption;
    private SecretKeySpec secretKey ;
    private byte[] key ;
	private String decryptedString;
	private String encryptedString;
	
	// Constructor
    public AESEncryption (){
    	
    	// Set the object reference to itself
    	AESEncryption = this;
    	
    	// Set the Encryption Key
        final String strPssword = "DGXSoftware";
        AESEncryption.setKey(strPssword);
    	
    }
	
    // Set the Key used to Encrypt and Decrypt
    private void setKey(String myKey){
   
    	MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
	    	key = Arrays.copyOf(key, 16); // use only first 128 bit
		    secretKey = new SecretKeySpec(key, "AES");
		    
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

    }
    
    // Get method for decryptedString
    private String getDecryptedString() {
		return decryptedString;
	}

    // Set method for decryptedString
	private void setDecryptedString(String decryptedString) {
		AESEncryption.decryptedString = decryptedString;
	}

	// Get method for getEncryptedString
    private String getEncryptedString() {
		return encryptedString;
	}

    // Set method for encryptedString
	private void setEncryptedString(String encryptedString) {
		AESEncryption.encryptedString = encryptedString;
	}

	// AES Encrypt Method
	private String encrypt(String strToEncrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            setEncryptedString(Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));
        
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: "+e.toString());
        }
        return null;

    }

	// AES Decrypt Method
    private String decrypt(String strToDecrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
           
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            setDecryptedString(new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt))));
            
        }
        catch (Exception e)
        {
         
            System.out.println("Error while decrypting: "+e.toString());

        }
        return null;
    }

    // Get an Encrypted String from PlainText
    public String getAESEncryption (String TextToEncrypt){
    	
    	// Return Null for Requests with Null Parameters or Empty Strings
    	if(TextToEncrypt == null) return null;
    	else if(TextToEncrypt.equals("")){return null;}
    	
    	// Encrypt and Return
        final String strToEncrypt = TextToEncrypt;
        AESEncryption.encrypt(strToEncrypt);
        final String EncryptedString =  AESEncryption.getEncryptedString();
    	
    	return EncryptedString;
    	
    }
    
    // Get a Decrypted/PlainText String from an Encrypted String
    public String getAESDecryption (String TextToDecrypt){

    	// Return Null for Requests with Null Parameters or Empty Strings
    	if(TextToDecrypt == null) return null;
    	else if(TextToDecrypt.equals("")){return null;}
    	
    	// Decrypt and Return
        final String strToDecrypt =  TextToDecrypt;
        AESEncryption.decrypt(strToDecrypt);
        final String DecryptedString =  AESEncryption.getDecryptedString();
    	
    	return DecryptedString;
    	
    }
    
    // Get an Encrypted String from PlainText (And Add URL Encoding for query string use)
    public String getURLEncodedAESEncryption (String TextToEncryptAndEncode){
    	
    	// Return Null for Requests with Null Parameters or Empty Strings
    	if(TextToEncryptAndEncode == null) return null;
    	else if(TextToEncryptAndEncode.equals("")){return null;}
    	
    	try {
    	
    		// Encrypt, Encode, then return
    		final String strToEncrypt = TextToEncryptAndEncode;
    		AESEncryption.encrypt(strToEncrypt);
    		final String EncryptedString = AESEncryption.getEncryptedString();
    		final String EncryptedAndEncodedString = URLEncoder.encode(EncryptedString, "UTF-8");
            
			return EncryptedAndEncodedString;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    // Get a Decrypted/PlainText String from an Encrypted String (And Add URL Decoding for query string use)
    public String getURLDecodedAESDecryption (String TextToDecodeAndDecrypt){
        
    	// Return Null for Requests with Null Parameters or Empty Strings
    	if(TextToDecodeAndDecrypt == null) return null;
    	else if(TextToDecodeAndDecrypt.equals("")){return null;}
    	
    	try {
    		
    		// Decode, Decrypt, then return
            final String DecodedString = URLDecoder.decode(TextToDecodeAndDecrypt, "UTF-8");
            AESEncryption.decrypt(DecodedString);
            final String DecodedAndDecryptedString =  AESEncryption.getDecryptedString();
            
    		return DecodedAndDecryptedString;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
     
}

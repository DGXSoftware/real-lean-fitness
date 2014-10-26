package dgx.software.com.UtilityPackage;

//SOURCE = http://javadigest.wordpress.com/2012/08/26/rsa-encryption-example/
//NOTES:
//Public key should be given to someone, which will be used to encrypt the data.
//Private will be used to decrypt the data.

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
* @author JavaDigest
* 
*/
public class RSAEncryption {

/**
* String to hold name of the encryption algorithm.
*/
private static final String ALGORITHM = "RSA";

/**
* String to hold the name of the private key file.
*/
private static final String PRIVATE_KEY_FILE = "/RSA/private.key";

/**
* String to hold name of the public key file.
*/
private static final String PUBLIC_KEY_FILE = "/RSA/public.key";

/**
* Generate key which contains a pair of private and public key using 1024
* bytes. Store the set of keys in Prvate.key and Public.key files.
* 
* @throws NoSuchAlgorithmException
* @throws IOException
* @throws FileNotFoundException
*/
private static void generateKey() {
 try {
   final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
   keyGen.initialize(1024);
   final KeyPair key = keyGen.generateKeyPair();

   File privateKeyFile = new File(PRIVATE_KEY_FILE);
   File publicKeyFile = new File(PUBLIC_KEY_FILE);

   // Create files to store public and private key
   if (privateKeyFile.getParentFile() != null) {
     privateKeyFile.getParentFile().mkdirs();
   }
   privateKeyFile.createNewFile();

   if (publicKeyFile.getParentFile() != null) {
     publicKeyFile.getParentFile().mkdirs();
   }
   publicKeyFile.createNewFile();

   // Saving the Public key in a file
   ObjectOutputStream publicKeyOS = new ObjectOutputStream(
       new FileOutputStream(publicKeyFile));
   publicKeyOS.writeObject(key.getPublic());
   publicKeyOS.close();

   // Saving the Private key in a file
   ObjectOutputStream privateKeyOS = new ObjectOutputStream(
       new FileOutputStream(privateKeyFile));
   privateKeyOS.writeObject(key.getPrivate());
   privateKeyOS.close();
 } catch (Exception e) {
   e.printStackTrace();
 }

}

/**
* The method checks if the pair of public and private key has been generated.
* 
* @return flag indicating if the pair of keys were generated.
*/
private static boolean areKeysPresent() {

 File privateKey = new File(PRIVATE_KEY_FILE);
 File publicKey = new File(PUBLIC_KEY_FILE);

 if (privateKey.exists() && publicKey.exists()) {
   return true;
 }
 return false;
}

/**
* Encrypt the plain text using public key.
* 
* @param text
*          : original plain text
* @param key
*          :The public key
* @return Encrypted text
* @throws java.lang.Exception
*/
private static byte[] encrypt(String text, PublicKey key) {
 byte[] cipherText = null;
 try {
   // get an RSA cipher object and print the provider
   final Cipher cipher = Cipher.getInstance(ALGORITHM);
   // encrypt the plain text using the public key
   cipher.init(Cipher.ENCRYPT_MODE, key);
   cipherText = cipher.doFinal(text.getBytes());
 } catch (Exception e) {
   e.printStackTrace();
 }
 return cipherText;
}

/**
* Decrypt text using private key.
* 
* @param text
*          :encrypted text
* @param key
*          :The private key
* @return plain text
* @throws java.lang.Exception
*/
private static String decrypt(byte[] text, PrivateKey key) {
 byte[] dectyptedText = null;
 try {
   // get an RSA cipher object and print the provider
   final Cipher cipher = Cipher.getInstance(ALGORITHM);

   // decrypt the text using the private key
   cipher.init(Cipher.DECRYPT_MODE, key);
   dectyptedText = cipher.doFinal(text);

 } catch (Exception ex) {
   ex.printStackTrace();
 }

 return new String(dectyptedText);
}


// Encrypts Original Text using private key and returns an encrypted byte Array.
public static byte [] RSAEncrypt(String OriginalText) {
	  
	  byte [] RSAEncryptedText = null;
	  
	    try {

	        // Check if the pair of keys are present else generate those.
	        if (!areKeysPresent()) {
	          // Method generates a pair of keys using the RSA algorithm and stores it
	          // in their respective files
	          generateKey();
	        }
         
	        // Encrypt the string using the public key
	        ObjectInputStream inputStream = null;
	        inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
	        final PublicKey publicKey = (PublicKey) inputStream.readObject();
	        final byte[] cipherText = encrypt(OriginalText, publicKey);
	        RSAEncryptedText = cipherText;

	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    
		return RSAEncryptedText;
	  
}

// Decrypts An encrypted byte Array and returns the Original Text
public static String RSADecrypt(byte [] RSAEncryptedText) {
	  
	  String RSADecryptedText = "";
	  
	    try {

	        // Check if the pair of keys are present else generate those.
	        if (!areKeysPresent()) {
	          // Method generates a pair of keys using the RSA algorithm and stores it
	          // in their respective files
	          generateKey();
	        }
        
	        // Decrypt the cipher text using the private key.
	        ObjectInputStream inputStream = null;
	        inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
	        final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
	        final String plainText = decrypt(RSAEncryptedText, privateKey);
	        RSADecryptedText = plainText;

	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	  
	    //return RSADecryptedText;
	    return RSADecryptedText;
}

/**
* Test the RSAEncryption
*/
/*
public static void main(String[] args) {
	  
	  String OriginalText = "Real Lean Fitness!!!";
	  
	  byte [] RSAEncryptedText = RSAEncrypt(OriginalText);
	  String RSADecryptedText = RSADecrypt(RSAEncryptedText);
	  
   // Printing the Original, Encrypted and Decrypted Text
   System.out.println("OriginalText: " + OriginalText);
   System.out.println("RSAEncryptedText: " + RSAEncryptedText);
   System.out.println("RSADecryptedText: " + RSADecryptedText);
	  
}
*/
}
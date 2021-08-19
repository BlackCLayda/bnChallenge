package client;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {

	 private static byte[] key = { 0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79 };

	 
	 public static String encryptByteArray(byte[] array) throws Exception
	    {
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	        String encryptedString = Base64.getEncoder().encodeToString(cipher.doFinal(array));
	        return encryptedString;
	    }

	    public static byte[] decryptByteArray(String strToDecrypt) throws Exception
	    {
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey);
	        return cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));
	    }
	
}

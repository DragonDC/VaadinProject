package hashGenerator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
	
	 	public static String generateMD5(String string){
	        return hashString(string, "MD5");
	    }
	 
	    public static String generateSHA1(String string){
	        return hashString(string, "SHA-1");
	    }
	 
	    public static String generateSHA256(String string){
	        return hashString(string, "SHA-256");
	    }
    
    private static String hashString(String string, String algorithm){
            MessageDigest digest = null;
            byte[] hashedBytes = null;
			try {
				digest = MessageDigest.getInstance(algorithm);
				hashedBytes = digest.digest(string.getBytes("UTF-8"));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
 
            return convertByteArrayToHexString(hashedBytes);

    }
    
    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }
    
}

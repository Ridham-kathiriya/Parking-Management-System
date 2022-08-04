package Modules.User.Utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Hash {
    public static String getHashCode(String inputString) {
        String encryptedText = "";
        int radix = 16;
        int textMaxLength = 32;
        try {
            MessageDigest MD = MessageDigest.getInstance("MD5");
            MD.update(inputString.getBytes());
            byte[] digest = MD.digest();
            BigInteger num = new BigInteger(1, digest);
            encryptedText = num.toString(radix);
            while (encryptedText.length() < textMaxLength) {
                encryptedText = "0" + encryptedText;
            }
        }catch (Exception e){
            System.out.println("Exception occurred: " + e.toString());
        }
        return encryptedText;
    }
}

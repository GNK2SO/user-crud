package br.com.manager.util;

import org.mindrot.jbcrypt.BCrypt;

public class Encoder {

    public static String encode(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }
    
    public static boolean match(String plainText, String encoded) {
        return BCrypt.checkpw(plainText, encoded);
    }
    
}

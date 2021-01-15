package com.example.weddingelements_android.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Base64;
import java.util.HashMap;

public class SignatureValidatior {

    public static HashMap<String,String> getKeyPair() throws NoSuchAlgorithmException {
        HashMap<String,String> map = new HashMap<>();
        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
        //Initializing the KeyPairGenerator
        keyPairGen.initialize(2048);
        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();
        //Getting the private key from the key pair
        String str_pubkey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
        String str_privkey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
        map.put("private",str_privkey);
        map.put("public",str_pubkey);
        return map;
    }
}

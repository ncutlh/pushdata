package com.apd.www.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Liuhong on 2015/6/1.
 */
public class DesUtil {
    //加密
    public static String encrypt (String data,String key)
            throws Exception
    {
        byte[] bt=encrypt(data.getBytes(),key.getBytes());
        String strs=new BASE64Encoder().encode(bt);
        return strs;
    }
    //解密
    public static String decrypt(String data,String key)
            throws Exception
    {
        if(data==null)
            return null;
        BASE64Decoder decorder=new BASE64Decoder();
        byte[] buf=decorder.decodeBuffer(data);
        byte[] bt=decrypt(buf,key.getBytes());
        return new String(bt);
    }

    //加密方法
    public static byte[] encrypt(byte[] data,byte[] key)
            throws Exception
    {
        DESKeySpec dks=new DESKeySpec(key);
        SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
        SecretKey secretkey=keyFactory.generateSecret(dks);
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(key);
        cipher.init(Cipher.ENCRYPT_MODE, secretkey,iv);
        return cipher.doFinal(data);
    }


    //解密方法
    public static byte[] decrypt(byte[] data,byte[] key)
            throws Exception
    {
        DESKeySpec dks=new DESKeySpec(key);
        SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
        SecretKey secretkey=keyFactory.generateSecret(dks);
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(key);
        cipher.init(Cipher.DECRYPT_MODE, secretkey,iv);
        return cipher.doFinal(data);
    }



}

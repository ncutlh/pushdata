package com.apd.www.config;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.math.BigDecimal;


public class BaseConfig {

    //check flag
    private static final boolean IS_CHECK_NOT_CONFIG_KEY = true;

    //environments
    private static PropertiesConfiguration settingsConfiguration = null;

    //settings
    private static PropertiesConfiguration environmentsConfiguration = null;

    static {
        try {
            //init with FileChangedReloadingStrategy
            settingsConfiguration = new PropertiesConfiguration(Global.SETTINGS_FILE_PATH);
            settingsConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());

            environmentsConfiguration = new PropertiesConfiguration(Global.ENVIRONMENTS_FILE_PATH);
            environmentsConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("配置文件加载失败，请检查配置文件！");
        }
    }


    private static void checkNullValue(String key, String value, boolean env) {
        if (IS_CHECK_NOT_CONFIG_KEY && value == null) {
            throw new RuntimeException((env ? "环境" : "业务") + "配置文件中找不到Key：'" + key + "'，请检查相应的配置文件！");
        }
    }

    protected static String getString(String key, String defaultValue) {
        String value = settingsConfiguration.getString(key);
        checkNullValue(key, value, false);
        //no value
        if (value == null) {
            return defaultValue;
        }
        /*try {
            value = new String(value.getBytes("ISO8859-1"), "UTF-8");
        } catch (Exception e) {
            return defaultValue;
        }*/
        return value == null ? defaultValue : value;
    }

    protected static int getInt(String key, int defaultValue) {
        String value = settingsConfiguration.getString(key);
        checkNullValue(key, value, false);
        return value == null ? defaultValue : Integer.parseInt(value);
    }

    protected static BigDecimal getDecimal(String key, BigDecimal defaultValue) {
        String value = settingsConfiguration.getString(key);
        checkNullValue(key, value, false);
        return value == null ? defaultValue : new BigDecimal(value);
    }

    protected static Boolean getBool(String key, Boolean defaultValue) {
        String value = settingsConfiguration.getString(key);
        checkNullValue(key, value, false);
        return value == null ? defaultValue : Boolean.parseBoolean(value);
    }


    //get environment value
    protected static String getEnvString(String key, String defaultValue) {
        String value = environmentsConfiguration.getString(key);
        checkNullValue(key, value, true);
        //no value
        if (value == null) {
            return defaultValue;
        }
        /*try {
            value = new String(value.getBytes("ISO8859-1"), "UTF-8");
        } catch (Exception e) {
            return defaultValue;
        }*/
        return value == null ? defaultValue : value;
    }

    protected  static long getLong(String key,long defaultValue){
        String value = settingsConfiguration.getString(key);
        checkNullValue(key,value,true);
        if(value == null){
            return defaultValue;
        }
        return value == null ? defaultValue : Long.parseLong(value);
    }

}

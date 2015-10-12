package com.apd.www.config;

import java.io.File;
import java.util.Date;

/**
 * Created by caihongguang on 14-9-29.
 */
public class Global {


    //settings
    public static String SETTINGS_FILE_PATH = null;
    public static String ENVIRONMENTS_FILE_PATH = null;

    static {
        //settings
        SETTINGS_FILE_PATH = System.getenv("APENGDAI_SETTINGS_FILE") == null ? System.getProperty("APENGDAI_SETTINGS_FILE") : System.getenv("APENGDAI_SETTINGS_FILE");
        if (SETTINGS_FILE_PATH == null) {
            throw new RuntimeException("没有设置环境变量：APENGDAI_SETTINGS_FILE");
        }
        File file = new File(SETTINGS_FILE_PATH);
        if (!file.exists()) {
            throw new RuntimeException("环境变量：APENGDAI_SETTINGS_FILE 设置的的配置文件找不到");
        }

        //environment
        ENVIRONMENTS_FILE_PATH = System.getenv("APENGDAI_ENVIRONMENTS_FILE") == null ? System.getProperty("APENGDAI_ENVIRONMENTS_FILE") : System.getenv("APENGDAI_ENVIRONMENTS_FILE");
        if (ENVIRONMENTS_FILE_PATH == null) {
            throw new RuntimeException("没有设置环境变量：APENGDAI_ENVIRONMENTS_FILE");
        }
        file = new File(ENVIRONMENTS_FILE_PATH);
        if (!file.exists()) {
            throw new RuntimeException("环境变量：APENGDAIÒ_ENVIRONMENTS_FILE 设置的的配置文件找不到");
        }
    }
}

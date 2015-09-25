package com.apd.www.config;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by caihongguang on 14-9-29.
 */
public final class WebConfig extends BaseConfig{



    //网贷雷达

    public static String getWdldKey(){return getString("WDLD_KEY","");}
    public static String getWdldPlatCode(){return getString("WDLD_PLAT_CODE","");}
    public static String getWdldLocalKey(){return getString("WDLD_LOCAL_KEY","");}



}

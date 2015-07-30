package com.apd.www.service;

import com.apd.www.pojo.User;
import com.apd.www.pojo.UserMarket;

/**
 * Created by Liuhong on 2015/7/30.
 */
public interface UserSerivce {

    User findById(int i);
    UserMarket getUserMarket(int channeluid);
}

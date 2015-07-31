package com.apd.www.service;

import com.apd.www.pojo.User;
import com.apd.www.pojo.UserMarket;

import java.util.List;

/**
 * Created by Liuhong on 2015/7/30.
 */
public interface UserSerivce {

    User findById(int i);
    UserMarket getUserMarketByUid(int uid);
    public List<UserMarket> getUserMarket(String channel);
}

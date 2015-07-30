package com.apd.www.service;

import com.apd.www.pojo.Investment;

import java.util.List;

/**
 * Created by laintime on 15/5/8.
 */
public interface InvestService {
    List getInvestList(int id);
    List<Investment> getInvestListByUid(int id);
}

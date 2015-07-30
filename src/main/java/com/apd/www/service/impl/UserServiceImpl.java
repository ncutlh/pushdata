package com.apd.www.service.impl;

import com.apd.www.dao.UserRepository;
import com.apd.www.pojo.User;
import com.apd.www.pojo.UserMarket;
import com.apd.www.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;

/**
 * Created by Liuhong on 2015/7/30.
 */
@Component
public class UserServiceImpl  implements UserSerivce{

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public User findById(int i) {
        return userRepository.findOne(i);
    }

    @Override
    public UserMarket getUserMarket(int channeluid) {
        TypedQuery query1 = em.createQuery("select c from UserMarket c where c.ext1 = "+channeluid, UserMarket.class);

        return (UserMarket) query1.getSingleResult();
    }


}

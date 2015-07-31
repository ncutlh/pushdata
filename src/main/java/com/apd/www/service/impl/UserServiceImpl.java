package com.apd.www.service.impl;

import com.apd.www.dao.UserRepository;
import com.apd.www.pojo.User;
import com.apd.www.pojo.UserMarket;
import com.apd.www.service.UserSerivce;
import com.apd.www.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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
    public UserMarket getUserMarketByUid(int uid){
        TypedQuery query1 = em.createQuery("select c from UserMarket c where c.userid = "+uid, UserMarket.class);
        return (UserMarket) query1.getSingleResult();
    }

    @Override
    public List<UserMarket> getUserMarket(String channel) {
        String sql ="select c from User u,UserMarket c " +
                    " where u.id = c.userid " +
                    " and u.registerat >=?1" +
                    " and u.registerat <=?2" +
                    " and c.channel=?3";
        TypedQuery query1 = em.createQuery( sql, UserMarket.class);
        query1.setParameter(1, DateUtils.getLastMonthFirstDay());
        query1.setParameter(2, DateUtils.getLastMonthLastDay());
        query1.setParameter(3,channel);
        return  query1.getResultList();
    }


}

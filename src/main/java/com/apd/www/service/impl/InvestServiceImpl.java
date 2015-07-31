package com.apd.www.service.impl;

import com.apd.www.pojo.Investment;
import com.apd.www.service.InvestService;
import com.apd.www.utils.DateUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by laintime on 15/5/8.
 */

@Component
public class InvestServiceImpl  implements InvestService{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List getInvestList(int id) {

        TypedQuery query = em.createQuery("select c from Investment c where projectid= ?1 and status in ('Subscribe','LoanRequest','LoanConfirm','Finished') ORDER BY createAt desc", Investment.class);
        query.setParameter(1,id);

        return query.getResultList();

    }



    @Override
    public List<Investment> getLastMonthInvestList(String channel) {
        String sql = "select i from Investment i , UserMarket c " +
                     " where i.investoruserid = c.userid" +
                     " and i.status in ('Subscribe','LoanRequest','LoanConfirm','Finished') " +
                     " and i.createat >=?1" +
                     " and i.createat <=?2" +
                     " and  c.channel=?3"+
                     " ORDER BY i.investmentid";
        TypedQuery query1 = em.createQuery( sql, Investment.class);
        query1.setParameter(1, DateUtils.getLastMonthFirstDay());
        query1.setParameter(2, DateUtils.getLastMonthLastDay());
        query1.setParameter(3,channel);
        return query1.getResultList();
    }
}

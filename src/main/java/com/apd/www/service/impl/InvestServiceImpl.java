package com.apd.www.service.impl;

import com.apd.www.pojo.Investment;
import com.apd.www.service.InvestService;
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
    public List<Investment> getInvestListByUid(int id) {

        TypedQuery query = em.createQuery("select c from Investment c where investoruserid= "+id+" and status in ('Subscribe','LoanRequest','LoanConfirm','Finished') ORDER BY id", Investment.class);

        return query.getResultList();

    }
}

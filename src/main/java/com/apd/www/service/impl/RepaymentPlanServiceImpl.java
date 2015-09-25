package com.apd.www.service.impl;

import com.apd.www.pojo.Investment;
import com.apd.www.pojo.RepaymentPlan;
import com.apd.www.service.RepaymentPlanService;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Liuhong on 2015/9/25.
 */
@Component
public class RepaymentPlanServiceImpl  implements RepaymentPlanService {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<RepaymentPlan> getListByInvId(int invId) {
        TypedQuery query = em.createQuery("select c from RepaymentPlan c where investmentid= ?1  ORDER BY planpayat", RepaymentPlan.class);
        query.setParameter(1,invId);
        return query.getResultList();
    }

    @Override
    public RepaymentPlan getByObject(int repaymentPlanId) {
        TypedQuery query = em.createQuery("select c from RepaymentPlan c where repaymentplanid= ?1", RepaymentPlan.class);
        query.setParameter(1,repaymentPlanId);
        return (RepaymentPlan)query.getSingleResult();
    }
}

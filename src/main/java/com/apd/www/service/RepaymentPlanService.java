package com.apd.www.service;

import com.apd.www.pojo.RepaymentPlan;

import java.util.List;

/**
 * Created by Liuhong on 2015/9/25.
 */
public interface RepaymentPlanService {

    List<RepaymentPlan> getListByInvId(int invId);

    RepaymentPlan getByObject(int repaymentPlanId);
}

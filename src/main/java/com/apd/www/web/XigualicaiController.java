package com.apd.www.web;

import com.alibaba.fastjson.JSON;
import com.apd.www.pojo.Investment;
import com.apd.www.pojo.Project;
import com.apd.www.pojo.xigualicai.XigualicaiParams;
import com.apd.www.pojo.xigualicai.XigualicaiProjectParams;
import com.apd.www.service.InvestService;
import com.apd.www.service.ProjectService;
import com.apd.www.service.UserSerivce;
import com.apd.www.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Liuhong on 2015/8/19.
 */
@Controller
public class XigualicaiController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private InvestService investService;

    @Autowired
    private UserSerivce userSerivce;

    /**
     * 借款列表数据；
     * http://lh.apengdai.com/xigualicai/onSaleProduct
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/xigualicai/onSaleProduct")
    public String loans(HttpServletRequest request) {

        Map<String, Object> resoultMap = new HashMap<String, Object>();
        resoultMap.put("dataList", "");
        resoultMap.put("recordCount", 0);
        resoultMap.put("apiCorp", "阿朋贷");
        resoultMap.put("transferTime", DateUtils.getDateLong(new Date()));

        List<Project> projectList = projectService.getXigualicaiProjectList();
        if (projectList != null && projectList.size() > 0) {
            resoultMap.put("recordCount", projectList.size());
            List<XigualicaiParams> xigualicaiParamsList = new ArrayList<XigualicaiParams>();
            for (Project project : projectList) {
                XigualicaiParams xigualicaiParams = new XigualicaiParams();
                xigualicaiParams.setPlatformName("阿朋贷");
                if ("PersonalCredit".equals(project.getProjectcategory())) {//"信易融"
                    xigualicaiParams.setCreditSeriesName("信易融");
                    xigualicaiParams.setCreditSeriesId(1);
                } else if ("CarMortgage".equals(project.getProjectcategory())) {//车易融
                    xigualicaiParams.setCreditSeriesName("车易融");
                    xigualicaiParams.setCreditSeriesId(2);
                } else {
                    xigualicaiParams.setCreditSeriesName("其他");
                    xigualicaiParams.setCreditSeriesId(3);
                }
                xigualicaiParams.setProductName(project.getProjectname());
                xigualicaiParams.setProductCode(String.valueOf(project.getId()));
                xigualicaiParams.setTotalInvestment(project.getAmount().intValue());
                xigualicaiParams.setAnnualRevenueRate(project.getInterestrate().doubleValue());

                if (BigDecimal.ONE.compareTo(project.getFinancingmaturity()) > 0) {
                    xigualicaiParams.setLoanLifePeriod(project.getFinancingmaturityday().intValue());
                    xigualicaiParams.setLoanLifeType("天");
                } else {
                    xigualicaiParams.setLoanLifePeriod(project.getFinancingmaturity().intValue());
                    xigualicaiParams.setLoanLifeType("月");
                }

                // 0 代表其他；1 按月等额本息还款,；2按月付息,到期还本, 3 按天计息，一次性还本付息；4，按月计息，一次性还本付息；5 按季分期还款，6 为等额本金，按月还本金附录有更详细解释）
                if (project.getRepaymentcalctype().equals("OneInterestOnePrincipal"))
                    xigualicaiParams.setInterestPaymentType("一次性还本付息");
                else if ("MonthlyInterestOnePrincipal".equals(project.getRepaymentcalctype()))
                    xigualicaiParams.setInterestPaymentType("按月付息,到期还本");
                else if ("EqualPrincipalAndInterest".equals(project.getRepaymentcalctype()))
                    xigualicaiParams.setInterestPaymentType("等额本息");
                else
                    xigualicaiParams.setInterestPaymentType("其他");

                xigualicaiParams.setGuaranteeInsitutions("");

                if ("SCHEDULED".equals(project.getProjectstatus())) {
                    xigualicaiParams.setOnlineState("准备");
                } else if ("OPENED".equals(project.getProjectstatus())) {
                    xigualicaiParams.setOnlineState("在售");
                } else if ("FINISHED".equals(project.getProjectstatus())) {
                    xigualicaiParams.setOnlineState("审核中");
                } else if ("SETTLED".equals(project.getProjectstatus())) {
                    xigualicaiParams.setOnlineState("还款中");
                } else if ("CLEARED".equals(project.getProjectstatus())) {
                    xigualicaiParams.setOnlineState("还款完成");
                } else if ("ARCHIVED".equals(project.getProjectstatus())) {
                    xigualicaiParams.setOnlineState("还款完成");
                } else {
                    xigualicaiParams.setOnlineState("已取消");
                }


                xigualicaiParams.setScale(project.getProgressPercent().doubleValue());
                xigualicaiParams.setPublishDate(DateUtils.getDateLong(project.getAllowinvestat()));
                if (project.getDealdate() != null)
                    xigualicaiParams.setEstablishmentDate(DateUtils.getDateLong(project.getDealdate()));
                else
                    xigualicaiParams.setEstablishmentDate(DateUtils.getDateLong(project.getBiddeadline()));
                xigualicaiParams.setExpireDate("");
                xigualicaiParams.setRewardRate(0.0);

                List<Investment> investments = investService.getInvestList(project.getId());
                if (investments != null && investments.size() > 0) {
                    xigualicaiParams.setInvestTimes(investments.size());
                } else {
                    xigualicaiParams.setInvestTimes(0);
                }
                xigualicaiParams.setProductURL("http://www.apengdai.com/project/info/" + project.getId() + "?from=xglc");

                xigualicaiParamsList.add(xigualicaiParams);
            }

            resoultMap.put("dataList", xigualicaiParamsList);
        }
        return JSON.toJSONString(resoultMap);
    }


    @RequestMapping(value = "/xigualicai/productStateInfo")
    @ResponseBody
    private String getProjectStatus(@RequestParam("queryProductIdList") String queryProductIdList) {

        Map<String, Object> resoultMap = new HashMap<String, Object>();
        resoultMap.put("dataList", "");
        resoultMap.put("recordCount", 0);
        resoultMap.put("apiCorp", "阿朋贷");
        resoultMap.put("transferTime", DateUtils.getDateLong(new Date()));

        if (StringUtils.isNotEmpty(queryProductIdList)) {
            List<Project> projectList = projectService.findByIds(queryProductIdList);
            if (projectList != null && projectList.size() > 0) {
                resoultMap.put("recordCount", projectList.size());
                List<XigualicaiProjectParams> xigualicaiParamsList = new ArrayList<XigualicaiProjectParams>();
                for (Project project : projectList) {
                    XigualicaiProjectParams xigualicaiProjectParams = new XigualicaiProjectParams();
                    xigualicaiProjectParams.setProductCode(String.valueOf(project.getId()));
                    if ("SCHEDULED".equals(project.getProjectstatus())) {
                        xigualicaiProjectParams.setOnlineState("准备");
                        xigualicaiProjectParams.setStatusChangeDate(DateUtils.getDateLong(project.getExpectedonlinedate()));
                    } else if ("OPENED".equals(project.getProjectstatus())) {
                        xigualicaiProjectParams.setOnlineState("在售");
                        xigualicaiProjectParams.setStatusChangeDate(DateUtils.getDateLong(project.getAllowinvestat()));
                    } else if ("FINISHED".equals(project.getProjectstatus())) {
                        xigualicaiProjectParams.setOnlineState("审核中");
                        xigualicaiProjectParams.setStatusChangeDate(DateUtils.getDateLong(project.getBidcompletedtime()));
                    } else if ("SETTLED".equals(project.getProjectstatus())) {
                        xigualicaiProjectParams.setOnlineState("还款中");
                        xigualicaiProjectParams.setStatusChangeDate(DateUtils.getDateLong(project.getDealdate()));
                    } else if ("CLEARED".equals(project.getProjectstatus())) {
                        xigualicaiProjectParams.setOnlineState("还款完成");
                    } else if ("ARCHIVED".equals(project.getProjectstatus())) {
                        xigualicaiProjectParams.setOnlineState("还款完成");
                    } else {
                        xigualicaiProjectParams.setOnlineState("已取消");
                    }
                    xigualicaiProjectParams.setScale(project.getProgressPercent().doubleValue());

                    if (project.getDealdate() != null)
                        xigualicaiProjectParams.setStatusChangeDate(DateUtils.getDateLong(project.getDealdate()));
                    else
                        xigualicaiProjectParams.setStatusChangeDate(DateUtils.getDateLong(project.getAllowinvestat()));


                    List<Investment> investments = investService.getInvestList(project.getId());
                    if (investments != null && investments.size() > 0) {
                        xigualicaiProjectParams.setInvestTimes(investments.size());
                    } else {
                        xigualicaiProjectParams.setInvestTimes(0);
                    }
                    xigualicaiProjectParams.setProductURL("http://www.apengdai.com/project/info/" + project.getId() + "?from=xglc");

                    xigualicaiParamsList.add(xigualicaiProjectParams);
                }
                resoultMap.put("dataList", xigualicaiParamsList);

            }

        }
        return JSON.toJSONString(resoultMap);
    }
}
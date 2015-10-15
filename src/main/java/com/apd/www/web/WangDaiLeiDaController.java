package com.apd.www.web;

import com.alibaba.fastjson.JSON;
import com.apd.www.config.WebConfig;
import com.apd.www.pojo.Investment;
import com.apd.www.pojo.Project;
import com.apd.www.pojo.RepaymentPlan;
import com.apd.www.pojo.UserMarket;

import com.apd.www.pojo.wangdaileida.WangDaiLeiDaInvParams;
import com.apd.www.pojo.wangdaileida.WangDaiLeiDaProParams;
import com.apd.www.pojo.wangdaileida.WangDaiLeiDaReplamentParams;
import com.apd.www.service.InvestService;
import com.apd.www.service.ProjectService;
import com.apd.www.service.RepaymentPlanService;
import com.apd.www.service.UserSerivce;
import com.apd.www.utils.DateUtils;
import com.apd.www.utils.DesUtil;
import com.apd.www.utils.WDLDDESUtils;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.mysql.fabric.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Liuhong on 2015/9/25.
 */
@Controller
public class WangDaiLeiDaController {

    @Autowired
    private UserSerivce userSerivce;

    @Autowired
    private InvestService investService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RepaymentPlanService repaymentPlanService;


    private Integer checkUid(String  wdldUserIDUnCryt){

        try {
            String ext3 = WDLDDESUtils.decrypt(wdldUserIDUnCryt, WebConfig.getWdldKey());
            String userId = WDLDDESUtils.decrypt(ext3, WebConfig.getWdldLocalKey());
            UserMarket userMarket = userSerivce.getUserMarketByUid(Integer.valueOf(userId));

            if(userMarket!=null  && "wdld".equals(userMarket.getExt5()))
            {
               return userMarket.getUserid();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String getResoult(String jsonString){
        String resoult = "";
        try {
            resoult = WDLDDESUtils.encrypt(jsonString, WebConfig.getWdldKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  resoult;
    }

    private boolean checkLocalPlat(String  localPlatKey){

        try {
            String delocalPlatKey= WDLDDESUtils.decrypt(localPlatKey, WebConfig.getWdldKey());
            if(WebConfig.getWdldKey().equals(delocalPlatKey))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //获得投资记录
    @ResponseBody
    @RequestMapping(value = "/wdld/getInvList")
    public String getInvList(HttpServletResponse response,
                             @RequestParam(value = "userID",required = true) String  userID,
                             @RequestParam(value = "n",required = true) String n) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("count","0");
        map.put("dataList","");
        int uid =checkUid(userID);
        try {
            String nu = WDLDDESUtils.decrypt(n, WebConfig.getWdldKey());
        if(uid!=0){
                List<Investment> invList = investService.getInvestListByUid(uid,Integer.valueOf(nu));
                if(invList!=null && invList.size()>0){
                    map.put("count",String.valueOf(invList.size()));
                    List<WangDaiLeiDaInvParams> wangDaiLeiDaInvList= new ArrayList<WangDaiLeiDaInvParams>();
                    for(Investment inv : invList){
                        WangDaiLeiDaInvParams wangDaiLeiDaInvParams = new WangDaiLeiDaInvParams();
                        wangDaiLeiDaInvParams.setBidID(String.valueOf(inv.getProjectid()));
                        wangDaiLeiDaInvParams.setInvestDate(DateUtils.getDateCompact(inv.getCreateat()));
                        wangDaiLeiDaInvParams.setInvestID(String.valueOf(inv.getInvestmentid()));
                        wangDaiLeiDaInvParams.setInvestMoney(String.valueOf(inv.getAmount()));
                        wangDaiLeiDaInvList.add(wangDaiLeiDaInvParams);
                    }
                    map.put("dataList",wangDaiLeiDaInvList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return JSON.toJSONString(map);
        return getResoult(JSON.toJSONString(map));
    }



    //获得投资记录
    @ResponseBody
    @RequestMapping(value = "/wdld/getInvCount")
    public String getInvCount(HttpServletResponse response,@RequestParam(value = "userID",required = true) String  userID)
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String,String> map = new HashMap<String, String>();
        map.put("count","0");
        int uid =checkUid(userID);
        if(uid!=0){
            Long invCount = investService.getInvestCountByUid(uid);
            map.put("count",String.valueOf(invCount));
        }
        return getResoult(JSON.toJSONString(map));
    }

    //获得回款计划
    @ResponseBody
    @RequestMapping(value = "/wdld/getReplayList",method = RequestMethod.POST)
    public String getReplayList(HttpServletResponse response,
                                @RequestParam(value = "investID",required = true)  String investIDKey)
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("count","0");
        map.put("dataList","");

        String investID = null;
        try {
            investID = WDLDDESUtils.decrypt(investIDKey, WebConfig.getWdldKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<RepaymentPlan> repaymentPlanList = repaymentPlanService.getListByInvId(Integer.valueOf(investID));
        List<Date> terms = ImmutableSet.copyOf(FluentIterable.from(repaymentPlanList).transform(new Function<RepaymentPlan, Date>() {
            @Override
            public Date apply(RepaymentPlan r) {
                return r.getPlanpayat();
            }
        })).asList();

        if(repaymentPlanList !=null && repaymentPlanList.size()>0){

            Map<Date,WangDaiLeiDaReplamentParams> dateListMap = new HashMap<Date,WangDaiLeiDaReplamentParams>();
            for(RepaymentPlan repaymentPlan : repaymentPlanList) {
                if(dateListMap.get(repaymentPlan.getPlanpayat())==null){
                    WangDaiLeiDaReplamentParams wangDaiLeiDaReplamentParams = new WangDaiLeiDaReplamentParams();
                    wangDaiLeiDaReplamentParams.setRefundID(String.valueOf(repaymentPlan.getRepaymentplanid()));
                    wangDaiLeiDaReplamentParams.setInvestID(String.valueOf(repaymentPlan.getInvestmentid()));
                    wangDaiLeiDaReplamentParams.setReturnDate(DateUtils.getDateCompact(repaymentPlan.getPlanpayat()));
                    wangDaiLeiDaReplamentParams.setCurrentTerm(String.valueOf(terms.indexOf(repaymentPlan.getPlanpayat())+1));
                    wangDaiLeiDaReplamentParams.setTotalTerm(String.valueOf(terms.size()));
                    wangDaiLeiDaReplamentParams.setPrincipal(String.valueOf(repaymentPlan.getPrincipalamount()));
                    wangDaiLeiDaReplamentParams.setInterest(String.valueOf(repaymentPlan.getInterestamount()));
                    wangDaiLeiDaReplamentParams.setManageFee("0.00");
                    wangDaiLeiDaReplamentParams.setStatus("Paied".equals(repaymentPlan.getStatus()) ? "1" : "0");
                    dateListMap.put(repaymentPlan.getPlanpayat(),wangDaiLeiDaReplamentParams);
                }else{
                    WangDaiLeiDaReplamentParams wangDaiLeiDaReplamentParams = dateListMap.get(repaymentPlan.getPlanpayat());
                    wangDaiLeiDaReplamentParams.setRefundID(wangDaiLeiDaReplamentParams.getRefundID()+","+repaymentPlan.getRepaymentplanid());
                    wangDaiLeiDaReplamentParams.setPrincipal(String.valueOf(new BigDecimal(wangDaiLeiDaReplamentParams.getPrincipal()).add(repaymentPlan.getPrincipalamount())));
                    wangDaiLeiDaReplamentParams.setInterest(String.valueOf(new BigDecimal(wangDaiLeiDaReplamentParams.getInterest()).add(repaymentPlan.getInterestamount())));
                }

            }

            List<WangDaiLeiDaReplamentParams> replamentParamsList = new ArrayList<WangDaiLeiDaReplamentParams>();
            for(Date term:terms){
                replamentParamsList.add(dateListMap.get(term));
            }
            map.put("count",String.valueOf(terms.size()));
            map.put("dataList",replamentParamsList);
        }
        return getResoult(JSON.toJSONString(map));
    }

    //获得标的信息
    @ResponseBody
    @RequestMapping(value = "/wdld/getProList")
    public String getProList(HttpServletResponse response,@RequestParam(value = "key",required = true) String  key) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("count","0");
        map.put("dataList","");

        if(!checkLocalPlat(key))
            return getResoult(JSON.toJSONString(map));

        List<Project> projectList = projectService.getAllowinvestatProjectList();
        List<WangDaiLeiDaProParams> proList = getProjectList(projectList,map);

        map.put("count", proList.size());
        map.put("dataList",proList);
        return getResoult(JSON.toJSONString(map));
    }


    //获得历史标信息

    /**
     *
     * @param response
     * @param dateTimeKey
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/wdld/getHisProList",method = RequestMethod.POST)
    public String getHisProList(HttpServletResponse response,@RequestParam(value = "dateTime",required = true) String  dateTimeKey) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("count","0");
        map.put("dataList",new ArrayList<String>());
        try {
           String  dateTime = WDLDDESUtils.decrypt(dateTimeKey,WebConfig.getWdldKey());
           String str_date =dateTime.substring(0,4)+"-"+dateTime.substring(4,6)+"-"+dateTime.substring(6,8)+" 00:00:00";
           String end_date =dateTime.substring(0,4)+"-"+dateTime.substring(4,6)+"-"+dateTime.substring(6,8)+" 23:59:59";
            List<Project> projectList  = projectService.getProjectListByTime(str_date,end_date);
            if(projectList!=null && projectList.size()>0) {
                map.put("count", projectList.size());
                map.put("dataList", getProjectList(projectList, map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//return JSON.toJSONString(map);
        return getResoult(JSON.toJSONString(map));
    }

    /**
     * 获得指定ｉｄ标的信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/wdld/getProInfo")
    public String getProInfo(HttpServletResponse response,@RequestParam(value = "id",required = true) String id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Project project = null;
        try {
            String  idStr = WDLDDESUtils.decrypt(id,WebConfig.getWdldKey());
            project = projectService.findById(Integer.valueOf(idStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getResoult(JSON.toJSONString(getProject(project)));
    }


    private List<WangDaiLeiDaProParams> getProjectList(List<Project> projectList,Map<String,Object> map){
        List<WangDaiLeiDaProParams> wdldProList = new ArrayList<WangDaiLeiDaProParams>();
        for(Project p:projectList){
            wdldProList.add(getProject(p));
        }
        return wdldProList;
    }


    private  WangDaiLeiDaProParams getProject(Project p) {

        WangDaiLeiDaProParams proParams = new WangDaiLeiDaProParams();
        if("CANCELED".equals(p.getProjectstatus())
           ||"TEST".equals(p.getProjectstatus())
           ||"OVERDUE".equals(p.getProjectstatus())
            || "BREACH".equals(p.getProjectstatus())){
            proParams.setIsValid("0");
        }else{
            proParams.setIsValid("1");
        }
        proParams.setId(String.valueOf(p.getId()));
        proParams.setTitle(p.getProjectname());
        proParams.setBidInfoUrl("http://www.apengdai.com/project/info/" + p.getId() + "?from=wdld1");
        if("PersonalCredit".equals(p.getProjectcategory())) {
            proParams.setType("信易融");
        }else if("CarMortgage".equals(p.getProjectcategory())){
             proParams.setType("车易融");
        }else if("DaySettle".equals(p.getProjectcategory())){
            proParams.setType("日易盈");
        }else if("Company".equals(p.getProjectcategory())){
            proParams.setType ("企业融");
        }

        proParams.setMoney(p.getAmount().toString());
        proParams.setRate(p.getInterestrate().multiply(new BigDecimal(100)).toString());
        proParams.setRateType("Y");
        proParams.setPrize("0.00");
        if (BigDecimal.ONE.compareTo(p.getFinancingmaturity()) > 0) {
            proParams.setLimitTime(String.valueOf(p.getFinancingmaturityday().intValue()));
            proParams.setLimitType("D");
        } else {
            proParams.setLimitTime(String.valueOf(p.getFinancingmaturity().intValue()));
            proParams.setLimitType("M");
        }
        // 0 代表其他；1 按月等额本息还款,；2按月付息,到期还本, 3 按天计息，一次性还本付息；4，按月计息，一次性还本付息；5 按季分期还款，6 为等额本金，按月还本金附录有更详细解释）
        if (p.getRepaymentcalctype().equals("OneInterestOnePrincipal"))
            proParams.setRepaymentType("D");
        else if ("MonthlyInterestOnePrincipal".equals(p.getRepaymentcalctype()))
            proParams.setRepaymentType("C");
        else if ("EqualPrincipalAndInterest".equals(p.getRepaymentcalctype()))
            proParams.setRepaymentType("A");


        proParams.setProcess(String.valueOf(p.getProgressPercent().multiply(new BigDecimal(100))));

        if (p.getIsrealborrower()) {
            proParams.setOwner(StringUtils.isEmpty(p.getRealborrowername())?"":Math.abs(p.getRealborrowername().hashCode()) + "");
        } else {
            proParams.setOwner(Math.abs(!StringUtils.isEmpty(p.getRealborroweridcard()) ? p.getRealborroweridcard().hashCode() : 0) + "");
        }
        proParams.setPlatCode(WebConfig.getWdldPlatCode());
        proParams.setStartTime(DateUtils.getDateCompact(p.getAllowinvestat()));
        proParams.setVerifyTime(DateUtils.getDateCompact(p.getBidcompletedtime()));
        return proParams;
    }

}

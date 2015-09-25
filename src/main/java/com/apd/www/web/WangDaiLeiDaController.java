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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Liuhong on 2015/9/25.
 */
public class WangDaiLeiDaController {

    @Autowired
    private UserSerivce userSerivce;

    @Autowired
    private InvestService investService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RepaymentPlanService repaymentPlanService;

    private static String WDLD_LOCAL_PLAT_KEY="qwsdfgjf";

    private static  String WDLD_KEY="Z5hrfsVq";
    private static  String WDLD_LOCAL_KEY="WDFSadfs";
    private static  String WDLD_PLAT_CODE="阿朋贷";

    private UserMarket checkUid(String  wdldUserIDUnCryt){

        try {
            String ext3 = WDLDDESUtils.decrypt(wdldUserIDUnCryt, WebConfig.getWdldKey());
            String plantUserId= DesUtil.decrypt(ext3, WebConfig.getWdldLocalKey());

            UserMarket userMarket = userSerivce.getUserMarketByUid(Integer.valueOf(plantUserId));
            if(userMarket!=null && "wdld".equals(userMarket.getChannel())){
               return userMarket;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkLocalPlat(String  localPlatKey){

        try {
            String delocalPlatKey= DesUtil.decrypt(localPlatKey, WebConfig.getWdldLocalKey());
            if(WDLD_LOCAL_PLAT_KEY.equals(delocalPlatKey))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //获得投资记录
    @ResponseBody
    @RequestMapping(value = "/wdld/getInvList")
    public String getInvList(@RequestParam(value = "userID",required = true) String  wdldUserIDUnCryt,
                             @RequestParam(value = "n",required = true) int  n) {
        UserMarket userMarket = checkUid(wdldUserIDUnCryt);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("count","0");
        map.put("dataList","");

        if(userMarket!=null){
            List<Investment> invList = investService.getInvestListByUid(userMarket.getUserid());
            if(invList!=null && invList.size()>0){
                map.put("count",String.valueOf(invList.size()));
                List<WangDaiLeiDaInvParams> wangDaiLeiDaInvList= new ArrayList<WangDaiLeiDaInvParams>();
                for(Investment inv : invList){
                    WangDaiLeiDaInvParams wangDaiLeiDaInvParams = new WangDaiLeiDaInvParams();
                    wangDaiLeiDaInvParams.setBidID(String.valueOf(inv.getProjectid()));
                    wangDaiLeiDaInvParams.setInvestDate(String.valueOf(inv.getCreateat()));
                    wangDaiLeiDaInvParams.setInvestID(String.valueOf(inv.getInvestmentid()));
                    wangDaiLeiDaInvParams.setInvestMoney(String.valueOf(inv.getAmount()));
                    wangDaiLeiDaInvList.add(wangDaiLeiDaInvParams);
                }
                map.put("dataList",wangDaiLeiDaInvList);
            }
        }
        return JSON.toJSONString(map);
    }



    //获得投资记录
    @ResponseBody
    @RequestMapping(value = "/wdld/getInvCount")
    public String getInvCount(@RequestParam(value = "userID",required = true) String  wdldUserIDUnCryt)
    {

        UserMarket userMarket = checkUid(wdldUserIDUnCryt);
        Map<String,String> map = new HashMap<String, String>();
        map.put("count","0");

        if(userMarket!=null)
        {
            int invCount = investService.getInvestCountByUid(userMarket.getUserid());
            map.put("count",String.valueOf(invCount));
        }

        return JSON.toJSONString(map);
    }

    //获得回款计划
    @ResponseBody
    @RequestMapping(value = "/wdld/getReplayList")
    public String getReplayList(@RequestParam(value = "investID",required = true)  String investID)
    {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("count","0");
        map.put("dataList","");
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
                    wangDaiLeiDaReplamentParams.setReturnDate(String.valueOf(repaymentPlan.getPlanpayat()));
                    wangDaiLeiDaReplamentParams.setCurrentTerm(String.valueOf(terms.indexOf(repaymentPlan.getPlanpayat())+1));
                    wangDaiLeiDaReplamentParams.setTotalTerm(String.valueOf(terms.size()));
                    wangDaiLeiDaReplamentParams.setPrincipal(String.valueOf(repaymentPlan.getPrincipalamount()));
                    wangDaiLeiDaReplamentParams.setInterest(String.valueOf(repaymentPlan.getInterestamount()));
                    wangDaiLeiDaReplamentParams.setManageFee("0.00");
                    wangDaiLeiDaReplamentParams.setStauts("Paied".equals(repaymentPlan.getStatus())?"1":"0");
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
            map.put("count",String.valueOf(repaymentPlanList.size()));
            map.put("dataList",replamentParamsList);
        }
        return JSON.toJSONString(map);
    }

    //获得标的信息
    @ResponseBody
    @RequestMapping(value = "/wdld/getProList")
    public String getProList(@RequestParam(value = "key",required = true) String  key) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("count","0");
        map.put("dataList","");

        if(!checkLocalPlat(key))
            return JSON.toJSONString(map);

        List<Project> projectList = projectService.getAllowinvestatProjectList();
        getProjectList(projectList,map);

        map.put("count", "0");
        map.put("dataList","");
        return JSON.toJSONString(map);
    }


    //获得历史标信息

    /**
     * yyyMMdd
     * @param dateTime
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/wdld/getHisProList")
    public String getHisProList(@RequestParam(value = "dateTime",required = true) String  dateTime) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("count","0");
        map.put("dataList","");
        String str_date =dateTime.substring(0,4)+"-"+dateTime.substring(5,7)+"-"+dateTime.substring(8,9)+" 00:00:00";
        String end_date =dateTime.substring(0,4)+"-"+dateTime.substring(5,7)+"-"+dateTime.substring(8,9)+" 23:59:59";

        try {
            List<Project> projectList  = projectService.getProjectListByTime(str_date,end_date);
            map.put("count",projectList.size());
            map.put("dataList",getProjectList(projectList,map));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(map);
    }

    /**
     * 获得指定ｉｄ标的信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/wdld/getProInfo")
    public String getProInfo(@RequestParam(value = "id",required = true) int id) {
        Project project = projectService.findById(id);
        return JSON.toJSONString(getProject(project));
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
        proParams.setId(String.valueOf(p.getId()));
        proParams.setTitle(p.getProjectname());
        proParams.setBidInfoUrl("http://www.apengdai.com/project/info/" + p.getId() + "?from=wdld1");
        proParams.setType(p.getProjectcategory());
        proParams.setMoney(p.getAmount().toString());
        proParams.setRate(p.getInterestrate().toString());
        proParams.setRateType("Y");
        proParams.setPrize("0.00");
        if (BigDecimal.ONE.compareTo(p.getFinancingmaturity()) > 0) {
            proParams.setLimitTime(String.valueOf(p.getFinancingmaturityday().intValue()));
            proParams.setLimitType("D");
        } else {
            proParams.setLimitTime(String.valueOf(p.getFinancingmaturity()));
            proParams.setLimitType("M");
        }
        // 0 代表其他；1 按月等额本息还款,；2按月付息,到期还本, 3 按天计息，一次性还本付息；4，按月计息，一次性还本付息；5 按季分期还款，6 为等额本金，按月还本金附录有更详细解释）
        if (p.getRepaymentcalctype().equals("OneInterestOnePrincipal"))
            proParams.setRepaymentType("D");
        else if ("MonthlyInterestOnePrincipal".equals(p.getRepaymentcalctype()))
            proParams.setRepaymentType("C");
        else if ("EqualPrincipalAndInterest".equals(p.getRepaymentcalctype()))
            proParams.setRepaymentType("A");


        proParams.setProcess(String.valueOf(p.getProgressPercent()));

        if (p.getIsrealborrower()) {
            proParams.setOwner(Math.abs(p.getRealborrowername().hashCode()) + "");
        } else {
            proParams.setOwner(Math.abs(!StringUtils.isEmpty(p.getRealborroweridcard()) ? p.getRealborroweridcard().hashCode() : 0) + "");
        }


        proParams.setPlatCode(WebConfig.getWdldPlatCode());
        proParams.setStartTime(DateUtils.getDateCompact(p.getAllowinvestat()));
        return proParams;
    }

}

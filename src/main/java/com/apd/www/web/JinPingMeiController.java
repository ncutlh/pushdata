package com.apd.www.web;

import com.alibaba.fastjson.JSON;
import com.apd.www.pojo.*;
import com.apd.www.pojo.jinpingmei.JinPingMeiParams;
import com.apd.www.service.InvestService;
import com.apd.www.service.ProjectService;
import com.apd.www.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liuhong on 2015/7/30.
 */
@Controller
public class JinPingMeiController {

    @Autowired
    private UserSerivce userSerivce;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private InvestService investService;

    @ResponseBody
    @RequestMapping(value = "/getJPMProjectList")
    public String getJPMProjectList() throws ParseException {


        List<JinPingMeiParams> jinpinmeiList = new ArrayList<JinPingMeiParams>();

        List<Project> scaiProjects = projectService.getJpmOpenProjectList();
        if(scaiProjects==null || scaiProjects.size()<=0){
            return "{}";
        }
        for(Project project:scaiProjects){
            JinPingMeiParams jinPingMeiParams = new JinPingMeiParams();
            jinPingMeiParams.setName(project.getProjectname());
            jinPingMeiParams.setRate(String.valueOf(project.getInterestrate().setScale(2, RoundingMode.DOWN)));
            jinPingMeiParams.setSum_scale(String.valueOf(project.getAmount()));

            if(project.getInvestmentedamount().compareTo(BigDecimal.ZERO)!=0)
                jinPingMeiParams.setSpeed(String.valueOf(project.getInvestmentedamount().multiply(new BigDecimal(100)).divide(project.getAmount(), 2, BigDecimal.ROUND_DOWN)));
            else
                jinPingMeiParams.setSpeed("0");
            if(BigDecimal.ONE.compareTo(project.getFinancingmaturity()) > 0){
                jinPingMeiParams.setDay(String.valueOf(project.getFinancingmaturityday().intValue()));
                jinPingMeiParams.setDate_type("1");
            } else {
                jinPingMeiParams.setDay(String.valueOf(project.getFinancingmaturity().intValue()));
                jinPingMeiParams.setDate_type("2");
            }
            jinPingMeiParams.setSurplus(String.valueOf(project.getAmount().subtract(project.getInvestmentedamount())));
            if(project.getRepaymentcalctype().equals("OneInterestOnePrincipal"))
                jinPingMeiParams.setPattern("一次性还本付息");
            else if("MonthlyInterestOnePrincipal".equals(project.getRepaymentcalctype()))
                jinPingMeiParams.setPattern("每月付息，到期还本");
            else if("EqualPrincipalAndInterest".equals(project.getRepaymentcalctype()))
                jinPingMeiParams.setPattern("等额本息");


            jinPingMeiParams.setCps_from("阿朋贷");
            jinPingMeiParams.setCps_proid(String.valueOf(project.getId()));
            jinPingMeiParams.setPro_url(String.valueOf("http://www.apengdai.com" + "/project/info/" + project.getId() + "?from=jpm"));
            jinPingMeiParams.setM_pro_url(String.valueOf("http://api.apengdai.com" + "/api/v1/project/info/" + project.getId()));
            jinPingMeiParams.setEnsure("抵押");
            jinpinmeiList.add(jinPingMeiParams);
        }
        return JSON.toJSONString(jinpinmeiList);
    }

    @RequestMapping(value = "/getProjectStatus/{projectId}")
    @ResponseBody
    private String getProjectStatus(@PathVariable("projectId") Integer projectId){


        Project project = projectService.findById(projectId);
            Map<String,Integer> statusMap=new HashMap<String,Integer>();
            if(project.getProjectstatus().equals("OPENED"))
                statusMap.put("status",1);
            else
                statusMap.put("status",2);
            return JSON.toJSONString(statusMap) ;



    }

    @ResponseBody
    @RequestMapping(value = "/jpm/getUser")
    public String getUser() throws ParseException {
        Map<String,Object> reMap=new HashMap<String,Object>();
        List<UserMarket> userMarketList =userSerivce.getUserMarket("jpm");
        if(userMarketList!=null && userMarketList.size()>0 ){
            for(UserMarket userMarket:userMarketList) {
                User user = userSerivce.findById(userMarket.getUserid());
                reMap.put("cps_from", "阿朋贷");
                reMap.put("uid", userMarket.getExt1());//JPM用户uid	暂时默认为0
                reMap.put("user_name", user.getUserName());//注册用户名
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                reMap.put("reg_time", sdf.format(user.getRegisterat()));//注册时间	例如：2015-07-13 15:12:00
            }
        }
        return JSON.toJSONString(reMap) ;
    }


    @ResponseBody
    @RequestMapping(value = "/jpm/getInvest")
    public String getInvest() throws ParseException {
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        List<Investment> invList = investService.getLastMonthInvestList("jpm");
            if(invList!=null && invList.size()>0){
                for(int i=0;i<invList.size();i++){
                    Map<String,Object> reMap=new HashMap<String,Object>();
                    Investment inv=invList.get(i);

                    UserMarket userMarket =userSerivce.getUserMarketByUid(inv.getInvestoruserid());
                    User user = userSerivce.findById(userMarket.getUserid());
                    Project project = projectService.findById(inv.getProjectid());

                    reMap.put("cps_from","阿朋贷");
                    reMap.put("pro_name",project.getProjectname());//产品名称
                    reMap.put("uid",userMarket.getExt1());//	JPM用户uid
                    reMap.put("user_name",user.getUserName());//用户注册名
                    reMap.put("pro_url",String.valueOf("http://www.apengdai.com" + "/project/info/" + project.getId() + "?from=jpm"));//投资的产品标的地址
                    reMap.put("u_amount",inv.getAmount());//用户投资金额
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    reMap.put("u_time",sdf.format(inv.getCreateat()));//用户投资时间	例如：2015-07-13 15:12:00
                    reMap.put("is_first",i==0?1:2);//是首次投资
                    mapList.add(reMap);
                }
            }

        return JSON.toJSONString(mapList) ;


    }
}

package com.apd.www.web;
import com.alibaba.fastjson.JSON;
import com.apd.www.pojo.Investment;
import com.apd.www.pojo.Project;
import com.apd.www.pojo.rong360.Rong360Params;
import com.apd.www.service.InvestService;
import com.apd.www.service.ProjectService;
import com.apd.www.utils.DateUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Liuhong on 2015/8/4.
 */
@Controller
public class Rong360Controller {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private InvestService investService;


    static String token_key = "alskjdhfgnvbcmxk";


    //校验AuthToken
    public boolean checkAuthToken(String authToken,String t) throws Exception {
        Boolean isValid = false;
        String localtoken=DigestUtils.md5Hex(DigestUtils.md5Hex(t)+token_key);
        if (localtoken.trim().equals(authToken))
            isValid = true;
        return isValid;
    }

    //http://www.rong360.com/licai-p2p/verify
    //借款列表数据；
    @ResponseBody
    @RequestMapping(value = "/rong360/loans")
    public String loans(@RequestParam("token") String token,
                        @RequestParam("t") String t,
                        @RequestParam("page") Integer page,
                        @RequestParam("count") Integer count) {
        Map<String,Object> rong360ParamsMap = new HashMap<String, Object>();
        try {
            if (token == null || "".equals(token) || !checkAuthToken(token,t)) {
                return "{" +
                        "\"data\": null," +
                        "\"msg\": \"token验证失败\",\n" +
                        "\"status\": 100\n" +
                        "}";
            }

            if (page == null || count==null) {
                return "{" +
                        "\"data\": null," +
                        "\"msg\": \"page或count为空\",\n" +
                        "\"status\": 100\n" +
                        "}";
            }

            List<Project> rong360ProjectList = projectService.getYong360ProjectList(count,page);
            Long countProjects = projectService.getYong360ProjectListCount();

            if (countProjects <= 0) {
                return "{" +
                        "\"data\": null," +
                        "\"msg\": \"没有符合条件的数据\",\n" +
                        "\"status\": 100\n" +
                        "}";
            }

            rong360ParamsMap.put("version",2);
            rong360ParamsMap.put("status",0);
            rong360ParamsMap.put("msg","");

            List<Rong360Params> loans = new ArrayList<Rong360Params>();
            for (Project project : rong360ProjectList) {
                loans.add(getparams(project));
            }
            Map<String,Object> listMap = new HashMap<String, Object>();
            listMap.put("list", loans);
            listMap.put("total_number", rong360ProjectList.size());
            rong360ParamsMap.put("data", listMap);

        } catch (Exception e) {
            return "{" +
                    "\"data\": null," +
                    "\"msg\": \"访问异常--"+e.getMessage()+"\",\n" +
                    "\"status\": 100\n" +
                    "}";
        }

        return JSON.toJSONString(rong360ParamsMap);
    }


    //投资列表数据；
    //借款列表数据；
    @ResponseBody
    @RequestMapping(value = "/rong360/data")
    public String data(@RequestParam("token")String token,
                        @RequestParam("t")String t,
                        @RequestParam("product_id")Integer product_id) {
        Map<String,Object> rong360ParamsMap = new HashMap<String, Object>();
        try {
            if (token == null || "".equals(token) || !checkAuthToken(token,t)) {
                return "{" +
                        "\"data\": null," +
                        "\"msg\": \"token验证失败\",\n" +
                        "\"status\": 100\n" +
                        "}";
            }

           Project project = projectService.findById(product_id);

            if (project ==null) {
                return "{" +
                        "\"data\": null," +
                        "\"msg\": \"没有对应的标的项目\",\n" +
                        "\"status\": 100\n" +
                        "}";
            }

            rong360ParamsMap.put("version",2);
            rong360ParamsMap.put("status",0);
            rong360ParamsMap.put("msg","");

            rong360ParamsMap.put("data", getparams(project));
        } catch (Exception e) {
            return "{" +
                    "\"data\": null," +
                    "\"msg\": \"访问异常--"+e.getMessage()+"\",\n" +
                    "\"status\": 100\n" +
                    "}";

        }

        return JSON.toJSONString(rong360ParamsMap);
    }


    private Rong360Params getparams(Project project){
        Rong360Params rong360Params = new Rong360Params();

         rong360Params.setId(String.valueOf(project.getId()));//是	项目id,唯一
         rong360Params.setName(project.getProjectname());//是	项目全名称
         rong360Params.setBorrow_url("http://www.apengdai.com/project/info/" + project.getId() + "?from=rong360");//是	项目地址
         rong360Params.setBorrower(project.getRealborrowername());//否	借款人名称
         rong360Params.setGuarant_mode(1);//是	0 无担保 1 本息担保 2 本金担保
         rong360Params.setGuarantors("");//	string	是	第三方担保时，需提供担保方名称，否则置为空
         rong360Params.setAccount(project.getAmount());//是	募资总额，单位元
         rong360Params.setAccount_yes(project.getInvestmentedamount());//是	已募资金额，单位元

        if (BigDecimal.ONE.compareTo(project.getFinancingmaturity()) > 0) {
            rong360Params.setBorrow_time(project.getFinancingmaturityday());//是	借款时长
            rong360Params.setBorrow_time_unit(1);//是	借款时长单位，1为天，2为月，3为年
        } else {
            rong360Params.setBorrow_time(project.getFinancingmaturity().intValue());//是	借款时长
            rong360Params.setBorrow_time_unit(2);//是	借款时长单位，1为天，2为月，3为年
        }
        List<Investment> investments = investService.getInvestList(project.getId());
        if (investments != null && investments.size() > 0)
            rong360Params.setTender_num(investments.size());//是	已投资人数量（随进度更新）
        else
            rong360Params.setTender_num(0);

         rong360Params.setProgress(project.getProgressPercent());//是	投资完成进度（<=1，4位有效数字），已经募集的金额除以计划募集金额
         rong360Params.setPublish_time(project.getExpectedonlinedate().getTime()/1000);//否	发布时间
         rong360Params.setStart_time(project.getAllowinvestat().getTime()/1000);//是	募资开始时间

        if(project.getDealdate()!=null) {
            rong360Params.setRepay_start_time(project.getDealdate().getTime()/1000);//是	还款开始时间（完成募资时必须，未满标则置为0）

            if (BigDecimal.ONE.compareTo(project.getFinancingmaturity()) > 0) {
                rong360Params.setRepay_end_time(DateUtils.addDays(DateUtils.getDateLong(project.getDealdate()),project.getFinancingmaturityday()).getTime()/1000);//是	还款结束时间（完成募资时必须，未满标则置为0）
            }else {
                rong360Params.setRepay_end_time(DateUtils.addDays(DateUtils.getDateLong(project.getDealdate()),project.getFinancingmaturity().intValue()).getTime()/1000);//是	还款结束时间（完成募资时必须，未满标则置为0）
            }

        }else{
            rong360Params.setRepay_start_time(Long.parseLong("0"));//是	还款开始时间（完成募资时必须，未满标则置为0）
            rong360Params.setRepay_end_time(Long.parseLong("0"));//是	还款结束时间（完成募资时必须，未满标则置为0）
        }
        //是	标状态{-1:流标，0：筹款中，1:已满标，2：已开始还款,3:预发布，4:还款完成，5:逾期}
        if (project.getProjectstatus().equals("CANCELED")) {
            rong360Params.setStatus(-1);
            rong360Params.setEnd_time(project.getAllowinvestat().getTime()/1000);//是	募资结束时间（完成募资时必须，未满标则置为0）
        } else if (project.getProjectstatus().equals("SCHEDULED")) {
            rong360Params.setEnd_time(0);//是	募资结束时间（完成募资时必须，未满标则置为0）
            rong360Params.setStatus(3);
        } else if (project.getProjectstatus().equals("OPENED")) {
            rong360Params.setEnd_time(0);//是	募资结束时间（完成募资时必须，未满标则置为0）
            rong360Params.setStatus(0);
        } else if (project.getProjectstatus().equals("FINISHED")) {
            rong360Params.setEnd_time(project.getBiddeadline().getTime()/1000);//是	募资结束时间（完成募资时必须，未满标则置为0）
            rong360Params.setStatus(1);
        } else if (project.getProjectstatus().equals("SETTLED")) {
            rong360Params.setEnd_time(project.getBiddeadline().getTime()/1000);//是	募资结束时间（完成募资时必须，未满标则置为0）
            rong360Params.setStatus(2);
        } else if (project.getProjectstatus().equals("CLEARED")) {
            rong360Params.setEnd_time(project.getBiddeadline().getTime()/1000);//是	募资结束时间（完成募资时必须，未满标则置为0）
            rong360Params.setStatus(4);
        } else if (project.getProjectstatus().equals("ARCHIVED")) {
            rong360Params.setEnd_time(project.getBiddeadline().getTime()/1000);//是	募资结束时间（完成募资时必须，未满标则置为0）
            rong360Params.setStatus(4);
        }

        //是	1 抵押借款 2 信用借款  3 质押借款 4 第三方担保 5 净值标
        if ("PersonalCredit".equals(project.getProjectcategory())){//"信易融"
            rong360Params.setBorrow_type(2);
        } else{//车易融
            rong360Params.setBorrow_type(1);
        }

        //是	1 按月付息 到期还本 2 按季付息，到期还本3 每月等额本息  4 到期还本息 5 按周等额本息还款 6按周付息，到期还本 7提前付息，到期一次性还本款方式，单种还款方式请用数值，如：1；多种还款方式请用英文’,’组合，如1,2,3；其余还款方式请沟通确认
        if (project.getRepaymentcalctype().equals("OneInterestOnePrincipal"))
            rong360Params.setPay_type(4);
        else if ("MonthlyInterestOnePrincipal".equals(project.getRepaymentcalctype()))
            rong360Params.setPay_type(1);
        else if ("EqualPrincipalAndInterest".equals(project.getRepaymentcalctype()))
            rong360Params.setPay_type(3);

         rong360Params.setApr(project.getInterestrate());//是	年化收益率，精确到小数点2位
         rong360Params.setStart_price(project.getMinbidamount());//是	起投金额，单位元
         rong360Params.setStep_price(BigDecimal.ZERO);//是	追加投入金额，单位元，若只要求投资额度大于起始金额，则本字段设置为0
       return rong360Params;

    }
}

package com.apd.www.web;
import com.alibaba.fastjson.JSON;
import com.apd.www.pojo.Investment;
import com.apd.www.pojo.Project;
import com.apd.www.pojo.User;
import com.apd.www.pojo.wangdaitianyan.WangDaiTianYanInvParams;
import com.apd.www.pojo.wangdaitianyan.WangDaiTianYanProjectParams;
import com.apd.www.pojo.wangdaitianyan.WangDaiTianyanParams;
import com.apd.www.service.InvestService;
import com.apd.www.service.ProjectService;
import com.apd.www.service.UserSerivce;
import com.apd.www.utils.DateUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Liuhong on 2015/8/4.
 */
public class WangDaiTianYanController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private InvestService investService;

    @Autowired
    private UserSerivce userSerivce;

    static String localUserName = "wangdaitianyan";
    static String localPassword = "735709";
    static String token_key = "sfdsgdskgdksjgdh";
    static Date timestmp;
    static String token = "";


    //校验AuthToken
    public boolean checkAuthToken(String authToken) throws Exception {
        Boolean isValid = false;
        String content = localUserName + localPassword + token_key;
        token = DigestUtils.md5Hex(content.getBytes("utf-8"));
        if (authToken.trim().equals(token) && timestmp.getTime() - (new Date()).getTime() < 10000)
            isValid = true;
        return isValid;
    }

    //Token 请求
    @ResponseBody
    @RequestMapping(value = "/wangdaitianyan/token")
    private static String token(@RequestParam("userName") String userName,@RequestParam("password") String password) throws Exception {
       if(userName.equals(localUserName) && password.equals(localPassword)) {
           String content = userName + password + token_key;
           token = DigestUtils.md5Hex(content.getBytes("utf-8"));
           timestmp = new Date();
       }
           return token;
    }

    //借款列表数据；
    @ResponseBody
    @RequestMapping(value = "/wangdaitianyan/loans")
    public String loans(@RequestParam("token") String token,
                        @RequestParam("status") Integer status,//0、正在投标中的借款标;1、已完成-包括还款中和已完成的借款标,为空所有状态
                        @RequestParam("time_from") String time_from,//状态为1 是对应平台满标字段的值检索  状态为0就以平台发标时间字段检索
                        @RequestParam("time_to") String time_to,// 状态为1 是对应平台满标字段的值检索 状态为0 就以平台发标时间字段检索
                        @RequestParam("page_size") Integer page_size,//(不做分页总页为1,分页从1 开始向上取整,不要给小数)
                        @RequestParam("page_index") Integer page_index) {
        WangDaiTianyanParams wangDaiTianyanParams = new WangDaiTianyanParams();
        try {
            if (token == null || "".equals(token) || !checkAuthToken(token)) {
                wangDaiTianyanParams.setResult_code(-1);
                wangDaiTianyanParams.setResult_msg("未授权的访问!");
                return JSON.toJSONString(wangDaiTianyanParams);
            }

            if (page_size == null) {
                wangDaiTianyanParams.setResult_code(-1);
                wangDaiTianyanParams.setResult_msg("page_size is null");
                return JSON.toJSONString(wangDaiTianyanParams);
            }

            List<Project> wdtyProjectList = projectService.getWDTYProjectList(status, time_from, time_to, page_size, page_index);
            Integer countProjects = projectService.getWDTYProjectListCount(status, time_from, time_to);

            if (countProjects <= 0) {
                wangDaiTianyanParams.setResult_code(-1);
                wangDaiTianyanParams.setResult_msg("没有标");
                wangDaiTianyanParams.setPage_count(0);
                wangDaiTianyanParams.setPage_index(page_index);
                wangDaiTianyanParams.setLoans(null);
                return JSON.toJSONString(wangDaiTianyanParams);
            }

            wangDaiTianyanParams.setResult_code(1);
            wangDaiTianyanParams.setResult_msg("");
            wangDaiTianyanParams.setPage_count((int) Math.ceil((double) countProjects / page_size));
            wangDaiTianyanParams.setPage_index(page_index);
            List<WangDaiTianYanProjectParams> loans = new ArrayList<WangDaiTianYanProjectParams>();
            for (Project project : wdtyProjectList) {
                WangDaiTianYanProjectParams wDTYProjectParams = new WangDaiTianYanProjectParams();
                wDTYProjectParams.setId(project.getId());
                wDTYProjectParams.setUrl("http://www.apengdai.com/project/info/" + project.getId() + "?from=wdty");
                wDTYProjectParams.setPlatform_name("阿朋贷");
                wDTYProjectParams.setTitle(project.getProjectname());
                wDTYProjectParams.setUsername(project.getRealborrowername());
                if ("SCHEDULED".equals(project.getProjectstatus())
                        || "OPENED".equals(project.getProjectstatus())) {
                    wDTYProjectParams.setStatus(0);//是	项目状态（0：预投标，1：投标中，2：投标结束）
                } else {
                    wDTYProjectParams.setStatus(1);
                }
                wDTYProjectParams.setUserid(project.getBorroweruserid());
                // 0 代表信用标，1 担保标，2 抵押标，3秒标，4 债权转让标（流转标，二级市场标的），5 理财计划（宝类业务），6 其它
                if ("PersonalCredit".equals(project.getProjectcategory())) {//"信易融"
                    wDTYProjectParams.setC_type(0);
                } else if ("CarMortgage".equals(project.getProjectcategory())) {//车易融
                    wDTYProjectParams.setC_type(2);
                } else {
                    wDTYProjectParams.setC_type(6);
                }
                wDTYProjectParams.setAmount(project.getAmount());
                wDTYProjectParams.setRate(project.getInterestrate());

                if (BigDecimal.ONE.compareTo(project.getFinancingmaturity()) > 0) {
                    wDTYProjectParams.setP_type(0);//"1"，期限类型0代表天，1代表月(不为空)
                    wDTYProjectParams.setPeriod(project.getFinancingmaturityday().intValue());
                } else {
                    wDTYProjectParams.setP_type(1);//"1"，期限类型0代表天，1代表月(不为空)
                    wDTYProjectParams.setPeriod(project.getFinancingmaturity().intValue());
                }
                // 0 代表其他；1 按月等额本息还款,；2按月付息,到期还本, 3 按天计息，一次性还本付息；4，按月计息，一次性还本付息；5 按季分期还款，6 为等额本金，按月还本金附录有更详细解释）
                if (project.getRepaymentcalctype().equals("OneInterestOnePrincipal"))
                    wDTYProjectParams.setPay_way(4);
                else if ("MonthlyInterestOnePrincipal".equals(project.getRepaymentcalctype()))
                    wDTYProjectParams.setPay_way(2);
                else if ("EqualPrincipalAndInterest".equals(project.getRepaymentcalctype()))
                    wDTYProjectParams.setProcess(project.getProgressPercent());
                wDTYProjectParams.setReward(BigDecimal.ZERO);
                wDTYProjectParams.setGuarantee(BigDecimal.ZERO);
                wDTYProjectParams.setStart_time(DateUtils.getDateLong(project.getAllowinvestat()));// "2014-03-13 14:44:26",标的创建时间(不为空)
                wDTYProjectParams.setEnd_time(DateUtils.getDateLong(project.getBiddeadline()));// "2014-03-13 16:44:26" ,结束时间(不为空，是满标时间)
                List<Investment> investments = investService.getInvestList(project.getId());
                if (investments != null && investments.size() > 0)
                    wDTYProjectParams.setInvest_num(investments.size());// “5”,投资次数(尽量不为空)
                else
                    wDTYProjectParams.setInvest_num(0);
                wDTYProjectParams.setC_reward(BigDecimal.ZERO);

                loans.add(wDTYProjectParams);
            }
        } catch (Exception e) {

            wangDaiTianyanParams.setResult_code(0);
            wangDaiTianyanParams.setResult_msg(e.getMessage());
            return JSON.toJSONString(wangDaiTianyanParams);
        }

        return JSON.toJSONString(wangDaiTianyanParams);
    }


    //投资列表数据；
    //借款列表数据；
    @ResponseBody
    @RequestMapping(value = "/wangdaitianyan/data")
    public String data(@RequestParam("token")String token,
                        @RequestParam("status")Integer id,//0、正在投标中的借款标;1、已完成-包括还款中和已完成的借款标,为空所有状态
                        @RequestParam("time_from")String time_from,//状态为1 是对应平台满标字段的值检索  状态为0就以平台发标时间字段检索
                        @RequestParam("time_to")String time_to,// 状态为1 是对应平台满标字段的值检索 状态为0 就以平台发标时间字段检索
                        @RequestParam("page_size")Integer page_size,//(不做分页总页为1,分页从1 开始向上取整,不要给小数)
                        @RequestParam("page_index")Integer page_index) {
        WangDaiTianyanParams wangDaiTianyanParams = new WangDaiTianyanParams();
        try {
            if (token == null || "".equals(token) || !checkAuthToken(token)) {
                wangDaiTianyanParams.setResult_code(-1);
                wangDaiTianyanParams.setResult_msg("未授权的访问!");
                return JSON.toJSONString(wangDaiTianyanParams);
            }

            if (page_size == null) {
                wangDaiTianyanParams.setResult_code(-1);
                wangDaiTianyanParams.setResult_msg("page_size is null");
                return JSON.toJSONString(wangDaiTianyanParams);
            }
            List<Investment> investments = investService.getInvestListByPage(id, page_size, page_index);
            Integer countInvestments = investService.getInvestListCount(id);

            if (countInvestments <= 0) {
                wangDaiTianyanParams.setResult_code(-1);
                wangDaiTianyanParams.setResult_msg("没有标");
                wangDaiTianyanParams.setPage_count(0);
                wangDaiTianyanParams.setPage_index(page_index);
                wangDaiTianyanParams.setLoans(null);
                return JSON.toJSONString(wangDaiTianyanParams);
            }
            List<WangDaiTianYanInvParams> loans = new ArrayList<WangDaiTianYanInvParams>();
            for(Investment investment:investments){
                WangDaiTianYanInvParams wangDaiTianYanInvParams = new WangDaiTianYanInvParams();
                wangDaiTianYanInvParams.setId(investment.getProjectid());//标的项目编号同借款标编号
                User user = userSerivce.findById(investment.getInvestoruserid());
                wangDaiTianYanInvParams.setUseraddress(user.getLocation());//用户所在地",
                wangDaiTianYanInvParams.setLink("http://www.apengdai.com/project/info/" + investment.getProjectid() + "?from=wdty");//http://www.platform.com/loans/id.html?loanId=170647",
                wangDaiTianYanInvParams.setUsername(user.getUserName()); // 投标人的用户名称
                wangDaiTianYanInvParams.setUserid(investment.getInvestoruserid()); // 投标人的用户编号/ID
                wangDaiTianYanInvParams.setType("手动");//投标方式例如：手动、自动
                wangDaiTianYanInvParams.setMoney(investment.getAmount().intValue());// 投标金额
                wangDaiTianYanInvParams.setAccount(investment.getAmount().intValue());// 有效金额：投标金额实际生效部分
                wangDaiTianYanInvParams.setStatus("成功");// 投标状态例如：成功、部分成功、失败
                wangDaiTianYanInvParams.setAdd_time(DateUtils.getDateLong(investment.getCreateat()));// 投标时间
                loans.add(wangDaiTianYanInvParams);
            }


        } catch (Exception e) {

            wangDaiTianyanParams.setResult_code(0);
            wangDaiTianyanParams.setResult_msg(e.getMessage());
            return JSON.toJSONString(wangDaiTianyanParams);
        }

        return JSON.toJSONString(wangDaiTianyanParams);
    }
}

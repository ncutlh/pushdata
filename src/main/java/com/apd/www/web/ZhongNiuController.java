package com.apd.www.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apd.www.dao.UserRepository;
import com.apd.www.pojo.*;
import com.apd.www.service.InvestService;
import com.apd.www.service.ProjectService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Liuhong on 2015/7/29.
 */
@Controller
public class ZhongNiuController {


    @Autowired
    private ProjectService projectService;


    @ResponseBody
    @RequestMapping(value = "/zhongniu/getProjectList")
    public String getProjectList() throws ParseException {
        Map<String,Object> map = new HashMap<String, Object>();

        List<Project> scaiProjects = projectService.getZhongniuProjectList();
        if(scaiProjects==null || scaiProjects.size()<=0){
            map.put("status",1);
            map.put("msg","");
            return JSON.toJSONString(map);
        }
        List<Map> list = new ArrayList<Map>();
        for(Project project:scaiProjects) {
//            pid	String	是	项目id,唯一
//            status	Int	是	项目状态（0：预投标，1：投标中，2：投标结束）
//            amounted	Long	是	已募资金额，单位元
//            progress	Float	是	进度（单位：%）如：进度为17%，则progress值为17

            Map<String, Object> listObject = new HashMap<String, Object>();
            listObject.put("pid", String.valueOf(project.getId()));
            listObject.put("amounted", project.getAmount());
            listObject.put("progress", project.getInvestmentedamount().divide(project.getAmount(), 2));
            if ("SCHEDULED".equals(project.getProjectstatus())) {
                listObject.put("status", 0);
            } else if ("OPENED".equals(project.getProjectstatus())) {
                listObject.put("status", 1);
            } else {
                listObject.put("status", 2);
            }
            list.add(listObject);
        }
        map.put("status",0);
        map.put("msg","");
        map.put("list",list);
        return JSON.toJSONString(map);
    }


    @ResponseBody
    @RequestMapping(value = "/zhongniu/getData/{projectId}")
    public String getData(@PathVariable("projectId") Integer projectId) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();

        Project zhongniuProject = projectService.findById(projectId);
        if (zhongniuProject == null ) {
            map.put("status", 2);
            map.put("msg","未找到对应项目");
            return JSON.toJSONString(map);
        }
        ZhongNiuParams zhongNiuParams = new ZhongNiuParams();
        zhongNiuParams.setPid(String.valueOf(zhongniuProject.getId()));//是	项目id,唯一
        zhongNiuParams.setName(zhongniuProject.getProjectname());//是	项目全名称
        zhongNiuParams.setUrl(String.valueOf("http://www.apengdai.com" + "/project/info/" + zhongniuProject.getId() + "?from=zhongniu"));//是	项目url地址
        if("PersonalCredit".equals(zhongniuProject.getProjectcategory()))
        zhongNiuParams.setType(6);//是	1:票据标;2:抵押标;3:担保标;4:保理标;5:融租标;6:信用标;7:债权标;
        else
        zhongNiuParams.setType(1);
        zhongNiuParams.setYield(zhongniuProject.getInterestrate().multiply(new BigDecimal(100)).floatValue());//是	年化利率（单位：%），精确到小数点2位
        zhongNiuParams.setDuration(zhongniuProject.getFinancingmaturityday().floatValue());//是	投资期限（单位：月），精确到小数点2位
        //是	1 按月付息 到期还本 2 按季付息，到期还本3 每月等额本息  4 到期还本息 5 按周等额本息还款 6按周付息，到期还本 7 按天到期还款 8按季分期还款 9 其他
        if(zhongniuProject.getRepaymentcalctype().equals("OneInterestOnePrincipal"))
            zhongNiuParams.setRepaytype(4);
        else if("MonthlyInterestOnePrincipal".equals(zhongniuProject.getRepaymentcalctype()))
            zhongNiuParams.setRepaytype(1);
        else if("EqualPrincipalAndInterest".equals(zhongniuProject.getRepaymentcalctype()))
            zhongNiuParams.setRepaytype(3);
        zhongNiuParams.setGuaranttype(1);//是	0 无担保 1 本息担保 2 本金担保
        zhongNiuParams.setThreshold(zhongniuProject.getMinbidamount().longValue());//是	起投金额，单位元
        if ("SCHEDULED".equals(zhongniuProject.getProjectstatus())) {
            zhongNiuParams.setStatus(0);//是	项目状态（0：预投标，1：投标中，2：投标结束）
        } else if ("OPENED".equals(zhongniuProject.getProjectstatus())) {
            zhongNiuParams.setStatus(1);
        } else {
            zhongNiuParams.setStatus(2);
        }

        zhongNiuParams.setAmount(zhongniuProject.getAmount().longValue());//是	目标金额，单位元
        zhongNiuParams.setAmounted(zhongniuProject.getInvestmentedamount().longValue());//是	已募资金额，单位元
        zhongNiuParams.setProgress(zhongniuProject.getProgressPercent().multiply(new BigDecimal(100)).floatValue());//是	进度（单位：%）
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfDay=new SimpleDateFormat("yyyy-MM-dd");
        zhongNiuParams.setStartdate(sdfDay.format(zhongniuProject.getAllowinvestat()));//是	投标开始日期
        zhongNiuParams.setEnddate(sdfDay.format(zhongniuProject.getBiddeadline()));//是	投标结束日期
        zhongNiuParams.setPublishtime(sdf.format(zhongniuProject.getAllowinvestat()));//是	项目发布时间



        List<Map<String,String>> detail = new ArrayList<Map<String,String>>();
        List<String> keys = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        if("PersonalCredit".equals(zhongniuProject.getProjectcategory())) {//"信易融"
             keys=Arrays.asList("title","content","content","title","content","content","content","content","title","content");
             values=Arrays.asList("项目简介","【信易融】8.5%-15%年化收益",
                             "信易融是阿朋贷平台推出的以借款人信用作为还款保障的互联网金融理财产品。阿朋贷凭借线下专业的信用管理团队，对借款人的借款申请进行资料复核、尽职调查、信用分析、信用评级等业务流程后，将满足条件的借款标的发布于平台，为投资人提供理财选择。",
                             "标的优势","【信易融】优势详解",
                             "100%本息担保：阿朋贷对信易融产品实施严格的风控体系，全面保障投资人的利益。一旦项目逾期，阿朋贷立即启动本息保障计划年化收益8.5%~15%,年化收益最高可达40倍银行活期存款收益。",
                             "资金第三方托管：平台不接触投资人资金，由国内知名第三方平台新浪支付进行第三方托管。通过新浪支付P2P托管账户，投资人资金账户与P2P平台自身账户的完全独立，实现资金流与信息流分离，平台方也无法擅自挪用投资人资金，保障投资人的资金安全。",
                             "投资期限灵活：产品设有1个月、3个月、6个月、9个月、12个月，投资人可根据个人理财计划自由选择。信息公开透明：信息全程披露，公开透明，拒绝暗箱操作。",
                             "资金用途",zhongniuProject.getPurpose());
        }else if("CarMortgage".equals(zhongniuProject.getProjectcategory())){//车易融
            keys=Arrays.asList("title","content","content","title","content","content","content","content","title","content");
             values=Arrays.asList("项目简介","【车易融】11%-12%年化收益",
                    "车易融是阿朋贷平台推出的以汽车抵押借贷项目为标的的互联网金融理财产品。阿朋贷线下信用管理团队对借款人的个人信息及借款申请进行审核后，确认为合格借款人便办理汽车质押（或抵押）手续。随后，阿朋贷将满足条件的借款标的发布于平台，为投资人提供理财选择。",
                    "标的优势","【车易融】优势详解",
                    "100%本息担保：如果借款人还款有障碍，阿朋贷会启动本息保障计划，先行用风险准备金垫付，然后通过线下各地区门店对债务方的车辆进行相应处置后获得资金保障。",
                    "资金第三方托管：平台不接触投资人资金，由国内知名第三方平台新浪支付进行第三方托管。通过新浪支付P2P托管账户，投资人资金账户与P2P平台自身账户完全隔离，实现资金流与信息流分离，平台方也无法擅自挪用投资人资金，保障投资人的资金安全。",
                    "投资期限短：车易融借款期限为1个月、3个月，投资期限较短，可以满足投资人短期投资的需要。信息公开透明：信息全程披露，公开透明，拒绝暗箱操作。",
                    "资金用途",zhongniuProject.getPurpose());
        }else{
            keys=Arrays.asList("title","content");
            values=Arrays.asList("资金用途",zhongniuProject.getPurpose());
        }

        for(int i=0;i<keys.size();i++){
            addDetails(detail,keys.get(i),values.get(i));
        }
        zhongNiuParams.setDetail(detail);

        map.put("status", 0);
        map.put("msg","");
        map.put("data",zhongNiuParams);
        return JSON.toJSONString(map);
    }


    private void addDetails(List<Map<String,String>> detail,String key,String value){
        Map<String,String> contentMap=new HashMap<String, String>();
        contentMap.put(key,value);
        detail.add(contentMap);
    }

}



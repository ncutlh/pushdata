package com.apd.www.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.apd.www.pojo.Investment;
import com.apd.www.pojo.JinPingMeiParams;
import com.apd.www.pojo.Project;
import com.apd.www.pojo.ResponsePojo;
import com.apd.www.service.InvestService;
import com.apd.www.service.ProjectService;
import com.apd.www.utils.EncryptUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by laintime on 15/4/23.
 */

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private InvestService investService;
    private static Log logger = LogFactory.getLog(ProjectController.class);

    private static final Map maptoke=new HashMap();

    @RequestMapping("/index.html")
    @ResponseBody
    public String getProject(HttpServletRequest request)
    {


        String strproduct_id=  request.getParameter("product_id");

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        logger.info(sdf.format(new Date())+"----收到数据请求!---->"+strproduct_id);
        if(StringUtils.isEmpty(strproduct_id))
        {
            ResponsePojo responsePojo=new ResponsePojo();
            responsePojo.setErrCode(-99);
            responsePojo.setProduct_id(0);
            responsePojo.setProgressOf("0");

            String str=  JSON.toJSONString(responsePojo);
            return  str;
        }

        try
        {
            Project project=projectService.findById(Integer.parseInt(strproduct_id));

            DecimalFormat df=new DecimalFormat("###.###");

            ResponsePojo responsePojo=new ResponsePojo();
            responsePojo.setErrCode(0);
            responsePojo.setProduct_id(Integer.parseInt(strproduct_id));
            responsePojo.setProgressOf(df.format(project.getProgressPercent().multiply(new BigDecimal(100))));

            String str=  JSON.toJSONString(responsePojo);
            return  str;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ResponsePojo responsePojo=new ResponsePojo();
        responsePojo.setErrCode(-99);
        responsePojo.setProduct_id(0);
        responsePojo.setProgressOf("0");

        String str=  JSON.toJSONString(responsePojo);
        return  str;

    }


    @RequestMapping("/getcsv")

    public String getcsv(HttpServletRequest request,HttpServletResponse response)
    {


        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("text/html;charset=UTF-8");
        //filename=xx.csv&timestamp=345454&token=gdfgugd

        String filename=request.getParameter("filename");
        String timestamp=request.getParameter("timestamp");
        String token=request.getParameter("token");

        System.out.println(System.currentTimeMillis());

        String key="kdsofjdsafjdsafjdklsafldsaflkafjdsafjkl";


        String asscd=  DigestUtils.md5DigestAsHex((filename+timestamp+key).getBytes());

        if(!asscd.equals(token))
        {
            return null;
        }

        if(System.currentTimeMillis()-Long.valueOf(timestamp)>600000)
        {
            return null;
        }

        String path ="/data/spring-boot/data/"+filename;
        FileInputStream fis = null;
        try {
            fis =new FileInputStream(path);

            byte[] buffer = new byte[1024];

            int len = 0;

            while((len=fis.read(buffer))>0){

                response.getOutputStream().write(buffer,0,len);

            }



        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(fis!=null)
                {
                    fis.close();
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }



        return null;

    }



    @RequestMapping("/tdt/getProjectsByDate.json")
    @ResponseBody
    public String  getProjectsByDate(HttpServletRequest request) throws ParseException {

       String date=request.getParameter("date");
       String page=request.getParameter("page");
       String pageSize=request.getParameter("pageSize");

        if(StringUtils.isEmpty(pageSize))
        {
            pageSize="20";
        }

        if(StringUtils.isEmpty(page))
        {
            page="1";
        }



        String token=request.getParameter("token");


        System.out.println("getdata--------->"+date+"----->token"+token);

        if(maptoke.get("token")==null)
        {
            Map map=new HashMap();
            map.put("token","token值无效");
            return JSON.toJSONString(map);
        }

        if(!token.equals(maptoke.get("token")))
        {
            Map map=new HashMap();
            map.put("token","token值无效");
            return  JSON.toJSONString(map);
        }

        List<Project> list= null;
        try {
            list = projectService.getProjectList(date,page,pageSize);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DecimalFormat df=new DecimalFormat("###.###");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List projList=new ArrayList();
        for(int i=0;i<list.size();i++)
        {
            Project p=list.get(i);
            Map map=new HashMap();
            map.put("title",p.getProjectname());
            map.put("amount", p.getAmount());
            map.put("schedule",df.format(p.getProgressPercent().multiply(new BigDecimal(100))));
            map.put("interestRate",df.format(p.getInterestrate().multiply(new BigDecimal(100)))+"%");
            map.put("projectId",p.getId());

            if(p.getFinancingmaturity().doubleValue()<1)
            {
                map.put("deadline",p.getFinancingmaturityday());
                map.put("deadLineUnit","天");
            }
            else
            {
                map.put("deadline",p.getFinancingmaturity());
                map.put("deadLineUnit","月");
            }

            map.put("reward",0);
            if(p.getProjectcategory().equals("CarMortgage"))
            {
                map.put("type","抵押标");
            }
            else
            {
                map.put("type","信用标");
            }

            if(p.getRepaymentcalctype().equals("MonthlyInterestOnePrincipal"))
            {
                map.put("repaymentType",5);
            }
            if(p.getRepaymentcalctype().equals("EqualPrincipalAndInterest"))
            {
                map.put("repaymentType",2);
            }
            if(p.getRepaymentcalctype().equals("OneInterestOnePrincipal"))
            {
                map.put("repaymentType",1);
            }


            if(p.getIsrealborrower()){
                map.put("userName",Math.abs(p.getRealborrowername().hashCode()) + "");
            } else {
                map.put("userName",Math.abs(!StringUtils.isEmpty(p.getRealborroweridcard()) ? p.getRealborroweridcard().hashCode():0) + "");
            }

            //map.put("userName",p.getBorroweruserid().hashCode());
            map.put("loanUrl","http://www.apengdai.com/project/info/"+p.getId());

            List<Investment> invList=investService.getInvestList(p.getId());
            List sub=new ArrayList();
            for(int j=0;j<invList.size();j++)
            {
                Investment iv1=invList.get(j);
                Map m1=new HashMap();
                m1.put("subscribeUserName",iv1.getInvestoruserid()+"".hashCode());
                m1.put("amount",iv1.getAmount());
                m1.put("validAmount",iv1.getAmount());
                m1.put("addDate",sdf.format(iv1.getCreateat()));
                m1.put("status",1);
                m1.put("type",0);
                sub.add(m1);
            }
            map.put("subscribes",sub);
            Date sdate=   invList.get(invList.size()-1).getCreateat();
            map.put("successTime",sdf.format(sdate));
            projList.add(map);
        }

        Map map=new HashMap();


        List listinfo=projectService.getProjectListInfo(date);
        map.put("totalPage",(Long)listinfo.get(0)%Integer.parseInt(pageSize)==0?(Long)listinfo.get(0)/Integer.parseInt(pageSize):(Long)listinfo.get(0)/Integer.parseInt(pageSize)+1);
        map.put("currentPage",page);
        map.put("totalCount",listinfo.get(0));
        map.put("totalAmount",listinfo.get(1));
        map.put("borrowList",projList);

        System.out.println(JSON.toJSONString(map));

        return JSON.toJSONString(map);
    }


    @RequestMapping("/tdt/login.json")
    @ResponseBody
    public String login(HttpServletRequest request)

    {

        String username=request.getParameter("username");
        String password=request.getParameter("password");

        System.out.println("--------->"+username);

        String strUserName="wangdaizhijia";
        String strPassword="eb009cefa9759b10d7bbd5cc969d95e5";

        if(maptoke.get("token")!=null)
        {
            Map map=new HashMap();
            map.put("return",1);

            Map m2=new HashMap();
            m2.put("token",maptoke.get("token"));

            map.put("data",m2);

            return JSON.toJSONString(map);
        }


        if(username.equals(strUserName)&&password.equals(strPassword))
        {
            Map map=new HashMap();
            map.put("return",1);

            Map m2=new HashMap();
            String str=EncryptUtil.md5(System.currentTimeMillis() + "wangdaizhijia");
            maptoke.put("token",str);
            //svatoken();
            m2.put("token",str);

            map.put("data",m2);

            return JSON.toJSONString(map);
        }
        else
        {
            Map map=new HashMap();
            map.put("return",0);


            map.put("data",null);

            return JSON.toJSONString(map);
        }


    }

    private void svatoken() {

        ScheduledThreadPoolExecutor ex=new ScheduledThreadPoolExecutor(1);

        ex.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {//清除key

                maptoke.remove("token");
                System.out.println("清除token--------->");

            }
        },60*60*1000,60*60*1000, TimeUnit.MILLISECONDS);
    }


    @ResponseBody
    @RequestMapping(value = "/getJPMProjectList")
    public String getJPMProjectList() throws ParseException {

        List<JinPingMeiParams> jinpinmeiList = new ArrayList<JinPingMeiParams>();

        List<Project> scaiProjects = projectService.getJpmOpenProjectList();

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
        if( project.isIspushtojinpingmei()){
            Map<String,Integer> statusMap=new HashMap<String,Integer>();
            if(project.getProjectstatus().equals("OPENED"))
                statusMap.put("status",1);
            else
                statusMap.put("status",2);
            return JSON.toJSONString(statusMap) ;
        }else{
            return null;
        }


    }

}

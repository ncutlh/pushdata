package com.apd.www.pojo.wangdaitianyan;

import java.math.BigDecimal;

/**
 * Created by Liuhong on 2015/8/4.
 */
public class WangDaiTianYanProjectParams {
    private Integer id;//"170647",标的编号，借款项目号，(不为空)
    private String  url;// "http://www.platform.com/loans/id.html?loanId=170647", (不为空)
    private String  platform_name;//"平台名称",平台名称(不为空)
    private String  title;// "电子产品私营业主扩大经营", (不为空)
    private String  username;// "xiaohaha",发标人（借款人）用户名(不为空)
    private Integer status;//" : "1"这笔借款标的状态，数字表示(不为空)
    private Integer userid;// "644706",发标人（借款人）用户标号的ID
//                               网贷天眼/投友圈网贷数据中心
//                               北京银讯财富信息技术有限公司http://www.p2peye.com/ | https://www.touyouquan.com/ 6 / 11
    private Integer c_type;// 0 代表信用标，1 担保标，2 抵押标，3秒标，4 债权转让标（流转标，二级市场标的），5 理财计划（宝类业务），6 其它
    private BigDecimal amount;// "50000",借款金额(不为空)
    private BigDecimal rate;// "0.18",借款年利率(不为空)
    private Integer period;// 借款期限的数字。如3月这里只返回3  若借款标的为流转标，对应的要有流转期限。
    private Integer p_type;//"1"，期限类型0代表天，1代表月(不为空)
    private Integer pay_way;// 0 代表其他；1 按月等额本息还款,；2按月付息,到期还本, 3 按天计息，一次性还本付息；4，按月计息，一次性还本付息；5 按季分期还款，6 为等额本金，按月还本金附录有更详细解释）
    private BigDecimal process;// "0.5",完成百分比(不为空)
    private BigDecimal reward;// "0.3",投标奖励
    private BigDecimal guarantee;// "0.2",担保奖励
    private String  start_time;// "2014-03-13 14:44:26",标的创建时间(不为空)
    private String  end_time;// "2014-03-13 16:44:26" ,结束时间(不为空，是满标时间)
    private Integer invest_num;// “5”,投资次数(尽量不为空)
    private BigDecimal c_reward;//" : "0.2"续投奖励

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlatform_name() {
        return platform_name;
    }

    public void setPlatform_name(String platform_name) {
        this.platform_name = platform_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getC_type() {
        return c_type;
    }

    public void setC_type(Integer c_type) {
        this.c_type = c_type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getP_type() {
        return p_type;
    }

    public void setP_type(Integer p_type) {
        this.p_type = p_type;
    }

    public Integer getPay_way() {
        return pay_way;
    }

    public void setPay_way(Integer pay_way) {
        this.pay_way = pay_way;
    }

    public BigDecimal getProcess() {
        return process;
    }

    public void setProcess(BigDecimal process) {
        this.process = process;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }

    public BigDecimal getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(BigDecimal guarantee) {
        this.guarantee = guarantee;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Integer getInvest_num() {
        return invest_num;
    }

    public void setInvest_num(Integer invest_num) {
        this.invest_num = invest_num;
    }

    public BigDecimal getC_reward() {
        return c_reward;
    }

    public void setC_reward(BigDecimal c_reward) {
        this.c_reward = c_reward;
    }
}

package com.apd.www.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by laintime on 15/4/23.
 */
@Entity
public class Project {

    @Id
    @Column(name = "ProjectID")
    private int id;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "investmentedamount")
    private BigDecimal investmentedamount;

    /**
     * 上线项目名称
     */
    private String projectname;




    /**
     * 计息方式 标的投满开始计息
     */
    private String interesttype;

    //private InterestDaysType interestDaysType;

    /**
     * 还款方式
     */
    private String repaymentcalctype;
    /**
     * 每月还款日 -- 暂时不用
     */
    private Integer paydateeverymonth;
    /**
     * 首次还款日 -- 暂时不用
     */
    private Date firstrepaymentdate;
    /**
     * 投资方预期年化利率
     */
    private BigDecimal interestrate = BigDecimal.ZERO;
    /**
     * 显示的预期年化利率
     */
    private String displayinterestrate;
    /**
     * 利率说明
     */
    private String interestratedes;


    /**
     * 融资期限(天)
     */
    private Integer financingmaturityday = 0;
    /**
     * 融资期限(月)
     */
    private BigDecimal financingmaturity = BigDecimal.ZERO;

    /**
     * 服务费费率
     */
    private BigDecimal servicefeerate = BigDecimal.ZERO;
    /**
     * 风险保证金费率
     */
    private BigDecimal munualguaranteefeerate = BigDecimal.ZERO;
    /**
     * 担保费费率
     */
    private BigDecimal guaranteefeerate = BigDecimal.ZERO;
    /**
     * 借款管理费费率
     */
    private BigDecimal loanmanagefeerate = BigDecimal.ZERO;
    /**
     * 逾期罚息费率/日
     */
    private BigDecimal latefeerate = BigDecimal.ZERO;
    /**
     * 逾期管理费费率
     */
    private BigDecimal latemanagerfeerate = BigDecimal.ZERO;

    private Integer guaranteecompanyid;

    private  BigDecimal minbidamount = BigDecimal.ZERO;

    /**
     * 融资方ID
     */
    private Integer borroweruserid;
    /**
     * 融资人缴费方式
     */
    private String borrowerpaytype;
    /**
     * 融资方是否实际借款人
     */
    private Boolean isrealborrower;
    /**
     * 实际借款人姓名
     */
    private String  realborrowername;

    private String realborroweridcard;

    public String getRealborroweridcard() {
        return realborroweridcard;
    }

    public void setRealborroweridcard(String realborroweridcard) {
        this.realborroweridcard = realborroweridcard;
    }

    /**
     * 预订上线日期
     */
    private Date expectedonlinedate;

    /**
     * 允许投标起始时间
     */
    private Date allowinvestat;

    /**
     * 认购截止时间
     */
    private Date biddeadline;
    /**
     * 基准还款日--暂时不用
     */
    private Date lastrepaymentdate;


    /**
     * SCHEDULED("已安排"),

     OPENED("开放投标"),

     //FAILED("流标"),

     FINISHED("已满标"),

     SETTLED("已结算"),

     CLEARED("已还清"),

     ARCHIVED("已存档"),

     CANCELED("已取消"),
     */
    private String projectstatus;

    private boolean ispushtojinpingmei;

    /**
     * 区域
     */
    private Integer area;

    /**
     * 资金用途
     */
    private String purpose;

    private String projectcategory;


    /**
     * 项目情况
     */
    private String projectdescription;


    /**
     * 项目投标完成时间
     */
    private Date bidcompletedtime;
    /**
     * 项目成交时间
     */
    private Date dealdate;

    private Date createat;

    private Date updateat;

    public Date getUpdateat() {
        return updateat;
    }

    public void setUpdateat(Date updateat) {
        this.updateat = updateat;
    }

    public Integer getFinancingmaturityday() {
        return financingmaturityday;
    }

    public void setFinancingmaturityday(Integer financingmaturityday) {
        this.financingmaturityday = financingmaturityday;
    }

    public String getProjectstatus() {
        return projectstatus;
    }

    public void setProjectstatus(String projectstatus) {
        this.projectstatus = projectstatus;
    }



    public String getProjectcategory() {
        return projectcategory;
    }

    public void setProjectcategory(String projectcategory) {
        this.projectcategory = projectcategory;
    }



    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getInteresttype() {
        return interesttype;
    }

    public void setInteresttype(String interesttype) {
        this.interesttype = interesttype;
    }

    public String getRepaymentcalctype() {
        return repaymentcalctype;
    }

    public void setRepaymentcalctype(String repaymentcalctype) {
        this.repaymentcalctype = repaymentcalctype;
    }

    public Integer getPaydateeverymonth() {
        return paydateeverymonth;
    }

    public void setPaydateeverymonth(Integer paydateeverymonth) {
        this.paydateeverymonth = paydateeverymonth;
    }

    public Date getFirstrepaymentdate() {
        return firstrepaymentdate;
    }

    public void setFirstrepaymentdate(Date firstrepaymentdate) {
        this.firstrepaymentdate = firstrepaymentdate;
    }

    public BigDecimal getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(BigDecimal interestrate) {
        this.interestrate = interestrate;
    }

    public String getDisplayinterestrate() {
        return displayinterestrate;
    }

    public void setDisplayinterestrate(String displayinterestrate) {
        this.displayinterestrate = displayinterestrate;
    }

    public String getInterestratedes() {
        return interestratedes;
    }

    public void setInterestratedes(String interestratedes) {
        this.interestratedes = interestratedes;
    }


    public BigDecimal getFinancingmaturity() {
        return financingmaturity;
    }

    public void setFinancingmaturity(BigDecimal financingmaturity) {
        this.financingmaturity = financingmaturity;
    }

    public BigDecimal getServicefeerate() {
        return servicefeerate;
    }

    public void setServicefeerate(BigDecimal servicefeerate) {
        this.servicefeerate = servicefeerate;
    }

    public BigDecimal getMunualguaranteefeerate() {
        return munualguaranteefeerate;
    }

    public void setMunualguaranteefeerate(BigDecimal munualguaranteefeerate) {
        this.munualguaranteefeerate = munualguaranteefeerate;
    }

    public BigDecimal getGuaranteefeerate() {
        return guaranteefeerate;
    }

    public void setGuaranteefeerate(BigDecimal guaranteefeerate) {
        this.guaranteefeerate = guaranteefeerate;
    }

    public BigDecimal getLoanmanagefeerate() {
        return loanmanagefeerate;
    }

    public void setLoanmanagefeerate(BigDecimal loanmanagefeerate) {
        this.loanmanagefeerate = loanmanagefeerate;
    }

    public BigDecimal getLatefeerate() {
        return latefeerate;
    }

    public void setLatefeerate(BigDecimal latefeerate) {
        this.latefeerate = latefeerate;
    }

    public BigDecimal getLatemanagerfeerate() {
        return latemanagerfeerate;
    }

    public void setLatemanagerfeerate(BigDecimal latemanagerfeerate) {
        this.latemanagerfeerate = latemanagerfeerate;
    }

    public Integer getGuaranteecompanyid() {
        return guaranteecompanyid;
    }

    public void setGuaranteecompanyid(Integer guaranteecompanyid) {
        this.guaranteecompanyid = guaranteecompanyid;
    }



    public Integer getBorroweruserid() {
        return borroweruserid;
    }

    public void setBorroweruserid(Integer borroweruserid) {
        this.borroweruserid = borroweruserid;
    }

    public String getBorrowerpaytype() {
        return borrowerpaytype;
    }

    public void setBorrowerpaytype(String borrowerpaytype) {
        this.borrowerpaytype = borrowerpaytype;
    }

    public Boolean getIsrealborrower() {
        return isrealborrower;
    }

    public void setIsrealborrower(Boolean isrealborrower) {
        this.isrealborrower = isrealborrower;
    }

    public String getRealborrowername() {
        return realborrowername;
    }

    public void setRealborrowername(String realborrowername) {
        this.realborrowername = realborrowername;
    }



    public Date getExpectedonlinedate() {
        return expectedonlinedate;
    }

    public void setExpectedonlinedate(Date expectedonlinedate) {
        this.expectedonlinedate = expectedonlinedate;
    }

    public Date getAllowinvestat() {
        return allowinvestat;
    }

    public void setAllowinvestat(Date allowinvestat) {
        this.allowinvestat = allowinvestat;
    }

    public Date getBiddeadline() {
        return biddeadline;
    }

    public void setBiddeadline(Date biddeadline) {
        this.biddeadline = biddeadline;
    }

    public Date getLastrepaymentdate() {
        return lastrepaymentdate;
    }

    public void setLastrepaymentdate(Date lastrepaymentdate) {
        this.lastrepaymentdate = lastrepaymentdate;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getProjectdescription() {
        return projectdescription;
    }

    public void setProjectdescription(String projectdescription) {
        this.projectdescription = projectdescription;
    }

    public Date getBidcompletedtime() {
        return bidcompletedtime;
    }

    public void setBidcompletedtime(Date bidcompletedtime) {
        this.bidcompletedtime = bidcompletedtime;
    }

    public Date getDealdate() {
        return dealdate;
    }

    public void setDealdate(Date dealdate) {
        this.dealdate = dealdate;
    }

    public BigDecimal getFactservicefee() {
        return factservicefee;
    }

    public void setFactservicefee(BigDecimal factservicefee) {
        this.factservicefee = factservicefee;
    }

    public BigDecimal getInterestamount() {
        return interestamount;
    }

    public void setInterestamount(BigDecimal interestamount) {
        this.interestamount = interestamount;
    }

    public Date getCreateat() {
        return createat;
    }

    public void setCreateat(Date createat) {
        this.createat = createat;
    }

    /**
     * 实际服务费  回款计划和还款计划平衡 扣除
     */
    private BigDecimal factservicefee = BigDecimal.ZERO;
    /**
     * 利息总和
     */
    private BigDecimal interestamount = BigDecimal.ZERO;



    protected Project()
    {

    }


    public BigDecimal getInvestmentedamount() {
        return investmentedamount;
    }

    public void setInvestmentedamount(BigDecimal investmentedamount) {
        this.investmentedamount = investmentedamount;
    }

    public int getId() {

        return id;
    }

    public boolean isIspushtojinpingmei() {
        return ispushtojinpingmei;
    }

    public void setIspushtojinpingmei(boolean ispushtojinpingmei) {
        this.ispushtojinpingmei = ispushtojinpingmei;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMinbidamount() {
        return minbidamount;
    }

    public void setMinbidamount(BigDecimal minbidamount) {
        this.minbidamount = minbidamount;
    }

    /**
     * 投标进度
     *
     * @return
     */
    public BigDecimal getProgressPercent() {
        if (this.amount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return this.investmentedamount.divide(this.amount, 4, BigDecimal.ROUND_DOWN);
    }



}

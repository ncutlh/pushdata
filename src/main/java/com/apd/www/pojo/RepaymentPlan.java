package com.apd.www.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Liuhong on 2015/9/25.
 */
public class RepaymentPlan {

    private int repaymentplanid;
    private int projectid;
    private int investmentid;
    private int projectrepaymentplanid;
    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal interestamount = BigDecimal.ZERO;
    private BigDecimal principalamount = BigDecimal.ZERO;
    private String plantype;
    private Date planpayat;
    private String paytype;

    private String status;
    /**
     * 债务人ID
     */
    private Integer debtoruserid;
    /**
     * 债权人ID
     */
    private Integer creditoruserid;
    /**
     * 实际支付人ID
     */
    private Integer payuserid;
    /**
     * 支付时间
     */
    private Date payat;
    private Integer freezeby;
    /**
     * 融资方是否还款
     */
    private boolean borrowerpaied;
    /**
     * 是否已发送消息
     */
    private boolean issendpaymessage;
    /**
     * 融资方还款时间
     */
    private Date borrowerpayat;
    private boolean isdealsnapshot;
    private String plancategory;

    private String paychannel;
    //转账操作控制状态
    private String payStatus;

    public int getRepaymentplanid() {
        return repaymentplanid;
    }

    public void setRepaymentplanid(int repaymentplanid) {
        this.repaymentplanid = repaymentplanid;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public int getInvestmentid() {
        return investmentid;
    }

    public void setInvestmentid(int investmentid) {
        this.investmentid = investmentid;
    }

    public int getProjectrepaymentplanid() {
        return projectrepaymentplanid;
    }

    public void setProjectrepaymentplanid(int projectrepaymentplanid) {
        this.projectrepaymentplanid = projectrepaymentplanid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getInterestamount() {
        return interestamount;
    }

    public void setInterestamount(BigDecimal interestamount) {
        this.interestamount = interestamount;
    }

    public BigDecimal getPrincipalamount() {
        return principalamount;
    }

    public void setPrincipalamount(BigDecimal principalamount) {
        this.principalamount = principalamount;
    }

    public String getPlantype() {
        return plantype;
    }

    public void setPlantype(String plantype) {
        this.plantype = plantype;
    }

    public Date getPlanpayat() {
        return planpayat;
    }

    public void setPlanpayat(Date planpayat) {
        this.planpayat = planpayat;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDebtoruserid() {
        return debtoruserid;
    }

    public void setDebtoruserid(Integer debtoruserid) {
        this.debtoruserid = debtoruserid;
    }

    public Integer getCreditoruserid() {
        return creditoruserid;
    }

    public void setCreditoruserid(Integer creditoruserid) {
        this.creditoruserid = creditoruserid;
    }

    public Integer getPayuserid() {
        return payuserid;
    }

    public void setPayuserid(Integer payuserid) {
        this.payuserid = payuserid;
    }

    public Date getPayat() {
        return payat;
    }

    public void setPayat(Date payat) {
        this.payat = payat;
    }

    public Integer getFreezeby() {
        return freezeby;
    }

    public void setFreezeby(Integer freezeby) {
        this.freezeby = freezeby;
    }

    public boolean isBorrowerpaied() {
        return borrowerpaied;
    }

    public void setBorrowerpaied(boolean borrowerpaied) {
        this.borrowerpaied = borrowerpaied;
    }

    public boolean isIssendpaymessage() {
        return issendpaymessage;
    }

    public void setIssendpaymessage(boolean issendpaymessage) {
        this.issendpaymessage = issendpaymessage;
    }

    public Date getBorrowerpayat() {
        return borrowerpayat;
    }

    public void setBorrowerpayat(Date borrowerpayat) {
        this.borrowerpayat = borrowerpayat;
    }

    public boolean isIsdealsnapshot() {
        return isdealsnapshot;
    }

    public void setIsdealsnapshot(boolean isdealsnapshot) {
        this.isdealsnapshot = isdealsnapshot;
    }

    public String getPlancategory() {
        return plancategory;
    }

    public void setPlancategory(String plancategory) {
        this.plancategory = plancategory;
    }

    public String getPaychannel() {
        return paychannel;
    }

    public void setPaychannel(String paychannel) {
        this.paychannel = paychannel;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}

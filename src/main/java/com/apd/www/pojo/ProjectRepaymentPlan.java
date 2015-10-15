package com.apd.www.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by liuhong on 15/10/14.
 */
@Entity
public class ProjectRepaymentplan {

    @Id
    @Column(name = "projectrepaymentplanid")
    private int projectrepaymentplanid;

    private int projectid;

    private Date planpayat;

    private BigDecimal amount = BigDecimal.ZERO;

    private String status;

    private String plantype;

    private String plancategory;

    private Date freezefinishat;

    private Integer freezeby;

    private boolean issendpaymessage;

    private boolean isdealsnapshot;


    public int getProjectrepaymentplanid() {
        return projectrepaymentplanid;
    }

    public void setProjectrepaymentplanid(int projectrepaymentplanid) {
        this.projectrepaymentplanid = projectrepaymentplanid;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public Date getPlanpayat() {
        return planpayat;
    }

    public void setPlanpayat(Date planpayat) {
        this.planpayat = planpayat;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlantype() {
        return plantype;
    }

    public void setPlantype(String plantype) {
        this.plantype = plantype;
    }

    public String getPlancategory() {
        return plancategory;
    }

    public void setPlancategory(String plancategory) {
        this.plancategory = plancategory;
    }

    public Date getFreezefinishat() {
        return freezefinishat;
    }

    public void setFreezefinishat(Date freezefinishat) {
        this.freezefinishat = freezefinishat;
    }

    public Integer getFreezeby() {
        return freezeby;
    }

    public void setFreezeby(Integer freezeby) {
        this.freezeby = freezeby;
    }

    public boolean isIssendpaymessage() {
        return issendpaymessage;
    }

    public void setIssendpaymessage(boolean issendpaymessage) {
        this.issendpaymessage = issendpaymessage;
    }

    public boolean isIsdealsnapshot() {
        return isdealsnapshot;
    }

    public void setIsdealsnapshot(boolean isdealsnapshot) {
        this.isdealsnapshot = isdealsnapshot;
    }

}

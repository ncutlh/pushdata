package com.apd.www.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by laintime on 15/5/8.
 */
@Entity
public class Investment {
    @Id
    @Column(name = "investmentid")
    private int id;

    private int investoruserid;

    private Double amount;

    private Date createat;

    private int projectid;

    private String status;

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Investment()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvestoruserid() {
        return investoruserid;
    }

    public void setInvestoruserid(int investoruserid) {
        this.investoruserid = investoruserid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreateat() {
        return createat;
    }

    public void setCreateat(Date createat) {
        this.createat = createat;
    }
}

package com.apd.www.pojo;

import javax.persistence.Column;
import java.util.List;

/**
 * Created by laintime on 15/5/8.
 */
public class BasePojo<T> {


    private int totalPage;
    private int currentPage;
    private int totalCount;
    private double totalAmount;
    private List<T> borrowList;


    @Column(name = "ProjectName")
    private String title;

    @Column(name = "InterestRate")
    private String interestRate;


    private int deadline;
    private String deadlineunit;

    private double reward;
    private String type;
    private int repaymentType;



    private String province;

    private String city;
    private String username;


    private String useravatarUrl;
    private String amountuseddesc;
    private double revenue;

    private String loanUrl;
    private String successtime;

    private String publishtime;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<T> getBorrowList() {
        return borrowList;
    }

    public void setBorrowList(List<T> borrowList) {
        this.borrowList = borrowList;
    }
}

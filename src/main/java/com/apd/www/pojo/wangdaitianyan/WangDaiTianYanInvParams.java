package com.apd.www.pojo.wangdaitianyan;

/**
 * Created by Liuhong on 2015/8/4.
 */
public class WangDaiTianYanInvParams {

    private Integer id;//标的项目编号同借款标编号
    private String useraddress;//用户所在地",
    private String link;//http://www.platform.com/loans/id.html?loanId=170647",
    private String username; // 投标人的用户名称
    private Integer userid; // 投标人的用户编号/ID
    private String type;//投标方式例如：手动、自动
    private Integer money;// 投标金额
    private Integer account;// 有效金额：投标金额实际生效部分
    private String status;// 投标状态例如：成功、部分成功、失败
    private String add_time;// 投标时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}

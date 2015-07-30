package com.apd.www.pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Liuhong on 2015/7/29.
 */
public class ZhongNiuParams {
    private String	    pid;//是	项目id,唯一
    private String	    name;//是	项目全名称
    private String	    url;//是	项目url地址
    private int	        type;//是	1:票据标;2:抵押标;3:担保标;4:保理标;5:融租标;6:信用标;7:债权标;
    private Float	    yield;//是	年化利率（单位：%），精确到小数点2位
    private Float	    duration;//是	投资期限（单位：月），精确到小数点2位
    private int	        repaytype;//是	1 按月付息 到期还本 2 按季付息，到期还本3 每月等额本息  4 到期还本息 5 按周等额本息还款 6按周付息，到期还本 7 按天到期还款 8按季分期还款 9 其他
    private int	        guaranttype;//是	0 无担保 1 本息担保 2 本金担保
    private Long	    threshold;//是	起投金额，单位元
    private int	        status;//是	项目状态（0：预投标，1：投标中，2：投标结束）
    private Long	    amount;//是	目标金额，单位元
    private Long	    amounted;//是	已募资金额，单位元
    private Float	    progress;//是	进度（单位：%）
    private List<Map<String,String>>          detail;//是	详细信息（包括标题-title，内容-content，图片-image）（不可添加html标签，可多个重复）
                                     //   限制：detail json数组长度不得超过30（超过数据将舍弃）
                                     //   排版顺序为数组顺序
    private String        startdate;//是	投标开始日期
    private String	    enddate  ;//是	投标结束日期
    private String	    publishtime;//是	项目发布时间

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Float getYield() {
        return yield;
    }

    public void setYield(Float yield) {
        this.yield = yield;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public int getRepaytype() {
        return repaytype;
    }

    public void setRepaytype(int repaytype) {
        this.repaytype = repaytype;
    }

    public int getGuaranttype() {
        return guaranttype;
    }

    public void setGuaranttype(int guaranttype) {
        this.guaranttype = guaranttype;
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getAmounted() {
        return amounted;
    }

    public void setAmounted(Long amounted) {
        this.amounted = amounted;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }

    public List<Map<String,String>> getDetail() {
        return detail;
    }

    public void setDetail(List<Map<String,String>> detail) {
        this.detail = detail;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }
}

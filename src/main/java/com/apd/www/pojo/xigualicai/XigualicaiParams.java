package com.apd.www.pojo.xigualicai;

/**
 * Created by Liuhong on 2015/8/19.
 */
public class XigualicaiParams {

    //平台名称
    private String platformName;
    //系列名称
    private String creditSeriesName;//车易融和新易融
    //系列ID
    private Integer creditSeriesId;
    //产品名称
    private String productName;
    //产品ID
    private String productCode;
    //募集金额
    private Integer totalInvestment;//	按照元计算，如果单位为万换算成元	1000000
    //产品收益
    private Double annualRevenueRate;//%换算成小数格式	0.0589
    //产品周期
    private Integer loanLifePeriod;//输入月份值或者天数值，如果同时有月份和天的值，全部转换成天	20
    //周期类型
    private String loanLifeType;//	loanLifeTypeEnu	Y	输入周期的单位，天/月	天
    //付息方式
    private String interestPaymentType;//等额本息
    //担保方
    private String guaranteeInsitutions;//深圳平安聚鑫资产管理有限公司
    //产品状态
    private String onlineState;//	onlineStateEnu	Y	准备/在售/审核中/还款中/取消/还款完成	在售
    //募集进度
    private Double scale;//%换算成小数格式	0.731
    //发布时间
    private String publishDate;//格式用YYYY-MM-DD HH:MM:SS	2015/06/30 09:33:18
    //成立时间
    private String establishmentDate;//格式用YYYY-MM-DD HH:MM:SS          产品满标的时间	2015/07/02 21:14:57
    //产品到期日
    private String expireDate;//格式用YYYY-MM-DD HH:MM:SS          产品到期时间	2016/07/02 21:14:57
    //奖励利息
    private Double rewardRate;//%换算成小数格式	0.02
    // 投资人次
    private Integer investTimes;//
    //产品URL
    private String productURL;//http://list.lufax.com/list/productDetail?productId=1649768&lufax_ref=http%3A%2F%2Flist.lufax.com%2Flist%2Fanyi

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getCreditSeriesName() {
        return creditSeriesName;
    }

    public void setCreditSeriesName(String creditSeriesName) {
        this.creditSeriesName = creditSeriesName;
    }

    public Integer getCreditSeriesId() {
        return creditSeriesId;
    }

    public void setCreditSeriesId(Integer creditSeriesId) {
        this.creditSeriesId = creditSeriesId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(Integer totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public Double getAnnualRevenueRate() {
        return annualRevenueRate;
    }

    public void setAnnualRevenueRate(Double annualRevenueRate) {
        this.annualRevenueRate = annualRevenueRate;
    }

    public Integer getLoanLifePeriod() {
        return loanLifePeriod;
    }

    public void setLoanLifePeriod(Integer loanLifePeriod) {
        this.loanLifePeriod = loanLifePeriod;
    }

    public String getLoanLifeType() {
        return loanLifeType;
    }

    public void setLoanLifeType(String loanLifeType) {
        this.loanLifeType = loanLifeType;
    }

    public String getInterestPaymentType() {
        return interestPaymentType;
    }

    public void setInterestPaymentType(String interestPaymentType) {
        this.interestPaymentType = interestPaymentType;
    }

    public String getGuaranteeInsitutions() {
        return guaranteeInsitutions;
    }

    public void setGuaranteeInsitutions(String guaranteeInsitutions) {
        this.guaranteeInsitutions = guaranteeInsitutions;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(String establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Double getRewardRate() {
        return rewardRate;
    }

    public void setRewardRate(Double rewardRate) {
        this.rewardRate = rewardRate;
    }

    public Integer getInvestTimes() {
        return investTimes;
    }

    public void setInvestTimes(Integer investTimes) {
        this.investTimes = investTimes;
    }

    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }
}

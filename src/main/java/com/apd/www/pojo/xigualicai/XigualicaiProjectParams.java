package com.apd.www.pojo.xigualicai;

/**
 * Created by Liuhong on 2015/8/20.
 */
public class XigualicaiProjectParams {

    //产品ID
    private String productCode;
    //产品状态
    private String onlineState;//	onlineStateEnu	Y	准备/在售/满标/审核中/还款中/取消/还款完成	在售
    //募集进度
    private Double scale;//	double	Y	%换算成小数格式	0.731
    //状态变更时间
    private String statusChangeDate;//	string	Y	格式用YYYY-MM-DD HH:MM	2015/6/30 16:03
    //投资人次
    private Integer investTimes;//	int	Y		30
    //产品URL
    private String productURL;//	string	Y		http://list.lufax.com/list/productDetail?productId=1649768&lufax_ref=http%3A%2F%2Flist.lufax.com%2Flist%2Fanyi

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState;
    }


    public String getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(String statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
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

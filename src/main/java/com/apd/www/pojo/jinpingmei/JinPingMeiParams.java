package com.apd.www.pojo.jinpingmei;

/**
 * Created by Liuhong on 2015/6/23.
 */
public class JinPingMeiParams {
    private String  name;//	string	是	产品名称
    private String  rate;//	float	是	年化利率	例如：年化利率为 19%，则返回19
    private String  speed;//	float	是	投资进度	例如：投资进度为 80.8%，则返回 80.8
    private String  sum_scale;//	float	是	总金额
    private String  day;//	int	是	期限
    private String  date_type;//	int	是	期限单位	返回1表示单位为天； 返回2表示单位为月
    private String  surplus;//	float	是	剩余可投资金额
    private String  pattern;//	string	是	还款方式	例如：等额本息，等额本金
    private String  cps_from;//	string	是	接入商平台名称	例如：人人贷，陆金所，爱投资，钱多多
    private String  cps_proid;//	int	是	接入商产品id
    private String  pro_url;//	string	是	产品标的地址	例如：http://域名 /project/detail/472?referer=jpm，蓝色字体部分由贵方自己定义，只需能证明是从我方带过去的即可。
    private String  m_pro_url;//	string	否	手机端产品标的地址	格式同上，如没有手机端，请跟许轩铖（产品）声明，QQ：248024860
    private String  ensure;//	string	是	资金保障方式	例如：抵押，全额担保等

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getSum_scale() {
        return sum_scale;
    }

    public void setSum_scale(String sum_scale) {
        this.sum_scale = sum_scale;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate_type() {
        return date_type;
    }

    public void setDate_type(String date_type) {
        this.date_type = date_type;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getCps_from() {
        return cps_from;
    }

    public void setCps_from(String cps_from) {
        this.cps_from = cps_from;
    }

    public String getCps_proid() {
        return cps_proid;
    }

    public void setCps_proid(String cps_proid) {
        this.cps_proid = cps_proid;
    }

    public String getPro_url() {
        return pro_url;
    }

    public void setPro_url(String pro_url) {
        this.pro_url = pro_url;
    }

    public String getM_pro_url() {
        return m_pro_url;
    }

    public void setM_pro_url(String m_pro_url) {
        this.m_pro_url = m_pro_url;
    }

    public String getEnsure() {
        return ensure;
    }

    public void setEnsure(String ensure) {
        this.ensure = ensure;
    }
}

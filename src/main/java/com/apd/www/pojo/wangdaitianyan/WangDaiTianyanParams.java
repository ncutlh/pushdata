package com.apd.www.pojo.wangdaitianyan;

import java.util.List;

/**
 * Created by Liuhong on 2015/8/4.
 */
public class WangDaiTianyanParams {
    /**
     *数字1 标识成功，否则是请求失败
     */
     String result_code;
    /**
     * 返回的错误或成功的文本消息
     */
     String result_msg;
    /**
     * 搜索到的页数
     * (不做分页总页为1,分页从1 开始向上取整,不要给小数)
     */
    String page_count;
    /**
     * 当前请求的页数
     */
    String page_index;

     List loans;


    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getPage_count() {
        return page_count;
    }

    public void setPage_count(String page_count) {
        this.page_count = page_count;
    }

    public String getPage_index() {
        return page_index;
    }

    public void setPage_index(String page_index) {
        this.page_index = page_index;
    }

    public List getLoans() {
        return loans;
    }

    public void setLoans(List loans) {
        this.loans = loans;
    }
}

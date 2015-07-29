package com.apd.www.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Liuhong on 2015/7/6.
 */
@Entity
public class ProjectChannel {

    @Id
    @Column(name = "PushId")
    private Integer pushid;

    @Column(name = "ProjectID")
    private Integer projectid;
    @Column(name = "ChannelId")
    private Integer channelid;
    private Date createAt;
    @Column(name = "IsPushed")
    private boolean ispushed;
    private Date updateAt;


    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getPushid() {

        return pushid;
    }

    public void setPushid(Integer pushid) {
        this.pushid = pushid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getChannelid() {
        return channelid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }

    public boolean isIspushed() {
        return ispushed;
    }

    public void setIspushed(boolean ispushed) {
        this.ispushed = ispushed;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}

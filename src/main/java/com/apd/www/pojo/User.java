package com.apd.www.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by laintime on 15/4/22.
 */
@Entity
public class User implements Serializable {
    @Id
    @Column(name = "userid")
    private Integer id;

    @Column(name ="username")
    private String userName;

    @Column(name ="registerat")
    private Date registerat;

    @Column(name ="location")
    private String location;

    protected User()
    {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer userId) {
        this.id = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getRegisterat() {
        return registerat;
    }

    public void setRegisterat(Date registerat) {
        this.registerat = registerat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

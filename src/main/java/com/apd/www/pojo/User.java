package com.apd.www.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

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
}

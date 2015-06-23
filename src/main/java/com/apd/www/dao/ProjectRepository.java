package com.apd.www.dao;


import com.apd.www.pojo.Project;
import com.apd.www.pojo.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by laintime on 15/4/23.
 */
public interface ProjectRepository extends CrudRepository<Project,Integer> {


}

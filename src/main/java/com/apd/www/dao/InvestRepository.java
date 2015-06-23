package com.apd.www.dao;


import com.apd.www.pojo.Investment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by laintime on 15/4/23.
 */
public interface InvestRepository extends CrudRepository<Investment,Integer> {


}

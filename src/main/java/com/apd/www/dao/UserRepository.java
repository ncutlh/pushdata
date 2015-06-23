package com.apd.www.dao;

import com.apd.www.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

/**
 * Created by laintime on 15/4/22.
 */
public interface UserRepository  extends CrudRepository<User,Integer> {

   Page<User> findAll(Pageable pageable);

    User findUserById(int i);
}

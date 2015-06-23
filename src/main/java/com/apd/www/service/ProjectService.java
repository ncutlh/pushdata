package com.apd.www.service;

import com.apd.www.pojo.Project;

import java.text.ParseException;
import java.util.List;

/**
 * Created by laintime on 15/4/23.
 */
public interface ProjectService {
    Project findById(int i);

    List getProjectList(String date, String page, String pageSize) throws ParseException;

    List getProjectListInfo(String date) throws ParseException;
}

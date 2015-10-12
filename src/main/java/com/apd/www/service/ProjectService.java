package com.apd.www.service;

import com.apd.www.pojo.Project;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;

/**
 * Created by laintime on 15/4/23.
 */
public interface ProjectService {

    Project findById(int i);

    public List<Project> findByIds(String ids);

    List getProjectList(String date, String page, String pageSize) throws ParseException;

    List getProjectListInfo(String date) throws ParseException;

    public List<Project> getJpmOpenProjectList() throws ParseException;

    public List<Project> getZhongniuProjectList() throws ParseException;


    public List<Project> getWDTYProjectList(Integer status,String time_from,String time_to,Integer page_size,Integer page_index) throws ParseException;

    public  Long getWDTYProjectListCount(Integer status,String time_from,String time_to) throws ParseException;


    public List<Project> getYong360ProjectList(Integer page_size,Integer page_index) throws ParseException;

    public Long getYong360ProjectListCount();

    public List<Project> getXigualicaiProjectList();

    public List<Project> getAllowinvestatProjectList();

    public List<Project> getProjectListByTime(String startDate,String endDate) throws ParseException;
}

package com.apd.www.service.impl;

import com.apd.www.dao.ProjectRepository;

import com.apd.www.pojo.Project;
import com.apd.www.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laintime on 15/4/23.
 */
@Component
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;


    @PersistenceContext
    private EntityManager em;

    @Override
    public Project findById(int id) {
        return projectRepository.findOne(id);
    }

    @Override
    public List<Project> getProjectList(String date, String page, String pageSize) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TypedQuery query = em.createQuery("select c from Project c where c.dealdate between ?1 and ?2 and projectstatus in ('FINISHED','SETTLED','CLEARED','ARCHIVED')", Project.class);
        query.setParameter(1,sdf.parse(date+" 00:00:00"));
        query.setParameter(2,sdf.parse(date+" 23:59:59"));
        query.setFirstResult((Integer.parseInt(page)-1)*Integer.parseInt(pageSize));
        query.setMaxResults(Integer.parseInt(pageSize));
        return query.getResultList();
    }

    @Override
    public List getProjectListInfo(String date) throws ParseException {

        List list=new ArrayList();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TypedQuery query = em.createQuery("select count (1) from Project c where c.dealdate between ?1 and ?2 and projectstatus in ('FINISHED','SETTLED','CLEARED','ARCHIVED')",Long.class);
        query.setParameter(1, sdf.parse(date + " 00:00:00"));
        query.setParameter(2, sdf.parse(date + " 23:59:59"));
        list.add(query.getSingleResult());
        TypedQuery query1 = em.createQuery("select sum(amount) from Project c where c.dealdate between ?1 and ?2 and projectstatus in ('FINISHED','SETTLED','CLEARED','ARCHIVED')", BigDecimal.class);
        query1.setParameter(1, sdf.parse(date + " 00:00:00"));
        query1.setParameter(2, sdf.parse(date + " 23:59:59"));
        list.add(query1.getSingleResult());
        return list;
    }



    @Override
    public List<Project> getJpmOpenProjectList() throws ParseException{
//        TypedQuery query = em.createQuery("select c from Project c where c.ispushtojinpingmei=1 and  c.projectstatus ='OPENED'", Project.class);
        TypedQuery query = em.createQuery("select c from Project c , ProjectChannel p where c.id = p.projectid and ispushed=0 and channelid=5", Project.class);
        return query.getResultList();
    }


    @Override
    public List<Project> getZhongniuProjectList() throws ParseException {
        TypedQuery query = em.createQuery("select c from Project c , ProjectChannel p where c.id = p.projectid and ispushed=0 and channelid=6", Project.class);
        return query.getResultList();
    }

    @Override
    public List<Project> getWDTYProjectList(Integer status,String time_from,String time_to,Integer page_size,Integer page_index) throws ParseException{
        String sql="select c from Project c , ProjectChannel p " +
                " where c.id = p.projectid and ispushed=0 and channelid=7";
        if(status==0){
            sql += " and c.createAt between ?1 and ?2 and projectstatus in ('SCHEDULED','OPENED')" +
                    " limit ?3,?4";
        }else if(status==1){
            sql += " and c.dealdate between ?1 and ?2 and projectstatus in ('FINISHED','SETTLED','CLEARED','ARCHIVED')" +
                   " limit ?3,?4";
        }else{
            sql += " and c.createat between ?1 and ?2 and projectstatus in ('SCHEDULED','OPENED','FINISHED','SETTLED','CLEARED','ARCHIVED')"+
                    " limit ?3,?4";
        }
        TypedQuery query = em.createQuery(sql, Project.class);
        query.setParameter(1, time_from);
        query.setParameter(2, time_to);
        query.setParameter(3, page_index);
        query.setParameter(4, page_index+page_size);
        return query.getResultList();
    }

    @Override
    public Integer getWDTYProjectListCount(Integer status, String time_from, String time_to) throws ParseException {
        String sql="select c from Project c , ProjectChannel p " +
                " where c.id = p.projectid and ispushed=0 and channelid=7";
        if(status==0){
            sql += " and c.createAt between ?1 and ?2 and projectstatus in ('SCHEDULED','OPENED')";
        }else if(status==1){
            sql += " and c.dealdate between ?1 and ?2 and projectstatus in ('FINISHED','SETTLED','CLEARED','ARCHIVED')";
        }else{
            sql += " and c.createat between ?1 and ?2 and projectstatus in ('SCHEDULED','OPENED','FINISHED','SETTLED','CLEARED','ARCHIVED')";
        }
        TypedQuery query = em.createQuery(sql, Project.class);
        query.setParameter(1, time_from);
        query.setParameter(2, time_to);
        return (Integer)query.getSingleResult();
    }
}

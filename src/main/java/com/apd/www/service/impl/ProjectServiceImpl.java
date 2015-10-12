package com.apd.www.service.impl;

import com.apd.www.dao.ProjectRepository;

import com.apd.www.pojo.Project;
import com.apd.www.service.ProjectService;
import com.apd.www.utils.DateUtils;
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
import java.util.Date;
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
    public List<Project> findByIds(String ids){
        String sql = "select c from Project c where c.id in ("+ids+")";
        TypedQuery query = em.createQuery(sql, Project.class);
        return query.getResultList();
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
        String sql ="select c from Project c , ProjectChannel p " +
                    " where c.id = p.projectid and ispushed=0 and channelid=5 and c.projectstatus ='OPENED'" +
                    " and allowinvestat <=?1";
        TypedQuery query = em.createQuery(sql, Project.class);
        query.setParameter(1, DateUtils.getDateLong(new Date()));
        return query.getResultList();
    }


    @Override
    public List<Project> getZhongniuProjectList() throws ParseException {
        String sql ="select c from Project c , ProjectChannel p where c.id = p.projectid and ispushed=0 and channelid=6 and projectstatus in ('SCHEDULED','OPENED')" +
                " and allowinvestat <=?1";
        TypedQuery query = em.createQuery(sql, Project.class);
        query.setParameter(1, DateUtils.getDateLong(new Date()));
        return query.getResultList();
    }

    @Override
    public List<Project> getWDTYProjectList(Integer status,String time_from,String time_to,Integer page_size,Integer page_index) throws ParseException{
        String sql="select c from Project c where c.id !=2809 and  c.id !=3040 and c.allowinvestat between ?1 and ?2";
        if(status==0){
            sql += " and projectstatus in ('SCHEDULED','OPENED')";
        }else if(status==1){
            sql += " and projectstatus in ('FINISHED','SETTLED','CLEARED','ARCHIVED')";
        }else{
            sql += " and projectstatus in ('SCHEDULED','OPENED','FINISHED','SETTLED','CLEARED','ARCHIVED')";
        }
        TypedQuery query = em.createQuery(sql, Project.class);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        query.setParameter(1, sdf.parse(time_from));
        query.setParameter(2, sdf.parse(time_to));
        query.setFirstResult(page_size*(page_index-1));
        query.setMaxResults(page_size);
        return query.getResultList();
    }

    @Override
    public Long getWDTYProjectListCount(Integer status, String time_from, String time_to) throws ParseException {
        String sql="select count(1) from Project c where c.id !=2809 and  c.id !=3040  and c.allowinvestat between ?1 and ?2";
        if(status==0){
            sql += " and projectstatus in ('SCHEDULED','OPENED')";
        }else if(status==1){
            sql += " and projectstatus in ('FINISHED','SETTLED','CLEARED','ARCHIVED')";
        }else{
            sql += " and projectstatus in ('SCHEDULED','OPENED','FINISHED','SETTLED','CLEARED','ARCHIVED')";
        }
        TypedQuery query = em.createQuery(sql,Long.class);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        query.setParameter(1, sdf.parse(time_from));
        query.setParameter(2, sdf.parse(time_to));
        return (Long)query.getSingleResult();
    }

    @Override
    public List<Project> getYong360ProjectList(Integer page_size, Integer page_index) throws ParseException {
        String sql="select c from Project c , ProjectChannel p " +
                " where c.id = p.projectid and ispushed=0 and channelid=8"+
                " and projectstatus in ('SCHEDULED','OPENED') " +
                " and allowinvestat <=?1";
        TypedQuery query = em.createQuery(sql, Project.class);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        query.setParameter(1, sdf.format(new Date()));
        query.setFirstResult(page_size*(page_index-1));
        query.setMaxResults(page_size);
        return query.getResultList();
    }

    @Override
    public Long getYong360ProjectListCount() {
        String sql="select count(1) from Project c , ProjectChannel p " +
                " where c.id = p.projectid and ispushed=0 and channelid=8"+
                " and projectstatus in ('SCHEDULED','OPENED')" +
                " and allowinvestat <=?1";
        TypedQuery query = em.createQuery(sql,Long.class);
        query.setParameter(1, DateUtils.getDateLong(new Date()));
        return (Long)query.getSingleResult();
    }


    @Override
    public List<Project> getXigualicaiProjectList(){
        String sql="select c from Project c , ProjectChannel p " +
                " where c.id = p.projectid and ispushed=0 and channelid=9"+
                " and projectstatus in ('SCHEDULED','OPENED') " +
                " and allowinvestat <=?1";
        TypedQuery query = em.createQuery(sql, Project.class);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        query.setParameter(1, sdf.format(new Date()));
        return query.getResultList();
    }


    @Override
    public List<Project> getAllowinvestatProjectList(){
        String sql="select c from Project c " +
                " where projectstatus in ('SCHEDULED','OPENED') " +
                " and allowinvestat <= ?1"+
                " and biddeadline >= ?1";
        TypedQuery query = em.createQuery(sql, Project.class);
        query.setParameter(1, new Date());
        return query.getResultList();
    }


    @Override
    public List<Project> getProjectListByTime(String startDate, String endDate) throws ParseException {
        String sql="select c from Project c where c.id !=2809 and  c.id !=3040 and c.allowinvestat between ?1 and ?2";
        TypedQuery query = em.createQuery(sql, Project.class);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        query.setParameter(1, sdf.parse(startDate));
        query.setParameter(2, sdf.parse(endDate));

        return query.getResultList();
    }
}

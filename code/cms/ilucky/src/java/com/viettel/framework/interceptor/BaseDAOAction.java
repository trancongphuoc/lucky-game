package com.viettel.framework.interceptor;

//
//
//package com.viettel.framework.interceptor.DAO;
//
//import com.opensymphony.xwork2.ActionSupport;
//import com.viettel.common.util.ArgChecker;
//import com.viettel.database.BO.BasicBO;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.struts2.ServletActionContext;
//import org.hibernate.*;
//import org.hibernate.criterion.Restrictions;
//
//public class BaseDAOAction extends ActionSupport
//{
//
//    public BaseDAOAction()
//    {
//    }
//
//    public Exception getException()
//    {
//        return exception;
//    }
//
//    public void setException(Exception exception)
//    {
//        this.exception = exception;
//    }
//
//    public Session getSession()
//    {
//        return session;
//    }
//
//    public void setSession(Session session)
//    {
//        this.session = session;
//    }
//
//    public void save(BasicBO objectToSave)
//        throws Exception
//    {
//        Session session = getSession();
//        ArgChecker.denyNull(objectToSave);
//        session.save(objectToSave);
//    }
//
//    public void update(BasicBO objectToUpdate)
//        throws Exception
//    {
//        Session session = getSession();
//        ArgChecker.denyNull(objectToUpdate);
//        session.update(objectToUpdate);
//    }
//
//    public void saveOrUpdate(BasicBO objectToSaveOrUpdate)
//        throws Exception
//    {
//        Session session = getSession();
//        ArgChecker.denyNull(objectToSaveOrUpdate);
//        session.saveOrUpdate(objectToSaveOrUpdate);
//    }
//
//    public void delete(BasicBO objectToDelete)
//        throws Exception
//    {
//        Session session = getSession();
//        ArgChecker.denyNull(objectToDelete);
//        session.delete(objectToDelete);
//    }
//
//    public void refresh(BasicBO objectToRefresh)
//        throws Exception
//    {
//        Session session = getSession();
//        session.refresh(objectToRefresh);
//    }
//
//    public BasicBO get(Object id, String strClassHandle)
//        throws Exception
//    {
//        Session session = getSession();
//        BasicBO instance = (BasicBO)session.get(strClassHandle, (Serializable)id);
//        if(instance != null)
//            session.refresh(instance);
//        return instance;
//    }
//
//    public BasicBO read(String strClassHandle, String idName, Object id)
//        throws Exception
//    {
//        Session session = getSession();
//        List lstObj = session.createCriteria(strClassHandle).add(Restrictions.eq(idName, id)).list();
//        BasicBO obj = null;
//        if(lstObj.size() > 0)
//            obj = (BasicBO)((BasicBO)lstObj.get(0)).clone();
//        return obj;
//    }
//
//    public List getAll(String strClassHandle)
//    {
//        Session session = getSession();
//        String queryString = (new StringBuilder()).append("from ").append(strClassHandle).toString();
//        Query queryObject = session.createQuery(queryString);
//        return queryObject.list();
//    }
//
//    public List findByProperty(String strClassHandle, String propertyName, Object value)
//    {
//        List lstReturn = new ArrayList();
//        String queryString = (new StringBuilder()).append("from ").append(strClassHandle).append(" as model where model.").append(propertyName).append("= ?").toString();
//        Query queryObject = getSession().createQuery(queryString);
//        queryObject.setParameter(0, value);
//        lstReturn = queryObject.list();
//        return lstReturn;
//    }
//
//    public List findByProperty(String strClassHandle, String propertyName, Object value, String orderClause)
//    {
//        List lstReturn = new ArrayList();
//        String queryString = (new StringBuilder()).append("from ").append(strClassHandle).append(" as model where model.").append(propertyName).append("= ? ").append(orderClause).toString();
//        Query queryObject = getSession().createQuery(queryString);
//        queryObject.setParameter(0, value);
//        lstReturn = queryObject.list();
//        return lstReturn;
//    }
//
//    public long getSequence(String sequenceName)
//        throws Exception
//    {
//        String strQuery = (new StringBuilder()).append("SELECT ").append(sequenceName).append(" .NextVal FROM Dual").toString();
//        Query queryObject = getSession().createSQLQuery(strQuery);
//        BigDecimal bigDecimal = (BigDecimal)queryObject.uniqueResult();
//        return bigDecimal.longValue();
//    }
//
//    public Date getSysdate()
//        throws Exception
//    {
//        String strQuery = "SELECT sysdate as system_datetime FROM Dual";
//        SQLQuery queryObject = getSession().createSQLQuery(strQuery);
//        queryObject.addScalar("system_datetime", Hibernate.TIMESTAMP);
//        Date sysdate = (Date)queryObject.uniqueResult();
//        return sysdate;
//    }
//
//    public HttpServletRequest getRequest()
//    {
//        return ServletActionContext.getRequest();
//    }
//
//    public HttpServletResponse getResponse()
//    {
//        return ServletActionContext.getResponse();
//    }
//
//    private Session session;
//    private Exception exception;
//}
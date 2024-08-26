package com.nh.repository;
import com.nh.bean.PO;
import com.nh.database.BeanUtil;
import com.nh.database.DB;
import com.nh.database.GenericMapper;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

public abstract class GenericDao<E extends PO> {
    protected final Logger log = Logger.getLogger(getClass());
    public static final int RECORD_PER_PAGE = 20;
			
    private Class<E> entityClass;
    protected RowMapper<E> mapper;
    
    protected GenericDao() {
        Class<?> superClazz = getClass();
        Type superType = superClazz.getGenericSuperclass();
        while (!(superType instanceof ParameterizedType)) {
            superClazz = superClazz.getSuperclass();
            superType = superClazz.getGenericSuperclass();
        }

        int paraIndex = 0;
        ParameterizedType genericSuperclass = (ParameterizedType) superType;
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[paraIndex++];
        mapper = new GenericMapper<>(entityClass);
    }

    public RowMapper<E> getMapper(){
    	return mapper;
    }

    public void setMapper(RowMapper<E> mapper) {
    	this.mapper = mapper;
    }
    
    /**
     * @author khangpn
     * <p>
     * Override this method to use for multi mappers
     * </p>
     * @return
     */
    public RowMapper<?> getNativeMapper() {
    	return null;
    }
    
    protected String getSqlSelect() {
    	String tableName = BeanUtil.getTableName(entityClass);
    	String sql = "SELECT * FROM " + tableName;
    	return sql;
    }
    
    protected String getSqlCount() {
    	String tableName = BeanUtil.getTableName(entityClass);
		String sql = "SELECT count(1) FROM " + tableName;
    	return sql;
    }
    
	public List<E> getList(int firstResult, int maxResults) {
		 String sql = getSqlSelect();
		 int start = firstResult;
		 int end = firstResult + maxResults;
		 sql = DB.addPagingSQL(sql, start, end);
		 List<E> list = (List<E>) DB.query(sql, getMapper());
		 return list;  
	}
	
    public List<E> getList(List<String> ids){

            String sql = getSqlSelect() + " WHERE " 
                            + BeanUtil.getIdColumnName(entityClass) + " IN (:param)";
            Map<String, List<String>> parameters = new HashMap<>();
            parameters.put("param", ids);

            List<E> list = DB.query(sql, parameters, getMapper());

            return list;
     }
	
    @SuppressWarnings("unchecked")
    public <T extends Serializable> List<T> getList(String sql, int firstResult, int maxResults, Class<T> cls, Object ... args) {
             int start = firstResult;
             int end = firstResult + maxResults;
             sql = DB.addPagingSQL(sql, start, end);
             List<T> views = (List<T>) DB.query(sql, args, getNativeMapper());
             return views;  
    }
    
    public List<E> getAll() {
    String sql = getSqlSelect();
    List<E> list = (List<E>) DB.query(sql, getMapper());
            return list;
    }
    
    @SuppressWarnings("unchecked")
    public boolean delete(E entity) {
    	return delete(entity.getId());
    }

    public boolean delete(String id) {
    	String tableName = BeanUtil.getTableName(entityClass);
    	String sql = "DELETE FROM " + tableName + " WHERE "+getKeyName()+" = ?";
    	int result = DB.update(sql, id);
    	return result > 0;
    }
    
    public Class<E> getEntityClass() {
        return entityClass;
    }
 
    /**
     * day la save new, tra ve la gia tri id cua entity, gia tri nay luon nam
     * vi tri dau tien
     * @author hadc
     * */
    @SuppressWarnings("unchecked")
    public String save(E entity) {
        String reVal = null;

        String sql = DB.getSqlInsert(entityClass);
        
        Object[] values = DB.getEntityValues(entity, DB.SQL_INSERT);

        int i = DB.update(sql, values);
        if(i > 0) {
                reVal = (String) values[0];
        }

        return reVal;
    }
    
    /**
     * insert ma khong can lay Sequence.getNextID, tang toc do hon so voi
     * insert thuong. Cai nay tuy theo nghiep vu ma chac chan co dung hay ko?
     * @author hadc
     * */
    public boolean saveOnly(E entity) {
//        String sql = DB.getSqlInsertOnly(entityClass);
        String sql = DB.getSqlInsert(entityClass);
        Object[] values = DB.getEntityValues(entity, DB.SQL_INSERTONLY);
        int count = DB.update(sql, values);
    	
    	return (count == 1);
    }
    
    @SuppressWarnings("unchecked")
	public E updateAndRefresh(E entity) {
    	boolean ok = update(entity);
    	
		if(ok){
			String id = entity.getId();
			return findById(id);
		} 
		
		return null;
	}
    
    /**
     * update cho nay bat buoc phai xem co phai la entity.isGenericMapper() hay
     * khong? cai nay mac dinh la fales, khi load bang GenericMaper se set la true
     * Vi neu ko load het du lieu (selec * from ..) thi khi update
     * du lieu se khong day du truong dan toi ghi de du lieu
     * @author hadc
     * */
    public boolean update(E entity) {
        boolean ok = false;
        if (entity.isGenericMapper()) {
            String sql = DB.getSqlUpdate(entityClass);
            Object[] values = DB.getEntityValues(entity, DB.SQL_UPDATE);
            int i = DB.update(sql, values);
            ok = i == 1;
        }

        return ok;
    }
    
    /**
     * 
     * */
    public void updateProperty(String id, String name,Serializable value){
    	String tableName = BeanUtil.getTableName(entityClass);
    	String idName = BeanUtil.getIdColumnName(entityClass);
    	String sql = "UPDATE " + tableName + " SET " + name + " = ? WHERE " 
    	+ idName + " = ?";
    	DB.update(sql, value, id);
    }
    
    
    public List<E> getList(String where,RowMapper<E> m){
    	String sql = getSqlSelect();
    	sql +=" WHERE  " + where;
    	List<E> list = (List<E>) DB.query(sql,m==null?mapper:m);
		return list;
    }
    
    
    public E findById(String id) {
        String idName = BeanUtil.getIdColumnName(entityClass);

        String sql = getSqlSelect();
        sql += " WHERE " + idName + " = ?";

        E e = DB.queryForObject(sql, mapper, id);
        return e;
    }
    
    public E findByColumName(String columnName, Object value) {
        String sql = getSqlSelect();
        sql += " WHERE " + columnName + " = ? and rownum <= 1";

        List<Object> values = new LinkedList<>();
        values.add(value);
        List<E> es = DB.query(sql,  values, mapper);
        if (es != null && !es.isEmpty()){
            return es.remove(0);
        }
        return null;
    }
    
    public E findByColumNames(List<String> columnNames, List<Object> values) {
        String sql = getSqlSelect();
        StringBuilder sb = new StringBuilder(sql);
        sb.append(" WHERE ");
        int i = 0;
        for (String columnName : columnNames) {
            if (i > 0) {
                sb.append(" AND ");
            }
            sb.append(columnName).append(" =? ");
            
            i++;        
        }
        List<E> es = DB.query(sb.toString(),  values, mapper);
        if (es != null && !es.isEmpty()){
            return es.remove(0);
        }
//        E e = DB.queryForObject(sb.toString(), mapper, values);
//        E e = DB.queryForObject(sb.toString(), mapper, values);
        return null;
    }
    
    public List<E> search(List<String> columnNames, List<Object> values) {
        String sql = getSqlSelect();
        StringBuilder sb = new StringBuilder(sql);
        sb.append(" WHERE ");
        int i = 0;
        for (String columnName : columnNames) {
            if (i > 0) {
                sb.append(" AND ");
            }
            sb.append(columnName).append(" =? ");
            
            i++;        
        }

        return DB.query(sb.toString(), mapper, values);
    }
    
    @SuppressWarnings("unchecked")
    public void refresh(E entity) {
        String i = entity.getId();
        entity = findById(i);
    }

    public Long getCount() {
            String sql = getSqlCount();
            Long count = DB.queryForLong(sql);
            return count;
    }

    public String getTableName(){
	return BeanUtil.getTableName(entityClass);
    }

   public String getKeyName(){
	   return BeanUtil.getIdColumnName(entityClass);
   }
  
   public int[] saveBatch(List<E> entities) {
	   int[] reValue = null;
	   String sql = DB.getSqlInsert(entityClass);
	   List<Object[]> params = new ArrayList<>();
	   Object[] entityValues = null;
	   for (E entity : entities) {
		   entityValues = DB.getEntityValues(entity, DB.SQL_INSERT);
		   params.add(entityValues);
	   }
	   
	   reValue = DB.batchUpdate(sql, params);
	   
	   return reValue;
   }
   
   public int[] saveBatchOnly(List<E> entities) {
	   int[] reValue = null;
	   String sql = DB.getSqlInsertOnly(entityClass);
	   List<Object[]> params = new ArrayList<>();
	   Object[] entityValues = null;
	   for (E entity : entities) {
		   entityValues = DB.getEntityValues(entity, DB.SQL_INSERTONLY);
		   params.add(entityValues);
	   }
	   
	   reValue = DB.batchUpdate(sql, params);
	   
	   return reValue;
   }
   
    public int[] updateBatch(List<E> entities) {
            int[] reValue = null;
            String sql = DB.getSqlUpdate(entityClass);
            List<Object[]> params = new ArrayList<>();
            Object[] entityValues = null;
            for (E entity : entities) {
                    entityValues = DB.getEntityValues(entity, DB.SQL_UPDATE);
                    params.add(entityValues);
            }

            reValue = DB.batchUpdate(sql, params);

            return reValue;
    }
   
   	private static final int SEARCH_NUMBER_COLUMN_DEFAULT = 2;
	
	private static final Map<Class<?>,String> searchSqlValues = 
			new HashMap<>();
	private static final Map<Class<?>,String> searchSqlCounts = 
					new HashMap<>();
	private static final Map<Class<?>,Integer> searchNumberParam = 
							new HashMap<>();
			
    private boolean initSearch(){
            boolean returnValue = false;
            if (searchSqlValues.containsKey(entityClass)) {
                    return true;
            }

            String sql = getSqlSelect();
            String sqlCount = getSqlCount();
            int number_column = 0;
            String whereClause = "";
            Class<?>[] clazzs = BeanUtil.getColumnTypes(entityClass);
            String[] searchColumnNames = getSearchColumns();
            if (searchColumnNames == null) {
                    searchColumnNames = BeanUtil.getColumnNames(entityClass);
            }

            int length = searchColumnNames.length;
            int max_column = getMaxSearchColumn();
            for (int i = 0; i < length; i++) {
                    Class<?> clazz = clazzs[i];
                    if(clazz.isAssignableFrom(String.class) 
                                    && (number_column < max_column)){
                            if(!whereClause.isEmpty()){
                                    whereClause += " or ";
                            }
                            returnValue = true;
                            whereClause += searchColumnNames[i] + " like ? ";
                            number_column++;
                    }

                    if (max_column == number_column) {
                            break;
                    }

            }

            if(!whereClause.isEmpty()){
                    sql += " WHERE " + whereClause;
                    sqlCount += " WHERE " + whereClause;
            }

            if(returnValue){
                    searchSqlValues.put(entityClass, sql);
                    searchNumberParam.put(entityClass, number_column);
                    searchSqlCounts.put(entityClass, sqlCount);
            }

            return returnValue;
    }
	
	private String getSearchValue(String searchValue){
		String val = searchValue;
		if(val == null || val.isEmpty()) {
			return val;
		}
		
		if(!val.startsWith("%")){
			val = "%" + val;
		}
		
		if(!val.endsWith("%")){
			val = val + "%";
		}
		
		return val;
	}
	
	public List<E> getList(int firstResult, int maxResults, String searchValue) {
		if (searchValue == null || searchValue.isEmpty()) {
			return getList(firstResult, maxResults);
		}
		String sql = searchSqlValues.get(entityClass);
		
		boolean loadOk = true;
		if(sql == null){
			loadOk = initSearch();
		}
		
		if(!loadOk) {
			return getList(firstResult, maxResults);
		}
		
		int number_column = searchNumberParam.get(entityClass);
		
		String val = getSearchValue(searchValue);
		
		List<Object> params = new ArrayList<>();
		for (int i = 0; i < number_column; i++) {
			params.add(val);
		}
		
		sql = DB.addPagingSQL(sql, firstResult, maxResults);
		
		List<E> list = (List<E>) DB.query(sql, params, mapper);
		return list;
	}

	public Long getCount(String searchValue) {
		if (searchValue == null) {
			return getCount();
		}
		
		String sqlCount = searchSqlCounts.get(entityClass);
		
		boolean loadOk = true;
		if(sqlCount == null){
			loadOk = initSearch();
		}
		
		if(!loadOk) {
			return getCount();
		}
		
		sqlCount = searchSqlCounts.get(entityClass);
		int number_column = searchNumberParam.get(entityClass);
		
		String val = getSearchValue(searchValue);
		List<Object> params = new ArrayList<>();
		for (int i = 0; i < number_column; i++) {
			params.add(val);
		}
		
		Long count = DB.queryForLong(sqlCount,params);
		
		return count;
	}
	
	public int getMaxSearchColumn(){
		return SEARCH_NUMBER_COLUMN_DEFAULT;
	}
	
	public String[] getSearchColumns() {
		return null;
	}
	
}

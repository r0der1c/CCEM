package ccesm.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.Query;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ccesm.exception.WebException;
import ccesm.util.WebUtility;


public class DaoImpl extends WebUtility implements Dao {
	public static final Logger logger = Logger.getLogger(DaoImpl.class);
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate npjdbcTemplate;



	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNpjdbcTemplate() {
		return npjdbcTemplate;
	}

	public void setNpjdbcTemplate(NamedParameterJdbcTemplate npjdbcTemplate) {
		this.npjdbcTemplate = npjdbcTemplate;
	}


	public <T> T object(String sql, HashMap<String, Object> params, Class<T> clazz){
		return getNpjdbcTemplate().queryForObject(sql.toString(), params, clazz);
	}

	public <T> T object(String sql, Class<T> clazz, Object... params){
		return getJdbcTemplate().queryForObject(sql.toString(), clazz, params);
	}

	public <T> T objectByQueryName(String queryName, HashMap<String, Object> params, Class<T> clazz){
		return getNpjdbcTemplate().queryForObject(getQueryByName(queryName), params, clazz);
	}

	public <T> T objectByQueryName(String queryName, Class<T> clazz, Object... params){
		return getJdbcTemplate().queryForObject(getQueryByName(queryName), clazz, params);
	}



	public Map<String, Object> map(String sql, HashMap<String, Object> params){
		return getNpjdbcTemplate().queryForMap(sql.toString(), params);
	}

	public Map<String, Object> map(String sql, Object... params){
		return getJdbcTemplate().queryForMap(sql.toString(), params);
	}

	public Map<String, Object> mapByQueryName(String queryName, HashMap<String, Object> params){
		return getNpjdbcTemplate().queryForMap(getQueryByName(queryName), params);
	}

	public Map<String, Object> mapByQueryName(String queryName, Object... params){
		return getJdbcTemplate().queryForMap(getQueryByName(queryName), params);
	}

	public List<Map<String, Object>> list(String sql, HashMap<String, Object> params){
		return getNpjdbcTemplate().queryForList(sql.toString(), params);
	}

	public List<Map<String, Object>> list(String sql, Object... params){
		return getJdbcTemplate().queryForList(sql.toString(), params);
	}

	public List<Map<String, Object>> listByQueryName(String queryName, HashMap<String, Object> params){
		return getNpjdbcTemplate().queryForList(getQueryByName(queryName), params);
	}

	public List<Map<String, Object>> listByQueryName(String queryName, Object... params){
		return getJdbcTemplate().queryForList(getQueryByName(queryName), params);
	}




	public int update(String sql, HashMap<String, Object> params){
		return getNpjdbcTemplate().update(sql.toString(), params);
	}

	public int update(String sql, Object... params){
		return getJdbcTemplate().update(sql.toString(), params);
	}

}

package ccesm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public interface Dao {
	public JdbcTemplate getJdbcTemplate();
	public NamedParameterJdbcTemplate getNpjdbcTemplate();
	public <T> T object(String sql, HashMap<String, Object> params, Class<T> clazz);
	public <T> T object(String sql, Class<T> clazz, Object... params);
	public Map<String, Object> map(String sql, HashMap<String, Object> params);
	public Map<String, Object> map(String sql, Object... params);
	public List<Map<String, Object>> list(String sql, HashMap<String, Object> params);
	public List<Map<String, Object>> list(String sql, Object... params);
	public int update(String sql, HashMap<String, Object> params);
	public int update(String sql, Object... params);
}

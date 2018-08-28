package ccesm.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Component
public class BaseDaoImpl extends DaoImpl implements BaseDao {
	@Resource
	public void setJdbcTemplateCFADB(JdbcTemplate jdbcTemplateCFADB) {
		setJdbcTemplate(jdbcTemplateCFADB);
	}
	@Resource
	public void setNpjdbcTemplateCFADB(NamedParameterJdbcTemplate npjdbcTemplateCFADB) {
		setNpjdbcTemplate(npjdbcTemplateCFADB);
	}
}

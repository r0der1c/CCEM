package ccesm.config.context;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class AppContext {
	private final static String DATASOURCE_PROPERTIES = "datasource.properties";

	@Resource
	private Properties hibernateProperties;

	@Resource
	private Properties datasourceProperties;

	@Bean(name = "datasourceProperties")
	public PropertiesFactoryBean datasourceProperties() {
		PropertiesFactoryBean bean = new PropertiesFactoryBean();
		bean.setLocation(new ClassPathResource(DATASOURCE_PROPERTIES));
		return bean;
	}

	@Bean(name = "datasource")
	public DataSource datasource() throws Exception {
		return BasicDataSourceFactory.createDataSource(datasourceProperties);
	}

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate() throws Exception {
		return new JdbcTemplate(datasource());
	}

	@Bean(name = "npjdbcTemplate")
	public NamedParameterJdbcTemplate npjdbcTemplate() throws Exception {
		return new NamedParameterJdbcTemplate(datasource());
	}

}

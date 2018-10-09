package ccesm.config.security;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final static String USERS_BY_USERNAME_QUERY = "SELECT CODUSR, CLAVE, DECODE(ESTADO_USUARIO, 'A', 1, 0) FROM SEC.SEC_USUARIOS WHERE CODUSR = ?";
	private final static String AUTHORITIES_BY_USERNAME_QUERY = "SELECT CODUSR, CODROL FROM SEC.SEC_ROLES_USUARIOS WHERE CODUSR = ? AND CODSIS = 'ADM'";

	@Resource private DataSource datasource;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(customJdbcDaoImpl());
		authenticationProvider.setPasswordEncoder(new Md5PasswordEncoder());
		return authenticationProvider;
	}

	@Bean
	public CustomJdbcDaoImpl customJdbcDaoImpl() {
		CustomJdbcDaoImpl customJdbcDaoImpl = new CustomJdbcDaoImpl();
		customJdbcDaoImpl.setDataSource(datasource);
		customJdbcDaoImpl.setUsersByUsernameQuery(USERS_BY_USERNAME_QUERY);
		customJdbcDaoImpl.setAuthoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
		return customJdbcDaoImpl;
	}

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/admin/**").permitAll()
			.antMatchers("/**").access("isAuthenticated()")
			.and().formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/home", true)
			.and().logout().logoutSuccessUrl("/login")
			.and().exceptionHandling().accessDeniedPage("/login")
			.and().csrf().disable();
	}


}
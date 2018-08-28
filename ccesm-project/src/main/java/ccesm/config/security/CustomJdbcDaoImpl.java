package ccesm.config.security;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class CustomJdbcDaoImpl extends JdbcDaoImpl {
	private static final Logger logger = Logger.getLogger(CustomJdbcDaoImpl.class);

//	@Resource private SecUsuarioDao secUsuarioDao;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		UserDetails user = super.loadUserByUsername(username);
		CustomUser customUser = new CustomUser(
				user.getUsername(),
				user.getPassword(),
				user.isEnabled(),user.isAccountNonExpired(),
				user.isCredentialsNonExpired(),
				user.isAccountNonLocked(),
				user.getAuthorities());
		return customUser;
	}

}

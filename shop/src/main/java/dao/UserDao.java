package dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import logic.User;


public class UserDao {
	private NamedParameterJdbcTemplate template; // spring에서의 jdbc 템플릿
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "insert into useraccount (userid, password, username, phoneno, postcode, address, email, birthday) "
				+ "values (:userid, :password, :username, :phoneno, :postcode, :address, :email, :birthday)";
		template.update(sql, param);
		
	}
}

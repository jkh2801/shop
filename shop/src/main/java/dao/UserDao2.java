package dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import logic.User;


public class UserDao2 {
	private NamedParameterJdbcTemplate template; // spring에서의 jdbc 템플릿
	private RowMapper <User> mapper = new BeanPropertyRowMapper<User>(User.class); // 컬럼명과 User의 프로퍼티를 이용하여 데이터를 저장
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "insert into useraccount (userid, password, username, phoneno, postcode, address, email, birthday) "
				+ "values (:userid, :password, :username, :phoneno, :postcode, :address, :email, :birthday)";
		template.update(sql, param);
		
	}
	public User selectOne(String userid) {
		Map <String, String> param = new HashMap<String, String>();
		param.put("id", userid);
		return template.queryForObject("select * from useraccount where userid = :id",param,  mapper);
	}
}

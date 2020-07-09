package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.User;

@Repository
public class UserDao {
	private NamedParameterJdbcTemplate template; // spring에서의 jdbc 템플릿
	private RowMapper <User> mapper = new BeanPropertyRowMapper<User>(User.class); // 컬럼명과 User의 프로퍼티를 이용하여 데이터를 저장
	private Map <String, Object> param = new HashMap <String, Object> ();
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}
	public void insert(User user) {
		param.clear();
		SqlParameterSource prop = new BeanPropertySqlParameterSource(user);
		String sql = "insert into useraccount (userid, password, username, phoneno, postcode, address, email, birthday) "
				+ "values (:userid, :password, :username, :phoneno, :postcode, :address, :email, :birthday)";
		template.update(sql, prop);
		
	}
	public User selectOne(String userid) {
		param.clear();
		param.put("id", userid);
		return template.queryForObject("select * from useraccount where userid = :id",param,  mapper);
	}
	public void updateUserinfo(User user) {
		param.clear();
		SqlParameterSource prop = new BeanPropertySqlParameterSource(user);
		template.update("update useraccount set username = :username, phoneno = :phoneno, postcode = :postcode, address = :address, email = :email, birthday = :birthday where userid = :userid", prop);
	}
	public void deleteUserinfo(String userid) {
		param.clear();
		param.put("userid", userid);
		template.update("delete from useraccount where userid=:userid", param);
	}
	public List<User> getlistAll() {
		return template.query("SELECT * FROM useraccount where userid != 'admin'", mapper);
	}
}

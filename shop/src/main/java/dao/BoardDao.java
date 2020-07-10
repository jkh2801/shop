package dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.Board;


@Repository
public class BoardDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper <Board> mapper = new BeanPropertyRowMapper <Board> (Board.class);
	private Map <String, Object> param = new HashMap <String, Object> ();

	@Autowired
	public void setDataSource(DataSource datasource) {
		template = new NamedParameterJdbcTemplate(datasource);
	}
	
	public int maxnum() {
		param.clear();
		return template.queryForObject("select ifnull(max(num), 0) num from board", param, Integer.class);
	}

	public void insert(Board board) {
		SqlParameterSource prop = new BeanPropertySqlParameterSource(board);
		System.out.println(board);
		String sql = "insert into board (num, name, pass, subject, content, fileurl, regdate, readcnt, grp, grplevel, grpstep) values(:num,:name,:pass,:subject,:content,:fileurl,now(),:readcnt,:grp,:grplevel,:grpstep)";
		template.update(sql, prop);
	}
	
	

}

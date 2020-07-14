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
		String sql = "insert into board (num, name, pass, subject, content, fileurl, regdate, readcnt, grp, grplevel, grpstep) values(:num,:name,:pass,:subject,:content,:fileurl,now(),:readcnt,:grp,:grplevel,:grpstep)";
		template.update(sql, prop);
	}

	public List<Board> getBoardAll(int start, int limit, String searchtype, String searchcontent) {
		param.clear();
		param.put("start", start);
		param.put("limit", limit);
		param.put("searchcontent", "%"+searchcontent+"%");
		String sql = "SELECT * FROM board ";
		if (searchtype != null) {
				sql += "where "+searchtype+" like :searchcontent";
		}
		sql += " ORDER BY grp desc, grpstep LIMIT :start, :limit";
		return template.query(sql, param, mapper);
	}

	public int countnum(String searchtype, String searchcontent) {
		param.clear();
		param.put("searchcontent", "%"+searchcontent+"%");
		String sql = "select count(*) from board ";
		if (searchtype != null) {
			sql += "where "+searchtype+" like :searchcontent";
		}
		System.out.println(sql);
		return template.queryForObject(sql, param, Integer.class);
	}

	public Board getBoard(Integer num) {
		param.clear();
		param.put("num", num);
		return template.queryForObject("select * from board where num = :num", param, mapper);
	}

	public void updateReadCnt(Integer num) {
		param.clear();
		param.put("num", num);
		template.update("update board set readcnt = readcnt+1 where num = :num", param);
	}

	public void grpstepAdd(Board board) {
		SqlParameterSource prop = new BeanPropertySqlParameterSource(board);
		template.update("update board set grpstep = grpstep+1 where grp = :grp and grpstep > :grpstep", prop);
	}

	public void update(Board board) {
		SqlParameterSource prop = new BeanPropertySqlParameterSource(board);
		template.update("update board set name=:name, pass=:pass, subject=:subject, content=:content, fileurl = :fileurl where num = :num", prop);
	}

	public void delete(int num) {
		param.clear();
		param.put("num", num);
		template.update("delete from board where num = :num", param);
	}

	
	

}

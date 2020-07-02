package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import logic.Item;

public class ItemDao {
	private NamedParameterJdbcTemplate template; // spring에서의 jdbc 템플릿
	private RowMapper <Item> mapper = new BeanPropertyRowMapper<Item>(Item.class); // 컬럼명과 Item의 프로퍼티를 이용하여 데이터를 저장
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Item> list() {
		return template.query("select * from item", mapper);
		// query : 결과가 List 로 리턴
	}

	public Item selectOne(Integer id) {
		Map <String, Integer> param = new HashMap<String, Integer>();
		param.put("id", id);
		return template.queryForObject("select * from item where id = :id",param,  mapper);
		// queryForObject : 반드시가 결과가 1개의 레코드이어야 한다.
	}
}

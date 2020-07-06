package dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import logic.Item;

@Repository
public class ItemDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper <Item> mapper = new BeanPropertyRowMapper <Item> (Item.class);
	private Map <String, Object> param = new HashMap <String, Object> ();
	
	@Autowired
	public void setDataSource(DataSource datasource) {
		System.out.println(datasource);
		template = new NamedParameterJdbcTemplate(datasource);
	}

	public List<Item> list() {
		return template.query("select * from item", mapper);
	}

	public Item selectOne(Integer id) {
		param.clear();
		param.put("id", id);
		return template.queryForObject("select * from item where id = :id",param,  mapper);
		// queryForObject : 반드시가 결과가 1개의 레코드이어야 한다.
	}
}

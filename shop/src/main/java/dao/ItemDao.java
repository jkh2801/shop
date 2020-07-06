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

	public void insert(Item item) {
		param.clear();
		System.out.println("insert====");
		System.out.println(item);
		int id = template.queryForObject("select ifnull(max(id), 0) from item", param, Integer.class);
		item.setId(++id+"");
		String sql = "insert into item (id, name, price, description, pictureUrl) values (:id, :name, :price, :description, :pictureURL)";
		SqlParameterSource prop = new BeanPropertySqlParameterSource(item);
		template.update(sql, prop);
	}

	public void update(Item item) {
		param.clear();
		String sql = "update item set name=:name, price=:price, description=:description ,pictureUrl=:pictureURL where id=:id";
		SqlParameterSource prop = new BeanPropertySqlParameterSource(item);
		template.update(sql, prop);
		
	}

	public void delete(Integer id) {
		param.clear();
		param.put("id", id);
		template.update("delete from item where id = :id", param);
		
	}
}

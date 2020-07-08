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
import logic.Sale;
import logic.SaleItem;


@Repository
public class SaleDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper <Sale> mapper = new BeanPropertyRowMapper <Sale> (Sale.class);
	private RowMapper <SaleItem> mapper2 = new BeanPropertyRowMapper <SaleItem> (SaleItem.class);
	private Map <String, Object> param = new HashMap <String, Object> ();
	
	@Autowired
	public void setDataSource(DataSource datasource) {
		template = new NamedParameterJdbcTemplate(datasource);
	}
	
	public List<Sale> getSaleid() {// 특정 정보를 가져오기 위해서는 template.queryForObject를 사용하면 된다.
		return template.query("select ifnull(max(saleid), 0)+1 saleid from sale", mapper);
	} 

	public void insert(Sale sale) {
		param.clear();
		SqlParameterSource prop = new BeanPropertySqlParameterSource(sale);
		String sql = "insert into sale (saleid, userid, saledate) "
				+ "values (:saleid, :userid, now())";
		template.update(sql, prop);
	}

	public void insertSaleItem(SaleItem saleItem) {
		param.clear();
		SqlParameterSource prop = new BeanPropertySqlParameterSource(saleItem);
		String sql = "insert into saleitem (saleid, seq, itemid, quantity) "
				+ "values (:saleid, :seq, :itemid, :quantity)";
		template.update(sql, prop);
		
	}

	public List<Sale> getlist(String id) {
		param.clear();
		param.put("userid", id);
		return template.query("select * from sale where userid= :userid", param, mapper);
	}

	public List<SaleItem> getsaleitemlist(int saleid) {
		param.clear();
		param.put("saleid", saleid);
		return template.query("select * from saleitem where saleid= :saleid", param, mapper2);
	}
	
	
}

package logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ItemDao;

@Service
public class ShopService {
	
	@Autowired
	private ItemDao itemDao;
	
	public List<Item> getItemList() {
		return itemDao.list();
	}

	public Item getItem(Integer id) {
		return itemDao.selectOne(id);
	}
	
}

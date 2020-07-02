package logic;

import java.util.List;

import dao.ItemDao;
import dao.UserDao;

public class ShopService {
	private ItemDao itemDao;
	private UserDao userDao;
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public List<Item> getItemList() {
		return itemDao.list();
	}

	public Item getItemById(Integer id) {
		return itemDao.selectOne(id);
	}

	public void insertUser(User user) {
		userDao.insert(user);
	}
	
}

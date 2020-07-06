package logic;

import java.util.List;

import dao.ItemDao2;
import dao.UserDao;

public class ShopService2 {
	private ItemDao2 itemDao;
	private UserDao userDao;
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setItemDao(ItemDao2 itemDao) {
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

	public User getUser(String userid) {
		return userDao.selectOne(userid);
	}
	
}

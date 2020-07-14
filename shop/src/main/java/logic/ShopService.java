package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.BoardDao;
import dao.ItemDao;
import dao.SaleDao;
import dao.UserDao;

@Service
public class ShopService {
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SaleDao saleDao;
	
	@Autowired
	private BoardDao boardDao;
	
	public List<Item> getItemList() {
		return itemDao.list();
	}

	public Item getItem(Integer id) {
		return itemDao.selectOne(id);
	}

	// 파일 업로드와 dao에 데이터를 전달
	public void itemCreate(Item item, HttpServletRequest request) {
		if(item.getPicture() != null && !item.getPicture().isEmpty()) { // 업로드 파일이 있으면
			uploadFileCreate(item.getPicture(), request, "img/");
			item.setPictureURL(item.getPicture().getOriginalFilename());
		}
		itemDao.insert(item);
	}

	private void uploadFileCreate(MultipartFile picture, HttpServletRequest request, String path) {
		String orgFile = picture.getOriginalFilename();
		String uploadPath = request.getServletContext().getRealPath("/") + path;
		// 웹 어플리케이션의 경로를 구하기 (webapp폴더까지)
		System.out.println(uploadPath);
		System.out.println("orgFile: "+ orgFile);
		File fpath = new File(uploadPath);
		if(!fpath.exists()) fpath.mkdirs();
		try {
			picture.transferTo(new File(uploadPath + orgFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void itemUpdate(Item item, HttpServletRequest request) {
		if(item.getPicture() != null && !item.getPicture().isEmpty()) { // 업로드 파일이 있으면
			uploadFileCreate(item.getPicture(), request, "img/");
			item.setPictureURL(item.getPicture().getOriginalFilename());
		}
		itemDao.update(item);
		
	}

	public void itemDelete(Integer id) {
		itemDao.delete(id);
	}

	public void userInsert(User user) {
		userDao.insert(user);
	}

	public User getUserByID(String userid) {
		return userDao.selectOne(userid);
	}

	public Sale checkend(User loginUser, Cart cart) {
		Sale sale = saleDao.getSaleid().get(0);
		sale.setUserid(loginUser.getUserid());
		sale.setUser(loginUser);
		saleDao.insert(sale);
		List <SaleItem> list = new ArrayList<SaleItem>();
		for (int i = 0; i < cart.getItemSetList().size(); i++) {
			SaleItem saleItem = new SaleItem(sale.getSaleid(), i+1, cart.getItemSetList().get(i));
			saleDao.insertSaleItem(saleItem);
			list.add(saleItem);
		}
		sale.setitemList(list);
		return sale;
	}

	public List<Sale> getlist(String id) {
		return saleDao.getlist(id);
	}

	public List<SaleItem> getsaleitemlist(int saleid) {
		return saleDao.getsaleitemlist(saleid);
	}

	public void updateUserinfo(User user) {
		userDao.updateUserinfo(user);
	}

	public void deleteUserinfo(String userid) {
		userDao.deleteUserinfo(userid);
	}

	public List<User> getlistAll() {
		return userDao.getlistAll();
	}

	public List<User> userlist(String[] idchks) {
		return userDao.userlist(idchks);
	}

	public void boardWrite(Board board, HttpServletRequest request) {
		if(board.getFile1() != null && !board.getFile1().isEmpty()) {
			uploadFileCreate(board.getFile1(), request, "board/file/");
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		int max = boardDao.maxnum();
		System.out.println("max: "+ max);
		board.setNum(++max);
		board.setGrp(max);
		boardDao.insert(board);
		
	}

	public int maxnum() {
		return boardDao.maxnum();
	}

	public List<Board> getBoardAll(int pageNum, int limit, String searchtype, String searchcontent) {
		return boardDao.getBoardAll((pageNum-1)*limit, limit, searchtype, searchcontent);
	}

	public int countnum(String searchtype, String searchcontent) {
		return boardDao.countnum(searchtype, searchcontent);
	}

	public Board getBoard(Integer num) {
		boardDao.updateReadCnt(num);
		return boardDao.getBoard(num);
	}

	public void grpstepAdd(Board board) {
		boardDao.grpstepAdd(board);
	}

	public void replyInsert(Board board, HttpServletRequest request) {
		if(board.getFile1() != null && !board.getFile1().isEmpty()) {
			uploadFileCreate(board.getFile1(), request, "board/file/");
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		boardDao.insert(board);
		
	}

	public void updateBoard(Board board, HttpServletRequest request) {
		if(board.getFile1() != null && !board.getFile1().isEmpty()) {
			uploadFileCreate(board.getFile1(), request, "board/file/");
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		boardDao.update(board);
	}

	public void deleteBoard(int num) {
		boardDao.delete(num);
	}


	
	
}

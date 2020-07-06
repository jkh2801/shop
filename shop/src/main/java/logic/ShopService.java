package logic;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
}

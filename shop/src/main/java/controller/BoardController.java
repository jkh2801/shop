package controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import exception.BoardException;
import logic.Board;
import logic.ShopService;

@Controller
@RequestMapping("board")
public class BoardController {

	@Autowired
	private ShopService service;
	
	// url : http://127.0.0.1:8080/contextpath/servlcetpath/index.jsp?seq=1&type=NOTICE
	// getRequestURL(): 위와 동일
	// getRequestURI() : /contextpath/servlcetpath/index.jsp (컨텍스트 경로+서블릿 경로)
	// getContextPath() : /contextpath (context 경로)
	// getServletPath() : /servlcetpath/index.jsp (servlet 경로)
	// getQueryString() : seq=1&type=NOTICE (쿼리 정보)
	// getServerName() : 127.0.0.1 (도메인 저옵)
	// getServerPort() : 8080 (포트 정보)
	
	@GetMapping("*")
	public ModelAndView getBoard(Integer num, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Board board = null;
		if(num == null) board = new Board();
		else {
			boolean readcnttable = false;
			if(request.getRequestURI().contains("datail.shop"))
				readcnttable = true;
			
		}
		mav.addObject("board", board);
		return mav;
	}
	
	@PostMapping("write")
	public ModelAndView write(@Valid Board board, BindingResult bresult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		try {
			service.boardWrite(board, request);
			mav.setViewName("redirect:list.shop");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BoardException("게시물 등록에 실패했습니다.", "write.shop");
		}
		return mav;
	}
	
	// pageNum : 현재 페이지 번호
	// maxpage : 최대 페이지
	// startpage : 보여지는 시작 페이지
	// endpage : 보여지는 끝 페이지
	// listcount : 전체 등록된 게시물 갯수
	// boardlist : 화면에 보여지는 게시물 객체들
	// boardno : 화면에 보여지는 게시물 번호
	
	@RequestMapping("list")
	public ModelAndView list(Integer pageNum, String searchtype, String searchcontent, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		int limit = 10; 
		if(pageNum == null || pageNum.toString().equals("")) pageNum = 1;
		if(searchtype == null || searchtype.trim().equals("") || searchcontent == null || searchcontent.trim().equals("")) {
			searchcontent = null;
			searchtype = null;
		}
		int listcount = service.countnum(searchtype, searchcontent);
		int startpage = ((int)(pageNum/10.0+0.9)-1) * 10 + 1;
		int endpage = startpage + 9;
		int maxpage = (int)((double)listcount/limit+0.95);
		if(endpage > maxpage) endpage = maxpage;
		int boardno = listcount - (pageNum-1) * limit;
		List <Board> list = service.getBoardAll(pageNum, limit, searchtype, searchcontent);
		mav.addObject("listcount", listcount);
		mav.addObject("pageNum", pageNum);
		mav.addObject("startpage", startpage);
		mav.addObject("endpage", endpage);
		mav.addObject("maxpage", maxpage);
		mav.addObject("boardlist", list);
		mav.addObject("boardno", boardno);
		mav.addObject("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return mav;
	}
	
	@GetMapping("detail")
	public ModelAndView detail(Integer num, HttpServletRequest request) {
		System.out.println(request.getRequestURI());
		ModelAndView mav = new ModelAndView();
		Board board = service.getBoard(num);
		System.out.println(board);
		mav.addObject("board", board);
		return mav;
	}
	
	@RequestMapping("imgupload")
	public String imgupload(MultipartFile upload, String CKEditorFuncNum, HttpServletRequest request, Model model) {
		// uplaod : ckeditor에서 전달해 주는 파일 정보의 이름 <input type="file" name="uplaod">
		// CKEditorFuncNum : CKEDITOR의 내부에서 사용되는 파라미터
		String path = request.getServletContext().getRealPath("/")+"board/imgfile/"; // 이미지를 저장할 포러더를 지정
		System.out.println(path);
		File f = new File(path);
		if(!f.exists()) f.mkdirs();
		if(!upload.isEmpty()) {
			File file = new File(path, upload.getOriginalFilename());
			// 업로드 될 파일을 저장할 File 객체 지정
			try {
				upload.transferTo(file); // 업로드 파일 생성
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String filename = "/shop/board/imgfile/"+upload.getOriginalFilename();
		System.out.println(filename);
		model.addAttribute("fileName", filename);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		return "ckedit";
	}
	
}

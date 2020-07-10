package controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import exception.LoginException;
import logic.Mail;
import logic.ShopService;
import logic.User;

@Controller // controller 역할을 수행하는 @Component 객체이다.
@RequestMapping("admin") // path에 cart으로 들어오는 요청을 처리해준다. (/cart/)
public class AdminController {
	
	@Autowired
	private ShopService service;
	
	@GetMapping("list")
	public ModelAndView list(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List <User> list = service.getlistAll();
		System.out.println(list);
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping("mailForm")
	public ModelAndView mailform (String [] idchks, HttpSession session) {
		ModelAndView mav = new ModelAndView("admin/mail");
		if(idchks == null || idchks.length == 0) throw new LoginException("메일을 보낼 대상을 선택하세요.", "list.shop");
		List <User> list = service.userlist(idchks);
		System.out.println(list);
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping("mail")
	public ModelAndView mail (Mail mail, HttpSession session) {
		ModelAndView mav = new ModelAndView("alert");
		mailSend(mail);
		mav.addObject("msg", "메일 전송이 완료되었습니다.");
		mav.addObject("url", "list.shop");
		return mav;
	}
	
	private final class MyAuthenticator extends Authenticator {
		private String id, pw;

		public MyAuthenticator(String id, String pw) {
			this.id = id;
			this.pw = pw;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(id, pw);
		}
	}
	private void mailSend(Mail mail) {
		// 네이버 메일 전송을 위한 인증 객체
		MyAuthenticator auth = new MyAuthenticator(mail.getNaverid(), mail.getNaverpw());
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream("C:/Users/GDJ24/git/shop/shop/src/main/resources/mail.properties");
			prop.load(fis); // mail.properties 의 내용을 Properties(Map객체)를 통해 넣는다.
			prop.put("mail.smtp.user", mail.getNaverid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 메일 전송을 위한 객체
		Session session = Session.getInstance(prop, auth);
		// 메일의 내용을 저장하기 위한 객체
		MimeMessage mimeMsg = new MimeMessage(session);
		try {
			// 보내는 사람 설정
			mimeMsg.setFrom(new InternetAddress(mail.getNaverid()+"@naver.com"));
			List <InternetAddress> address = new ArrayList<InternetAddress>();
			String [] emails = mail.getRecipient().split(",");
			// System.out.println("emails: "+Arrays.toString(emails)); // [하하하< jkh2801@naver.com>]
			for(String email: emails) {
				try {
					address.add(new InternetAddress(new String(email.getBytes("utf-8"),"8859_1")));
					// new String(email.getBytes("utf-8"),"8859_1") : email 문자열을 byte [] 형태로 변경하며 이때 utf-8로 인식
					// 8859_1 : btye [] 배열을 8859_1로 변경하여 문자열로 리턴
					// 이를 안 할시 한글이 깨져서 나온다.
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			InternetAddress [] arr = new InternetAddress [emails.length];
			for (int i = 0; i < address.size(); i++) {
				arr[i] = address.get(i);
			}
			// 보낸일자
			mimeMsg.setSentDate(new Date());
			// 보낸살마
			mimeMsg.setRecipients(Message.RecipientType.TO, arr);
			// 제목
			mimeMsg.setSubject(mail.getTitle());
			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart message = new MimeBodyPart();
			// 내용부분
			message.setContent(mail.getContents(), mail.getMtype());
			multipart.addBodyPart(message);
			// 첨부파일 부분
			for(MultipartFile mf : mail.getFile1()) {
				if((mf != null ) && (!mf.isEmpty())) multipart.addBodyPart(bodyPart(mf));
			}
			mimeMsg.setContent(multipart);
			Transport.send(mimeMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BodyPart bodyPart(MultipartFile mf) {
		MimeBodyPart body = new MimeBodyPart();
		String orgFile = mf.getOriginalFilename();
		String path = "d:/spring/mail/";
		File f = new File(path);
		if(!f.exists()) f.mkdirs();
		File f1 = new File(path+orgFile); // 업로드된 내용을 저장하는 파일
		try {
			mf.transferTo(f1); // 업로드 완성
			body.attachFile(f1); // 메일에 첨부
			body.setFileName(new String(orgFile.getBytes("UTF-8"),"8859_1")); // 첨부파일 이름 설정
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}
}

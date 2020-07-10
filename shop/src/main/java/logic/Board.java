package logic;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class Board {
	private int num, readcnt, grp, grplevel, grpstep;
	@NotEmpty(message = "글쓴이를 입력하세요.")
	private String name;
	@NotEmpty(message = "비밀번호를 입력하세요.")
	private String pass;
	@NotEmpty(message = "제목를 입력하세요.")
	private String subject;
	@NotEmpty(message = "내용를 입력하세요.")
	private String content;
	private String fileurl;
	private Date regdate;
	private MultipartFile file1;
	@Override
	public String toString() {
		return "Board [num=" + num + ", readcnt=" + readcnt + ", grp=" + grp + ", grplevel=" + grplevel + ", grpstep="
				+ grpstep + ", name=" + name + ", pass=" + pass + ", subject=" + subject + ", content=" + content
				+ ", fileurl=" + fileurl + ", regdate=" + regdate + ", file1=" + file1 + "]";
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getReadcnt() {
		return readcnt;
	}
	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}
	public int getGrp() {
		return grp;
	}
	public void setGrp(int grp) {
		this.grp = grp;
	}
	public int getGrplevel() {
		return grplevel;
	}
	public void setGrplevel(int grplevel) {
		this.grplevel = grplevel;
	}
	public int getGrpstep() {
		return grpstep;
	}
	public void setGrpstep(int grpstep) {
		this.grpstep = grpstep;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public MultipartFile getFile1() {
		return file1;
	}
	public void setFile1(MultipartFile file1) {
		this.file1 = file1;
	}
	
}

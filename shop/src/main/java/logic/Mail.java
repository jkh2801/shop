package logic;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public class Mail {
	private String recipient, title, mtype, contents, naverid, naverpw;
	private List<MultipartFile> file1;
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMtype() {
		return mtype;
	}
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getNaverid() {
		return naverid;
	}
	public void setNaverid(String naverid) {
		this.naverid = naverid;
	}
	public String getNaverpw() {
		return naverpw;
	}
	public void setNaverpw(String naverpw) {
		this.naverpw = naverpw;
	}
	public List<MultipartFile> getFile1() {
		return file1;
	}
	public void setFile1(List<MultipartFile> file1) {
		this.file1 = file1;
	}
	@Override
	public String toString() {
		return "Mail [recipient=" + recipient + ", title=" + title + ", mtype=" + mtype + ", contents=" + contents
				+ ", naverid=" + naverid + ", naverpw=" + naverpw + ", file1=" + file1 + "]";
	}
	
}

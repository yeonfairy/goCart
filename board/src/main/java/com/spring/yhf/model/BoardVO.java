package com.spring.yhf.model;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Alias("BoardVO")
public class BoardVO {

	private int id;
	private String img;
	private String title;
	private String con;
	private String file; // db에 저장할 변경된 파일 이름
	private MultipartFile multipartFile;
	private String originalFile; // db에서 원본파일 읽어오는 인스턴스
	
	
	
	@Override
	public String toString() {
		return "BoardVO [id=" + id + ", img=" + img + ", title=" + title + ", con=" + con + ", file=" + file
				+ ", multipartFile=" + multipartFile + ", originalFile=" + originalFile + "]";
	}
	public String getOriginalFile() {
		return originalFile;
	}
	public void setOriginalFile(String originalFile) {
		this.originalFile = originalFile;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	
}

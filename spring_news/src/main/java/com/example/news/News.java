package com.example.news;

public class News {

	private int aid;		//번호(자동증가:auto_increment)
	private String title;	//타이틀
	private String img;		//이미지
	private String date;	//등록일시(timestamp)
	private String content;	//뉴스 내용
	
	//getter, setter
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}

package com.spring.yhf.model;

public class PageMaker {

	private int totalCount; // 게시판 전체 데이터 수
	private int displayPageNum = 10; // 게시판 화면에서 화면에 보여질 페이지 번호 개수
	
	private int startPage; // 현재 화면에서 보이는 startPage 번호
	private int endPage; // 현재 화면에 보이는 endPage 번호
	
	private boolean prev; // 페이징 이전버튼 활성화 여부
	private boolean next; // 페이징 다음버튼 활성화 여부
	
	private Criteria cri; // Criteria를 주입받는다.

	public int getTotalCount() {
		return totalCount; // select문의 count(*)를 이용해 전체 게시글수를 구한다.
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		calcData();
	}
	
	private void calcData() {
		endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);
		//							현재 페이지 번호 				한번에 보여질 페이지 번호 개수
		
		startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
														// criteria의 한 페이지당 게시글 개수
		
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		prev = startPage == 1 ? false : true;
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}
	
	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", displayPageNum=" + displayPageNum + ", startPage=" + startPage
				+ ", endPage=" + endPage + ", prev=" + prev + ", next=" + next + ", cri=" + cri + "]";
	}
	
	
}

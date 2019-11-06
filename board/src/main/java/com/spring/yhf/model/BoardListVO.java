package com.spring.yhf.model;

import java.util.List;

public class BoardListVO {

	private int boardTotalCount; // 글 총 갯수
	private int currentPageNum; // 현재페이지
	private List<BoardVO> boardList; // 데이터 담을 리스트
	private int pageTotalCount; // 페이지 수
	private int boardCounterPerPage; //페이지당 보여줄 글 갯수
	private int firstRow; // 페이지 첫줄
	
	public BoardListVO(int boardTotalCount, int currentPageNum, List<BoardVO> boardList, int boardCounterPerPage, int firstRow) {
		this.boardTotalCount = boardTotalCount;
		this.currentPageNum = currentPageNum;
		this.boardList = boardList;
		this.boardCounterPerPage = boardCounterPerPage;
		this.firstRow = firstRow;
		calculatePageTotalCount();
	}
	
	private void calculatePageTotalCount() {
		if(boardTotalCount == 0) {
			pageTotalCount = 0;
		} else {
			pageTotalCount = boardTotalCount / boardCounterPerPage;
			if(boardTotalCount % boardCounterPerPage > 0) {
				pageTotalCount ++;
			}
		}
	}

	public int getBoardTotalCount() {
		return boardTotalCount;
	}

	public void setBoardTotalCount(int boardTotalCount) {
		this.boardTotalCount = boardTotalCount;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public List<BoardVO> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<BoardVO> boardList) {
		this.boardList = boardList;
	}

	public int getPageTotalCount() {
		return pageTotalCount;
	}

	public void setPageTotalCount(int pageTotalCount) {
		this.pageTotalCount = pageTotalCount;
	}

	public int getBoardCounterPerPage() {
		return boardCounterPerPage;
	}

	public void setBoardCounterPerPage(int boardCounterPerPage) {
		this.boardCounterPerPage = boardCounterPerPage;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	@Override
	public String toString() {
		return "BoardListVO [boardTotalCount=" + boardTotalCount + ", currentPageNum=" + currentPageNum + ", boardList="
				+ boardList + ", pageTotalCount=" + pageTotalCount + ", boardCounterPerPage=" + boardCounterPerPage
				+ ", firstRow=" + firstRow + "]";
	}
	
}

package com.spring.yhf.dao;

import java.util.List;

import com.spring.yhf.model.BoardVO;

public interface BoardDaoInterface {

	List<BoardVO> getListI(int firstRow, int line);

	int pageCount();

	BoardVO getDetailI(int id);

	int upload(int id, String file, String originalFileName);


}

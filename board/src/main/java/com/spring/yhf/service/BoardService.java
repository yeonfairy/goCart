package com.spring.yhf.service;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.yhf.dao.BoardDaoInterface;
import com.spring.yhf.model.BoardListVO;
import com.spring.yhf.model.BoardVO;

@Service
public class BoardService {

	@Autowired
	private SqlSessionTemplate sessionTemplate;
	private BoardDaoInterface boardDao;
	
	
	public BoardListVO getList(int line, int pageNumber) {
		
		boardDao = sessionTemplate.getMapper(BoardDaoInterface.class);
		int currentPageNum = pageNumber; // 현재 페이지
		
		int listTotalCount = boardDao.pageCount();
		List<BoardVO> boardList = null;
		int firstRow = 0;
		
		if(listTotalCount > 0) {
			
			firstRow = (pageNumber - 1) * (line * 4); //한줄에 4개씩이므로 보여줄 line*4개씩
			boardList = boardDao.getListI(firstRow, line * 4);
		}
		
		return new BoardListVO(listTotalCount, currentPageNum, boardList, line*4, firstRow);
	}
	
	public BoardVO getDetail(int id) {
		boardDao = sessionTemplate.getMapper(BoardDaoInterface.class);
		return boardDao.getDetailI(id);
	}
	
	public int uploadS(BoardVO boardVO, HttpServletRequest request) throws IllegalStateException, IOException {
		
		String uploadUri = "/resources/uploadfile"; // 파일을 저장할 물리적 경로
		String dir = request.getSession().getServletContext().getRealPath(uploadUri);
		String dbFile = ""; // 데이터베이스 저장할 파일 이름
		System.out.println(boardVO.getId() + boardVO.getMultipartFile().toString());
		int resultCnt = 0;
		
		MultipartFile f = boardVO.getMultipartFile();
		String originalFileName = null; 
		String originalFileExtension = null;
		
		String storedFile = this.getDetail(boardVO.getId()).getFile(); // 데이터베이스에서 기존에 저장된 파일을 불러온다
		
		if(!f.isEmpty()) {
			if(storedFile != null) { // 기존데이터베이스에 저장된 파일이 있다면 서버에서 해당 파일을 먼저 삭제한다
				new File(dir,storedFile).delete(); // 서버에서 저장된 파일이름을 찾아 삭제시킨다.
			}
			originalFileName = f.getOriginalFilename(); // 파일의 원본이름 (필요할 경우 데이터베이스에 등록시키면됨)
			originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 원본파일의 확장자 이름
			dbFile = boardVO.getId() + "_" + System.currentTimeMillis() + originalFileExtension; // 새로 지어줄 파일이름에 확장자를 추가
			boardVO.getMultipartFile().transferTo(new File(dir, dbFile)); // 파일을 전송
			
			boardVO.setFile(dbFile); // 데이터베이스에 저장할 파일이름을 설정
			System.out.println(dbFile);
		}
		try {
			boardDao = sessionTemplate.getMapper(BoardDaoInterface.class); // 서버에 파일을 저장시킨 후 데이터베이스에
			System.out.println("파일저장 완료");
			resultCnt = boardDao.upload(boardVO.getId(), boardVO.getFile(), originalFileName); // 데이터베이스에 파일명을 저장시킴
		} catch(Exception e) {
			System.out.println("파일저장 실패" + e.getMessage());
			new File(dir, dbFile).delete(); // 파일저장을 실패할 경우 물리적 경로에 있는 파일을 삭제시킨다.
		}
		return resultCnt;
	}
	
	public void download(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 파일다운로드가 service에서 실행되도록 정의했으므로 DownLoadImple클래스는 사용하지 않아도 된다.
		boardDao = sessionTemplate.getMapper(BoardDaoInterface.class);
		BoardVO boardVO = boardDao.getDetailI(id); // 데이터베이스에서 해당아이디의 데이터를 전부 가져옴
		
		String storedFileName = boardVO.getFile(); // 데이터베이스에서 저장된 파일이름을 가져옴
		String originalFileName = boardVO.getOriginalFile(); // 데이터베이스에서 파일 원본이름을 가져옴
		String uploadUri = "/resources/uploadfile"; // 파일이 저장된 물리적 경로
		String dir = request.getSession().getServletContext().getRealPath(uploadUri);
		
		byte fileByte[] = FileUtils.readFileToByteArray(new File(dir, storedFileName)); // 파일이 저장된 위치에서 해당하는 이름을 읽어서 가져옴
		
		response.setContentType("application/octet-stream"); // 다운로드시 다른이름으로 저장이 뜨도록 설정
		// attachment는 첨부파일을 의미한다.
		// fileName은 첨부파일의 이름을 지정하는 역할을 한다.
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName, "UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	}


}

package com.spring.yhf.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownLoadImple extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String fileName = null;
		File file = (File)model.get("fileName"); // 맵방식으로 fileName이라고 저장된 파일을 가져옴

		byte fileByte[] = FileUtils.readFileToByteArray(new File(file.getPath()));
		response.setContentType("application/octet-stream"); // 다운로드시 다른이름으로 저장이 뜨도록 설정
		/*int length = (int) file.length();
		response.setContentLength(length);*/
		response.setContentLength(fileByte.length);
		
		/*String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1; // 브라우저가 explore인지 아닌지 구분
		
		if(ie) {
			fileName = URLEncoder.encode(file.getName(), "utf-8").replace("+", "%20");
		} else {
			fileName = new String(file.getName().getBytes("utf-8"),"iso-8859-1").replace("+", "%20");
		}
		response.setHeader("Content-Disposition", "attachment; " + "filename=\"" + fileName + "\";");*/
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getName(), "UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		response.getOutputStream().write(fileByte);
	     
	    response.getOutputStream().flush();
	    response.getOutputStream().close();


		/*OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		
		try {
			int temp;
			fis = new FileInputStream(file);
			while((temp = fis.read()) != -1) {
				out.write(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		out.flush();*/
	}

	
}

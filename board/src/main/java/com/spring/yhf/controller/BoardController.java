package com.spring.yhf.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.yhf.model.BoardListVO;
import com.spring.yhf.model.BoardVO;
import com.spring.yhf.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getList(HttpServletRequest request, Model model) {
		
		String data = request.getParameter("line"); // 몇개줄인지 파라미터 받는 값
		String page = request.getParameter("page"); // 몇페이지인지 파라미터 받는 값
		
		int pageNumber = 1;
		
		if(page != null) {
			pageNumber = Integer.parseInt(page);
		}
		BoardListVO list;
		
		if(data == null || data.equals("")) {
			list = boardService.getList(3,pageNumber);
		} else {
			list = boardService.getList(Integer.parseInt(data),pageNumber);
		}
		
		model.addAttribute("list",list);
		
		return "board/list";
	}
	
	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request, Model model) {
		
		String id = request.getParameter("id");
		
		model.addAttribute("detail", boardService.getDetail(Integer.parseInt(id)));
		
		return "board/detail";
	}
	
	List<BoardVO> list = new ArrayList<BoardVO>();
	
	// 결과값을 리턴할때 값을 한글로 넘기려면 한글이 깨지는 현상이 발생
	// requestmapping쪽에 produces = "application/text; charset=utf8"를 추가시키면 한글이 깨지지 않는다
	@RequestMapping(value = "cart", method = RequestMethod.POST,  produces = "application/text; charset=utf8")
	@ResponseBody
	public String cart(BoardVO boardVO, HttpServletRequest request) throws UnsupportedEncodingException {
		String chk = request.getParameter("chk");
		//System.out.println("파라미터로 넘어오는 데이터 확인 : " + boardVO + "체크확인 : " + chk);
		HttpSession session = request.getSession();
		String result;
		
		if(chk.equals("true")) {
			System.out.println("체크박스 체크확인");
			list.add(boardVO);
			for(int i = 0; i < list.size()-1; i ++) {
				if(list.get(i).getId() == boardVO.getId()) {
					list.remove(list.get(i));
				}
			}
			session.setAttribute("cartSession", list);
			result = "장바구니에 추가되었습니다.";
		} else {
			System.out.println("체크박스 체크 해제");
			for(int j = 0; j < list.size(); j++) {
				if (list.get(j).getId() == boardVO.getId()) {
					list.remove(list.get(j));
				}
			}
			session.setAttribute("cartSession", list);
			result = "장바구니에서 삭제되었습니다.";
		}

		//System.out.println("리스트에 담긴 데이터 확인 : " + list);
		return result;
		
	}
	
	@RequestMapping(value = "cart", method = RequestMethod.GET)
	public ModelAndView goCart(HttpSession session) {
		//System.out.println("세션에 저장된 데이터 : " + session.getAttribute("cartSession"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("session", session.getAttribute("cart"));
		modelAndView.setViewName("board/cart");
		
		return modelAndView;
	}

}

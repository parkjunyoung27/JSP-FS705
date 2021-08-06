package com.FS705.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.BoardDAO;
import com.FS705.dao.LogDAO;
import com.FS705.dto.BoardDTO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;


@WebServlet("/boardm")
public class Boardm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Boardm() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		//if((int) session.getAttribute("grade") != 9) {
		//	response.sendRedirect("./error");
		//}
	
		//-----------------log남기기----------------------------------------
		//Session 불러오기
		String id = "";
		if(session.getAttribute(id) != null) {
			id = (String)session.getAttribute("id");
		}
		
		LogDTO logDto = new LogDTO();
				
		logDto.setLogIp(Util.getIP(request));
		logDto.setLogTarget("boardm");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		ArrayList<BoardDTO> boardList = null;

		//Paging
		int page = 1; //페이지 기본값 설정
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		} // 페이지 있으면 파라미터값에 저장
		request.setAttribute("page", page); //페이지 보내기
		
		//첫화면 일때  grade 아직 설정 안할 때 
		boardList = BoardDAO.boardList((page-1) * 20);
		String category = request.getParameter("category");
		String subCategory = request.getParameter("subCategory");  
		
		//첫화면 일때  category, subCategory 아직 설정 안할 때 
		if(request.getParameter("category") == null 
				&& request.getParameter("subCategory") == null) {
			boardList = BoardDAO.boardList((page-1) * 20);
		}
		//ip와 target이 전체가 선택될때 
		else if(request.getParameter("category") == "" 
				&& request.getParameter("subCategory") == "") {
			boardList = BoardDAO.boardList((page-1) * 20);
		
		//ip와 target 둘 다 null값이 아닐때 
		}else if(request.getParameter("category") != "" 
				&& request.getParameter("subCategory") != "") {
			boardList = BoardDAO.selectCategorySub(category, subCategory,(page-1) * 20);
			
		//ip값만 null이 아닐때 
		}else if(request.getParameter("category") != ""
				&& request.getParameter("subCategory") == "") {
			boardList = BoardDAO.selectCategory(category, (page-1) * 20);
		//target값만 null이 아닐때 
		}else if(request.getParameter("category") != ""
				&& request.getParameter("subCategory") == "") {
			boardList = BoardDAO.selectSubCategory(subCategory, (page-1) * 20);
		}
		request.setAttribute("list", boardList);
		
		
		//옵션에 붙을 목록 설정
		ArrayList<String> categoryList = BoardDAO.optionList("bcategory");
		request.setAttribute("categoryList", categoryList);
		ArrayList<String> subCategoryList = BoardDAO.optionList("subCategory");
		request.setAttribute("subCategoryList", subCategoryList);
		//category, target 값 
		request.setAttribute("category", request.getParameter("category")); // ip 
		request.setAttribute("subCategory", request.getParameter("subCategory")); // ip 

		//total
		if(boardList != null && boardList.size() > 0) {
			request.setAttribute("totalCount", boardList.get(0).getTotalCount());
		}
		
		//RD에 붙이기 
		RequestDispatcher rd = request.getRequestDispatcher("./boardm.jsp"); // index.jsp가 열리면서 해당 내용이 뜸 	
		rd.forward(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//-----------------log남기기----------------------------------------
		String id = "";
		if(session.getAttribute(id) != null) {
			id = (String)session.getAttribute("id");
		}
		
		LogDTO logDto = new LogDTO();		
		logDto.setLogIp(Util.getIP(request));
		logDto.setLogTarget("boardm");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("post");
		LogDAO.insertLog(logDto);
		
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		request.setAttribute("page", page);
	}

}

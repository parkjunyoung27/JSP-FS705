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
		
//		if((int) session.getAttribute("category") != 9) {
//			response.sendRedirect("./index");
//		}
//	
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
		
		//Paging
		int page = 1; //페이지 기본값 설정
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		} // 페이지 있으면 파라미터값에 저장
		request.setAttribute("page", page); //페이지 보내기		
		
		//첫화면 일때  category 아직 설정 안할 때 
		String order = "";
		if(request.getParameter("order") != null) {
			order = request.getParameter("order");
		}
		
		String category = request.getParameter("category");
		String subCategory = request.getParameter("subCategory");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		System.out.println("order : " + order + " category : " + category +" subCategory : " + subCategory + " searchType : "+ searchType + " searchText : " + searchText);
		
		request.setAttribute("order", order);
		request.setAttribute("category", request.getParameter("category"));
		request.setAttribute("subCategory", request.getParameter("subCategory"));
		request.setAttribute("searchType", request.getParameter("searchType"));  
		request.setAttribute("searchText", request.getParameter("searchText")); 

		String orderSql = "";
		if(order != null) {
			if(order.equals("bno")) {
				orderSql = "ORDER BY bno DESC";
			}else if(order.equals("bcount")) {
				orderSql = "ORDER BY bcount DESC";
			}else if(order.equals("blike")) {
				orderSql = "ORDER BY blike DESC";
			}
		}
		
		ArrayList<BoardDTO> boardList = null;
		//초기화면 모든 검색, 옵션 선택 안할때 
		if(searchType == null) {
			boardList = BoardDAO.boardList((page-1)*10, orderSql);
		}else {//검색,옵션 셀렉트 할 때
			if(searchType.equals("all")) { //전체검색 할 때
				System.out.println("전체검색");
				if(category.equals("")&& subCategory.equals("")&& searchText.equals("")){
					boardList = BoardDAO.boardList((page-1) * 10, orderSql);
				//ip 선택시
				}else if(!category.equals("")&& subCategory.equals("") && searchText.equals("")){				
					boardList = BoardDAO.selectCategory(category, (page-1) * 10, orderSql);
				}else if(category.equals("")&& !subCategory.equals("") && searchText.equals("")){				
					boardList = BoardDAO.selectSubcategory(subCategory, (page-1) * 10, orderSql);
				}else if(!category.equals("")&& !subCategory.equals("") && searchText.equals("")){				
					boardList = BoardDAO.selectCategorySubcategory(category, subCategory, (page-1) * 10, orderSql);
				}else if(category.equals("")&& subCategory.equals("") && !searchText.equals("")){				
					boardList = BoardDAO.searchAll(searchType, searchText, (page-1) * 10, orderSql);
				}else if(!category.equals("")&& subCategory.equals("") && !searchText.equals("")){				
					boardList = BoardDAO.searchAllCategory(searchType, searchText, category,(page-1) * 10, orderSql);
				}else if(category.equals("")&& !subCategory.equals("") && !searchText.equals("")){				
					boardList = BoardDAO.searchAllSubcategory(searchType, searchText, subCategory, (page-1) * 10, orderSql);
				}else if(!category.equals("")&& !subCategory.equals("") && !searchText.equals("")){				
					boardList = BoardDAO.searchAllCategorySubcategory(searchType, searchText, category, subCategory, (page-1) * 10, orderSql);
				}
			
			}else {//전체검색 아닐 때
				//검색값 입력 안할 때
				if(searchText.equals("")) {
					if(category.equals("")&& subCategory.equals("")){
						boardList = BoardDAO.boardList(page, orderSql);
					}else if(!category.equals("")&& subCategory.equals("")){
						boardList = BoardDAO.selectCategory(category, (page-1) * 10, orderSql);
					}else if(category.equals("")&& !subCategory.equals("")){
						boardList = BoardDAO.selectSubcategory(subCategory, (page-1) * 10, orderSql);
					}else if(!category.equals("")&& !subCategory.equals("")){
						boardList = BoardDAO.selectCategorySubcategory(category, subCategory, (page-1) * 10, orderSql);
					}
					
				}else {//검색값이 들어갈 때 
					if(category.equals("")&& subCategory.equals("")){
						boardList = BoardDAO.search(searchType, searchText, page, orderSql);
					}else if(!category.equals("")&& subCategory.equals("")){
						boardList = BoardDAO.searchCategory(searchType, searchText, category, (page-1) * 10, orderSql);
					}else if(category.equals("")&& !subCategory.equals("")){
						boardList = BoardDAO.searchSubcategory(searchType, searchText, subCategory, (page-1) * 10, orderSql);
					}else if(!category.equals("")&& !subCategory.equals("")){
						boardList = BoardDAO.searchBoth(searchType, searchText, category, subCategory,  (page-1) * 10, orderSql);
					}
				}		
			}
		}
		
		request.setAttribute("list", boardList);
		
		//옵션에 붙을 목록 설정
		ArrayList<String> categoryList = BoardDAO.optionList("bcategory");
		request.setAttribute("categoryList", categoryList);
		
		ArrayList<String> subCategoryList = null;
		if(category != null) {		
			if(category.equals("food")) {			
				subCategoryList = BoardDAO.subCatgoryList("foodboardview");
				request.setAttribute("subCategoryList", subCategoryList);
			}else if(category.equals("sports")){
				subCategoryList = BoardDAO.subCatgoryList("sportsview");
				request.setAttribute("subCategoryList", subCategoryList);
			}
		}	
		

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
		
		int sum = 0;
		String[] numbers = null;
		if(request.getParameter("check") == null) {
			System.out.println("삭제할 것이 없습니다.");
		}else {
			numbers=request.getParameterValues("check");
			for(String string : numbers) {
				int number = Util.str2Int(string);
				int result = BoardDAO.delete(number);
				sum += result;
			}
			System.out.println(sum + "개 게시글 삭제!!");
			response.sendRedirect("./boardm2");
			
		}
	}
	
}

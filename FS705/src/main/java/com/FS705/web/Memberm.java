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

import com.FS705.dao.LogDAO;
import com.FS705.dao.MemberDAO;
import com.FS705.dto.LogDTO;
import com.FS705.dto.MemberDTO;
import com.FS705.util.Util;


@WebServlet("/memberm")
public class Memberm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Memberm() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
//		if((int) session.getAttribute("grade") != 9) {
//			response.sendRedirect("./error");
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
		logDto.setLogTarget("memberm");
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
				
		String order = request.getParameter("order");
		String grade = request.getParameter("grade");
		String gender = request.getParameter("gender");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		
		request.setAttribute("order", request.getParameter("order")); // order 
		request.setAttribute("grade", request.getParameter("grade")); // grade 
		request.setAttribute("gender", request.getParameter("gender")); // gender
		request.setAttribute("searchType", request.getParameter("searchType")); // searchName
		request.setAttribute("searchText", request.getParameter("searchText")); // search
		
		String orderSql = "";
		if(order != null) {
			if(order.equals("DESC")) {
				orderSql = "ORDER BY no DESC";
			}
		}
		
		ArrayList<MemberDTO> memberList = null;		
		//초기화면 모든 검색, 옵션, 선택 안할때 

		if(searchType == null) {
			memberList = MemberDAO.memberList((page-1)*3, orderSql);
		}else {//검색,옵션 셀렉트 할 때
			if(searchType.equals("all")) { //전체검색 할 때
				System.out.println("전체검색");
					//검색 폼만 누를 때 
				if(grade.equals("")&& gender.equals("")&& searchText.equals("")){
					memberList = MemberDAO.memberList((page-1) * 3, orderSql);
				//ip 선택시
				}else if(!grade.equals("")&& gender.equals("") && searchText.equals("")){				
					memberList = MemberDAO.selectGrade(grade, (page-1) * 3, orderSql);
				}else if(grade.equals("")&& !gender.equals("") && searchText.equals("")){				
					memberList = MemberDAO.selectGender(gender, (page-1) * 3,orderSql);
				}else if(!grade.equals("")&& !gender.equals("") && searchText.equals("")){				
					memberList = MemberDAO.selectGradeGender(grade, gender, (page-1) * 3,orderSql);
				}else if(grade.equals("")&& gender.equals("") && !searchText.equals("")){				
					memberList = MemberDAO.searchAll(searchType, searchText, (page-1) * 3,orderSql);
				}else if(!grade.equals("")&& gender.equals("") && !searchText.equals("")){				
					memberList = MemberDAO.searchAllGrade(searchType, searchText, grade,(page-1) * 3, orderSql);
				}else if(grade.equals("")&& !gender.equals("") && !searchText.equals("")){				
					memberList = MemberDAO.searchAllGender(searchType, searchText, gender, (page-1) * 3,orderSql);
				}else if(!grade.equals("")&& !gender.equals("") && !searchText.equals("")){				
					memberList = MemberDAO.searchAllGradeGender(searchType, searchText, grade, gender, (page-1) * 3,orderSql);
				}
			
			}else {//전체검색 아닐 때
				//검색값 입력 안할 때
				if(searchText.equals("")) {
					if(grade.equals("")&& gender.equals("")){
						memberList = MemberDAO.memberList(page,orderSql);
					}else if(!grade.equals("")&& gender.equals("")){
						memberList = MemberDAO.selectGrade(grade, (page-1) * 3,orderSql);
					}else if(grade.equals("")&& !gender.equals("")){
						memberList = MemberDAO.selectGender(gender, (page-1) * 3,orderSql);
					}else if(!grade.equals("")&& !gender.equals("")){
						memberList = MemberDAO.selectGradeGender(grade, gender, (page-1) * 3,orderSql);
					}
					
				}else {//검색값이 들어갈 때 
					if(grade.equals("")&& gender.equals("")){
						memberList = MemberDAO.search(searchType, searchText, page,orderSql);
					}else if(!grade.equals("")&& gender.equals("")){
						memberList = MemberDAO.searchGrade(searchType, searchText, grade, (page-1) * 3,orderSql);
					}else if(grade.equals("")&& !gender.equals("")){
						memberList = MemberDAO.searchGender(searchType, searchText, gender, (page-1) * 3,orderSql);
					}else if(!grade.equals("")&& !gender.equals("")){
						memberList = MemberDAO.searchBoth(searchType, searchText, grade, gender,  (page-1) * 3,orderSql);
								}
					}		
			}
		}
		
		
		request.setAttribute("list", memberList);

		//옵션에 붙을 grade 목록
		ArrayList<String> gradeList = MemberDAO.optionList("grade");		
		ArrayList<String> genderList = MemberDAO.optionList("sex");
		request.setAttribute("gradeList", gradeList);
		request.setAttribute("genderList", genderList);

		///totalCount 계산
		if(memberList != null && memberList.size() > 0) {
			request.setAttribute("totalCount", memberList.get(0).getTotalCount());
		}
		
		//RD에 붙이기 
		RequestDispatcher rd = request.getRequestDispatcher("./memberm.jsp"); // index.jsp가 열리면서 해당 내용이 뜸 	
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
		logDto.setLogTarget("memberm");
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
			System.out.println("삭제할것이 없습니다.");
		}else {
		numbers = request.getParameterValues("check");
			for (String string : numbers) {
				int number = Util.str2Int(string);
				int result = MemberDAO.delete(number);
				sum += result;
			}
			System.out.println(sum+"명 삭제 완료!!!");
			response.sendRedirect("./memberm");
		}
	}
}

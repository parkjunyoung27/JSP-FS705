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
		logDto.setLogTarget("memberm");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		ArrayList<MemberDTO> memberList = null;
		
		String grade = request.getParameter("grade");
		String gender = request.getParameter("gender");
		request.setAttribute("grade", request.getParameter("grade")); // ip 
		request.setAttribute("gender", request.getParameter("gender")); // gender
		
		//Paging
		int page = 1; //페이지 기본값 설정
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		} // 페이지 있으면 파라미터값에 저장
		request.setAttribute("page", page); //페이지 보내기
				
		//첫화면 일때  grade 아직 설정 안할 때 
		if(request.getParameter("grade") == null &&
				request.getParameter("gender") == null ) {
		memberList = MemberDAO.memberList((page-1) * 20);
		}
		else if(request.getParameter("grade") == "" &&
				request.getParameter("gender") == "" ) {
			memberList = MemberDAO.memberList((page-1) * 20);
		}
		else if(request.getParameter("grade") != "" &&
				request.getParameter("gender") != "" ) {
			memberList = MemberDAO.selectGradeGender(grade, gender,(page-1)*20);
		}
		else if(request.getParameter("grade") != "" &&
				request.getParameter("gender") == "" ) {
			memberList = MemberDAO.selectGrade(grade, (page-1)*20);
		}
		else if(request.getParameter("grade") == "" &&
				request.getParameter("gender") != "" ) {
			memberList = MemberDAO.selectGender(gender, (page-1)*20);
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
	}

}

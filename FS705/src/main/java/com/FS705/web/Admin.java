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
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;


@WebServlet("/admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LogDAO logDao = LogDAO.getInstance();

    public Admin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		//grade가 있어야 됨 grade=9 는 관리자 등급 
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
		logDto.setLogTarget("admin");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		ArrayList<LogDTO> logList = null;

		//Paging
		int page = 1; //페이지 기본값 설정
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		} // 페이지 있으면 파라미터값에 저장
		request.setAttribute("page", page); //페이지 보내기

		
		//첫화면 일때  ip, target 아직 설정 안할 때 
		if(request.getParameter("ip") == null 
				&& request.getParameter("target") == null) {
		logList = logDao.loglist((page-1) * 20);
		}
		//ip와 target이 전체가 선택될때 
		else if(request.getParameter("ip") == "" 
				&& request.getParameter("target") == "") {
		logList = logDao.loglist((page-1) * 20);
		
		//ip와 target 둘 다 null값이 아닐때 
		}else if(request.getParameter("ip") != "" 
				&& request.getParameter("target") != "") {
			String ip = request.getParameter("ip");
			String target = request.getParameter("target");
			logList = logDao.selectIpTarget(ip, target,(page-1) * 20);
			
		//ip값만 null이 아닐때 
		}else if(request.getParameter("ip") != ""
				&& request.getParameter("target") == "") {
			String ip = request.getParameter("ip");
			logList = logDao.selectIP(ip, (page-1) * 20);
		//target값만 null이 아닐때 
		}else if(request.getParameter("target") != ""
				&& request.getParameter("ip") == "") {
			String target = request.getParameter("target");
			logList = logDao.selectTarget(target, (page-1) * 20);
		}
		request.setAttribute("list", logList); // log리스트		
		
		//옵션에 붙을 ip 목록
		ArrayList<String> ipList = logDao.list("logIp");
		request.setAttribute("ipList", ipList);
		//옵션에 붙을 target 목록 
		ArrayList<String> targetList = logDao.list("logTarget");
		request.setAttribute("targetList", targetList);
		
		//ip, target 보내기
		request.setAttribute("ip", request.getParameter("ip")); // ip 
		request.setAttribute("target", request.getParameter("target")); //target
		
		//totalcount 계산
		if(logList != null && logList.size() > 0) {
			request.setAttribute("totalCount", logList.get(0).getTotalCount());
		}
		
		//RD에 붙이기 
		RequestDispatcher rd = request.getRequestDispatcher("./admin.jsp"); // index.jsp가 열리면서 해당 내용이 뜸 	
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
		logDto.setLogTarget("admin");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("post");
		LogDAO.insertLog(logDto);
		
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		request.setAttribute("page", page);

		//---------------------- 값 받아오기 --------------------------
		String searchName = request.getParameter("searchname");//검색 종류
		String search = request.getParameter("adminsearch");//설치입력값

		ArrayList<LogDTO> logList = null;
		if(searchName.equals("all")) {
			logList = logDao.searchAll(searchName, search, (page-1)*20);
		}
		else {
		logList = logDao.search(searchName, search, (page-1)*20);
		}
		RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
		request.setAttribute("list", logList);
		
		//totalount
		if(logList != null && logList.size()>0) {
			request.setAttribute("totalCount", logList.get(0).getTotalCount());
		}
		
		rd.forward(request, response);

	}

}

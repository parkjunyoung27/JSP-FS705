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
//		if((int) session.getAttribute("grade") != 9) {
//			response.sendRedirect("./error");
//		}
		
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
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);

		String order = request.getParameter("order");
		String ip = (String)request.getParameter("ip");
		String target = (String)request.getParameter("target");
		String searchType =(String) request.getParameter("searchType");
		String searchText = (String)request.getParameter("searchText");

		//Paging
		int page = 1; //페이지 기본값 설정
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		} // 페이지 있으면 파라미터값에 저장
		request.setAttribute("page", page); //페이지 보내기

		
		request.setAttribute("order",request.getParameter("order"));
		
		String orderSql = "";
		if(order != null) {
			if(order.equals("DESC")) {
				orderSql = "ORDER BY logNo DESC";							
			}
		}
		
		
		System.out.println(order);
		ArrayList<LogDTO> logList = null;
		
	//초기화면 모든 검색,옵션 선택 안할때  	
		if(searchType == null) {	
			if(ip == null && target == null && searchType == null && searchText == null) {
				logList = logDao.loglist((page-1) * 20,  orderSql);
			}
		}else {//검색,옵션 셀렉트 할 때
			if(searchType.equals("all")) { //전체검색 할 때
				System.out.println("전체검색");
					//검색 폼만 누를 때 
				if(ip.equals("")&& target.equals("")&& searchText.equals("")){
					logList = logDao.loglist((page-1) * 20, orderSql);
				//ip 선택시
				}else if(!ip.equals("")&& target.equals("") && searchText.equals("")){				
					logList = logDao.selectIP(ip, (page-1) * 20, orderSql);
				}else if(ip.equals("")&& !target.equals("") && searchText.equals("")){				
					logList = logDao.selectTarget(target, (page-1) * 20,orderSql);
				}else if(!ip.equals("")&& !target.equals("") && searchText.equals("")){				
					logList = logDao.selectIpTarget(ip, target, (page-1) * 20,orderSql);
				}else if(ip.equals("")&& target.equals("") && !searchText.equals("")){				
					logList = logDao.searchAll(searchType, searchText, (page-1) * 20,orderSql);
				}else if(!ip.equals("")&& target.equals("") && !searchText.equals("")){				
					logList = logDao.searchAllIp(searchType, searchText, ip,(page-1) * 20,orderSql);
				}else if(ip.equals("")&& !target.equals("") && !searchText.equals("")){				
					logList = logDao.searchAllTarget(searchType, searchText, target, (page-1) * 20,orderSql);
				}else if(!ip.equals("")&& !target.equals("") && !searchText.equals("")){				
					logList = logDao.searchAllIpTarget(searchType, searchText, ip, target, (page-1) * 20,orderSql);
				}
			
			}else {//전체검색 아닐 때
				//검색값 입력 안할 때
				if(searchText.equals("")) {
					if(ip.equals("")&& target.equals("")){
						logList = logDao.loglist(page,orderSql);
					}else if(!ip.equals("")&& target.equals("")){
						logList = logDao.selectIP(ip, (page-1) * 20,orderSql);
					}else if(ip.equals("")&& !target.equals("")){
						logList = logDao.selectTarget(target, (page-1) * 20,orderSql);
					}else if(!ip.equals("")&& !target.equals("")){
						logList = logDao.selectIpTarget(ip, target, (page-1) * 20,orderSql);
					}
					
				}else {//검색값이 들어갈 때 
					if(ip.equals("")&& target.equals("")){
						logList = logDao.search(searchType, searchText, page,orderSql);
					}else if(!ip.equals("")&& target.equals("")){
						logList = logDao.searchIp(searchType, searchText, ip, (page-1) * 20,orderSql);
					}else if(ip.equals("")&& !target.equals("")){
						logList = logDao.searchTarget(searchType, searchText, target, (page-1) * 20,orderSql);
					}else if(!ip.equals("")&& !target.equals("")){
						logList = logDao.searchBoth(searchType, searchText, ip, target,  (page-1) * 20,orderSql);
								}
					}		
			}
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
		request.setAttribute("searchType", request.getParameter("searchType")); //target
		request.setAttribute("searchText", request.getParameter("searchText")); //target
		
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
				
		int sum = 0;
		String[] numbers = null;

		if(request.getParameter("check") == null) {
			System.out.println("삭제할것이 없습니다.");
		}else {
		numbers = request.getParameterValues("check");
			for (String string : numbers) {
				int number = Util.str2Int(string);
				int result = LogDAO.deleteLog(number);
				sum += result;
			}
			System.out.println(sum+"개 로그 삭제!!!");
			response.sendRedirect("./admin");
		}
	}

}

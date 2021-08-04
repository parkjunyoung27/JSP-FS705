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
		
		request.setAttribute("list", boardList);
		
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

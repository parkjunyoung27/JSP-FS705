package com.FS705.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.FS705.util.Util;


/**
 * Servlet implementation class Index
 */
@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Index() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//Paging
	int page = 1;
	if(request.getParameter("page") != null) {
		page = Util.str2Int2(request.getParameter("page"));
	}
		
	//Session 불러오기
	HttpSession session = request.getSession();
	String id = "";
	if(session.getAttribute(id) != null) {
		id = (String)session.getAttribute("id");
	}
	
	//log남기기
	HashMap<String, Object>log = new HashMap<String, Object>();
	log.put("ip", Util.getIP(request));
	log.put("id", id);
	log.put("target", "index");
	log.put("etc", request.getHeader("User-Agent"));
	//LogDAO.insertLog(log);
	
	//RD에 붙이기 
	RequestDispatcher rd = request.getRequestDispatcher("./index.jsp"); // index.jsp가 열리면서 해당 내용이 뜸 
	rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}

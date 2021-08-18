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
import com.FS705.dao.SportsDAO;
import com.FS705.dto.BoardDTO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;

@WebServlet("/sports")
public class Sports extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Sports() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();

		String id = "";
		if(session.getAttribute(id) != null) {
			id = (String)session.getAttribute("id");
		}
		
		LogDTO logDto = new LogDTO();
				
		logDto.setLogIp(Util.getIP(request));
		logDto.setLogTarget("Sports");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		
		ArrayList<BoardDTO> list = null;
		if(request.getParameter("category")==null) {
			list = SportsDAO.getInstance().sportsList((page - 1) * 10);
			request.setAttribute("dto", list);
		} else {
			int category = Util.str2Int(request.getParameter("category"));					
			list = SportsDAO.getInstance().selectList((page - 1) * 10, category);
			request.setAttribute("dto", list);
		}
		
		if (list.size() > 0) {
			request.setAttribute("totalCount", list.get(0).getSportsCount());
		}	
		request.setAttribute("page", page);
		RequestDispatcher rd = request.getRequestDispatcher("./sports/sports.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();

		String id = "";
		if(session.getAttribute(id) != null) {
			id = (String)session.getAttribute("id");
		}
		
		LogDTO logDto = new LogDTO();
				
		logDto.setLogIp(Util.getIP(request));
		logDto.setLogTarget("Sports");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("post");
		LogDAO.insertLog(logDto);
		
		String searchOption = request.getParameter("searchOption");
		String search = request.getParameter("search");
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}		
		ArrayList<BoardDTO> list = SportsDAO.getInstance().searchList(searchOption, search, (page - 1) * 10);
		if(list != null && list.size() > 0) {
			request.setAttribute("totalCount", list.get(0).getSportsCount());
		}
		request.setAttribute("page", page);
		request.setAttribute("dto", list);
		RequestDispatcher rd = request.getRequestDispatcher("./sports/sports.jsp");
		rd.forward(request, response);		
		
	}

}

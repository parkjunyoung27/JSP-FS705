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
import com.FS705.dao.NoticeBoardDAO;
import com.FS705.dto.LogDTO;
import com.FS705.dto.NoticeBoardDTO;
import com.FS705.util.Util;

@WebServlet("/notice")
public class Notice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Notice() {
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
		logDto.setLogTarget("Notice");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		
		ArrayList<NoticeBoardDTO> list = null;
		if(request.getParameter("category") == null && request.getParameter("sort") == null) {
			list = NoticeBoardDAO.getInstance().boardList((page - 1) * 10);
			request.setAttribute("dto", list);
		} else if(request.getParameter("category") != null || request.getParameter("sort") == null) {
			int category = Util.str2Int(request.getParameter("category"));					
			list = NoticeBoardDAO.getInstance().selectList((page - 1) * 10, category);
			request.setAttribute("dto", list);
		} else if(request.getParameter("sort") != null) {
			int sort = Util.str2Int(request.getParameter("sort"));					
			list = NoticeBoardDAO.getInstance().sortList((page - 1) * 10, sort);
			request.setAttribute("dto", list);
		}
		
		if (list.size() > 0) {
			request.setAttribute("totalCount", list.get(0).getBoardcount());
		}	
		request.setAttribute("page", page);
		RequestDispatcher rd = request.getRequestDispatcher("./notice.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

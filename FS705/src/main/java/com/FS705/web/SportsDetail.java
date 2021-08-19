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
import com.FS705.dao.SportsCommentDAO;
import com.FS705.dao.SportsDAO;
import com.FS705.dto.BoardDTO;
import com.FS705.dto.CommentDTO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;

@WebServlet("/sportsDetail")
public class SportsDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SportsDetail() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String id = "";
		if(session.getAttribute(id) != null) {
			id = (String) session.getAttribute("id");
		}
		
		LogDTO logDto = new LogDTO();
		
		logDto.setLogIp(Util.getIP(request));
		logDto.setLogTarget("SportsDetail");
		logDto.setLogdId((String) session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		if(request.getParameter("bno") != null 
				&& Util.str2Int2(request.getParameter("bno")) != 0
				){
			
		RequestDispatcher rd = 
				request.getRequestDispatcher("./sports/sportsDetail.jsp");
		
		SportsDAO.getInstance().count(Util.str2Int(request.getParameter("bno")));
		BoardDTO dto = 
				SportsDAO.getInstance().detail(Util.str2Int(request.getParameter("bno")));
		request.setAttribute("dto", dto);
		
		if(dto.getCommentCount() > 0) {
			ArrayList<CommentDTO> cmtdto 
							= SportsCommentDAO.getInstance().sportsCommentList((dto.getBno()));
			request.setAttribute("cmtdto", cmtdto);
		}
		
		rd.forward(request, response);
			
		} else {
			response.sendRedirect("./error?code=sportsDetailError");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

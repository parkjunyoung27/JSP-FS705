package com.FS705.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.LogDAO;
import com.FS705.dao.SportsDAO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;

@WebServlet("/sportsVote")
public class SportsVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SportsVote() {
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
		logDto.setLogTarget("SportsVote");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		int result = 0;
		result = SportsDAO.getInstance().sportsVote(Util.str2Int(request.getParameter("bno")), request.getParameter("vote"));
		
		if(result == 1) {
			response.sendRedirect("./sportsDetail?bno="+request.getParameter("bno"));
		} else {
			response.sendRedirect("./error?code=sportsVoteError");
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

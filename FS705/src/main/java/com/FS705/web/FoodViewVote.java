package com.FS705.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.FoodBoardDAO;
import com.FS705.dao.FoodCommentDAO;
import com.FS705.dao.LogDAO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;

@WebServlet("/foodViewVote")
public class FoodViewVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FoodViewVote() {
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
		logDto.setLogTarget("FoodViewVote");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		int result = 0;
		if(request.getParameter("bno") != null && request.getParameter("vote") != null
				&& Util.str2Int(request.getParameter("bno"))!= 0) {
		result = FoodBoardDAO.getInstance().boardViewVote(Util.str2Int(request.getParameter("bno")), request.getParameter("vote"));
		} else if (request.getParameter("bno") != null 	&& Util.str2Int(request.getParameter("bno")) != 0 
				&& request.getParameter("cno") != null && Util.str2Int(request.getParameter("cno")) != 0) {
			result = FoodCommentDAO.getInstance().foodCommentLike(Util.str2Int(request.getParameter("cno")));
		}
		if(result == 1) {
			response.sendRedirect("./foodView?bno="+request.getParameter("bno"));
		} else {
			response.sendRedirect("./error?code=foodViewVoteError");
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

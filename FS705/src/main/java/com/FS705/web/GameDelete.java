package com.FS705.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.GameBoardDAO;
import com.FS705.dao.LogDAO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;

@WebServlet("/gameDelete")
public class GameDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GameDelete() {
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
		logDto.setLogTarget("GameDelete");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("post");
		LogDAO.insertLog(logDto);
		
		if(request.getSession().getAttribute("id") != null && request.getSession().getAttribute("name") != null
		&& request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0 ) {
//		int test = 1;
//		if(test==1) {
			int result = 0;
			id = (String)request.getSession().getAttribute("id");
			int bno = Util.str2Int(request.getParameter("bno"));
			result = GameBoardDAO.getInstance().boardDelete(bno, id);
			if(result == 1) {
				response.sendRedirect("./gameBoard");
			} else {
				response.sendRedirect("./error?code=gameDeleteError1");				
			}
		} else {
			response.sendRedirect("./error?code=gameDeleteError2");
		}		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

package com.FS705.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.HumorBoardDAO;
import com.FS705.dao.LogDAO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;

@WebServlet("/humorDelete")
public class HumorDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HumorDelete() {
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
		logDto.setLogTarget("HumorDelete");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
//		if(request.getSession().getAttribute("id") != null && request.getSession().getAttribute("name") != null
//		&& request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0 ) {
		int test = 1;
		if(test==1) {
			int result = 0;
			id = "kimkim";
			int bno = Util.str2Int(request.getParameter("bno"));
			result = HumorBoardDAO.getInstance().boardDelete(bno, id);
			if(result == 1) {
				response.sendRedirect("./humorBoard");
			} else {
				response.sendRedirect("./error?code=humorDeleteError1");				
			}
		} else {
			response.sendRedirect("./error?code=humorDeleteError2");
		}		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

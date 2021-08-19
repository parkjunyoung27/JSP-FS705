package com.FS705.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.FoodCommentDAO;
import com.FS705.dao.LogDAO;
import com.FS705.dto.FoodCommentDTO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;

@WebServlet("/foodCommentWrite")
public class FoodCommentWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FoodCommentWrite() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		logDto.setLogTarget("FoodCommentWrite");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("post");
		LogDAO.insertLog(logDto);
		
		if(request.getSession().getAttribute("id") != null && request.getSession().getAttribute("name") != null
				&& request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0 && request.getParameter("ccontent") != null) {	      	
			int result = 0;
			FoodCommentDTO cmt = new FoodCommentDTO();
			cmt.setBno(Util.str2Int(request.getParameter("bno")));
			cmt.setCcontent(request.getParameter("ccontent"));
			cmt.setId((String) request.getSession().getAttribute("id"));
			cmt.setCip(Util.getIP(request));
			result = FoodCommentDAO.getInstance().foodCommentWrite(cmt);
			if(result == 1){
				response.sendRedirect("./foodView?bno="+cmt.getBno());
			}else {
				response.sendRedirect("./error?code=foodCommentWriteError1");				
			}			
		} else {
			response.sendRedirect("./error?code=foodCommentWriteError2");			
		}		
	}
}

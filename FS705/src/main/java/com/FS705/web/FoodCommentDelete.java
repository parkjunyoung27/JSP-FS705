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

@WebServlet("/foodCommentDelete")
public class FoodCommentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FoodCommentDelete() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String id = "";
		if(session.getAttribute(id) != null) {
			id = (String)session.getAttribute("id");
		}
		
		LogDTO logDto = new LogDTO();
				
		logDto.setLogIp(Util.getIP(request));
		logDto.setLogTarget("FoodCommentDelete");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("post");
		LogDAO.insertLog(logDto);
		
		if(request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0
		&& request.getParameter("cno") != null && Util.str2Int(request.getParameter("cno")) != 0
		&& request.getSession().getAttribute("id") != null){	
			int result = 0;
			int bno = Util.str2Int(request.getParameter("bno"));
			int cno = Util.str2Int(request.getParameter("cno"));
			
			FoodCommentDTO cmt = new FoodCommentDTO();
			cmt.setId((String) request.getSession().getAttribute("id"));
			int check = FoodCommentDAO.getInstance().checkWriter(bno, cno, id);
			//작성 글 & id 확인
			if(check == 1 || Util.str2Int((String) request.getSession().getAttribute("grade"))==9) {
				result = FoodCommentDAO.getInstance().foodCommentDelete(bno, cno);				
			}
			
			
			if(result == 1) {
				response.sendRedirect("./foodView?bno="+cmt.getBno());
			} else {
				response.sendRedirect("./error?code=foodCommentDeleteError1");				
			}
		} else {
			response.sendRedirect("./error?code=foodCommentDeleteError2");
		}		
	}
}

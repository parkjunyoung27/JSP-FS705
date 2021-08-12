package com.FS705.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FS705.dao.HumorBoardDAO;
import com.FS705.util.Util;

@WebServlet("/humorViewVote")
public class HumorViewVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HumorViewVote() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;
		result = HumorBoardDAO.getInstance().boardViewVote(Util.str2Int(request.getParameter("bno")), request.getParameter("vote"));
		
		if(result == 1) {
			response.sendRedirect("./humorView?bno="+request.getParameter("bno"));
		} else {
			response.sendRedirect("./error?code=humorViewVoteError");
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

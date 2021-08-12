package com.FS705.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FS705.dao.GameBoardDAO;
import com.FS705.util.Util;

@WebServlet("/gameViewVote")
public class GameViewVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GameViewVote() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;
		result = GameBoardDAO.getInstance().boardViewVote(Util.str2Int(request.getParameter("bno")), request.getParameter("vote"));
		
		if(result == 1) {
			response.sendRedirect("./gameView?bno="+request.getParameter("bno"));
		} else {
			response.sendRedirect("./error?code=gameViewVoteError");
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

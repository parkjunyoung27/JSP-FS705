package com.FS705.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FS705.dao.GameBoardDAO;
import com.FS705.dao.GameCommentDAO;
import com.FS705.dto.GameBoardDTO;
import com.FS705.dto.GameCommentDTO;
import com.FS705.util.Util;

@WebServlet("/gameView")
public class GameView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GameView() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0) {
		RequestDispatcher rd = request.getRequestDispatcher("./game/gameView.jsp");		
		
		//게시글 조회수 카운트
		GameBoardDAO.getInstance().boardViewCount(Util.str2Int(request.getParameter("bno")));
		GameBoardDTO dto = GameBoardDAO.getInstance().boardView(Util.str2Int(request.getParameter("bno")));		
		request.setAttribute("dto", dto);
		
		//댓글 존재하면 불러오기
		if(dto.getGamecommentcount()>0) {
			ArrayList<GameCommentDTO> cmtdto = GameCommentDAO.getInstance().boardCommentList((dto.getBno()));
			request.setAttribute("cmtdto", cmtdto);			
		}
		
		rd.forward(request, response);
		} else {
			response.sendRedirect("./error?code=gameViewError");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

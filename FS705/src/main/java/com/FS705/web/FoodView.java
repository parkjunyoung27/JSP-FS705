package com.FS705.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FS705.dao.FoodBoardDAO;
import com.FS705.dao.FoodCommentDAO;
import com.FS705.dto.FoodBoardDTO;
import com.FS705.dto.FoodCommentDTO;
import com.FS705.util.Util;

@WebServlet("/foodView")
public class FoodView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FoodView() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0) {
		RequestDispatcher rd = request.getRequestDispatcher("./food/foodView.jsp");		
		//게시글 조회수 카운트
		FoodBoardDAO.getInstance().boardViewCount(Util.str2Int(request.getParameter("bno")));
		FoodBoardDTO dto = FoodBoardDAO.getInstance().boardView(Util.str2Int(request.getParameter("bno")));		
		request.setAttribute("dto", dto);
		//댓글 존재하면 불러오기
		if(dto.getFoodcommentcount()>0) {
			ArrayList<FoodCommentDTO> cmtdto = FoodCommentDAO.getInstance().boardCommentList((dto.getBno()));
			request.setAttribute("cmtdto", cmtdto);			
		}
		
		rd.forward(request, response);
		} else {
			response.sendRedirect("./error?code=foodViewError");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

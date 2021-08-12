package com.FS705.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FS705.dao.HumorBoardDAO;
import com.FS705.dao.HumorCommentDAO;
import com.FS705.dto.HumorBoardDTO;
import com.FS705.dto.HumorCommentDTO;
import com.FS705.util.Util;

@WebServlet("/humorView")
public class HumorView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HumorView() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0) {
		RequestDispatcher rd = request.getRequestDispatcher("./humor/humorView.jsp");		
		//게시글 조회수 카운트
		HumorBoardDAO.getInstance().boardViewCount(Util.str2Int(request.getParameter("bno")));
		HumorBoardDTO dto = HumorBoardDAO.getInstance().boardView(Util.str2Int(request.getParameter("bno")));		
		request.setAttribute("dto", dto);
		//댓글 존재하면 불러오기
		if(dto.getHumorcommentcount()>0) {
			ArrayList<HumorCommentDTO> cmtdto = HumorCommentDAO.getInstance().boardCommentList((dto.getBno()));
			request.setAttribute("cmtdto", cmtdto);			
		}
		
		rd.forward(request, response);
		} else {
			response.sendRedirect("./error?code=humorViewError");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

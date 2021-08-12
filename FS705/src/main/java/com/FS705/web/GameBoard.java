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
import com.FS705.dto.GameBoardDTO;
import com.FS705.util.Util;

@WebServlet("/gameBoard")
public class GameBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GameBoard() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		
		ArrayList<GameBoardDTO> list = null;
		if(request.getParameter("category")==null) {
			list = GameBoardDAO.getInstance().boardList((page - 1) * 10);
			request.setAttribute("dto", list);
		} else {
			int category = Util.str2Int(request.getParameter("category"));					
			list = GameBoardDAO.getInstance().selectList((page - 1) * 10, category);
			request.setAttribute("dto", list);
		}
		
		if (list.size() > 0) {
			request.setAttribute("totalCount", list.get(0).getGameboardcount());
		}	
		request.setAttribute("page", page);
		RequestDispatcher rd = request.getRequestDispatcher("./game/gameBoard.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String searchOption = request.getParameter("searchOption");
		String search = request.getParameter("search");
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}		
		ArrayList<GameBoardDTO> list = GameBoardDAO.getInstance().searchList(searchOption, search, (page - 1) * 10);
		if(list != null && list.size() > 0) {
			request.setAttribute("totalCount", list.get(0).getGameboardcount());
		}
		request.setAttribute("page", page);
		request.setAttribute("dto", list);
		RequestDispatcher rd = request.getRequestDispatcher("./game/gameBoard.jsp");
		rd.forward(request, response);		
	}
}

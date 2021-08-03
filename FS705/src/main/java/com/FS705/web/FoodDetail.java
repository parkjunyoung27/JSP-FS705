package com.FS705.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FS705.dao.FoodBoardDAO;
import com.FS705.util.Util;

@WebServlet("/foodDetail")
public class FoodDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FoodDetail() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0) {
		RequestDispatcher rd = request.getRequestDispatcher("./foodDetail.jsp");
		request.setAttribute("dto", FoodBoardDAO.getInstance().boardView(Util.str2Int(request.getParameter("bno"))));
		rd.forward(request, response);
		} else {
			response.sendRedirect("./error?code=foodDetailError");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

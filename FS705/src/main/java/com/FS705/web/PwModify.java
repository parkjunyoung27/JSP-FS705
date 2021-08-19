package com.FS705.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FS705.dao.LoginDAO;
import com.FS705.dto.LoginDTO;

@WebServlet("/pwModify")
public class PwModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PwModify() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글처리
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//값이 있으면 
		if(request.getParameter("id") != null 
				&& request.getParameter("pw1") != null
			) {
			
			String id = request.getParameter("id");
			String pw = request.getParameter("pw1");
			
			//DTO에 담아 DAO로 보내기
			LoginDTO dto = new LoginDTO();
			dto.setId(id);
			dto.setPw(pw);
			
			int result = LoginDAO.getInstance().pwUpdate(dto);
			
			//성공시 디스패쳐로 joinResult.jsp에 연결
			RequestDispatcher rd = request.getRequestDispatcher("./pwUpdateResult.jsp");
			request.setAttribute("updateResult", result);
			rd.forward(request, response);
			
		} else {
			//아닐시 5985에러페이지로
			response.sendRedirect("error.jsp?error=5985");
		}
	}
	

}

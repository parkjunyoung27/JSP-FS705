package com.FS705.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.LoginDAO;
import com.FS705.dto.LoginDTO;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("./plusBar.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		//개인정보 담아오기
		LoginDAO dao = LoginDAO.getInstance();
		LoginDTO dto = new LoginDTO();
		
		dto.setId(id);
		dto.setPw(pw);
		LoginDTO result = dao.login(dto);
		
		//로그인 성공실패 비교하기
		if(result == null || result.getName() == null) {
			//실패시 -> 인덱스
			response.sendRedirect("./error.jsp");			
		}else {
			//세션만들고 값 저장
			HttpSession session = request.getSession();
			session.setAttribute("id", result.getId());
			session.setAttribute("name", result.getName());
			session.setAttribute("grade", result.getGrade());
			session.setAttribute("profile", result.getProfile());
			
			//성공시 -> 인덱스
			response.sendRedirect("./index.jsp");
		}
	
	}

}

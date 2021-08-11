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



@WebServlet("/join")
public class join extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public join() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글처리
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

		//값이 null이 아니면 mariaDB에 값넣기
		if(request.getParameter("id") != null 
			&& request.getParameter("pw1") != null
			&& request.getParameter("pw2") != null
			&& request.getParameter("nickName") != null
			&& request.getParameter("sex") != null
			&& request.getParameter("birthDate") != null
			&& request.getParameter("email") != null
			&& request.getParameter("hint") != null
			&& request.getParameter("hintAnswer") != null
			&& request.getParameter("pw1").equals(request.getParameter("pw2"))
			) {
			
			String id = request.getParameter("id");
			String pw1 = request.getParameter("pw1");
			String nickName = request.getParameter("nickName");
			String sex = request.getParameter("sex");
			String birthDate = request.getParameter("birthDate");
			String email = request.getParameter("email");
			String hint	= request.getParameter("hint");
			String hintAnswer = request.getParameter("hintAnswer");
			
			//DTO에 담아 DAO로 보내기
			LoginDTO dto = new LoginDTO();
			dto.setId(id);
			dto.setPw(pw1);
			dto.setName(nickName);
			dto.setSex(sex);
			dto.setBirthdate(birthDate);
			dto.setEmail(email);
			dto.setHint(hint);
			dto.setHintAnswer(hintAnswer);
			
			LoginDAO dao = LoginDAO.getInstance();
			int count = dao.join(dto);
			
			//성공시 디스패쳐로 joinResult.jsp에 연결
			RequestDispatcher rd = request.getRequestDispatcher("./joinResult.jsp");
			request.setAttribute("count", count);
			request.setAttribute("id", id);
			
			rd.forward(request, response);
			
		} else {
			//아닐시 5985에러페이지로
			response.sendRedirect("error.jsp?error=5985");
		}
	}
}

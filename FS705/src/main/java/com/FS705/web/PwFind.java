package com.FS705.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.LoginDAO;
import com.FS705.dto.LoginDTO;

@WebServlet("/pwFind")
public class PwFind extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PwFind() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글처리
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//값받아오기
		//각값이 존재한다면 해당값이 테이블이 존재하는지 확인
		if(request.getParameter("id") != null
				&& request.getParameter("email") != null
				&& request.getParameter("pwAnswer") != null) {
			
			String id = request.getParameter("id");
			String email = request.getParameter("email");
			String pwAnswer = request.getParameter("pwAnswer");
			
        	//DAO 호출
        	int checkAnswer = LoginDAO.getInstance().pwFind(id, email, pwAnswer);
        	
        	//pwFindResult로 결과 송신
        	HttpSession session = request.getSession();
        	session.setAttribute("checkAnswer", checkAnswer);
        	session.setAttribute("id", id);
        	response.sendRedirect("./pwFindResult.jsp");
		}  else {
			//아닐시 5985에러페이지로
			response.sendRedirect("error.jsp?error=5985");
		}
	}
}

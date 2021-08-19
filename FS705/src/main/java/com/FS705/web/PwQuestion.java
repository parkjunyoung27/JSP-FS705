package com.FS705.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FS705.dao.LoginDAO;
import com.FS705.dto.LoginDTO;

@WebServlet("/pwQuestion")
public class PwQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PwQuestion() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글처리
		request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    if(request.getParameter("id") != null 
        		&& request.getParameter("email") != null
        		) {
        	//dto에 담기
        	String id = request.getParameter("id");
        	String email = request.getParameter("email");
        	
        	//DTO에 담기
        	LoginDTO dto = new LoginDTO();
        	//DAO 호출 및 DTO값 리턴
        	String hintResult = LoginDAO.getInstance().pwQuestion(id, email);
        	//결과값 보내기 
    		PrintWriter pw = response.getWriter();
    		pw.println(hintResult);
	    }
	}
}

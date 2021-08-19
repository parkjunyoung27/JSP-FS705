package com.FS705.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.LoginDAO;
import com.FS705.dto.LoginDTO;

@WebServlet("/idFind")
public class IdFind extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IdFind() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글처리
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		
        //값이 null이 아니라면 변수를 지정하고 DAO 호출
        if(request.getParameter("name") != null 
        		&& request.getParameter("sex") != null
        		&& request.getParameter("birthDate") != null
        		) {
        	//dto에 담기
        	String name = request.getParameter("name");
        	String sex = request.getParameter("sex");
        	String birthDate = request.getParameter("birthDate");

        	//DTO에 담기
        	LoginDTO dto = new LoginDTO();
        	dto.setName(name);
        	dto.setSex(sex);
        	dto.setBirthdate(birthDate);
        	
        	//DAO 호출 및 DTO값 리턴
        	dto = LoginDAO.getInstance().idFind(dto);
        	
        	//세션만들고 값보내기
        	HttpSession session = request.getSession();
        	session.setAttribute("id2", dto.getId());
        	response.sendRedirect("./idFindResult.jsp");
        } 

	}

}

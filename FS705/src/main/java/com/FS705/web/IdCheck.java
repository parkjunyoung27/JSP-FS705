package com.FS705.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FS705.dao.LoginDAO;

@WebServlet("/idCheck")
public class IdCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IdCheck() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//파라미터 값 받아오기 및 확인 - 정상
		String id = request.getParameter("id");
		//DAO에서 결과값 받아오기 - 정상
		int idResult = LoginDAO.getInstance().idCheck(id);
		//결과값 보내기 
		PrintWriter pw = response.getWriter();
		pw.println(idResult);
		
	}

}

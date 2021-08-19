package com.FS705.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.FoodBoardDAO;
import com.FS705.dao.GameBoardDAO;
import com.FS705.dao.HumorBoardDAO;
import com.FS705.dao.LogDAO;
import com.FS705.dao.NoticeBoardDAO;
import com.FS705.dao.SportsDAO;
import com.FS705.dto.BoardDTO;
import com.FS705.dto.FoodBoardDTO;
import com.FS705.dto.GameBoardDTO;
import com.FS705.dto.HumorBoardDTO;
import com.FS705.dto.LogDTO;
import com.FS705.dto.NoticeBoardDTO;
import com.FS705.util.Util;

@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Index() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");

	//Paging
	int page = 1;
	if(request.getParameter("page") != null) {
		page = Util.str2Int2(request.getParameter("page"));
	}
	request.setAttribute("page", page);
	
	//-----------------log남기기----------------------------------------
	//Session 불러오기
	HttpSession session = request.getSession();
	String id = "";
	if(session.getAttribute(id) != null) {
		id = (String)session.getAttribute("id");
	}
	
	LogDTO logDto = new LogDTO();
	
	logDto.setLogIp(Util.getIP(request));
	logDto.setLogTarget("index");
	logDto.setLogdId((String)session.getAttribute(id));
	logDto.setLogEtc(request.getHeader("User-Agent"));
	logDto.setLogMethod("get");

	LogDAO.insertLog(logDto);
	//공지사항 게시글
	ArrayList<NoticeBoardDTO> nlist = null;
	nlist = NoticeBoardDAO.getInstance().noticeBoardLike();
	request.setAttribute("ndto", nlist);
	
	//foodBoard 추천 게시글
	ArrayList<FoodBoardDTO> flist = null;
	flist = FoodBoardDAO.getInstance().foodBoardLike();
	request.setAttribute("fdto", flist);
	
	//sportsBoard 추천 게시글
	ArrayList<BoardDTO> slist = null;
	slist = SportsDAO.getInstance().sportsLike();
	request.setAttribute("sdto", slist);
	
	//gameBoard 추천 게시글
	ArrayList<GameBoardDTO> glist = null;
	glist = GameBoardDAO.getInstance().gameBoardLike();
	request.setAttribute("gdto", glist);
	
	//humorBoard 추천 게시글
	ArrayList<HumorBoardDTO> hlist = null;
	hlist = HumorBoardDAO.getInstance().humorBoardLike();
	request.setAttribute("hdto", hlist);
	
	//RD에 붙이기 
	RequestDispatcher rd = request.getRequestDispatcher("./index.jsp"); // index.jsp가 열리면서 해당 내용이 뜸 
	rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}

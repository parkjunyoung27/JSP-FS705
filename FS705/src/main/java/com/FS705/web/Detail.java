package com.FS705.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.LogDAO;
import com.FS705.dao.NoticeBoardDAO;
import com.FS705.dto.LogDTO;
import com.FS705.dto.NoticeBoardDTO;
import com.FS705.util.Util;

@WebServlet("/detail")
public class Detail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Detail() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = "";
		if(session.getAttribute(id) != null) {
			id = (String)session.getAttribute("id");
		}
		
		LogDTO logDto = new LogDTO();
				
		logDto.setLogIp(Util.getIP(request));
		logDto.setLogTarget("NoticeDetail");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		if(request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0) {
		RequestDispatcher rd = request.getRequestDispatcher("./detail.jsp");		
		int bno = 0;
		
		//이전글 다음글 
		if(request.getParameter("nextPrev") == null && Util.str2Int2(request.getParameter("nextPrev")) == 0) {
			bno = Util.str2Int(request.getParameter("bno"));			
		} else {
			bno = NoticeBoardDAO.getInstance().nextPrev(Util.str2Int(request.getParameter("bno")), Util.str2Int(request.getParameter("nextPrev")));
		}
		//게시글 조회수 카운트
		NoticeBoardDAO.getInstance().boardViewCount(bno);
		NoticeBoardDTO dto = NoticeBoardDAO.getInstance().boardView(bno);		
		request.setAttribute("dto", dto);
	
		//댓글 존재하면 불러오기
//		if(dto.getFoodcommentcount()>0) {
//			ArrayList<NoticeCommentDTO> cmtdto = NoticeCommentDAO.getInstance().boardCommentList((dto.getBno()));
//			request.setAttribute("cmtdto", cmtdto);			
//		}
//		
		rd.forward(request, response);
		} else {
			response.sendRedirect("./error?code=foodViewError");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

package com.FS705.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.HumorCommentDAO;
import com.FS705.dao.LogDAO;
import com.FS705.dto.HumorCommentDTO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;

@WebServlet("/humorCommentModify")
public class HumorCommentModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HumorCommentModify() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String id = "";
		if (session.getAttribute(id) != null) {
			id = (String) session.getAttribute("id");
		}

		LogDTO logDto = new LogDTO();

		logDto.setLogIp(Util.getIP(request));
		logDto.setLogTarget("HumorCommentModify");
		logDto.setLogdId((String) session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("post");
		LogDAO.insertLog(logDto);
		request.setCharacterEncoding("UTF-8");

		if (request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0
				&& request.getParameter("cno") != null && Util.str2Int(request.getParameter("cno")) != 0
				&& request.getParameter("ccontent") != null && request.getSession().getAttribute("id") != null) {

			int result = 0;
			HumorCommentDTO cmt = new HumorCommentDTO();
			cmt.setBno(Util.str2Int(request.getParameter("bno")));
			cmt.setCno(Util.str2Int(request.getParameter("cno")));
			cmt.setCcontent(Util.replace(request.getParameter("ccontent")));
			cmt.setCip(Util.getIP(request));
			cmt.setId((String) request.getSession().getAttribute("id"));
			result = HumorCommentDAO.getInstance().humorCommentModify(cmt);

			if (result == 1) {
				response.sendRedirect("./humorView?bno=" + cmt.getBno());
			} else {
				response.sendRedirect("./error?code=humorCommentModifyError1");
			}
		} else {
			response.sendRedirect("./error?code=humorCommentModifyError2");
		}
	}

}

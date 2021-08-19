package com.FS705.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.LogDAO;
import com.FS705.dao.SportsDAO;
import com.FS705.dto.LogDTO;
import com.FS705.util.FileThing;
import com.FS705.util.Util;

@WebServlet("/sportsDelete")
public class SportsDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SportsDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		String id = "";
		if(session.getAttribute(id) != null) {
			id = (String)session.getAttribute("id");
		}
		
		LogDTO logDto = new LogDTO();
				
		logDto.setLogIp(Util.getIP(request));
		logDto.setLogTarget("SportsDelete");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		if (request.getParameter("bno") != null && Util.str2Int2(request.getParameter("bno")) != 0
				&& session.getAttribute("id") != null) {
			
			int result = 0;
			id = "kwon";
			SportsDAO dao = SportsDAO.getInstance();
			int bno = Util.str2Int(request.getParameter("bno"));
			ArrayList<String> fileName = dao.findFileName(bno, id);

			if (fileName != null) {
				FileThing ft = new FileThing();
				String path = request.getSession().getServletContext().getRealPath("/");
				if(fileName.get(0) != null) {					
					ft.fileDelete(path + "upload" + File.separator + "sportsFile" + File.separator, fileName.get(0));
				}
				if(fileName.get(1) != null) {					
					ft.fileDelete(path + "upload" + File.separator + "sportsThumbnail" + File.separator, fileName.get(1));
				}
			}
			
			result = dao.delete(bno, id);
			
			if(result == 1) {
				response.sendRedirect("./sports");				
			}else {
				response.sendRedirect("./error?code=sportsDelete");
			}
			
		} else {
			response.sendRedirect("./error?code=sportsDelete");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}

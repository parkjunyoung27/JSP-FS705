package com.FS705.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
		
		// gno들어오는지, 세션있는지
		if (request.getParameter("gno") != null && Util.str2Int2(request.getParameter("gno")) != 0
				&& session.getAttribute("id") != null) {
			// 1. 파일이 있는지 확인
			SportsDAO dao = SportsDAO.getInstance();
			// 1-1. map을 만들어서 보내주세요. gno, id
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("gno", request.getParameter("gno"));
			map.put("id", session.getAttribute("id"));

			ArrayList<String> fileName = dao.findFileName(map);
			System.out.println("fileName : " + fileName);

			if (fileName != null) {
				FileThing ft = new FileThing();
				String path = request.getSession().getServletContext().getRealPath("/");
				// String path2 = request.getServletContext().getRealPath("/");
				if(fileName.get(0) != null) {					
					ft.fileDelete(path + "upload" + File.separator, fileName.get(0));
				}
				if(fileName.get(1) != null) {					
					ft.fileDelete(path + "thumbnail" + File.separator, fileName.get(1));
				}
			}
			// 2. 파일 먼저 삭제
			// 3. 데이터 베이스 삭제
			int result = dao.del(map);
			// 4. 페이지 이동
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
		doGet(request, response);
	}

}

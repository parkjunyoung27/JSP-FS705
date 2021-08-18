package com.FS705.web;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.LogDAO;
import com.FS705.dao.SportsDAO;
import com.FS705.dto.BoardDTO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/sportsModify")
public class SportsModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SportsDAO dao = SportsDAO.getInstance();
       
    public SportsModify() {
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
		logDto.setLogTarget("SportsModify");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		if(request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0) {
		
			id = "kwon";
			BoardDTO modifyImport = SportsDAO.getInstance().modifyImport(Util.str2Int(request.getParameter("bno")), id);		
			RequestDispatcher rd = request.getRequestDispatcher("./sports/sportsModify.jsp");
			request.setAttribute("modifyImport", modifyImport);
			rd.forward(request, response);
			
		} else {
			response.sendRedirect("./error?code=sportsModifyError1");
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();

		String id = "";
		if(session.getAttribute(id) != null) {
			id = (String)session.getAttribute("id");
		}
		
		LogDTO logDto = new LogDTO();
				
		logDto.setLogIp(Util.getIP(request));
		logDto.setLogTarget("SportsModify");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("post");
		LogDAO.insertLog(logDto);
		
		request.setCharacterEncoding("UTF-8");
		String path = request.getServletContext().getRealPath("/");
		String savePath = path + "upload" + File.separator + "sportsFile" + File.separator;
		int size = 1024 * 1024 * 20;
		
		MultipartRequest multi = new MultipartRequest(
				request, savePath, size, "UTF-8", new DefaultFileRenamePolicy());
		
		if(multi.getParameter("bno") != null 
				&& Util.str2Int2(multi.getParameter("bno")) != 0) {
			
			id = "kwon";
			int result = 0;
			int bno = Util.str2Int(multi.getParameter("bno"));
			String title = Util.replace(multi.getParameter("title"));
			String content = Util.replace(multi.getParameter("content"));
			String subCategory = multi.getParameter("semiCate");
			
			BoardDTO dto = new BoardDTO();
			dto.setBno(bno);
			dto.setBtitle(title);
			dto.setBcontent(content);
			dto.setSubCategory(subCategory);
			dto.setId(id);
			
			result = dao.sportsModify(dto);
			
			if(result == 1) {
				response.sendRedirect("./sportsDetail?bno=" + bno);
			} else { 
				response.sendRedirect("./error?code=sportsModifyError2");
			}
		} else {
			response.sendRedirect("./error?code=sportsModifyError3");
		}
	}

}

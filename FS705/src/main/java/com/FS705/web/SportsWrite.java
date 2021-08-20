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

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.FS705.dao.LogDAO;
import com.FS705.dao.SportsDAO;
import com.FS705.dto.BoardDTO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;

@WebServlet("/sportsWrite")
public class SportsWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public SportsWrite() {
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
		logDto.setLogTarget("SportsWrite");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
    	
    	RequestDispatcher rd 
    			= request.getRequestDispatcher("./sports/sportsWrite.jsp");
    	rd.forward(request, response);
    	
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession();
	
			String id = "";
			if(session.getAttribute(id) != null) {
				id = (String)session.getAttribute("id");
			}
	
			LogDTO logDto = new LogDTO();
			
			logDto.setLogIp(Util.getIP(request));
			logDto.setLogTarget("SportsWrite");
			logDto.setLogdId((String)session.getAttribute(id));
			logDto.setLogEtc(request.getHeader("User-Agent"));
			logDto.setLogMethod("post");
			LogDAO.insertLog(logDto);
			
			String path = request.getServletContext().getRealPath("/");
			String savePath = path + "upload" + File.separator + "sportsFile" + File.separator;
			int size = 1024 * 1024 * 20;
			
			MultipartRequest multi = new MultipartRequest(
								request, savePath, size, "UTF-8", new DefaultFileRenamePolicy());
			
			if(request.getSession().getAttribute("id") != null
					&& request.getSession().getAttribute("name") != null) {
			
				String title = Util.replace(multi.getParameter("title"));
				String content = Util.replace(multi.getParameter("content"));
				String subCategory = multi.getParameter("semiCate");
				String saveFile = null;
				String thumbnailPath = null;
				if(multi.getOriginalFileName("file1") != null) {
					saveFile = multi.getFilesystemName("file1");
					
					thumbnailPath = path + "upload" + File.separator + "sportsThumbnail" + File.separator;
					BufferedImage inputImg 
									= ImageIO.read(new File(savePath + saveFile));
						
					int width = 160;
					int height = 160;
						
					String[] imgs = {"png", "gif", "jpg", "bmp"};
					
					for(String format : imgs) {
						BufferedImage outputImg 
								= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
						
						Graphics2D gr2d = outputImg.createGraphics();
						gr2d.drawImage(inputImg, 0, 0, width, height, null);
				
						File thumb = new File(thumbnailPath + saveFile);
						FileOutputStream fos = new FileOutputStream(thumb);
						ImageIO.write(outputImg, format, thumb);
						fos.close();
						
					}
			}
			
			BoardDTO dto = new BoardDTO();
			
			dto.setBtitle(title);
			dto.setBcontent(content);
			dto.setBfile(saveFile);
			dto.setBthumbnail(saveFile);
			dto.setSubCategory(subCategory);
			dto.setId((String) session.getAttribute("id"));
			
			SportsDAO dao = SportsDAO.getInstance();
			int result = dao.sportsWrite(dto);
		
			if (result == 1) {
				response.sendRedirect("./sports");
			} else {
				response.sendRedirect("./error?code=sportsWriteError1");
			}
		
		} else if (multi.getParameter("title") == null && multi.getParameter("content") == null) {
			response.sendRedirect("./error?code=sportsWriteError2");
		} else {
			response.sendRedirect("./error?code=sportsWriteError3");
		}
	}
}

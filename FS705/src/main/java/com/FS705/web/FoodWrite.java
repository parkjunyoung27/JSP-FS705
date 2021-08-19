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

import com.FS705.dao.FoodBoardDAO;
import com.FS705.dao.LogDAO;
import com.FS705.dto.FoodBoardDTO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/foodWrite")
public class FoodWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FoodWrite() {
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
		logDto.setLogTarget("FoodWrite");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		RequestDispatcher rd = request.getRequestDispatcher("./food/foodWrite.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();

		String id = "";
		if(session.getAttribute(id) != null) {
			id = (String)session.getAttribute("id");
		}
		
		LogDTO logDto = new LogDTO();
				
		logDto.setLogIp(Util.getIP(request));
		logDto.setLogTarget("FoodWrite");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("post");
		LogDAO.insertLog(logDto);
		
		String path = request.getServletContext().getRealPath("/");
		String savePath = path + "upload/foodUpload/"; // food 파일 저장 경로
		int maxSize = 1024 * 1024 * 10; // 파일 업로드 사이즈 설정

		MultipartRequest multi = new MultipartRequest(request, savePath, maxSize, "UTF-8",
				new DefaultFileRenamePolicy());

		if (request.getSession().getAttribute("id") != null && request.getSession().getAttribute("name") != null){
			String title = Util.replace(multi.getParameter("title"));
			String content = Util.replace(multi.getParameter("content"));
			String subCategory = multi.getParameter("semiCate");
			String saveFile = null;
			String thumbnail = null;
			if (multi.getOriginalFileName("file") != null) {
				saveFile = multi.getFilesystemName("file"); // 파일 저장 이름
				
				thumbnail = path + "upload/foodThumbnail/";
				BufferedImage inputImg = ImageIO.read(new File(savePath + saveFile));

				int width = 240;
				int height = 180;

				String[] imgs = { "png", "gif", "jpg", "jpeg" };
				for (String format : imgs) {
					BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

					Graphics2D gr2d = outputImg.createGraphics();
					gr2d.drawImage(inputImg, 0, 0, width, height, null);

					// 파일쓰기
					File thumb = new File(thumbnail + saveFile);
					FileOutputStream fos = new FileOutputStream(thumb);
					ImageIO.write(outputImg, format, thumb);
					fos.close();
				}
			}
			FoodBoardDTO dto = new FoodBoardDTO();
			dto.setId((String) request.getSession().getAttribute("id"));
			dto.setBtitle(title);
			dto.setBcontent(content);
			dto.setSubCategory(subCategory);
			dto.setBfile(saveFile);
			dto.setBthumbnail(saveFile);
			int result = FoodBoardDAO.getInstance().boardWrite(dto);

			if (result == 1) {
				response.sendRedirect("./foodBoard");
			} else {
				response.sendRedirect("./error?code=foodWriteError1");
			}

		} else if (multi.getParameter("title") == null && multi.getParameter("content") == null) {
			response.sendRedirect("./error?code=foodWriteError2");
		} else {
			response.sendRedirect("./error?code=foodWriteError3");
		}

	}

}

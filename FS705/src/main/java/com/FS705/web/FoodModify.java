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

@WebServlet("/foodModify")
public class FoodModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FoodModify() {
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
		logDto.setLogTarget("FoodModify");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
	
	if(request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0
			&& request.getSession().getAttribute("id") != null ){
		 id = (String) request.getSession().getAttribute("id");
		FoodBoardDTO modifyImport = FoodBoardDAO.getInstance().boardModifyImport(Util.str2Int(request.getParameter("bno")), id);		
		RequestDispatcher rd = request.getRequestDispatcher("./food/foodModify.jsp");
		request.setAttribute("modifyImport", modifyImport);
		rd.forward(request, response);
	}else {
		response.sendRedirect("./error?code=foodModifyError1");
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
		logDto.setLogTarget("FoodModify");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("post");
		LogDAO.insertLog(logDto);
		
		request.setCharacterEncoding("UTF-8");
		String path = request.getServletContext().getRealPath("/");
		String savePath = path + "upload/foodUpload/"; // food 파일 저장 경로
		int maxSize = 1024 * 1024 * 10; //파일 업로드 사이즈 설정
		
		MultipartRequest multi = new MultipartRequest(request, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		
		if(multi.getParameter("bno") != null && Util.str2Int(multi.getParameter("bno")) != 0
			&& request.getSession().getAttribute("id") != null){
			id = (String) request.getSession().getAttribute("id");
			int result = 0;
			int bno = Util.str2Int(multi.getParameter("bno"));
			String title = Util.replace(multi.getParameter("title"));
			String content = Util.replace(multi.getParameter("content"));
			String subCategory = multi.getParameter("semiCate");
			String saveFile = null;
			String thumbnail = null;
			if(multi.getOriginalFileName("file") != null) {
				saveFile = multi.getFilesystemName("file"); // 파일 저장 이름
				System.out.println(multi.getFilesystemName("file"));
				thumbnail = path + "upload/foodThumbnail/";
				BufferedImage inputImg = ImageIO.read(new File(savePath + saveFile));
				
				int width = 48;
				int height = 48;
				System.out.println(path);
				String[] imgs = {"png", "gif", "jpg", "jpeg"};
				for (String format : imgs) {
					BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
					
					Graphics2D gr2d = outputImg.createGraphics();
					gr2d.drawImage(inputImg, 0, 0, width, height, null);
					
					//파일쓰기
					File thumb = new File(thumbnail + saveFile);
					FileOutputStream fos = new FileOutputStream(thumb);
					ImageIO.write(outputImg, format, thumb);
					fos.close();					
				}
			}
			System.out.println(saveFile);
			FoodBoardDTO dto = new FoodBoardDTO();
			dto.setBno(bno);
			dto.setBtitle(title);
			dto.setBcontent(content);
			dto.setSubCategory(subCategory);
			dto.setBfile(saveFile);
			dto.setBthumbnail(saveFile);
			dto.setId(id);			
			
			result = FoodBoardDAO.getInstance().boardModifyContent(dto);
			if(result == 1) {
				response.sendRedirect("./foodView?bno="+bno);
			}else {
				response.sendRedirect("./error?code=foodModifyError2");
			}
		}else {
			response.sendRedirect("./error?code=foodModifyError3");
		}	
	}
}

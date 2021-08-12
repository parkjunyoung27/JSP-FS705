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

import com.FS705.dao.HumorBoardDAO;
import com.FS705.dto.HumorBoardDTO;
import com.FS705.util.Util;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/humorWrite")
public class HumorWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HumorWrite() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("./humor/humorWrite.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = request.getServletContext().getRealPath("/");
		String savePath = path + "upload/humorUpload/"; // humor 파일 저장 경로
		int maxSize = 1024 * 1024 * 10; // 파일 업로드 사이즈 설정

		MultipartRequest multi = new MultipartRequest(request, savePath, maxSize, "UTF-8",
				new DefaultFileRenamePolicy());

//		if (request.getSession().getAttribute("id") != null && request.getSession().getAttribute("name") != null) {
		int test = 1;
		if (test == 1) {
			String title = Util.replace(multi.getParameter("title"));
			String content = Util.replace(multi.getParameter("content"));
			String subCategory = multi.getParameter("semiCate");
			String saveFile = null;
			String thumbnail = null;
			if (multi.getOriginalFileName("file1") != null) {
				saveFile = multi.getFilesystemName("file1"); // 파일 저장 이름

				thumbnail = path + "upload/humorThumbnail/";
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
			HumorBoardDTO dto = new HumorBoardDTO();
			dto.setId((String) request.getSession().getAttribute("id"));
			dto.setBtitle(title);
			dto.setBcontent(content);
			dto.setSubCategory(subCategory);
			dto.setBfile(saveFile);

			int result = HumorBoardDAO.getInstance().boardWrite(dto);

			if (result == 1) {
				response.sendRedirect("./humorBoard");
			} else {
				response.sendRedirect("./error?code=humorWriteError1");
			}

		} else if (multi.getParameter("title") == null && multi.getParameter("content") == null) {
			response.sendRedirect("./error?code=humorWriteError2");
		} else {
			response.sendRedirect("./error?code=humorWriteError3");
		}

	}

}

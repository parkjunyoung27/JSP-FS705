package com.FS705.web;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

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
import com.FS705.dao.SportsDAO;

@WebServlet("/sportsWrite")
public class SportsWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SportsWrite() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher rd 
    			= request.getRequestDispatcher("sportsWrite.jsp");
    	rd.forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글처리
		request.setCharacterEncoding("UTF-8");
		//multipart
		//경로 = webapp가 최상위 경로입니다 == /
		String path = request.getSession().getServletContext().getRealPath("/");
		//String realPath = path + "upload/";아래로 수정
		String realPath = path + "upload" + File.separator;
		System.out.println("경로 : " + path);
		int size = 1024 * 1024 * 5;
		MultipartRequest multi = new MultipartRequest(request, realPath,
						size, "UTF-8", new DefaultFileRenamePolicy());
		
		String title = multi.getParameter("title");
		String content = multi.getParameter("content");
		//String upFile = multi.getOriginalFileName("file1"); //실제 업로드시 파일 이름
		String saveFile = multi.getFilesystemName("file1");//저장된 이름
		//title과 content에 html코드 특수문자로 변경해주세요.
		
		System.out.println("title : " + title);
		System.out.println("cotnent : " + content);
		//System.out.println("업로드시 이름 : "+ upFile); 
		System.out.println("저장시 이름 : " + saveFile);
		
		//저장시켜주세요
		
		//썸네일 만들겠습니다.
		String thumbnailPath = path + "thumbnail" + File.separator;
		//C:\workspaceJSP\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\jul19web/upload/파일명
		BufferedImage inputImg 
						= ImageIO.read(new File(realPath + saveFile));
		
		//가로세로 크기 지정
		int width = 160;
		int height = 160;
		
		//이미지 확장자 확인
		String[] imgs = {"png", "gif", "jpg", "bmp"};
		
		for(String format : imgs) {
			BufferedImage outputImg 
							= new BufferedImage(width, height,
									BufferedImage.TYPE_INT_RGB);
			Graphics2D gr2d = outputImg.createGraphics();
			gr2d.drawImage(inputImg, 0, 0, width, height, null);

			//파일쓰기
			File thumb = new File(thumbnailPath + saveFile);
			//폴더가 없다면 생성
			if(thumb.exists()) {
				//thumb.mkdir();//폴더 생성
				thumb.mkdirs();//여러 폴더 생성
			}
			FileOutputStream fos = new FileOutputStream(thumb);
			ImageIO.write(outputImg, format, thumb);
			fos.close();
		}
		
		
		//객체 생성
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("content", content);
		map.put("saveFile", saveFile);
		map.put("thumbnail", saveFile);//썸네일입니다. 변경할겁니다.
		
		//ID값도 보내겠습니다 
		HttpSession session = request.getSession();
		map.put("id", session.getAttribute("id"));
		
		//DAO호출
		SportsDAO dao = SportsDAO.getInstance();
		int result = dao.sportsWrite(map);
		System.out.println("결과 확인 : " + result);
		//페이지 이동
		response.sendRedirect("sports");
	}

}



















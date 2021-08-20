package com.FS705.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.FoodBoardDAO;
import com.FS705.dao.LogDAO;
import com.FS705.dto.LogDTO;
import com.FS705.util.FileThing;
import com.FS705.util.Util;

@WebServlet("/foodDelete")
public class FoodDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FoodDelete() {
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
		logDto.setLogTarget("FoodDelete");
		logDto.setLogdId((String)session.getAttribute(id));
		logDto.setLogEtc(request.getHeader("User-Agent"));
		logDto.setLogMethod("get");
		LogDAO.insertLog(logDto);
		
		if(request.getSession().getAttribute("id") != null && request.getSession().getAttribute("name") != null
		&& request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0 ) {
			int result = 0;
			int bno = Util.str2Int(request.getParameter("bno"));
			int check = FoodBoardDAO.getInstance().checkWriter(bno, (String)request.getSession().getAttribute("id"));
			//작성 글 & id 확인
			if(check == 1 || (int)request.getSession().getAttribute("grade") == 9) {
				//파일 확인
				ArrayList<String> fileName=FoodBoardDAO.getInstance().findFileName((String)request.getSession().getAttribute("id"), bno);
				System.out.println(fileName);
				if(fileName != null) {//파일 삭제
					String path = request.getSession().getServletContext().getRealPath("/");
					if(fileName.get(0) != null) {
						FileThing.fileDelete2(path + "upload/foodUpload/", fileName.get(0));				
						}
						if(fileName.get(1) !=null) {
							FileThing.fileDelete2(path + "upload/foodThumbnail/", fileName.get(1));								
						}
				}
				result = FoodBoardDAO.getInstance().boardDelete(bno);				
			}
			if(result == 1) {
				response.sendRedirect("./foodBoard");
			} else {
				response.sendRedirect("./error?code=foodDeleteError1");				
			}
		} else {
			response.sendRedirect("./error?code=foodDeleteError2");
		}		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

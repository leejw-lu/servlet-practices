package guestbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import guestbook.dao.GuestBookDao;
import guestbook.vo.GuestBookVo;

public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String action= request.getParameter("a");
		if("deleteform".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
			rd.forward(request, response);
			
		} else if ("delete".equals(action)) {
			String no= request.getParameter("no");
			String password= request.getParameter("password");

			GuestBookVo vo=new GuestBookVo();
			vo.setNo(Long.parseLong(no));
			vo.setPassword(password);
			
			new GuestBookDao().deleteByNo(vo);
			
			response.sendRedirect(request.getContextPath()+ "/gb");
			
		} else if ("add".equals(action)) {
			String name= request.getParameter("name");
			String password= request.getParameter("password");
			String contents= request.getParameter("contents");
			
			GuestBookVo vo=new GuestBookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContents(contents);
			
			new GuestBookDao().insert(vo);
			
			response.sendRedirect(request.getContextPath()+ "/gb");
			
		} else {
			/* default action (list) */
			List<GuestBookVo> list = new GuestBookDao().findAll();
			request.setAttribute("list", list);
			RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(request, response);
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

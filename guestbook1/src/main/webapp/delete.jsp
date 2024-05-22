<%@page import="guestbook.vo.GuestBookVo"%>
<%@page import="guestbook.dao.GuestBookDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String no= request.getParameter("no");
	String password= request.getParameter("password");

	GuestBookVo vo=new GuestBookVo();
	vo.setNo(Long.parseLong(no));
	vo.setPassword(password);
	
	new GuestBookDao().deleteByNo(vo);
	
	response.sendRedirect("/guestbook1");
%>
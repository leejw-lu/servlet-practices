<%@page import="emaillist.vo.EmaillistVo"%>
<%@page import="emaillist.dao.EmaillistDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String firstName= request.getParameter("fn");
	String lastName= request.getParameter("ln");
	String email= request.getParameter("email");
	
	EmaillistVo vo=new EmaillistVo();
	vo.setFisrtName(firstName);
	vo.setLastName(lastName);
	vo.setEmail(email);
	
	new EmaillistDao().insert(vo);
	
	response.sendRedirect("/emaillist1");
%>
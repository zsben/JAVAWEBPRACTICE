package com.hua.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.hua.dao.AdminDao;
import com.hua.dao.DaoFactory;
import com.hua.dao.StudentDao;
import com.hua.dao.TeacherDao;
import com.hua.entity.Admin;
import com.hua.entity.Student;
import com.hua.entity.Teacher;
import com.hua.utils.MD5;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		resp.sendRedirect("login.jsp");
	}
	
}

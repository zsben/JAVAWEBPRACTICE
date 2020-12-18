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

@WebServlet("/pwd")
public class PwdServlet extends HttpServlet {

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = Integer.parseInt(req.getParameter("id"));
		String type = req.getParameter("type");
		String pwd = req.getParameter("pwd");
		String newPwd = req.getParameter("newPwd");
		String newPwd2 = req.getParameter("newPwd2");
		if(!newPwd.equals(newPwd2)) {
			req.setAttribute("msg", "两次密码不一致");
			req.getRequestDispatcher("pwd.jsp").forward(req, resp);
		}else {
			try {
				if("0".equals(type)) {
					DaoFactory.getInstance().getStudentDao().update(MD5.encrypByMd5(MD5.encrypByMd5(newPwd)),id);
				}
				if("1".equals(type)) {
					DaoFactory.getInstance().getTeacherDao().update(MD5.encrypByMd5(MD5.encrypByMd5(newPwd)),id);
				}
				if("2".equals(type)) {
					DaoFactory.getInstance().getAdminDao().update(MD5.encrypByMd5(MD5.encrypByMd5(newPwd)),id);
				}
				req.setAttribute("msg", "修改成功");
				req.getRequestDispatcher("pwd.jsp").forward(req, resp);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}

package com.hua.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.hua.dao.DaoFactory;
import com.hua.entity.Student;
import com.hua.entity.Teacher;
import com.hua.entity.Course;
import com.hua.entity.Sc;
import com.hua.utils.MD5;
import com.hua.utils.PageInfo;
import com.hua.utils.PathUtils;

/**
 * 综合统计
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Map<String, Object>> list = DaoFactory.getInstance().getScDao().query_jgl();
			request.setAttribute("list", list);
			List<Map<String, Object>> top5List = DaoFactory.getInstance().getScDao().top5();
			request.setAttribute("top5List", top5List);
			request.getRequestDispatcher("main.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

package com.hua.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
import com.hua.utils.MD5;
import com.hua.utils.PageInfo;
import com.hua.utils.PathUtils;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("list".equals(method)) { // 课程列表
			this.list(request, response);
		}else if("add".equals(method)) { // 新增课程后跳转到列表页面
			this.add(request, response);
		}else if("v_add".equals(method)) { // 管理员课程管理新增课程按钮
			this.v_add(request, response);
		}else if("edit".equals(method)) { // 跳转到修改课程界面
			this.findById(request, response);
		}else if("editsubmit".equals(method)) { // 修改界面后跳转到列表
			this.editsubmit(request, response);
		}else if("delete".equals(method)) { // 删除课程后跳转到列表
			this.delete(request, response);
		}
		
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			DaoFactory.getInstance().getCourseDao().delete(Integer.parseInt(id));
			//直接重定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request)+"course?method=list");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	private void editsubmit(HttpServletRequest request, HttpServletResponse response) {
		String cName = request.getParameter("cName");
		Integer cId = getIntParameter(request, "cId");
		//老师ID
		Integer tId = Integer.parseInt(request.getParameter("tId"));
		Course course = new Course();
		course.setcId(cId);
		course.setcName(cName);
		//new一个Teacher对象，然后设置ID，然后再设置course对象的setTeacher关联 一下即可
		Teacher teacher = new Teacher();
		teacher.settId(tId);
		course.setTeacher(teacher);
		try {
			DaoFactory.getInstance().getCourseDao().update(course);
			//直接重定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request)+"course?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void findById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		PageInfo<Teacher> pageInfo = new PageInfo<>(1);
		pageInfo.setPageSize(1000);
		try {
			Course course = DaoFactory.getInstance().getCourseDao().findById(Integer.parseInt(id));
			pageInfo = DaoFactory.getInstance().getTeacherDao().list(null, pageInfo);
			request.setAttribute("course", course);
			request.setAttribute("teachers", pageInfo.getList());
			request.getRequestDispatcher("page/course/update.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void v_add(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException  {
		PageInfo<Teacher> pageInfo = new PageInfo<>(1);
		pageInfo.setPageSize(1000);
		try {
			pageInfo = DaoFactory.getInstance().getTeacherDao().list(null, pageInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("teachers", pageInfo.getList());
		request.getRequestDispatcher("page/course/add.jsp").forward(request, response);
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) {
		String cName = request.getParameter("cName");
		//老师ID
		Integer tId = Integer.parseInt(request.getParameter("tId"));
		Course course = new Course();
		course.setcName(cName);
		//new一个Teacher对象，然后设置ID，然后再设置course对象的setTeacher关联 一下即可
		Teacher teacher = new Teacher();
		teacher.settId(tId);
		course.setTeacher(teacher);
		try {
			DaoFactory.getInstance().getCourseDao().add(course);
			//直接重定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request)+"course?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void list(HttpServletRequest request, HttpServletResponse response) {
		//当前页码
		Integer pageNo = getIntParameter(request, "pageNo");
		//查询条件
		Integer cId = getIntParameter(request, "cId");
		String cName = request.getParameter("cName");
		String tName = request.getParameter("tName");
		String userName = request.getParameter("userName");
		
		Course course = new Course();
		course.setcId(cId);
		course.setcName(cName);
		Teacher teacher = new Teacher();
		teacher.settName(tName);
		teacher.setUserName(userName);
		course.setTeacher(teacher);
		
		//构造了一个pageInfo对象
		PageInfo<Course> pageInfo = new PageInfo<>(pageNo);
		try {
			pageInfo = DaoFactory.getInstance().getCourseDao().list(course,pageInfo);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			request.setAttribute("pageInfo", pageInfo);
			//回写到页面
			request.setAttribute("course", course);
			request.getRequestDispatcher("page/course/list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Integer getIntParameter(HttpServletRequest request,String name) {
		if(StringUtils.isNoneBlank(request.getParameter(name))) {
			return Integer.parseInt(request.getParameter(name));
		}else {
			return null;
		}
	}
}

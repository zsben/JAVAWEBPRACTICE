package com.hua.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@WebServlet("/loadFile")
public class LoadFileServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "upload";

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 512);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(10 * 1024 * 1024);
        upload.setHeaderEncoding("utf-8");

        String uploadPath = req.getServletContext().getRealPath("./")
                + File.separator
                + UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            if (fileItems != null && fileItems.size() > 0) {
                for (FileItem item : fileItems) {
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        System.out.println(fileName);
                    }
                }
            }

            req.setAttribute("message", "批量导入成功");
        } catch (Exception exception) {
        }

        req.getRequestDispatcher("message.jsp").forward(req, resp);

        InputStream inputStream = null;


    }
}

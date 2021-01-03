<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>批量导入</title>
    <link rel="stylesheet" href="${basePath}static/css/styles.css"/>
    <link rel="stylesheet" href="${basePath}static/css/font-awesome-4.7.0/css/font-awesome.min.css"/>
    <script src="${basePath}static/js/jquery.min.js" type="text/javascript"></script>
    <script src="${basePath}static/js/jquery-validation-1.14.0/jquery.validate.js" type="text/javascript"></script>
    <script src="${basePath}static/js/jquery-validation-1.14.0/localization/messages_zh.js"
            type="text/javascript"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
    <form method="post" action="${basePath}loadFile" enctype="multipart/form-data">
        请选择一个execl文件:
        <input type="file" name="uploadFile"/>
        <br/><br/>
        <input type="submit" value="上传"/>
    </form>
</body>
</html>

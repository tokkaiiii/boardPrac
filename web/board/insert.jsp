<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2024-07-15
  Time: 오전 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
      input:focus, textarea:focus {
        outline: none;
      }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <form action="/board.do?method=insert" method="post" enctype="multipart/form-data">
            <table class="table table-striped"
                   style="text-align: center; border: 1px solid #dddddd">
                <thead>
                <tr>
                    <th colspan="2" style="text-align:center ; background-color: #dddddd;">글쓰기</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><input type="text" class="form-control" placeholder="제목" name="title"
                               maxlength="50"></td>
                </tr>
                <tr>
                    <td><input type="file" class="form-control"  name="file" ></td>
                </tr>
                <tr>
                    <td><textarea type="text" class="form-control" placeholder="내용" name="content"
                                  maxlength="2048" style="height:350px"></textarea></td>
                </tr>
                </tbody>
            </table>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button class="btn btn-outline-dark" type="submit">글쓰기</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>

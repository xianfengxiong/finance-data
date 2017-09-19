<%--
  Created by IntelliJ IDEA.
  User: xxf
  Date: 2017/9/19
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>spider status</title>
    <script src="/static/jquery-3.2.1.min.js"></script>
    <script>
        getData();

        function getData() {
            $.get("/spider/status",function (jsonResponse) {
                var code = jsonResponse["code"];
                if (code == "200") {
                    var data = jsonResponse["data"];
                    var leftRequestsCount = data["leftRequestsCount"];
                    var totalRequestsCount = data["totalRequestsCount"];
                    $("#leftRequestsCount").text(leftRequestsCount);
                    $("#totalRequestsCount").text(totalRequestsCount);
                } else {
                    alert("请求出错");
                }
            })
        }

        window.setInterval(getData,2000);
    </script>
</head>
<body>
    <table>
        <tr>
            <td>待抓取链接数:</td>
            <td id="leftRequestsCount"></td>
        </tr>
        <tr>
            <td>总抓取链接数:</td>
            <td id="totalRequestsCount"></td>
        </tr>
    </table>
</body>
</html>

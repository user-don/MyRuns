<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.don.myapplication.backend.data.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Query Result</title>
</head>
<body>
    <h1>Exercise Entries List</h1>

		<%
        ArrayList<ServerEE> resultList = (ArrayList<ServerEE>) request
                .getAttribute("result");
        if (resultList != null) { %>

            <table border="1" style="width:100%">
            <tr>
                <td>ID</td>
                <td>Input Type</td>
                <td>Activity Type</td>
                <td>Date and Time</td>
                <td>Duration</td>
                <td>Distance</td>
                <td>Avg Speed</td>
                <td>Calories</td>
                <td>Climb</td>
                <td>Heart Rate</td>
                <td>Comment</td>
            </tr>

            <%
            for (ServerEE entry : resultList) {
                %>
                <tr>
                    <td><%=entry.mID%></td>
                    <td><%=entry.mInputType%></td>
                    <td><%=entry.mActivityType%></td>
                    <td><%=entry.mDateTime%></td>
                    <td><%=entry.mDuration%></td>
                    <td><%=entry.mDistance%></td>
                    <td><%=entry.mAvgSpeed%></td>
                    <td><%=entry.mCalories%></td>
                    <td><%=entry.mClimb%></td>
                    <td><%=entry.mHeartRate%></td>
                    <td><%=entry.mComment%></td>
                    <td>
                        <a href="/delete.do?id=<%=entry.mID%>">Delete</a>
                    </td>
                </tr>
            <% } %>

            </table>
        <% } %>
</body>
</html>
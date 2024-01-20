<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="progetto.gruppo14.SESSIONE_COOKIES.Sessione" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<head>
    <title>Tum4World</title>
    <script src="${pageContext.request.contextPath}/JAVASCRIPT/visite.js"></script>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/IMMAGINI/icon.png" type="image/x-icon">
    <meta charset="uft-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/generale.css">

    <%
        HttpSession mysession = request.getSession(false);
        if(mysession == null){
            Sessione sessioneObj = new Sessione();
            sessioneObj.dealWithInvalidSession(request, response);
        }
        else{
            boolean DoPopUp = (Boolean) mysession.getAttribute("DoPopUp");
            pageContext.setAttribute("DoPopUp", DoPopUp);
        %>
            <c:if test = "${DoPopUp == true}">
                <%@include file="PopUpCookie.jsp"%>
                <%
                    mysession.setAttribute("DoPopUp", false);
                %>
            </c:if>
        <% } %>

</head>
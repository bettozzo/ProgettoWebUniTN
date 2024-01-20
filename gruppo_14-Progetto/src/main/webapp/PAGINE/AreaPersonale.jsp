<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="progetto.gruppo14.SESSIONE_COOKIES.Sessione" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>


<html>
    <%@include file="../FRAMMENTI/Head.jsp"%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Amministratore.css">--%>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/IMMAGINI/icon.png" type="image/x-icon">
    <body>
        <%@include file="../FRAMMENTI/Header.jsp"%>
        <%@include file="../FRAMMENTI/MenuPersonale.jsp"%>

        <%--https://stackoverflow.com/questions/25213686/include-a-jsp-file-in-another-with-cases--%>

        <%
                String ruolo = (String) session.getAttribute("ruolo");
                pageContext.setAttribute("ruolo", ruolo);
        %>
            <c:choose>
                <c:when test="${ruolo.equals('\"admin\"')}">
                    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Amministratore.css">
                    <%@include file="../FRAMMENTI/Amministratore.jsp"%>
                </c:when>

                <c:when test="${ruolo.equals('\"aderente\"')}">
                    <%@include file="../FRAMMENTI/Aderente.html"%>
                    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Aderente.css">
                </c:when>

                <c:when test="${ruolo.equals('\"simpatizzante\"')}">
                    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Simpatizzante.css">
                    <%@include file="../FRAMMENTI/Simpatizzante.html"%>
                </c:when>

                <c:when test="${ruolo ne 'simpatizzante' && ruolo ne 'aderente' && ruolo ne 'admin'}">
                    <%@include file="../FRAMMENTI/SmthWrong.html"%>
                </c:when>
            </c:choose>

        <%@include file="../FRAMMENTI/Footer.html"%>
    </body>
</html>
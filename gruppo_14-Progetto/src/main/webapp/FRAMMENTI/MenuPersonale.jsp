<menu>
    <div>
        <ul>
            <%
                String path = request.getContextPath();
            %>
            <li><a href=<%= response.encodeURL (path+"/PAGINE/Home.jsp") %>>Home page</a></li>
            <li><a href=<%= response.encodeURL (path+"/PAGINE/ChiSiamo.jsp") %>>Chi siamo</a></li>
            <li><a href=<%= response.encodeURL (path+"/PAGINE/Attivita.jsp") %>>Attività</a></li>
            <li><a href=<%= response.encodeURL (path+"/PAGINE/Contatti.jsp") %>>Contatti</a></li>
            <li><a href=<%= response.encodeURL (path+"/PAGINE/SignIn.jsp") %>>Sign In</a></li>
            <li><a href=<%= response.encodeURL (path+"/PAGINE/Login.jsp") %>>Login</a></li>
            <li><a href=<%= response.encodeURL (path+"/LogoutServlet")%>>Logout</a></li>
        </ul>
    </div>
</menu>
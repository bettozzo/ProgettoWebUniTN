<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="../JAVASCRIPT/LoginValidate.js"></script>

<div class="conForm" >
    <h1>
        <br>
        Login
    </h1>

    <div class="boxed" id="fallimento">
        <p>14: Login Fallito, controllare le credenziali inserite</p>
    </div>

    <form id="login" method="post" onsubmit="return validate()" action=<%= response.encodeURL (request.getContextPath()+"/Login") %>>
        <div class="row">
            <div class="column">
                <label for="username">Username:</label>
            </div>
            <div class="column">
                <input id="username" name="username" type="text">
            </div>
        </div>
        <div class="row">
            <div class="column">
                <label for="pass">Password:</label>
            </div>
            <div class="column">
                <input id="pass" name="password" type="password">
            </div>
        </div>
        <div class="row">
            <div class="column">
                <button type="submit"> Conferma </button>
            </div>
            <div class="column">
                <button type="reset"> Reset </button>
            </div>
        </div>
    </form>
</div>

<script>
    /*quando il login fallisce per credenziali sbagliate, nella sessione il campo LoginFallito è settato a true.
    * la funzione js controlla se LoginFallito==true, e se è cosi mostra un popup quando la pagina è caricata.
    * LoginFallito è poi settato a false*/
    <%
        boolean LoginFallito = (Boolean) session.getAttribute("LoginFallito");
    %>
    var popUp=<%=LoginFallito%> ;
    if(popUp){
        document.getElementById('fallimento').style.display='block';
    }else{
        document.getElementById('fallimento').style.display='none';
    }
    <%
       session.setAttribute("LoginFallito", false);
    %>
</script>




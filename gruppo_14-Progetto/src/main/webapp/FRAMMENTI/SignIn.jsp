<script src="../JAVASCRIPT/SignInValidate.js"></script>


<div class="conForm">
    <h1>
        <br>
        Compila il form con i tuoi dati personali per procedere alla registrazione.
    </h1>

    <div class="boxed" id="fallimento">
        <p>14: Sign Fallito, credenziali non disponibili</p>
    </div>

    <form id="login"  method="post" onsubmit="return signInValidate()" action=<%= response.encodeURL (request.getContextPath()+"/Registrazione") %>>
        <div class="row">
            <div class="column">
                <label for="nome">Nome:</label>
            </div>
            <div class="column">
                <input type="text" name="nome" id="nome">
            </div>
        </div>
        <div class="row">
            <div class="column">
                <label for="cognome">Cognome:</label>
            </div>
            <div class="column">
                <input type="text" name="cognome" id="cognome">
            </div>
        </div>
        <div class="row">
            <div class="column">
                <label for="nascita">Data di nascita (formato GG/MM/AAAA):</label>
            </div>
            <div class="column">
                <input type="text" name="nascita" id="nascita">
            </div>
        </div>
        <div class="row">
            <div class="column">
                <label for="mail">Mail:</label>
            </div>
            <div class="column">
                <input type="text" name="mail" id="mail">
            </div>
        </div>
        <div class="row">
            <div class="column">
                <label for="telefono">Numero di telefono:</label>
            </div>
            <div class="column">
                <input type="text" name="telefono" id="telefono">
            </div>
        </div>
        <div class="row">
            <div class="column">
                <p>Ruolo con cui ci si vuole iscrivere:</p>
            </div>
            <div class="column">
                <div id="elenco">
                    <div class="elementoElenco">
                        <input type="radio" id="simpatizzante" name="ruolo" value="simpatizzante">
                        <label for="simpatizzante">Simpatizzante</label>
                    </div>
                    <div class="elementoElenco">
                        <input type="radio" id="aderente" name="ruolo" value="aderente">
                        <label for="aderente">Aderente</label>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="column">
                <label for="username">Username:</label>
            </div>
            <div class="column">
                <input type="text" name="username" id="username">
            </div>
        </div>
        <div class="row">
            <div class="column">
                <label for="password">Password:</label>
            </div>
            <div class="column">
                <input type="password" name="password" id="password">
            </div>
        </div>
        <div class="row">
            <div class="column">
                <label for="confermaps">Conferma della password:</label>
            </div>
            <div class="column">
                <input type="password" name="confermaps" id="confermaps">
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
    /*quando il signin fallisce per credenziali non disponibili, nella sessione il campo SigninFallito è settato a true.
    * la funzione js controlla se SigninFallito==true, e se è cosi mostra un popup quando la pagina è caricata.
    * SigninFallito è poi settato a false*/
    <%
        boolean SigninFallito = (Boolean) session.getAttribute("SigninFallito");
    %>
    var popUp=<%=SigninFallito%> ;
    if(popUp){
        document.getElementById('fallimento').style.display='block';

    }else{
        document.getElementById('fallimento').style.display='none';
    }
    <%
       session.setAttribute("SignginFallito", false);
    %>
</script>



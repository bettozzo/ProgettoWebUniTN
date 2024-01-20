<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>

<div id="homepage">
    <h1>Benvenuto nell'associazione Tum4World!</h1>

    <div class="row">
        <div class="column" id="column1">
            <img src="${pageContext.request.contextPath}/IMMAGINI/logo.PNG" alt="logo" id="logo">
        </div>
        
        <div class="column" id="column2">
            <p id="p-descrizione">
                <b>TUM4WORLD: Una missione di pace!</b>
                Benvenuti nel sito della nostra associazione, Tum4World!!<br>
                Il nostro bellissimo logo è stato disegnato dai bambini del Mozambico che frequentano la scuola elementare da noi costruita!
            </p>
            <a href="${pageContext.request.contextPath}/Volantino.pdf" download="Tum4World_Volantino">
                <button id="btn-scarica">Scarica volantino</button>
            </a>
            <img src="${pageContext.request.contextPath}/IMMAGINI/imgDescrittiva.jpg" alt="immagine che ci rappresenta" id="img-descrittiva">
        </div>
    </div>    
</div>
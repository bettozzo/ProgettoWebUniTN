//quando il file js è incluso nella pagina, mostra l'elemento overlay (il pop up che chiede di accettare i cookie)
//se l'utete clicca "Accetta", viene efettuata una chiamata alla servlet che crea un cookie
//il pop up è "chiuso" nascondendo l'elemento overlay

var delay_popup = 1000;
setTimeout("document.getElementById('popupOverlay').style.display='block'", delay_popup);

function Accetta(){
    //path = http://localhost:8080/Gruppo14_war_exploded
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];

    closePopup();
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", path+"/CreaCookie" , true);
    xhttp.onreadystatechange = function () {
        var done = 4, ok = 200;
        if (this.readyState === done && this.status === ok){
        }
    };
    xhttp.send();
}

function Rifiuta(){
    closePopup();
}
function closePopup() {
    document.getElementById('popupOverlay').style.display = 'none';
}
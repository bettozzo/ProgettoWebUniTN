var oggetto = "";

function getCheckboxValue() {
    var arrayCheckbox = ["domande", "commenti", "suggerimenti", "problemi", "altro"];
    for (var i = 0; i < arrayCheckbox.length; i++) {
        let checkbox = document.getElementById(arrayCheckbox[i])
        if(checkbox.checked){
            var selected = checkbox.value;
            oggetto=oggetto + selected + " ";
        }
    }
}

function sendEmail() {
    //crea l'oggetto della mail con i valori spuntati nella checkbox
    getCheckboxValue();
    let body = "dettagli";
    body+="\n";
    //aggiungo in fondo al testo della mail
    //nome e cognome inseriti nell'apposito campo
    body+="nome-cognome";

    /*
    Queste due righe di codice sarebbero necessarie per mandare una vera mail,
    ma noi non abbiamo una vera url a cui inviare la richiesta
    */
    //const Email = new XMLHttpRequest();
    //Email.open("GET", "/server", true);
    /*
    //commentato per permettere il reindirizzamento della pagina anche se non viene inviata mai la mail
    Email.send({
        Host : "smtp.mailtrap.io",
        To : 'tum4worldExample@example.com',
        From : "mail",
        Subject : "oggetto",
        Body : body
    }).then
    */
        (
            window.open("emailConfermata.jsp")
        );

}

//controlla la mail utilizzando una pattern regex
function checkMail(mail) {
    let ret = true;
    if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))) {
        alert("Indirizzo mail non valido")
        ret = false;
    }
    return ret;
}

//convalida il campo della mail
function MailValidate(){
    let mail = document.getElementById("mail").value;
    let res = true;

    if(!checkMail(mail)){
        res = false;
    }

    //se viene inserito un indirizzo mail valido invia la mail
    if(res){
        sendEmail();
    }

    return res;
}
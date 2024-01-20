/*
convalida i campi del signin
non devono esserci campi nulli, deve essere selezionato un ruolo, non sono permessi apostrofi (restrizione scelrta da noi per comodità nel DB)
controlla che l'utente non sia minorenne considerando la data di nascita inserita
controlla la mail e il telefono utilizzando una pattern regex
verifica i requisiti della password
*/
function signInValidate(){
    let nome = document.getElementById("nome").value;
    let cognome = document.getElementById("cognome").value;
    let nascita = document.getElementById("nascita").value;
    let mail = document.getElementById("mail").value;
    let telefono = document.getElementById("telefono").value;

    let simpatizzante = document.getElementById("simpatizzante").checked;
    let aderente = document.getElementById("aderente").checked;

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let confermaps = document.getElementById("confermaps").value;

    let res = true;

    if(nome==="" || cognome==="" || nascita==="" || mail==="" || telefono==="" || username==="" || password==="" || confermaps===""){
        alert("Non lasciare campi vuoti");
        res = false;
    }

    if(simpatizzante===false && aderente===false){
        alert("Selezionare un ruolo");
        res = false;
    }

    if(nome.includes("'") || cognome.includes("'") || mail.includes("'") ||
        telefono.includes("'") || username.includes("'") || password.includes("'")){
        alert("Non è permesso l'utilizzo di apostrofi");//per motivi relativi alla sintassi del database
        res = false;
    }

    if(!checkNascita(nascita)){
        res = false;
    }

    if(!checkMail(mail)){
        res = false;
    }

    if(!checkTelefono(telefono)){
        res = false;
    }

    if(!checkPassword(password)){
        res = false;
    }
    else if(password !== confermaps){
        alert("La password e la sua conferma devono coincidere");
        res = false;
    }
    return res;
}

function checkNascita(nascita){
    let ret = true;
    let splitted = nascita.split(/\//);
    let giorno = splitted[0];
    let mese = splitted[1];
    let anno = splitted[2];

    let oggi = new Date();

    if(!(giorno.length===2 && mese.length===2 && anno.length===4)){
        alert("Inserire la data in un formato valido");
        ret = false;
    }
    else if(oggi.getFullYear()-Number(anno) < 18){
        alert("Un minorenne non può iscriversi al sito");
        ret = false;
    }
    return ret;
}

function checkMail(mail) {
    let ret = true;
    if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))) {
        alert("Indirizzo mail non valido")
        ret = false;
    }
    return ret;
}

function checkTelefono(telefono){ /*https://www.abstractapi.com/guides/validate-phone-number-javascript*/
    let ret = true;
    if(!(/^\(?(\d{3})\)?[- ]?(\d{3})[- ]?(\d{4})$/.test(telefono))){
        alert("Inserire un numero di telefono valido")
        ret = false;
    }
    return ret;
}

function checkPassword(password){

    let res = true;

    if(password.length !== 8){
        alert("La password deve essere lunga 8 caratteri");
        res = false;
    }
    if( !(password.includes("S") && password.includes("A"))){
        alert("La password deve contenere i caratteri S ed A");
        res = false;
    }
    if(!(password.includes("$") || password.includes("!") || password.includes("?"))){
        alert("La password deve contenere almeno un carattere fra: $, ! e ?");
        res = false;
    }
    if(!(/[0-9]/.test(password))){
        alert("La password deve contenere almeno un numero");
        res = false;
    }
    if(!(/[A-Z]/.test(password))){
        alert("La password deve contenere almeno una maiuscola");
        res = false;
    }
    return res;
}
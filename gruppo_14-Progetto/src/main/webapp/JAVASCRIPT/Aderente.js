//La funzione che dopo aver premuto il pulsante fa visibile il blocco con i dati personali del utente e nasconde tutti gli altri blocchi
function mostraDati() {
    document.getElementById('dati').style.display='block';
    document.getElementById('attivita').style.display='none';
    document.getElementById('cancellazione').style.display='none';
    document.getElementById('donazione').style.display='none';
    showHideFun();
}
//La funzione che dopo aver premuto il pulsante fa visibile il blocco con le attività da scegliere e nasconde tutti gli altri blocchi
function scegliAttivita() {
    document.getElementById('attivita').style.display='block';
    document.getElementById('dati').style.display='none';
    document.getElementById('cancellazione').style.display='none';
    document.getElementById('donazione').style.display='none';
    statoIscrizione();
}
//La funzione che dopo aver premuto il pulsante fa visibile il blocco con la possibilità di cancellare l'iscrizione al sito e nasconde tutti gli altri blocchi
function cancelliTutto() {
    document.getElementById('cancellazione').style.display='block';
    document.getElementById('attivita').style.display='none';
    document.getElementById('dati').style.display='none';
    document.getElementById('donazione').style.display='none';

}
//La funzione che dopo aver premuto il pulsante fa visibile il blocco con la possibilità di inviare la donazione e nasconde tutti gli altri blocchi
function faiDonazione() {
    document.getElementById('donazione').style.display='block';
    document.getElementById('cancellazione').style.display='none';
    document.getElementById('attivita').style.display='none';
    document.getElementById('dati').style.display='none';

}
//La funzione che richiede dalla Database i dati di iscrizione dell'utente attuale alle attività
function statoIscrizione() {
    //path = http://localhost:8080/Gruppo14_war_exploded
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];
    var url = path+"/RetrieveUtentiRuolo?singolo=true";
    var myrequest = new XMLHttpRequest(); // crea un oggetto XMLHttpRequest
    myrequest.open("POST", url, true);
    //la chiamata a open non apre la connessione. Configura solo la richiesta, ma l’attività di rete comincia solo dopo la chiamata a send
    myrequest.responseType = "text";

    //una funzione da eseguire ogni volta quando cambia lo stato dell'oggetto
    myrequest.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            // chiama la funzione statoAttuale e le passa come il parametro l'attività dell'utente attuale
            statoAttuale(JSON.parse(myrequest.responseText).attivita); // La proprietà responseText restituisce la risposta del server come stringa di testo
        }
    };
    myrequest.send(); //apre la connessione ed invia la richiesta al server
}
//La funzione che fa visibile l'attività alle quali l'utente attuale è gia iscritto
function statoAttuale(attivita) {
    var textIscritto;
    var box;

    //Perchè l'attività è un array per iterarsi ci serve un ciclo
    for (let i=0; i<attivita.length; i++) {
        if (attivita[i] === "Sanità") {
            textIscritto = document.getElementById("conferma1"); //prende la nota di conferma corrisposta a ogni tipo di attività
            box = document.getElementById("box1"); // prende il checkbox corrisposto a ogni tipo di attività
            textIscritto.style.display = "block"; //mostra la nota di conferma
            box.checked = true; // fa checkbox cliccato
        } else if (attivita[i] === "Didattica") {
            textIscritto = document.getElementById("conferma2");
            box = document.getElementById("box2");
            textIscritto.style.display = "block";
            box.checked = true;
        } else if (attivita[i] === "Sensibilizzazione") {
            textIscritto = document.getElementById("conferma3");
            box = document.getElementById("box3");
            textIscritto.style.display = "block";
            box.checked = true;
        }
    }
}
//La funzione dopo aver cliccato sull'immagine o sulla checkbox lo fa vedere come cliccato e chiama l'altra funzione per passare i dati alla database
function iscrizione(idImg, idConferma, idBox) {
    var textIscritto = document.getElementById(idConferma);
    var attivita;
    var iscrivere;
    var box = document.getElementById(idBox);
    if (idImg==="img1") { attivita = "Sanità"}
    else if (idImg==="img2") { attivita = "Didattica"}
    else if (idImg==="img3") { attivita = "Sensibilizzazione"}

    if(textIscritto.style.display==="none") { //se checkbox non è ancora cliccato, lo fa cliccato e mostra la nota di conferma corrispettiva
        textIscritto.style.display="block";
        box.checked=true;
        iscrivere = "true"; // cambia lo stato di iscrizione per passarlo alla Database
    } else {
        textIscritto.style.display="none";
        box.checked=false;
        iscrivere = "false";
    }
    //chiama la funzione iscrizioneAttivita per passare i dati alla Database
    iscrizioneAttivita(iscrivere, attivita, idConferma);
}

//La funzione passa i dati riguarda l'iscrizione all'attività alla Database
function iscrizioneAttivita(iscrizione, attivita, idConferma) {
    //path = http://localhost:8080/Gruppo14_war_exploded
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];
    var url = path+"/gestisciIscrizioneAttivita?iscrizione="+iscrizione+"&attivita="+attivita;
    var myrequest = new XMLHttpRequest(); // crea un oggetto XMLHttpRequest
    myrequest.open("POST", url, true);
    //la chiamata a open non apre la connessione. Configura solo la richiesta, ma l’attività di rete comincia solo dopo la chiamata a send

    //una funzione da eseguire ogni volta quando cambia lo stato dell'oggetto
    myrequest.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            if(iscrizione) { //dopo aver ricevuto la risposta dal server mostra la nota di conferma
                document.getElementById(idConferma).textContent = "Sei iscritto a questa attività!";
            }
        }
    }
    myrequest.send(); //apre la connessione ed invia la richiesta al server
}
//La funzione che dopo l'utente attuale clicca il bottone per cancellare la propria iscrizione gli mostra una conferma e communica alla Database il fatto della cancellazione
function cancellaDati() {
    //path = http://localhost:8080/Gruppo14_war_exploded
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];
    var url = path+"/CancellazioneDatiUtenti";
    var myrequest = new XMLHttpRequest(); // crea un oggetto XMLHttpRequest
    myrequest.open("POST", url, true);
    //la chiamata a open non apre la connessione. Configura solo la richiesta, ma l’attività di rete comincia solo dopo la chiamata a send
    myrequest.responseType ="text";

    //una funzione da eseguire ogni volta quando cambia lo stato dell'oggetto
    myrequest.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) { //dopo aver ricevuto la risposta dal server
            window.location=path+"/LogoutServlet"; // reindirizzia l'utente che ha cancellato la sua iscrizione alla Homepage
            alert("La tua iscrizione è stata cancellata."); //mostra l'alert che conferma il fatto di cancellazione
        }
    }
    myrequest.send(); //apre la connessione ed invia la richiesta al server
}
//La funzione che prende il mese attuale e la somma di donazione e gli comunica all'altra funzione
function inviaDonazione() {
    var numeroAMese={ // trasforma il valore numerico del mese in una stringa
        1:"gennaio",
        2:"febbraio",
        3:"marzo",
        4:"aprile",
        5:"maggio",
        6:"giugno",
        7:"luglio",
        8:"agosto",
        9:"settembre",
        10:"ottobre",
        11:"novembre",
        12:"dicembre"
    }
    const somma = document.getElementById('somma').value; // prende la somma della donazione inserita dall'utente
    const d = new Date(); // crea un oggetto Data
    let month = numeroAMese[d.getMonth()+1]; // prende il mese attuale (come il numero)
    comunicaDonazione(somma, month); // chiama la funzione per comunicare i dati alla Database
}
//La funzione che comunica alla Database la somma e il mese di donazione fatta
function comunicaDonazione(somma, month) {
    //path = http://localhost:8080/Gruppo14_war_exploded
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];
    var url = path+"/Donazioni?donazione="+somma+"&mese="+month;
    var myrequest = new XMLHttpRequest(); // crea un oggetto XMLHttpRequest
    myrequest.open("POST", url, true);
    //la chiamata a open non apre la connessione. Configura solo la richiesta, ma l’attività di rete comincia solo dopo la chiamata a send

    //una funzione da eseguire ogni volta quando cambia lo stato dell'oggetto
    myrequest.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200) { //dopo aver ricevuto conferma dal server
            alert("Grazie per la tua donazione di "+somma+"€!"); // mostra allert di conferma
        }
    };
    myrequest.send(); //apre la connessione ed invia la richiesta al server

}

//La funzione che richiede i dati dell'utente attuale dalla Database
function showHideFun(){
    //path = http://localhost:8080/Gruppo14_war_exploded
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];
    var myJson;
    var url = path+"/RetrieveUtentiRuolo?singolo=true";
    var myrequest = new XMLHttpRequest(); // crea un oggetto XMLHttpRequest
    myrequest.open("POST", url, true);
    //la chiamata a open non apre la connessione. Configura solo la richiesta, ma l’attività di rete comincia solo dopo la chiamata a send

    myrequest.responseType = "text";
    //una funzione da eseguire ogni volta quando cambia lo stato dell'oggetto
    myrequest.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            changeContent(JSON.parse(myrequest.responseText)); // chiama la funzione changeContent per far mostrare i dati dell'utente nella pagina
        }
    };

    myrequest.send(); //apre la connessione ed invia la richiesta al server
}
//La funzione che fa visibili i dati dell'utente nella pagina
function changeContent(utente){
    let areaVisualizzazione = document.getElementById('dati'); //la sezione della pagina dove vengono mostrati i dati

    while (areaVisualizzazione.childNodes.length) { //pulisce la pagina se i dati gia presentano
        areaVisualizzazione.removeChild(areaVisualizzazione.childNodes[0]);
    }
    // stampa i dati dell'utente attuale
    areaVisualizzazione.innerHTML += "<h2 style=\"text-align:center;\">"+"I tuoi dati personali sono"+"</h2>";
    if (utente !== null){
        let content =
            " <div id=\"Utente\"> <div id=\"NomeCognome\">"
            + "<h3 id=\"Nome\">" + utente.nome + " "+ utente.cognome +"</h3>"
            + "</div> <div id=\"DatiPersonali\">"
            + "<p id=\"Username\"> &ensp;Username:&ensp; "+utente.username+"</p>"
            + "<p id=\"Nascita\"> &ensp;Data di Nascita:&ensp; " + utente.nascita +"</p>"
            + "<p id=\"Email\"> &ensp;Email:&ensp; "+utente.email+"</p>"
            + "<p id=\"Telefono\"> &ensp;Telefono:&ensp; "+utente.telefono+"</p>"
            + "<p id=\"Attività\"> &ensp;Attività:&ensp; "+utente.attivita+"</p>"
            + "</div>";

        areaVisualizzazione.innerHTML += content;
    }
}
/*
* ruolo = admin/aderente/simpatizzante/tutti
*/
function getDataUtenti(ruolo){
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];
    var url = path+"/RetrieveUtentiRuolo?ruolo="+ruolo;
    var myrequest = new XMLHttpRequest();
    myrequest.open("POST", url, true);
    myrequest.responseType = "text";
    myrequest.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            visualizzaUtenti(JSON.parse(this.response), ruolo);
        }
    };
    myrequest.send();
}

function aggiornaFileVisite(){
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];
    var url = path+"/Visite?mostra=true";
    var myrequest = new XMLHttpRequest();
    myrequest.open("POST", url, true);
    myrequest.responseType = "text";
    myrequest.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            readTextFile("./visite.JSON", function(visiteJson){
                HistVisite([
                    visiteJson.Sito, visiteJson.Home, visiteJson.ChiSiamo,
                    visiteJson.Attivita, visiteJson.DescrizioneA1, visiteJson.DescrizioneA2,
                    visiteJson.DescrizioneA3, visiteJson.Contatti, visiteJson.EmailConfermata,
                    visiteJson.SignIn,  visiteJson.RegistrazioneConfermata,  visiteJson.LogIn, visiteJson.AreaPersonale
                ]);
            });
        }
    };
    myrequest.send();
}

function readTextFile(file, callback) {
    var rawFile = new XMLHttpRequest();
    rawFile.overrideMimeType("application/json");
    rawFile.open("GET", file, true);
    rawFile.onreadystatechange = function() {
        if (rawFile.readyState === 4 && rawFile.status === 200) {
            callback(JSON.parse(rawFile.responseText));
        }
    }
    rawFile.send(null);
}

function resetDashboard(){
    let areaVisualizzazione = document.getElementById("areaVisualizzazione");
    while (areaVisualizzazione.childNodes.length) {
        areaVisualizzazione.removeChild(areaVisualizzazione.childNodes[0]);
    }
}

function visualizzaUtenti(utentiJson, ruolo){
    let areaVisualizzazione = document.getElementById("areaVisualizzazione");
    resetDashboard();

    //if no data is available
    if (utentiJson === null) {
        areaVisualizzazione.textContent = "Sembra non ci siano dati da visualizzare per la categoria selezionata";
        return
    }

    if (ruolo === "tutti" || ruolo === "simpatizzante" || ruolo === "aderente") {
        areaVisualizzazione.innerHTML += "<h2 style=\"text-align:center;\">"+ruolo+"</h2>";
        for(let i in utentiJson.utenti) {
            let utente = utentiJson.utenti[i];
            let content = " <div id=\"Utente\"> <div id=\"NomeCognome\">"
                + "<h3 id=\"Nome\">" + utente.nome + " " + utente.cognome + "</h3>"
                + "</div> <div id=\"DatiPersonali\">"
                + "<p id=\"Username\"> &ensp;Username:&ensp; " + utente.username + "</p>"
                + "<p id=\"Nascita\"> &ensp;Data di Nascita:&ensp; " + utente.nascita + "</p>"
                + "<p id=\"Email\"> &ensp;Email:&ensp; " + utente.email + "</p>"
                + "<p id=\"Telefono\"> &ensp;Telefono:&ensp; " + utente.telefono + "</p>"
                + "</div>";
            areaVisualizzazione.innerHTML += content;
        }
    }
}

function visualizzaVisite(){
    let areaVisualizzazione = document.getElementById("areaVisualizzazione");

    resetDashboard();
    areaVisualizzazione.innerHTML += "<h1><br>Area personale amministratore.<br><h1>"
    areaVisualizzazione.innerHTML += "<div id=\"container\"</div>";

    var reset = "<br><br>"
        +"<button id=\"reset\" onclick=\"resetVisite()\">Resetta visite</button>";
    areaVisualizzazione.innerHTML += reset;

    aggiornaFileVisite();
}

function visualizzaDonazioni(){
    let areaVisualizzazione = document.getElementById("areaVisualizzazione");
    resetDashboard();
    areaVisualizzazione.innerHTML += "<h1><br>Area personale amministratore.<br><h1>"
    areaVisualizzazione.innerHTML += "<div id=\"container\"></div><br><br>";

    var reset = "<br><br>"
        +"<button id=\"reset\" onclick=\"resetDonazioni()\">Resetta donazioni</button>";
    areaVisualizzazione.innerHTML += reset;

    readTextFile("./donazioni.JSON", function(donazioniJson){
        var donazioni = [];
        if(!donazioniJson.successo === true){
            alert("Errore nella ricezione delle donazioni.");
            return;
        }

        for(var mese in donazioniJson.mese){
            donazioni.push(donazioniJson.mese[mese]);
        }
        HistDonazioni(donazioni);
    })
}

function resetDonazioni(){
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];
    var url = path+"/Donazioni?reset=true";
    var myrequest = new XMLHttpRequest();
    myrequest.open("POST", url, true);
    myrequest.responseType = "text";
    myrequest.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            alert("Resettate le donazioni! 💸");
        }
    };
    myrequest.send();
}

function resetVisite(){
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];
    var url = path+"/Visite?reset=true";
    var myrequest = new XMLHttpRequest();
    myrequest.open("POST", url, true);
    myrequest.responseType = "text";
    myrequest.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            alert("Resettato i valori! 🐱‍🐉");
        }
    };
    myrequest.send();
}

function HistVisite(visite){
    Highcharts.chart('container', {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Visite'
        },
        subtitle: {
            text: 'Ogni colonna riporta il numero di visite della pagina corrispondente'
        },
        xAxis: {
            categories: [
                'Sito',
                'Home',
                'ChiSiamo',
                'Attività',
                'DescrizioneA1',
                'DescrizioneA2',
                'DescrizioneA3',
                'Contatti',
                'emailConfermata',
                'SignIn',
                'registrazioneConfermata',
                'LogIn',
                'areaPersonale'
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 1,
                borderWidth: 1,
                groupPadding: 1,
                shadow: false
            }
        },
        series: [{
            name: 'Visite',
            data: [visite[0], visite[1],visite[2],visite[3],visite[4],visite[5],visite[6],visite[7],visite[8],visite[9],visite[10],visite[11],visite[12]]
        }]
    });
}

function HistDonazioni(donazioni){
    Highcharts.chart('container', {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Donazioni'
        },
        subtitle: {
            text: 'Ogni colonna riporta il numero di donazioni effettuate nel mese corrispondente'
        },
        xAxis: {
            categories: [
                'Gennaio',
                'Febbraio',
                'Marzo',
                'Aprile',
                'Maggio',
                'Giugno',
                'Luglio',
                'Agosto',
                'Settembre',
                'Ottobre',
                'Novembre',
                'Dicembre'
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 1,
                borderWidth: 1,
                groupPadding: 1,
                shadow: false
            }
        },
        series: [{
            name: 'Donazioni',
            data: [donazioni[0],donazioni[1],donazioni[2],donazioni[3],donazioni[4],donazioni[5],donazioni[6],donazioni[7],donazioni[8],donazioni[9],donazioni[10],donazioni[11]]

        }]
    });
}
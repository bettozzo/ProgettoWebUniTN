function conta() {
    var paginaInUrl = window.location.pathname.split('/').at(-1);
    //Nella homepage non è presente la pagina
    //Dopo logout torno alla homepage, ma URL contiene "/Logout"
    if(paginaInUrl==="" || paginaInUrl==="Logout"){
        paginaInUrl = "Home.jsp";
    }
    else if(paginaInUrl==="Login"){ //analogo a logout
        paginaInUrl = "AreaPersonale.jsp"
    }
    //aggiorno il DB, passandoli il nome della pagina, senza estensioni
    aggiornaInDB(paginaInUrl.split('.')[0]);
}

function aggiornaInDB(pagina){
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];
    var url= path+'/Visite?pagina='+pagina;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", url, true);
    xhttp.send();
}


conta();
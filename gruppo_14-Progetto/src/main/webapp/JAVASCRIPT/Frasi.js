var arrayFrasi = [];
function getJson() {
    //path = http://localhost:8080/Gruppo14_war_exploded
    let path = window.location.origin + "/" + window.location.pathname.split("/")[1];
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", path+"/FrasiServlet", true);
    xhttp.responseType = "text";
    xhttp.onreadystatechange = function () {
        var done = 4, ok = 200;
        if (this.readyState === done && this.status === ok){
            arrayFrasi =this.response.split("\"").filter((str) => str!=="").slice(0, -1);
        }
    };
    xhttp.send();
}

let lastElement = -1;
function changeFrasi() {
    if (arrayFrasi !== []) {
        let randomIndex = Math.floor(Math.random() * arrayFrasi.length);
        lastElement = randomIndex;
        document.getElementById("citazione").textContent = arrayFrasi[randomIndex];
    }
    else{
        document.getElementById("citazione").textContent = "errore nel caricare la frase :(";
    }
}

getJson();
setInterval(changeFrasi, 20*1000);

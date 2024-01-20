//convalida i campi del login (unica restrizione che non siano vuoti, che esistano controlla il DB)
function loginValidate() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let res = validateFields(username, password);

    return res;
}

function validateFields(username, password){

    let res = true;
    if(username==="" || password===""){
        alert("Non lasciare campi vuoti.");
        res = false;
    }

    return res;
}
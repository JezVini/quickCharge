function cepSearch(valor) {
    if (cepValidateAndSanitize(valor)) {
        fieldsWaiting("...");

        var script = document.createElement('script');

        var cep = valor.replace(/\D/g, '');
        script.src = 'https://viacep.com.br/ws/'+ cep + '/json/?callback=cepDataReceiver';
        document.body.appendChild(script);
    } else {
        addressInputsEmpty();
        Atlas.notifications.showAlert("Formato de CEP inválido", "warning");
    }
}

function cepDataReceiver(json) {
    if (!("erro" in json)) {
        document.getElementById('address').value=(json.logradouro);
        document.getElementById('district').value=(json.bairro);
        document.getElementById('city').value=(json.localidade);
        document.getElementById('state').value=(json.uf);
    } else {
        addressInputsEmpty();
        Atlas.notifications.showAlert("CEP não encontrado", "warning");
    }
}

function cepValidateAndSanitize(cep) {
    var cep = cep.replace(/\D/g, '');
    
    if (cep != "") {
        var validacep = /^[0-9]{8}$/;
        if (validacep.test(cep)) {
            return true;
        }
    }
    return false;
}

function addressInputsEmpty() {
    document.getElementById('address').value=("");
    document.getElementById('district').value=("");
    document.getElementById('city').value=("");
    document.getElementById('state').value=("");
}

function fieldsWaiting(character) {
    document.getElementById('address').value = character;
    document.getElementById('district').value = character;
    document.getElementById('city').value = character;
    document.getElementById('state').value = character;
}

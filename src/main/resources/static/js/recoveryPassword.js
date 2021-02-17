function registrationFunction(url, formId, inputType) {
    const urlForRecoveryEmail = url;
    let recoveryPassword = document.getElementById(formId);
    let formElements = new FormData(recoveryPassword);

    let passData = {
        type: document.getElementById(inputType).value
    }
    console.log('newData = ' + passData);
    let json_newData = JSON.stringify(passData);
    sendRequest('POST', urlForRecoveryEmail, json_newData)
        .then(() => {
            console.log('Send creating user data');
        })
        .catch(err => console.log(err));

    async function sendRequest(method, url, body) {
        await fetch(urlForRecoveryEmail, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        }).then(response => {
            if (response.ok) {
                document.getElementById('password-text').innerHTML = '<small class="help-block d-flex justify-content-center text-success">Ссылка на восстановление пароля отправлена</small>'
            } else {
                document.getElementById('password-text').innerHTML = '<small class="help-block d-flex justify-content-center text-danger">Email не найден</small>'
            }
        }).catch(err => console.log(err));
    }
}
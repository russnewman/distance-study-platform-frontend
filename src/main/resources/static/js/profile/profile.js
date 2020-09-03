var inputValidator = new InputValidator();

let forms = document.querySelectorAll('.needs-validation');
forms.forEach(form => {
    form.addEventListener('submit', checkCorrect);
});

let confirmPassInput = document.getElementById('new-password-confirm-input')
let newPassInput = document.getElementById('new-password-input')
confirmPassInput.addEventListener('input', inputValidator.validateConfirmationPass.bind(inputValidator))
newPassInput.addEventListener('input', inputValidator.validatePass.bind(inputValidator))

function checkCorrect(event) {
    var inputs = event.target.querySelectorAll('input:not([type="hidden"])')
    let valid = true;
    for (var i = 0; i < inputs.length; i++) {
        if (!inputs[i].classList.contains('is-valid')) {
            valid = false;
            break;
        }
    }
    if (!valid) {
        event.preventDefault();
        event.stopPropagation();
    }
}

function back() {
    window.location.href = "/home"
}
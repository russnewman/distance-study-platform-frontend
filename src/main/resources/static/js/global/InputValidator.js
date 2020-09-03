class InputValidator {
    setInvalidAndErrorMessage(target, message) {
        target.nextElementSibling.textContent = message;
        target.classList.remove('is-valid');
        target.classList.add('is-invalid');
    }

    setValid(target) {
        target.classList.remove('is-invalid');
        target.classList.add('is-valid');
    }

    setDefault(target) {
        target.classList.remove('is-invalid');
        target.classList.remove('is-valid');
    }

    validateNotNull(target) {
        let value = target.value;
        if (value === "") {
            this.setInvalidAndErrorMessage(target, 'Can\'t be empty');
            return true;
        }
        return false;
    }

    validateName(target) {
        let value = target.value;
        if (this.validateNotNull(target)) {
            return;
        }
        if (!RegexStorage.namePattern().test(value)) {
            this.setInvalidAndErrorMessage(target, 'Should start with Uppercase and minimum 2 characters');
        } else {
            this.setValid(target);
        }
    }

    validateEmail(target) {
        if (this.validateNotNull(target)) {
            return;
        }
        if (!target.checkValidity()) {
            this.setInvalidAndErrorMessage(target, 'Incorrect email');
        } else {
            this.setValid(target);
        }
    }

    findPass(confirmPassInput) {
        let inputs = confirmPassInput.closest('form').querySelectorAll('input[type="password"]')
        return inputs[inputs.length - 2]
    }

    findConfirmPass(confirmPassInput) {
        let inputs = confirmPassInput.closest('form').querySelectorAll('input[type="password"]')
        return inputs[inputs.length - 1]
    }

    validateConfirmationPass(event) {
        let confirmPassInput = event.target;
        let confirmPassValue = confirmPassInput.value;
        let passInput = this.findPass(confirmPassInput);
        if (this.validateNotNull(confirmPassInput)) {
            return;
        }
        if (confirmPassValue !== passInput.value) {
            this.setInvalidAndErrorMessage(confirmPassInput, 'Different passwords!');
        } else {
            this.setValid(confirmPassInput);
        }
    }

    validatePass(event) {
        let passInput = event.target
        this.validateSinglePass(passInput)
        let confirmPassInput = this.findConfirmPass(passInput);

        if (passInput.value === confirmPassInput.value) {
            this.setValid(confirmPassInput)
        } else {
            this.setInvalidAndErrorMessage(confirmPassInput, 'Different passwords!')
        }
    }

    validateSinglePass(target) {
        let passInput = target;
        let passInputValue = passInput.value;

        if (this.validateNotNull(passInput)) {
            return;
        }
        let checkInfo = this.checkIfPasswordCorrect(passInputValue)
        if (!checkInfo.correct) {
            this.setInvalidAndErrorMessage(passInput, checkInfo.message)
        } else {
            this.setValid(passInput)
        }
    }

    validatePhone(target) {
        if (this.validateNotNull(target)) {
            return;
        }
        this.setValid(target)
    }

    checkIfPasswordCorrect(value) {
        let message;
        let correct = false;
        if (value.length < 6) {
            message = 'Should have 6 or more character'
        } else if (!RegexStorage.passwordPattern().test(value)) {
            message =  'Should contain only characters and numbers'
        } else if (!new RegExp('[A-Za-z]+?').test(value)) {
            message = 'Should contain character'
        } else if (!new RegExp('[0-9]+?').test(value)) {
            message = 'Should contain number'
        } else {
            correct = true;
        }
        return {correct: correct, message: message}
    }
}
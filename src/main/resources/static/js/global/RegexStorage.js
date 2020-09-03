class RegexStorage {
    static namePattern() {
        return new RegExp("^[A-Z][a-z]+$");
    }

    static passwordPattern() {
        return new RegExp('^[A-Za-z0-9]+$');
    }
}
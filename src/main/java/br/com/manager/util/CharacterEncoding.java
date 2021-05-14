package br.com.manager.util;

public enum CharacterEncoding {
    UTF_8("UTF-8");

    private String value;

    private CharacterEncoding(String encoding) {
        this.value = encoding;
    }

    public String value() {
        return value;
    }
}

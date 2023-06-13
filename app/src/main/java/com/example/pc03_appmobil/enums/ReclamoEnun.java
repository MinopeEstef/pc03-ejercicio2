package com.example.pc03_appmobil.enums;

public enum ReclamoEnun {

    TABLE_NAME ("mi_reclamos"),
    COL_ID ("id"),
    COL_CODIGO( "codigo"),
    COL_ASUNTO ("asunto"),
    COL_DESCRIPCION ("descripcion"),
    COL_ESTADO ("estado"),
    COL_FECHA("fecha"),
    KEY_NAME("reclamo");

    private String value;

    ReclamoEnun(String value) {
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

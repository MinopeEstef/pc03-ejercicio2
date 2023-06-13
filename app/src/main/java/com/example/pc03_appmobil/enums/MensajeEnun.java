package com.example.pc03_appmobil.enums;

public enum MensajeEnun {

    OK_REGISTRO("Registro guardado"),
    ERROR_REGISTRO("Error al registrar"),
    CAMPO_IMPOCOMPLETO("Complete todos los campos"),
    ELIMINAR_TODO("¿Estás seguro de que deseas eliminar todos los datos?"),
    ELIMINAR_ITEM("Estás seguro de eliminar el dato?")
    ;
    private String value;

    MensajeEnun(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

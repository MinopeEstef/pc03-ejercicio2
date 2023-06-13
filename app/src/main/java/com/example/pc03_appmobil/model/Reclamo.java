package com.example.pc03_appmobil.model;

import java.io.Serializable;

public class Reclamo  implements Serializable {

    private int id;
    private String asunto;
    private String codigo;
    private String descripcion;
    private String estado;
    private String fecha;

    public Reclamo(int id, String asunto, String codigo, String descripcion, String estado, String fecha) {
        this.id = id;
        this.asunto = asunto;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
    }
    public Reclamo( String asunto, String codigo, String descripcion, String estado, String fecha) {
         this.asunto = asunto;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Reclamo{" +
                "id=" + id +
                ", asunto='" + asunto + '\'' +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}

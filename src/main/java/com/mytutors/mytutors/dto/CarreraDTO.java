package com.mytutors.mytutors.dto;

public class CarreraDTO {

    private Long id;
    private String nombre;

    public CarreraDTO() {}

    public CarreraDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}

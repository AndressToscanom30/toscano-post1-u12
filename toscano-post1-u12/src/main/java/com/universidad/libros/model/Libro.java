package com.universidad.libros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Column(nullable = false)
    private String autor;

    @NotNull(message = "El año es obligatorio")
    @Positive
    private Integer anio;

    @NotBlank(message = "El género es obligatorio")
    private String genero;

    private Boolean disponible = true;

    // Constructores
    public Libro() {}

    public Libro(String titulo, String autor, Integer anio, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.genero = genero;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
}
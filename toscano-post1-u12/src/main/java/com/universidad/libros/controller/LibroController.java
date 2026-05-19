package com.universidad.libros.controller;

import com.universidad.libros.model.Libro;
import com.universidad.libros.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService service;

    public LibroController(LibroService service) {
        this.service = service;
    }

    // GET /api/libros
    @GetMapping
    public List<Libro> listar() {
        return service.listarTodos();
    }

    // GET /api/libros/disponibles
    @GetMapping("/disponibles")
    public List<Libro> disponibles() {
        return service.listarDisponibles();
    }

    // GET /api/libros/{id}
    @GetMapping("/{id}")
    public Libro obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // GET /api/libros/genero/{genero}
    @GetMapping("/genero/{genero}")
    public List<Libro> porGenero(@PathVariable String genero) {
        return service.buscarPorGenero(genero);
    }

    // GET /api/libros/autor?nombre=...
    @GetMapping("/autor")
    public List<Libro> porAutor(@RequestParam String nombre) {
        return service.buscarPorAutor(nombre);
    }

    // POST /api/libros
    @PostMapping
    public ResponseEntity<Libro> crear(@Valid @RequestBody Libro libro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(libro));
    }

    // PUT /api/libros/{id}
    @PutMapping("/{id}")
    public Libro actualizar(@PathVariable Long id, @Valid @RequestBody Libro libro) {
        return service.actualizar(id, libro);
    }

    // DELETE /api/libros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
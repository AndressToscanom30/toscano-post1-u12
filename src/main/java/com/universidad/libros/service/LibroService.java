package com.universidad.libros.service;

import com.universidad.libros.exception.LibroNotFoundException;
import com.universidad.libros.model.Libro;
import com.universidad.libros.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository repo;

    public LibroService(LibroRepository repo) {
        this.repo = repo;
    }

    public List<Libro> listarTodos() {
        return repo.findAll();
    }

    public List<Libro> listarDisponibles() {
        return repo.findByDisponibleTrue();
    }

    public List<Libro> buscarPorGenero(String genero) {
        return repo.findByGeneroIgnoreCase(genero);
    }

    public List<Libro> buscarPorAutor(String autor) {
        return repo.findByAutorContainingIgnoreCase(autor);
    }

    public Libro buscarPorId(Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new LibroNotFoundException(id));
    }

    public Libro crear(Libro libro) {
        return repo.save(libro);
    }

    public Libro actualizar(Long id, Libro datos) {
        Libro libro = buscarPorId(id);
        libro.setTitulo(datos.getTitulo());
        libro.setAutor(datos.getAutor());
        libro.setAnio(datos.getAnio());
        libro.setGenero(datos.getGenero());
        libro.setDisponible(datos.getDisponible());
        return repo.save(libro);
    }

    public void eliminar(Long id) {
        buscarPorId(id); // lanza 404 si no existe
        repo.deleteById(id);
    }
}
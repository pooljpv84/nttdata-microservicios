package com.paul.micro1.service;

import com.paul.micro1.domain.Persona;
import com.paul.micro1.dto.PersonaRequest;
import com.paul.micro1.dto.PersonaResponse;
import com.paul.micro1.repository.PersonaRepository;
import com.paul.micro1.exception.BusinessException;
import com.paul.micro1.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository repo;

    @Transactional
    public Long create(PersonaRequest req) {
        if (repo.existsByIdentificacion(req.identificacion())) {
            throw new BusinessException("La identificación ya existe");
        }
        Persona p = toEntity(req);
        return repo.save(p).getId();
    }

    @Transactional
    public void update(Long id, PersonaRequest req) {
        Persona p = repo.findById(id).orElseThrow(() -> new NotFoundException("Persona no encontrada"));
        updateEntity(p, req);
        repo.save(p);
    }

    public Persona getEntidadPersonaById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Persona no encontrada"));
    }

    public PersonaResponse getPersonaById(Long id) {
        return toResponse(getEntidadPersonaById(id));
    }

    public Page<PersonaResponse> listar(int page, int size, String sort) {
        Page<Persona> pag = repo.findAll(PageRequest.of(page, size, Sort.by(sort).ascending()));
        return pag.map(this::toResponse);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Persona no encontrada");
        repo.deleteById(id);
    }

    // --- Mappers mínimos locales ---
    private Persona toEntity(PersonaRequest r) {
        return Persona.builder()
                .nombre(r.nombre())
                .genero(r.genero())
                .edad(r.edad())
                .identificacion(r.identificacion())
                .direccion(r.direccion())
                .telefono(r.telefono())
                .build();
    }

    private void updateEntity(Persona p, PersonaRequest r) {
        p.setNombre(r.nombre());
        p.setGenero(r.genero());
        p.setEdad(r.edad());
        p.setDireccion(r.direccion());
        p.setTelefono(r.telefono());
    }

    private PersonaResponse toResponse(Persona p) {
        return new PersonaResponse(
                p.getId(), p.getIdentificacion(), p.getNombre(),
                p.getDireccion(), p.getGenero(), p.getTelefono());
    }
}

package com.paul.micro1.service;

import com.paul.micro1.domain.Cliente;
import com.paul.micro1.domain.Persona;
import com.paul.micro1.dto.ClienteRequest;
import com.paul.micro1.dto.ClienteResponse;
import com.paul.micro1.dto.ClienteUpdateRequest;
import com.paul.micro1.dto.ClientePersonaCreateRequest;
import com.paul.micro1.dto.PersonaMiniRequest;
import com.paul.micro1.events.ClienteEventProducer;
import com.paul.micro1.repository.ClienteRepository;
import com.paul.micro1.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paul.micro1.exception.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepo;
    private final PersonaRepository personaRepo;
    private final ClienteEventProducer producer;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long create(ClienteRequest req) {
        Persona persona = personaRepo.findByIdentificacion(req.identificacion())
                .orElseThrow(() -> new NotFoundException("Persona no existe para esa identificación"));
        if (clienteRepo.existsByClienteId(req.clienteId())) {
            throw new BusinessException("clienteId ya existe");
        }
        if (clienteRepo.existsById(persona.getId())) {
            throw new BusinessException("La persona ya está registrada como cliente");
        }
        // Evitar inserción en PERSONA (JOINED): insertar directamente en la tabla cliente usando el id existente
        boolean estado = req.estado() == null || req.estado();
        em.createNativeQuery("INSERT INTO cliente (id, clienteid, contrasenia, estado) VALUES (?, ?, ?, ?)")
                .setParameter(1, persona.getId())
                .setParameter(2, req.clienteId())
                .setParameter(3, req.contrasenia())
                .setParameter(4, estado)
                .executeUpdate();
        // Asegurar que el contexto no mantenga Persona sin "upgrade" a Cliente
        em.flush();
        em.clear();

        // RECUPERAR entidad y PUBLICAR KAFKA
        Cliente c = clienteRepo.findById(persona.getId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado post-insert"));
        producer.publishCreated(c);

        return persona.getId();
    }

    @Transactional
    public Long createFull(ClientePersonaCreateRequest req) {
        PersonaMiniRequest pr = req.persona();
        if (pr == null) throw new BusinessException("Datos de persona son obligatorios");
        // Validar unicidad de identificacion para crear persona
        if (personaRepo.existsByIdentificacion(pr.identificacion())) {
            throw new BusinessException("La identificación ya existe");
        }
        // Crear persona primero
        Persona persona = Persona.builder()
                .nombre(pr.nombre())
                .genero(req.genero())
                .edad(req.edad())
                .identificacion(pr.identificacion())
                .direccion(pr.direccion())
                .telefono(pr.telefono())
                .build();
        Long personaId = personaRepo.save(persona).getId();

        // clienteId efectivo: si no viene, usar identificacion
        String clienteId = (req.clienteId() == null || req.clienteId().isBlank()) ? pr.identificacion() : req.clienteId();
        if (clienteRepo.existsByClienteId(clienteId)) {
            throw new BusinessException("clienteId ya existe");
        }
        // Insertar en cliente usando el id de persona
        boolean estado = req.estado() == null || req.estado();
        em.createNativeQuery("INSERT INTO cliente (id, clienteid, contrasenia, estado) VALUES (?, ?, ?, ?)")
                .setParameter(1, personaId)
                .setParameter(2, clienteId)
                .setParameter(3, req.password())
                .setParameter(4, estado)
                .executeUpdate();
        // Asegurar visibilidad y evitar entidades en caché como Persona
        em.flush();
        em.clear();
        // PUBLICAR evento KAFKA
        Cliente c = clienteRepo.findById(personaId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado post-insert"));
        producer.publishCreated(c);

        return personaId;
    }

    public ClienteResponse getClienteById(String clienteId) {
        Cliente c = clienteRepo.findByClienteId(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        return new ClienteResponse(
                c.getId(), c.getClienteId(), c.getIdentificacion(), c.getNombre(), c.getEstado()
        );
    }

    public ClienteResponse getByPk(Long id) {
        Cliente c = clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        return toResponse(c);
    }

    public ClienteResponse getByIdentificacion(String identificacion) {
        Cliente c = clienteRepo.findByIdentificacion(identificacion)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        return toResponse(c);
    }

    public List<ClienteResponse> listar() {
        return clienteRepo.findAll()
                .stream()
                .map(c -> new ClienteResponse(
                        c.getId(), c.getClienteId(), c.getIdentificacion(), c.getNombre(), c.getEstado()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void cambiarEstado(String clienteId, boolean estado) {
        Cliente c = clienteRepo.findByClienteId(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        c.setEstado(estado);
        clienteRepo.save(c);
        producer.publishEstadoChanged(c); // kafka detecta cambio
    }

    @Transactional
    public void update(Long id, ClienteUpdateRequest req) {
        Cliente c = clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        c.setContrasenia(req.contrasenia());
        c.setEstado(req.estado());
        clienteRepo.save(c);
        producer.publishUpdated(c); // kafka update
    }
    @Transactional
    public void delete(Long id) {
        if (!clienteRepo.existsById(id)) {
            throw new NotFoundException("Cliente no encontrado");
        }
        //solo borrar al ciente no a la persona
        clienteRepo.deleteById(id);
        producer.publishDeleted(id); //kafka deleted
    }


    private ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(
                c.getId(), c.getClienteId(), c.getIdentificacion(), c.getNombre(), c.getEstado()
        );
    }
}

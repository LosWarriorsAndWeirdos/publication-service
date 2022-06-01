package com.acme.publicacion.controller;

import com.acme.publicacion.entity.Publication;
import com.acme.publicacion.service.PublicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/publications")
public class PublicationController {
    @Autowired
    private PublicationService publicationService;

    @GetMapping
    public ResponseEntity<List<Publication>> listAllPublications()
    {
        List<Publication> publications = new ArrayList<>();
        publications = publicationService.ListAllPublications();
        if (publications.isEmpty())
        {
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(publications);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Publication> getPublication(@PathVariable("id") Long id){
        log.info("Obteniendo la publicaciono con id {}", id);
        Publication publication = publicationService.getPublication(id);
        if (publication==null)
        {
            log.error("La publicacion con id {} no existe", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publication);
    }

    @PostMapping
    public ResponseEntity<Publication> createPropietario(@Valid @RequestBody Publication publication, BindingResult result)
    {
        log.info("Creando publicación {}", publication);
        if (result.hasErrors())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Publication publicationDB = publicationService.createPublication(publication);
        return ResponseEntity.status(HttpStatus.CREATED).body(publicationDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updatePublication(@PathVariable("id") long id, @RequestBody Publication publication)
    {
        log.info("Actualizando publicacion con id {}", id);
        Publication currentPublication = publicationService.getPublication(id);

        if (currentPublication == null)
        {
            log.error("No se puede actualizar. Publicación con Id {} no fue encontrado", id);
            return ResponseEntity.notFound().build();
        }
        currentPublication = publicationService.updatePublication(publication);
        return ResponseEntity.ok(currentPublication);
    }

    private String formatMessage(BindingResult result)
    {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}

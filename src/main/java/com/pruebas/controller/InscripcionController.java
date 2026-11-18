package com.pruebas.controller;

import com.pruebas.model.Inscripcion;
import com.pruebas.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService service;

    @PostMapping
    public ResponseEntity<?> inscribir(@RequestBody Inscripcion inscripcion) {
        try {
            Inscripcion nuevaInscripcion = service.inscribir(inscripcion.getCodigoEstudiante(), inscripcion.getCursoId());

            return ResponseEntity.status(HttpStatus.OK).body(nuevaInscripcion);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
package com.pruebas.service;

import com.pruebas.model.Inscripcion;
import com.pruebas.repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    public Inscripcion inscribir(String codigoEstudiante, Long cursoId) {
        boolean existe = inscripcionRepository.existsByCodigoEstudianteAndCursoId(codigoEstudiante, cursoId);
        if (existe) {
            throw new RuntimeException("El estudiante ya se encuentra inscrito en este curso.");
        }

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCodigoEstudiante(codigoEstudiante);
        inscripcion.setCursoId(cursoId);

        return inscripcionRepository.save(inscripcion);
    }
}
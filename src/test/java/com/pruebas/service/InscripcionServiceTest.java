package com.pruebas.service;

import com.pruebas.model.Inscripcion;
import com.pruebas.repository.InscripcionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InscripcionServiceTest {

    private static final String ALUMNO = "Carlos Asmat";
    private static final String CODIGO = "I202411474";

    @Mock
    private InscripcionRepository inscripcionRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("Alumno: " + ALUMNO + " | Código: " + CODIGO);
    }

    @Test
    @DisplayName("Caso 1: Inscribir a un estudiante correctamente")
    void testInscribirEstudianteCorrectamente() {
        String codigoEstudiante = "I202411474";
        Long idCurso = 2L;

        Inscripcion inscripcionMock = new Inscripcion();
        inscripcionMock.setCodigoEstudiante(codigoEstudiante);
        inscripcionMock.setCursoId(idCurso);

        when(inscripcionRepository.existsByCodigoEstudianteAndCursoId(codigoEstudiante, idCurso)).thenReturn(false);
        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(inscripcionMock);

        Inscripcion resultado = inscripcionService.inscribir(codigoEstudiante, idCurso);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(codigoEstudiante, resultado.getCodigoEstudiante());
        verify(inscripcionRepository, times(1)).save(any(Inscripcion.class));
    }

    @Test
    @DisplayName("Caso 2: Intentar inscribir a un estudiante que ya está inscrito")
    void testInscribirEstudianteDuplicado() {
        String codigoEstudiante = "I202411474";
        Long idCurso = 1L;

        when(inscripcionRepository.existsByCodigoEstudianteAndCursoId(codigoEstudiante, idCurso)).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            inscripcionService.inscribir(codigoEstudiante, idCurso);
        });

        assertNotNull(exception.getMessage());
        verify(inscripcionRepository, never()).save(any(Inscripcion.class));
    }
}
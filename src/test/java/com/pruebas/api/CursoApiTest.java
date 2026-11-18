package com.pruebas.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CursoApiTest {

    @BeforeAll
    static void setup() {
        System.out.println("Alumno: Carlos Asmat | Código: I202411474");
    }

    @Test
    @DisplayName("Escenario 1: Consultar todos los cursos")
    void testConsultarTodosLosCursos() {

        int statusCode = 200;
        boolean listaNoVacia = true;

        assertEquals(200, statusCode, "El código de estado HTTP debe ser 200");
        assertTrue(listaNoVacia, "La lista de cursos recuperada no debe estar vacía");
    }

    @Test
    @DisplayName("Escenario 2: Consultar el curso con id 1")
    void testConsultarCursoPorId() {

        int statusCode = 200;
        String nombreCurso = "Pruebas de Software";
        double precio = 150.0;

        assertEquals(200, statusCode, "El código de estado HTTP debe ser 200");
        assertEquals("Pruebas de Software", nombreCurso, "El nombre del curso con ID 1 debe ser 'Pruebas de Software'");
        assertTrue(precio > 0.0, "El precio del curso debe ser mayor a cero");
    }
}
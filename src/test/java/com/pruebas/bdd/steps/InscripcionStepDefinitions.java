package com.pruebas.bdd.steps;

import com.pruebas.ModeloT1PswApplication;
import io.cucumber.java.Before;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InscripcionStepDefinitions {

    private static boolean servidorIniciado = false;
    private Response response;
    private Map<String, Object> requestBody;
    private static String ultimoCodigoEstudiante;

    @BeforeAll
    public static void iniciarServidor() {
        if (!servidorIniciado) {
            new Thread(() -> ModeloT1PswApplication.main(new String[]{})).start();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            servidorIniciado = true;
        }
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        requestBody = new HashMap<>();
    }

    @Dado("que un estudiante nuevo se inscribe en el curso {int}")
    public void queUnEstudianteNuevoSeInscribeEnElCurso(int idCurso) {
        ultimoCodigoEstudiante = "I" + System.currentTimeMillis();
        requestBody.put("codigoEstudiante", ultimoCodigoEstudiante);
        requestBody.put("cursoId", idCurso);
    }

    @Dado("que el estudiante {string} intenta inscribirse de nuevo en el curso {int}")
    public void queElEstudianteIntentaInscribirseDeNuevoEnElCurso(String codigoEstudiante, int idCurso) {

        requestBody.put("codigoEstudiante", ultimoCodigoEstudiante != null ? ultimoCodigoEstudiante : codigoEstudiante);
        requestBody.put("cursoId", idCurso);
    }

    @Cuando("envío la solicitud de inscripción al sistema")
    public void envioLaSolicitudDeInscripcionAlSistema() {
        response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/inscripciones");
    }

    @Cuando("envío la solicitud de inscripción duplicada al sistema")
    public void envioLaSolicitudDeInscripcionDuplicadaAlSistema() {
        response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/inscripciones");
    }

    @Entonces("el sistema responde con el código de estado {int}")
    public void elSistemaRespondeConElCodigoDeEstado(int statusCodeEsperado) {
        response.then().statusCode(statusCodeEsperado);
    }

    @Entonces("el sistema rechaza la inscripción con un código de error 4xx o 5xx")
    public void elSistemaRechazaLaInscripcionConUnCodigoDeError() {
        int statusCode = response.getStatusCode();
        assertTrue(statusCode >= 400, "Se esperaba un código de error 4xx o 5xx, pero fue: " + statusCode);
    }
}
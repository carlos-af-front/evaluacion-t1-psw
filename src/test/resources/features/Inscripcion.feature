# language: es
Característica: Inscripción de cursos

  Escenario: incripcion correcta
    Dado que un estudiante nuevo se inscribe en el curso 2
    Cuando envío la solicitud de inscripción al sistema
    Entonces el sistema responde con el código de estado 200

  Escenario: inscripcion duplicadaa
    Dado que un estudiante nuevo se inscribe en el curso 2
    Y envío la solicitud de inscripción al sistema
    Y que el estudiante "I202411474" intenta inscribirse de nuevo en el curso 2
    Cuando envío la solicitud de inscripción duplicada al sistema
    Entonces el sistema rechaza la inscripción con un código de error 4xx o 5xx
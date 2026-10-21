# Entrega P3-P4 — Préstamo de Material del Aula Maker

Aplicación web desarrollada con Spring Boot, Spring MVC y Thymeleaf para consultar materiales del aula maker y solicitar préstamos temporales sin persistencia.

## Requisitos de Ejecución
- Java 17 o superior.
- Maven 3.6+.
- Ejecutar con `./mvnw spring-boot:run` o desde la clase principal en NetBeans / IntelliJ IDEA.
- Puerto por defecto: `http://localhost:8080/` (o el configurado en `application.properties`).

## Rutas Principales
- `GET /` — Muestra el catálogo con la lista de materiales en memoria.
- `GET /materiales/{codigo}` — Ficha del material (`@PathVariable`) con parámetro opcional `turno` (`@RequestParam`).
- `GET /solicitud/nueva` — Muestra el formulario de solicitud enlazado al DTO.
- `POST /solicitudes` — Recibe el DTO validado, realiza la validación cruzada de fechas y presenta el resumen.

## Explicación de Ámbitos y Uso en la Vista

1. **Parámetros de Método (`@PathVariable`, `@RequestParam`, `@Valid`):**
    - Se utilizan para capturar fragmentos de la URL o parámetros de consulta recibidos en las peticiones HTTP del controlador.

2. **Variables Locales (dentro de los métodos de Java):**
    - Como `diasPrestamo` o `diasUnidad`, se usan para realizar operaciones intermedias o cálculos de negocio temporales antes de la renderización.

3. **Propiedades del DTO (`SolicitudPrestamoForm`):**
    - Definen la estructura de los datos del formulario, incluyendo las anotaciones de validación (`@NotBlank`, `@Email`, `@Min`, `@Max`, `@FutureOrPresent`).

4. **Atributos del Modelo (`Model`):**
    - Transfieren los datos del controlador a las vistas Thymeleaf. En la plantilla se consumen mediante expresiones `${...}` con `th:text` (evitando ejecuciones HTML maliciosas).

## Comprobaciones Realizadas
- Navegación al catálogo y ficha de recursos (`/materiales/MICRO?turno=tarde`).
- Manejo de error 404 para material inexistente (`/materiales/NO-EXISTE`).
- Validación en servidor de formulario vacío y campos erróneos con retorno de datos.
- Conversión de tipos incorrectos en campos numéricos/fechas.
- Validación cruzada de fechas (devolución anterior a recogida).
- Renderizado seguro de resumen sin persistencia.
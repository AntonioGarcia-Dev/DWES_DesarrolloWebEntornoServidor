# Evaluación Inicial - Gestión de Nóminas (DWES)

## Comentarios sobre el desarrollo de la práctica

Tal y como se pide en la rúbrica de entrega, dejo por aquí documentado cómo ha ido el desarrollo, lo que he podido resolver bien y los puntos donde me he atascado.

**Lo que he resuelto sin problema:**
La parte de la herencia (que `Empleado` herede de `Persona`) y el montaje de los constructores base lo tenía bastante fresco de primero. También saqué rápido la lógica de la clase `Nomina` y el cálculo del sueldo cruzando la categoría del empleado con el array estático.

**Dificultades y correcciones que he tenido que hacer:**
* **Manejo de excepciones personalizadas:** Me costó encajar bien la clase `DatosNoCorrectosException`. Al principio dudaba si hacer las comprobaciones en el `main` o dentro de la propia clase. Tras consultarlo, entendí que lo correcto era meter la validación (los `if` de los años y categorías) dentro de los constructores de `Empleado` lanzando el `throw`, y luego ya capturarlo con el `try-catch` en el programa principal para frenar la ejecución.
* **Encapsulamiento y lectura del enunciado:** Por inercia de encapsular siempre el código, definí los atributos de la clase `Persona` como `private`. Luego, releyendo el PDF, vi que el punto 1.1 pedía explícitamente que fueran `public`, así que lo tuve que cambiar.
* **Estructura del proyecto (Maven):** La primera versión del código que hice en clase no estaba estructurada con Maven. Al final he preferido cambiar el IDE, montar el proyecto bien en IntelliJ con Maven desde cero, organizar todo dentro del paquete `Laboral` y enlazar GitHub correctamente para entregarlo limpio.
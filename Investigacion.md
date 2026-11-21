# Investigación del algoritmo — `sortMerge` (V2)

## 1. Algoritmo, variante y archivos del proyecto

En esta práctica el algoritmo que analizo es **Merge Sort** en su variante **V2** (`"MergeSort"`), trabajando de forma ascendente sobre arreglos de primitivos `int[]`[cite: 14, 17, 18].

Los archivos principales donde entra en juego son:
- `src/Metodos.java`: tiene el método público `sortMerge(int[] list)` y el método privado recursivo `mergeSort(int[] list, int[] aux, int izq, int der)` que hace el trabajo pesado[cite: 17].
- `src/App.java`: llama a `m.sortMerge(...)` dentro de `Benchmark.medir(...)` sobre copias independientes (`list1c2`, `list2c2`, etc.) para las muestras de `10.000`, `50.000` y `100.000`, valida que quede ordenado y guarda el resultado[cite: 18].
- `src/PruebasAceptacion.java` y `evidencias/pruebas.md`: reúnen las pruebas de aceptación y los casos límite[cite: 8].
- `README.md`: concentra el contexto general del proyecto y la tabla comparativa de tiempos[cite: 10].

Para adaptarlo a la práctica, tomé la lógica clásica de ordenamiento por mezcla, la ajusté a `int[]` creando un arreglo auxiliar `int[] aux` del mismo tamaño al entrar a `sortMerge` y la conecté con el cronómetro de `Benchmark`[cite: 17, 18].

## 2. Cómo funciona paso a paso y qué se conserva

Cuando se llama a `sortMerge(int[] list)`, primero revisa si `list.length <= 1`; si es así, sale rápido porque un arreglo vacío o de un solo dato ya está ordenado[cite: 17]. Si tiene más elementos, reserva `int[] aux = new int[list.length]` y arranca `mergeSort(list, aux, 0, list.length - 1)`[cite: 17].

- **Parámetros:** `list` es el arreglo que se va ordenando, `aux` es el arreglo auxiliar para apoyar las mezclas, e `izq` y `der` marcan los límites inclusivos del tramo actual[cite: 17].
- **Caso base:** cuando `izq >= der`, el subarreglo tiene 0 o 1 posición y ya no se parte más.
- **Avance de la recursión:** se calcula el punto medio (`medio = izq + (der - izq) / 2`) y se invoca `mergeSort` sobre `[izq, medio]` y `[medio + 1, der]`. Como cada llamada reduce el rango, siempre llega al caso base.
- **Propiedad que se mantiene:** cada vez que termina una llamada recursiva y su mezcla, el segmento `[izq, der]` queda ordenado de menor a mayor con los mismos valores que tenía originalmente.

## 3. División, mezcla, arreglo auxiliar y empates

- **División:** parto el intervalo en dos mitades usando `medio = izq + (der - izq) / 2`, una forma segura de sacar el centro sin riesgo de desbordar un `int` al sumar índices muy grandes.
- **Mezcla:** una vez que las dos mitades ya están ordenadas, se van comparando sus elementos y copiando el menor en orden ascendente hasta unir ambas partes.
- **Arreglo auxiliar:** `aux` se crea dentro de `sortMerge(int[] list)` antes de empezar la recursión[cite: 17]. Como toda la llamada a `sortMerge` va dentro de la lambda de `Benchmark.medir(...)`[cite: 18], el costo de crear ese arreglo queda dentro del tiempo medido.
- **Empates:** cuando dos elementos comparados son iguales, priorizar el de la mitad izquierda (`<=`) hace que los valores repetidos conserven su orden relativo original.

## 4. Traza manual sobre `[7, 2, 7, 0, 4, 2]`

Partimos del arreglo inicial (índices `0` a `5`): `[7, 2, 7, 0, 4, 2]`.

- **Subdivisiones:**
  - Rango `0..5` (`[7, 2, 7, 0, 4, 2]`, `medio = 2`):
    - Mitad izquierda `0..2` (`[7, 2, 7]`, `medio = 1`):
      - `0..1` (`[7, 2]`, `medio = 0`) -> `0..0` (`[7]`) y `1..1` (`[2]`)
      - `2..2` (`[7]`)
    - Mitad derecha `3..5` (`[0, 4, 2]`, `medio = 4`):
      - `3..4` (`[0, 4]`, `medio = 3`) -> `3..3` (`[0]`) y `4..4` (`[4]`)
      - `5..5` (`[2]`)

- **Mezclas intermedias y resultado final:**
  - Mezcla `[7]` y `[2]` -> `[2, 7]` (en `0..1`)
  - Mezcla `[2, 7]` y `[7]` -> `[2, 7, 7]` (en `0..2`)
  - Mezcla `[0]` y `[4]` -> `[0, 4]` (en `3..4`)
  - Mezcla `[0, 4]` y `[2]` -> `[0, 2, 4]` (en `3..5`)
  - Mezcla final `[2, 7, 7]` y `[0, 2, 4]` -> **`[0, 2, 2, 4, 7, 7]`** (en `0..5`).

## 5. Complejidad, memoria y estabilidad

- **Tiempo:**
  - **Mejor caso:** $\Theta(n \log n)$, porque siempre se divide a la mitad y se mezclan los $n$ elementos en cada nivel.
  - **Caso promedio:** $\Theta(n \log n)$, asumiendo entradas aleatorias sin un sesgo especial donde cada nivel hace un recorrido lineal $O(n)$ a lo largo de $\log_2 n$ niveles.
  - **Peor caso:** $\Theta(n \log n)$, ya que la estructura de partición y mezcla no cambia drásticamente según el orden inicial de los datos.
- **Memoria auxiliar y pila:** el arreglo `aux` implica memoria extra $O(n)$ (un arreglo de tamaño `list.length`)[cite: 17], y la pila de llamadas llega como máximo a $O(\log n)$ niveles de profundidad.
- **Estabilidad:** esta implementación es **estable** siempre que al haber empate se tome primero el elemento de la mitad izquierda (`<=`), respetando el orden en que venían los repetidos (como ocurre con los `2` y los `7` en la traza).

## 6. Caso desfavorable y pruebas reales

Un escenario menos favorable para esta versión es trabajar con muestras grandes (como `100.000` elementos) donde se suma el trabajo de copiar/mezclar sobre estructuras auxiliares y el uso de memoria frente a métodos *in-place* sobre primitivos[cite: 18].

En mis pruebas con `10.000`, `50.000` y `100.000` elementos[cite: 18], y con los casos de prueba de aceptación (`[]`, `[5]`, `[3, 3, 3]`, `[7, 2, 7, 0, 4, 2]`, ya ordenados o en orden inverso), el método corrió sin errores por profundidad recursiva ni por desbordamiento de índices.

## 7. Relación con la tabla de tiempos y fuentes

- **Conexión con los resultados (`README.md`):**
  - En `10.000` datos, `MergeSort` tardó `5.5362 ms`, por debajo de `Burbuja` (`113.0521 ms`), `Arrays.sort(int[])` (`22.0919 ms`) y `Arrays.sort(Comparator)` (`25.5198 ms`)[cite: 6].
  - En `50.000` (`54.2903 ms`) y `100.000` (`42.6377 ms`), sigue estando lejísimos de `Burbuja` (`4793.963 ms` y `20288.495 ms`), aunque queda algo arriba de `Arrays.sort(int[])` (`10.9231 ms` y `20.6892 ms`) por el manejo de arreglo auxiliar y mezcla frente a un ordenamiento *in-place* sobre primitivos[cite: 6, 17].

- **Fuentes consultadas:**
  - Sedgewick, R., & Wayne, K. *Algorithms* (4th ed.), sección de Mergesort. Consultado el `21/11/2026`. Adapté: la idea general del ordenamiento por mezcla con arreglo auxiliar, el manejo de empates para conservar estabilidad y el análisis de tiempo $O(n \log n)$ con espacio auxiliar $O(n)$.
  - GeeksforGeeks — *Merge Sort* (`https://www.geeksforgeeks.org/merge-sort/`). Consultado el `21/11/2026`. Adapté: revisión rápida de la división por punto medio y el paso de mezcla de subarreglos como apoyo para estructurar la traza.
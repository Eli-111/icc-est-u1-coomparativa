import java.util.concurrent.ThreadLocalRandom;

public class Generador {

    /**
     * Genera exactamente n enteros aleatorios en [0, n] inclusive.
     * Rango soportado: n >= 0 hasta Integer.MAX_VALUE - 1.
     */
    public static int[] generate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("El tamano n no puede ser negativo: " + n);
        }
        if (n == 0) {
            return new int[0];
        }

        long limiteSuperiorExclusivo = (long) n + 1L;
        int[] arreglo = new int[n];
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < n; i++) {
            arreglo[i] = (int) random.nextLong(limiteSuperiorExclusivo);
        }
        return arreglo;
    }

    /**
     * Convierte un int[] a Integer[] fuera de la medicion para la variante V4.
     */
    public static Integer[] toIntegerArray(int[] origen) {
        Integer[] destino = new Integer[origen.length];
        for (int i = 0; i < origen.length; i++) {
            destino[i] = origen[i];
        }
        return destino;
    }

    /**
     * Verifica orden ascendente y conservacion de longitud en int[] (fuera del cronometro).
     */
    public static boolean esOrdenadoAscendente(int[] arr, int longitudEsperada) {
        if (arr == null || arr.length != longitudEsperada) {
            return false;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica orden ascendente y conservacion de longitud en Integer[] (fuera del cronometro).
     */
    public static boolean esOrdenadoAscendente(Integer[] arr, int longitudEsperada) {
        if (arr == null || arr.length != longitudEsperada) {
            return false;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
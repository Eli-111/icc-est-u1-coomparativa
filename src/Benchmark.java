public class Benchmark {
    public static Results medir(Runnable operacion, String nombre, String sample) {
        long inicio = System.nanoTime();
        operacion.run();
        long fin = System.nanoTime();
        return new Results(nombre, fin - inicio, sample);
    }
}
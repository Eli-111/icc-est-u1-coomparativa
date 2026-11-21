import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Metodos m = new Metodos();

        // LOS 3 ARREGLOS BASE
        int[] list1 = Generador.generate(10000);
        int[] list2 = Generador.generate(50000);
        int[] list3 = Generador.generate(100000);

        // COPIAS INDEPENDIENTES (fuera del cronometro)
        int[] list1c1 = list1.clone();
        int[] list1c2 = list1.clone();
        int[] list1c3 = list1.clone();
        Integer[] list1c4 = Generador.toIntegerArray(list1);

        int[] list2c1 = list2.clone();
        int[] list2c2 = list2.clone();
        int[] list2c3 = list2.clone();
        Integer[] list2c4 = Generador.toIntegerArray(list2);

        int[] list3c1 = list3.clone();
        int[] list3c2 = list3.clone();
        int[] list3c3 = list3.clone();
        Integer[] list3c4 = Generador.toIntegerArray(list3);

        // Comparador numerico seguro (sin restar valores)
        Comparator<Integer> ascendente = Comparator.naturalOrder();

        // LISTADO DE RESULTADOS (12 mediciones)
        List<Results> resultados = new ArrayList<>();

        // --- TAMANO 10.000 ---
        Results resultaM1S1 = Benchmark.medir(() -> m.sortBubble(list1c1), "Burbuja", "10.000");
        validar(list1c1, list1.length, "Burbuja", "10.000");
        resultados.add(resultaM1S1);

        Results resultaM2S1 = Benchmark.medir(() -> m.sortMerge(list1c2), "MergeSort", "10.000");
        validar(list1c2, list1.length, "MergeSort", "10.000");
        resultados.add(resultaM2S1);

        Results resultaM3S1 = Benchmark.medir(() -> m.sortArrays(list1c3), "Arrays.sort(int[])", "10.000");
        validar(list1c3, list1.length, "Arrays.sort(int[])", "10.000");
        resultados.add(resultaM3S1);

        Results resultaM4S1 = Benchmark.medir(() -> m.sortArraysComparator(list1c4, ascendente), "Arrays.sort(Comparator)", "10.000");
        validar(list1c4, list1.length, "Arrays.sort(Comparator)", "10.000");
        resultados.add(resultaM4S1);

        // --- TAMANO 50.000 ---
        Results resultaM1S2 = Benchmark.medir(() -> m.sortBubble(list2c1), "Burbuja", "50.000");
        validar(list2c1, list2.length, "Burbuja", "50.000");
        resultados.add(resultaM1S2);

        Results resultaM2S2 = Benchmark.medir(() -> m.sortMerge(list2c2), "MergeSort", "50.000");
        validar(list2c2, list2.length, "MergeSort", "50.000");
        resultados.add(resultaM2S2);

        Results resultaM3S2 = Benchmark.medir(() -> m.sortArrays(list2c3), "Arrays.sort(int[])", "50.000");
        validar(list2c3, list2.length, "Arrays.sort(int[])", "50.000");
        resultados.add(resultaM3S2);

        Results resultaM4S2 = Benchmark.medir(() -> m.sortArraysComparator(list2c4, ascendente), "Arrays.sort(Comparator)", "50.000");
        validar(list2c4, list2.length, "Arrays.sort(Comparator)", "50.000");
        resultados.add(resultaM4S2);

        // --- TAMANO 100.000 ---
        Results resultaM1S3 = Benchmark.medir(() -> m.sortBubble(list3c1), "Burbuja", "100.000");
        validar(list3c1, list3.length, "Burbuja", "100.000");
        resultados.add(resultaM1S3);

        Results resultaM2S3 = Benchmark.medir(() -> m.sortMerge(list3c2), "MergeSort", "100.000");
        validar(list3c2, list3.length, "MergeSort", "100.000");
        resultados.add(resultaM2S3);

        Results resultaM3S3 = Benchmark.medir(() -> m.sortArrays(list3c3), "Arrays.sort(int[])", "100.000");
        validar(list3c3, list3.length, "Arrays.sort(int[])", "100.000");
        resultados.add(resultaM3S3);

        Results resultaM4S3 = Benchmark.medir(() -> m.sortArraysComparator(list3c4, ascendente), "Arrays.sort(Comparator)", "100.000");
        validar(list3c4, list3.length, "Arrays.sort(Comparator)", "100.000");
        resultados.add(resultaM4S3);

        // IMPRESION FINAL DE LOS 12 RESULTADOS
        for (Results r : resultados) {
            System.out.println(r);
        }

        // RESUMEN EN MILISEGUNDOS PARA TABLA README.md
        System.out.println("\n--- RESUMEN EN MILISEGUNDOS (README.md) ---");
        for (Results r : resultados) {
            System.out.printf("%-24s | %-8s | %.4f ms%n", r.getNombre(), r.getSample(), r.getMillis());
        }
    }

    private static void validar(int[] arr, int esperado, String metodo, String sample) {
        if (!Generador.esOrdenadoAscendente(arr, esperado)) {
            throw new IllegalStateException("Fallo de ordenamiento en " + metodo + " (" + sample + ")");
        }
    }

    private static void validar(Integer[] arr, int esperado, String metodo, String sample) {
        if (!Generador.esOrdenadoAscendente(arr, esperado)) {
            throw new IllegalStateException("Fallo de ordenamiento en " + metodo + " (" + sample + ")");
        }
    }
}
import java.util.Arrays;
import java.util.Comparator;

public class PruebasAceptacion {

    public static void main(String[] args) {
        Metodos m = new Metodos();
        Comparator<Integer> ascendente = Comparator.naturalOrder();

        // CA 01: n = 10000, 50000 y 100000 -> exactamente n elementos en [0, n] inclusive
        for (int n : new int[]{10000, 50000, 100000}) {
            int[] muestra = Generador.generate(n);
            boolean ok = muestra.length == n && dentroDeIntervalo(muestra, 0, n);
            System.out.println("CA 01 (n=" + n + "): " + (ok ? "PASS" : "FAIL"));
        }

        // CA 02: n = 0 devuelve vacio; n negativo lanza excepcion explicita
        int[] vacio = Generador.generate(0);
        boolean ca02CeroOk = vacio.length == 0;
        boolean ca02NegativoOk = false;
        try {
            Generador.generate(-5);
        } catch (IllegalArgumentException ex) {
            ca02NegativoOk = true;
        }
        System.out.println("CA 02 (n=0 y n=-5): " + ((ca02CeroOk && ca02NegativoOk) ? "PASS" : "FAIL"));

        // CA 03 y CA 07: clones independientes y base intacta
        int[] base = Generador.generate(20);
        int[] baseAntes = base.clone();
        int[] c1 = base.clone();
        int[] c2 = base.clone();
        int[] c3 = base.clone();
        Integer[] c4 = Generador.toIntegerArray(base);

        m.sortBubble(c1);
        m.sortMerge(c2);
        m.sortArrays(c3);
        m.sortArraysComparator(c4, ascendente);

        int[] esperada = baseAntes.clone();
        Arrays.sort(esperada);

        boolean ca03Y07Ok = Arrays.equals(base, baseAntes)
                && Arrays.equals(c1, esperada)
                && Arrays.equals(c2, esperada)
                && Arrays.equals(c3, esperada)
                && Arrays.equals(c4, Generador.toIntegerArray(esperada));
        System.out.println("CA 03 & CA 07 (clones, base intacta y conservacion): " + (ca03Y07Ok ? "PASS" : "FAIL"));

        // CA 04: ordenar [7, 2, 7, 0, 4, 2] con V1-V4 -> [0, 2, 2, 4, 7, 7]
        int[] entradaCA04 = {7, 2, 7, 0, 4, 2};
        int[] esperadoCA04 = {0, 2, 2, 4, 7, 7};
        System.out.println("CA 04: " + (probarTodasLasVariantes(m, ascendente, entradaCA04, esperadoCA04) ? "PASS" : "FAIL"));

        // CA 05: casos limite [], [5] y [3, 3, 3]
        boolean ca05Vacio = probarTodasLasVariantes(m, ascendente, new int[]{}, new int[]{});
        boolean ca05Uno = probarTodasLasVariantes(m, ascendente, new int[]{5}, new int[]{5});
        boolean ca05Repetidos = probarTodasLasVariantes(m, ascendente, new int[]{3, 3, 3}, new int[]{3, 3, 3});
        System.out.println("CA 05 (limites): " + ((ca05Vacio && ca05Uno && ca05Repetidos) ? "PASS" : "FAIL"));

        // CA 06: orden previo [0, 1, 2, 3] y [3, 2, 1, 0] -> [0, 1, 2, 3]
        int[] esperado06 = {0, 1, 2, 3};
        boolean ca06Asc = probarTodasLasVariantes(m, ascendente, new int[]{0, 1, 2, 3}, esperado06);
        boolean ca06Desc = probarTodasLasVariantes(m, ascendente, new int[]{3, 2, 1, 0}, esperado06);
        System.out.println("CA 06 (orden previo): " + ((ca06Asc && ca06Desc) ? "PASS" : "FAIL"));

        // CA 08: medicion valida con lambda, sample real y duracion >= 0
        int[] sample08 = {4, 1, 3, 2};
        Results res08 = Benchmark.medir(() -> m.sortMerge(sample08), "MergeSort", "4");
        boolean ca08Ok = "MergeSort".equals(res08.getNombre())
                && "4".equals(res08.getSample())
                && res08.getDurationNs() >= 0
                && Generador.esOrdenadoAscendente(sample08, 4);
        System.out.println("CA 08 (benchmark y unidades): " + (ca08Ok ? "PASS" : "FAIL"));

        // CA 09: operacion que lanza excepcion conserva causa original
        boolean ca09Ok = false;
        try {
            Benchmark.medir(() -> {
                throw new IllegalStateException("Fallo simulado en operacion");
            }, "OperacionConFallo", "0");
        } catch (IllegalStateException ex) {
            ca09Ok = "Fallo simulado en operacion".equals(ex.getMessage());
        }
        System.out.println("CA 09 (manejo de excepcion): " + (ca09Ok ? "PASS" : "FAIL"));
    }

    private static boolean probarTodasLasVariantes(
            Metodos m, Comparator<Integer> ascendente, int[] entrada, int[] esperado) {
        int[] v1 = entrada.clone();
        int[] v2 = entrada.clone();
        int[] v3 = entrada.clone();
        Integer[] v4 = Generador.toIntegerArray(entrada);
        Integer[] espObj = Generador.toIntegerArray(esperado);

        m.sortBubble(v1);
        m.sortMerge(v2);
        m.sortArrays(v3);
        m.sortArraysComparator(v4, ascendente);

        return Arrays.equals(v1, esperado)
                && Arrays.equals(v2, esperado)
                && Arrays.equals(v3, esperado)
                && Arrays.equals(v4, espObj);
    }

    private static boolean dentroDeIntervalo(int[] arr, int minInclusive, int maxInclusive) {
        for (int val : arr) {
            if (val < minInclusive || val > maxInclusive) {
                return false;
            }
        }
        return true;
    }
}
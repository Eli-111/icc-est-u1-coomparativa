import java.util.Arrays;
import java.util.Comparator;

public class Metodos {

    // V1: Metodo basico (Burbuja optimizada) - int[] ascendente
    public void sortBubble(int[] list) {
        int n = list.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swap = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (list[j] > list[j + 1]) {
                    int tmp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = tmp;
                    swap = true;
                }
            }
            if (!swap) {
                break;
            }
        }
    }

    // V2: Metodo investigado (Merge Sort) - int[] ascendente
    public void sortMerge(int[] list) {
        if (list.length <= 1) {
            return;
        }
        int[] aux = new int[list.length];
        mergeSort(list, aux, 0, list.length - 1);
    }

    private void mergeSort(int[] list, int[] aux, int izq, int der) {
        if (izq >= der) {
            return;
        }
        int medio = izq + (der - izq) / 2;
        mergeSort(list, aux, izq, medio);
        mergeSort(list, aux, medio + 1, der);
        merge(list, aux, izq, medio, der);
    }

    private void merge(int[] list, int[] aux, int izq, int medio, int der) {
        for (int i = izq; i <= der; i++) {
            aux[i] = list[i];
        }
        int i = izq;
        int j = medio + 1;
        int k = izq;

        while (i <= medio && j <= der) {
            if (aux[i] <= aux[j]) {
                list[k++] = aux[i++];
            } else {
                list[k++] = aux[j++];
            }
        }
        while (i <= medio) {
            list[k++] = aux[i++];
        }
        while (j <= der) {
            list[k++] = aux[j++];
        }
    }

    // V3: Arrays.sort con int[] - ascendente
    public void sortArrays(int[] list) {
        Arrays.sort(list);
    }

    // V4: Arrays.sort con Integer[] y Comparator<Integer> - ascendente numerico
    public void sortArraysComparator(Integer[] list, Comparator<Integer> comparator) {
        Arrays.sort(list, comparator);
    }
}

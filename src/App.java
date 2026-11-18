import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Generador g = new Generador();
        Benchmark b = new Benchmark();

        List<Integer> list = g.generate(100); // Nota: tamaño actualizado a 100000

        Results benGenList = b.medir(
            () -> g.generate(100),
            "Generacion de listado",
            "100"
        );

        System.out.println(benGenList);
    }
}
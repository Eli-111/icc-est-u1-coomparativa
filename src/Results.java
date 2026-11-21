public class Results {
    private final String nombre;
    private final long durationNs;
    private final String sample;

    public Results(String nombre, long durationNs, String sample) {
        this.nombre = nombre;
        this.durationNs = durationNs;
        this.sample = sample;
    }

    public String getNombre() {
        return nombre;
    }

    public long getDurationNs() {
        return durationNs;
    }

    public String getSample() {
        return sample;
    }

    public double getMillis() {
        return durationNs / 1_000_000.0;
    }

    @Override
    public String toString() {
        double segundos = durationNs / 1_000_000_000.0;
        return "Results [" + nombre + ", duration=" + durationNs + "ns, " + segundos + "s, sample=" + sample + "]";
    }
}
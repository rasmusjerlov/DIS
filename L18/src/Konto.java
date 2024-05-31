public class Konto {
    private String kontonr;
    private double saldo;

    public Konto(String kontonr, int saldo) {
        this.kontonr = kontonr;
        this.saldo = saldo;
    }

    public String getKontonr() {
        return kontonr;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}

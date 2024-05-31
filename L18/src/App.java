
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ArrayList<Konto> konti = new ArrayList<>();

        Konto k1 = new Konto("12345678", 500);
        Konto k2 = new Konto("12345688", 1500);

        konti.add(k1);
        konti.add(k2);

        try {
            BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));

            Connection minConnection;
            minConnection = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost;databaseName=master;user=sa;password=‘L061ik2202! -e MSSQL_PID=community’;");

            System.out.println("Indtast frakonto: ");
            String fraKonto = inLine.readLine();

            System.out.println("Indtast tilkonto: ");
            String tilKonto = inLine.readLine();

            System.out.println("Indtast beløb: ");
            String beløbString = inLine.readLine();
            double beløb = Double.parseDouble(beløbString);

            Statement stmt = minConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);


            String sql = "UPDATE Konto SET saldo = saldo + ? WHERE ktoNr = ?";
            PreparedStatement prestmt = minConnection.prepareStatement(sql);
            prestmt.setDouble(1, beløb);
            prestmt.setString(2, fraKonto);
            prestmt.executeUpdate();

            String sql1 = "UPDATE Konto SET saldo = saldo - ? WHERE ktoNr = ?";
            PreparedStatement prestmt1 = minConnection.prepareStatement(sql1);
            prestmt1.setDouble(1, beløb);
            prestmt1.setString(2, tilKonto);
            prestmt1.executeUpdate();



//            String sql = "update Konto where ktoNr = tilKonto";// preparedStatement
//            PreparedStatement prestmt = minConnection.prepareStatement(sql);
//            prestmt.clearParameters();




            for (Konto kf : konti) {
                if (kf.getKontonr().equals(fraKonto)) {
                    if (kf.getSaldo() > beløb) {
                        for (Konto kt : konti) {
                            if (kt.getKontonr().equals(tilKonto)) {
                                System.out.println("Saldo: " + kt.getSaldo() + " kr.");
                                kf.setSaldo(kf.getSaldo() - beløb);
                                System.out.println("Frakonto ny saldo: " + kf.getSaldo());
                                kt.setSaldo(kt.getSaldo() + beløb);
                                System.out.println("Tilkonto ny saldo: " + kt.getSaldo());
                                minConnection.rollback();
                            }
                        }
                    }
                }
            }




            minConnection.commit();


            if (minConnection != null)
                minConnection.close();
        } catch (Exception e) {
            System.out.println("fejl:  " + e.getMessage());
        }
    }
}

package PasswordOpgave;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;

public class Gui extends Application {
	Button btnLogin, btnOpret, btnscene2;
	Label lblbrugernavn, lblPassword, lblBesked;
	Label lblscene2, lblInfoBruger;
	GridPane pane1, pane2;
	Scene scene1, scene2;
	Stage thestage;	
	private PasswordField password = new PasswordField();
	private static TextField brugernavn = new TextField();
	Connection conn = DriverManager.getConnection(
			"jdbc:sqlserver://localhost;databaseName=disPasswords;user=sa;password=‘L061ik2202! -e MSSQL_PID=community’;");

    public Gui() throws SQLException {
    }

    public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		thestage = primaryStage;
		
		btnLogin = new Button("Log in");
		btnOpret = new Button("Opret");
		btnscene2 = new Button("Tilbage til log in");
		btnLogin.setOnAction(e -> ButtonClicked(e));
		btnOpret.setOnAction(e -> ButtonClicked(e));
		btnscene2.setOnAction(e -> ButtonClicked(e));
		lblbrugernavn = new Label("Navn");
		lblPassword = new Label("Password");
		lblBesked = new Label("Hello World");

		lblscene2 = new Label("Du er nu logget ind");
		lblInfoBruger = new Label("Bruger info");
		
		pane1 = new GridPane();
		pane2 = new GridPane();
		pane1.setVgap(10);
		pane2.setVgap(10);
		
		pane1.setStyle("-fx-background-color: yellow;-fx-padding: 10px;");
		pane2.setStyle("-fx-background-color: lightgreen;-fx-padding: 10px;");

		pane1.setPadding(new Insets(5));
		pane1.setHgap(10);
		pane1.setVgap(10);

		pane1.add(lblbrugernavn, 0, 0);
		pane1.add(brugernavn, 0, 1, 2, 1);
		pane1.add(lblPassword, 0, 2);
		pane1.add(password, 0, 3, 2, 1);
		pane1.add(btnLogin, 0, 4);
		pane1.add(btnOpret, 1, 4);
		pane1.add(lblBesked, 0, 5);

		pane2.setPadding(new Insets(5));
		pane2.setHgap(10);
		pane2.setVgap(10);
		
		pane2.add(lblInfoBruger, 0, 0);
		pane2.add(btnscene2, 0, 1);

		scene1 = new Scene(pane1, 200, 200);
		scene2 = new Scene(pane2, 200, 200);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene1);
		primaryStage.show();
	}

	public void ButtonClicked(ActionEvent e) {
		if (e.getSource() == btnLogin) {
            try {

				String passwordText = password.getText();
				String usernameText = brugernavn.getText();

				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(passwordText.getBytes(StandardCharsets.UTF_8));
				byte[] hashedPassword = md.digest();

				String hashedPasswordHex = new BigInteger(1, hashedPassword).toString(16);
				String sql = "select * from users where username = ? and hashedPassword = ?";
				PreparedStatement prestmt = conn.prepareStatement(sql);
				prestmt.setString(1, brugernavn.getText());
				prestmt.setString(2, hashedPasswordHex);

				ResultSet res = prestmt.executeQuery();

				if (res.next() && brugernavn.getText().equals(res.getString(1)) && hashedPasswordHex.equals(res.getString(2)));
					lblBesked.setText("Du er nu logget ind");
					System.out.println("Passwords match");
					thestage.setScene(scene2);
					return;
				} catch (SQLException ex) {
                throw new RuntimeException(ex);
				} catch (NoSuchAlgorithmException ex) {
				throw new RuntimeException(ex);
			}
        } else if (e.getSource() == btnOpret) {
			//TODO
            try {

				String sql = "insert into users values(?,?) ";// preparedStatement
				PreparedStatement prestmt = conn.prepareStatement(sql);
				String passwordText = password.getText();
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(passwordText.getBytes(StandardCharsets.UTF_8));
				byte[] hashedPassword = md.digest();

				prestmt.clearParameters();

				String hashedPasswordHex = new BigInteger(1, hashedPassword).toString(16);
				prestmt.setString(1, brugernavn.getText());
				prestmt.setString(2, hashedPasswordHex);
				prestmt.executeUpdate();

            } catch (SQLException ex) {
				if (ex.getMessage().contains("PRIMARY KEY")) {
					lblBesked.setText("Brugernavnet er allerede taget");
				}
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
            password.clear();
			brugernavn.clear();
			System.out.println("Oprettet");
			lblBesked.setText("Brugeren er oprettet");
		} else {
			lblBesked.setText("");
			password.clear();
			brugernavn.clear();
			thestage.setScene(scene1);

		}
	}

	public byte[] generateSalt() {
		byte[] salt = new byte[16]; // Recommended salt size is 16 bytes
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		// Convert byte array to a String for storage (optional)
		return salt;
		// ... (Use the generated salt for password hashing)
	}


}

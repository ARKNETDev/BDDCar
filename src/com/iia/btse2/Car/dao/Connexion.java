package com.iia.btse2.Car.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
	private static final String URL = "jdbc:sqlserver://ANTOINE-PC;databaseName=Voiture;";
	private static final String PASSWORD = "SQL2014";
	private static final String LOGIN = "sa";
	private static Connection connection;

	public static Connection getConnection() {
		Connexion.connection = null;

		try {
			// Chargement du driver sql server
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			// Lance et r�cup�re la connexion
			Connexion.connection = DriverManager.getConnection(
					Connexion.URL, Connexion.LOGIN, Connexion.PASSWORD);
		} catch (ClassNotFoundException e) {
			System.out.println("Impossible de charger le driver");
		} catch (SQLException e) {
			System.out.println("Erreur lors de la connexion");
		}

		return Connexion.connection;
	}
	
	public static void close() {
		try {
			Connexion.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.iia.btse2.Car.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.iia.btse2.Car.entity.Voiture;

public class VoitureDAO implements IDao<Voiture>{
	private static final String TABLE = "Car";
	private static final String ID = "Identifiant";
	private static final String MARQUE = "Marque";
	private static final String YEAR = "Annee";
	private static final String MODEL = "Modele";
	private static final String COLOR = "Color";
	private static final String PRICE = "Prix";
	private static final String SPEED = "Vitesse";

	@Override
	public boolean create(Voiture object) {
		String req = "INSERT INTO Car (Marque, Annee, Modele, Couleur, Prix, Vitesse) VALUES('t', GETDATE(), 't', 't', 1, 1)";
		//String req = "INSERT INTO Car (Marque, Annee, Modele, Couleur, Prix, Vitesse) VALUES ('test', GETDATE(), 't', 't', 1, 1);";

		try {
			PreparedStatement st = Connexion.getConnection().prepareStatement(req);
			if (st.executeUpdate(req) == 1) {
				return true;}
			/*Statement st = Connexion.getConnection().createStatement();
			ResultSet result = st.executeQuery(req);*/
			
		} catch (SQLException e) {
			System.out.println("erreur lors de l'insertion de la voiture");
		}

		return false;
	}

	@Override
	public boolean update(Voiture object) {
		String req = String.format("UPDATE %s SET %s=%s, %s=%s, %s=%s, %s=%s, %s=%s, %s=%s WHERE %s=?",
				VoitureDAO.TABLE,
				VoitureDAO.MARQUE,
				object.getMarque(),
				VoitureDAO.YEAR,
				object.getAnnee(),
				VoitureDAO.MODEL,
				object.getModele(),
				VoitureDAO.COLOR,
				object.getCouleur(),
				VoitureDAO.PRICE,
				object.getPrix(),
				VoitureDAO.SPEED,
				object.getVitesse(),
				VoitureDAO.ID);
		
		try {
			PreparedStatement st = Connexion.getConnection().prepareStatement(req);
			st.setInt(1, object.getID());
			if (st.executeUpdate() >= 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("erreur lors de la mise � jour de la voiture");
		}

		return false;
	}

	@Override
	public boolean delete(Voiture object) {
		String req = String.format("DELETE FROM %s WHERE %s=?",
				VoitureDAO.TABLE,
				VoitureDAO.ID);

		try {
			PreparedStatement st = Connexion.getConnection().prepareStatement(req);
			st.setInt(1, object.getID());
			if (st.executeUpdate() >= 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("erreur lors de la suppression de la voiture");
		}

		return false;
	}

	@Override
	public Voiture findById(int id) {
		//String req = String.format("SELECT * FROM %s WHERE %s=%d", VoitureDAO.TABLE, VoitureDAO.ID, id);
		String req = String.format("SELECT * FROM Car WHERE Identifiant = " + id);
		try {
			PreparedStatement st = Connexion.getConnection().prepareStatement(req);
			ResultSet rs = st.executeQuery(req);

			if (rs.next()) {
				return this.cursorToVoiture(rs);
			}
		} catch (SQLException e) {
			System.out.println("erreur lors de la r�cup�ration de la voiture");
		}
		return null;
	}

	@Override
	public List<Voiture> findAll() {
		List<Voiture> clients = new ArrayList<Voiture>();

		//String req = String.format("SELECT * FROM %s", VoitureDAO.TABLE);
		String req = "SELECT * FROM Car";

		try {
			PreparedStatement st = Connexion.getConnection().prepareStatement(req);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				clients.add(this.cursorToVoiture(rs));
			}

			return clients;

		} catch (SQLException e) {
			System.out.println("erreur lors de la r�cup�ration des voitures");
		}

		return null;
	}

	private Voiture cursorToVoiture(ResultSet resultSet) {
		Voiture voiture = null;
		try {
			voiture = new Voiture();
			voiture.setID(resultSet.getInt(VoitureDAO.ID));
			voiture.setMarque(resultSet.getString(VoitureDAO.MARQUE));
			voiture.setAnnee(resultSet.getInt(VoitureDAO.YEAR));
			voiture.setModele(resultSet.getString(VoitureDAO.MODEL));
			voiture.setCouleur(resultSet.getString(VoitureDAO.COLOR));
			voiture.setPrix(resultSet.getFloat(VoitureDAO.PRICE));
			voiture.setVitesse(resultSet.getInt(VoitureDAO.SPEED));
		} catch (SQLException e) {
			System.out.println("erreur lors de la r�cup�ration de la voiture");
		}

		return voiture;
	}
}

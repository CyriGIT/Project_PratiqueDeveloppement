package ch.hearc.cafheg.infrastructure.persistance;

import ch.hearc.cafheg.business.allocations.Allocataire;
import ch.hearc.cafheg.business.allocations.NoAVS;
import ch.hearc.cafheg.utils.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllocataireMapper extends Mapper {

  private static final String QUERY_FIND_ALL = "SELECT NOM,PRENOM,NO_AVS FROM ALLOCATAIRES";
  private static final String QUERY_FIND_WHERE_NOM_LIKE = "SELECT NOM,PRENOM,NO_AVS FROM ALLOCATAIRES WHERE NOM LIKE ?";
  private static final String QUERY_FIND_WHERE_NUMERO = "SELECT NO_AVS, NOM, PRENOM FROM ALLOCATAIRES WHERE NUMERO=?";
  private static final String QUERY_UPDATE_ALLOCATAIRE = "UPDATE ALLOCATAIRES SET NOM=?, PRENOM=? WHERE NO_AVS=?";
  private static final String QUERY_DELETE_ALLOCATAIRE =  "DELETE FROM ALLOCATAIRES WHERE NUMERO = ?";

  public List<Allocataire> findAll(String likeNom) {
    Log.info("findAll() " + likeNom);
    Connection connection = activeJDBCConnection();
    try {
      PreparedStatement preparedStatement;
      if (likeNom == null) {
        Log.debug("SQL: " + QUERY_FIND_ALL);
        preparedStatement = connection
                .prepareStatement(QUERY_FIND_ALL);
      } else {

        Log.debug("SQL: " + QUERY_FIND_WHERE_NOM_LIKE);
        preparedStatement = connection
                .prepareStatement(QUERY_FIND_WHERE_NOM_LIKE);
        preparedStatement.setString(1, likeNom + "%");
      }
      Log.info("Allocation d'un nouveau tableau");
      List<Allocataire> allocataires = new ArrayList<>();

      Log.info("Exécution de la requête");
      try (ResultSet resultSet = preparedStatement.executeQuery()) {

        Log.info("Allocataire mapping");
        while (resultSet.next()) {
          Log.debug("ResultSet#next");
          allocataires
                  .add(new Allocataire(new NoAVS(resultSet.getString(3)),
                          resultSet.getString(1),
                          resultSet.getString(2)));
        }
      }
      Log.info("Allocataires trouvés " + allocataires.size());
      return allocataires;
    } catch (SQLException e) {
      Log.error("Erreur lors de recherche des allocataires"  + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public Allocataire findById(long id) {
    Log.debug("findById " + id);
    Connection connection = activeJDBCConnection();
    try {
      Log.debug("SQL:" + QUERY_FIND_WHERE_NUMERO);
      PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_WHERE_NUMERO);
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      Log.debug("ResultSet#next");
      resultSet.next();
      Log.info("Allocataire mapping");
      return new Allocataire(new NoAVS(resultSet.getString(1)),
              resultSet.getString(2), resultSet.getString(3));
    } catch (SQLException e) {
      Log.error("Allocataire introuvable: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public void updateAllocataire(Allocataire allocataire) {
    Log.info("updateAllocataire() " + allocataire);
    Connection connection = activeJDBCConnection();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE_ALLOCATAIRE);
      preparedStatement.setString(1, allocataire.getNom());
      preparedStatement.setString(2, allocataire.getPrenom());
      preparedStatement.setString(3, allocataire.getNoAVS().getValue());
      preparedStatement.executeUpdate();
      Log.info("Mise à jour effectué de l'allocataire : " + allocataire.getNom() + " " + allocataire.getPrenom());
    } catch (SQLException e) {
      Log.error("Erreur lors de la mise à jour de l'allocataire" + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public void deleteById(Long id) {
    Log.warn("Supprimer l'Allocataire ayant l'id" + id);
    try (Connection connection = activeJDBCConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE_ALLOCATAIRE)) {

      preparedStatement.setLong(1, id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      Log.error("Erreur lors de la suppression de l'allocataire" + e.getMessage());
      throw new RuntimeException("Erreur lors de la suppression de l'allocataire", e);
    }
  }

}

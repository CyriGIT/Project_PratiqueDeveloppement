package ch.hearc.cafheg.infrastructure.persistance;

import ch.hearc.cafheg.business.common.Montant;
import ch.hearc.cafheg.business.versements.VersementAllocation;
import ch.hearc.cafheg.business.versements.VersementAllocationNaissance;
import ch.hearc.cafheg.business.versements.VersementParentEnfant;
import ch.hearc.cafheg.business.versements.VersementParentParMois;
import ch.hearc.cafheg.utils.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VersementMapper extends Mapper {

  private final String QUERY_FIND_ALL_ALLOCATIONS_NAISSANCE = "SELECT V.DATE_VERSEMENT,AN.MONTANT FROM VERSEMENTS V JOIN ALLOCATIONS_NAISSANCE AN ON V.NUMERO=AN.FK_VERSEMENTS";
  private final String QUERY_FIND_ALL_VERSEMENTS = "SELECT V.DATE_VERSEMENT,A.MONTANT FROM VERSEMENTS V JOIN VERSEMENTS_ALLOCATIONS VA ON V.NUMERO=VA.FK_VERSEMENTS JOIN ALLOCATIONS_ENFANTS AE ON AE.NUMERO=VA.FK_ALLOCATIONS_ENFANTS JOIN ALLOCATIONS A ON A.NUMERO=AE.FK_ALLOCATIONS";
  private final String QUERY_FIND_ALL_VERSEMENTS_PARENTS_ENFANTS = "SELECT AL.NUMERO AS PARENT_ID, E.NUMERO AS ENFANT_ID, A.MONTANT FROM VERSEMENTS V JOIN VERSEMENTS_ALLOCATIONS VA ON V.NUMERO=VA.FK_VERSEMENTS JOIN ALLOCATIONS_ENFANTS AE ON AE.NUMERO=VA.FK_ALLOCATIONS_ENFANTS JOIN ALLOCATIONS A ON A.NUMERO=AE.FK_ALLOCATIONS JOIN ALLOCATAIRES AL ON AL.NUMERO=V.FK_ALLOCATAIRES JOIN ENFANTS E ON E.NUMERO=AE.FK_ENFANTS";
  private final String QUERY_FIND_ALL_VERSEMENTS_PARENTS_ENFANTS_PAR_MOIS = "SELECT AL.NUMERO AS PARENT_ID, A.MONTANT, V.DATE_VERSEMENT, V.MOIS_VERSEMENT FROM VERSEMENTS V JOIN VERSEMENTS_ALLOCATIONS VA ON V.NUMERO=VA.FK_VERSEMENTS JOIN ALLOCATIONS_ENFANTS AE ON AE.NUMERO=VA.FK_ALLOCATIONS_ENFANTS JOIN ALLOCATIONS A ON A.NUMERO=AE.FK_ALLOCATIONS JOIN ALLOCATAIRES AL ON AL.NUMERO=V.FK_ALLOCATAIRES JOIN ENFANTS E ON E.NUMERO=AE.FK_ENFANTS";
  private final String QUERY_FIND_ALLOCATAIRE_VERSEMENT = "SELECT 1 FROM VERSEMENTS WHERE FK_ALLOCATAIRES = ? LIMIT 1";

  public List<VersementAllocationNaissance> findAllVersementAllocationNaissance() {
    Log.debug("findAllVersementAllocationNaissance()");
    //System.out.println("findAllVersementAllocationNaissance()");
    Connection connection = activeJDBCConnection();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_ALL_ALLOCATIONS_NAISSANCE);
      ResultSet resultSet = preparedStatement.executeQuery();
      List<VersementAllocationNaissance> versements = new ArrayList<>();
      while (resultSet.next()) {
        Log.debug("resultSet#next");
        //System.out.println("resultSet#next");
        versements.add(
                new VersementAllocationNaissance(new Montant(resultSet.getBigDecimal(2)),
                        resultSet.getDate(1).toLocalDate()));

      }
      return versements;
    } catch (SQLException e) {
      Log.error("SQLException: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public List<VersementAllocation> findAllVersementAllocation() {
    Log.info("findAllVersementAllocation()");
    //System.out.println("findAllVersementAllocation()");
    Connection connection = activeJDBCConnection();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_ALL_VERSEMENTS);
      ResultSet resultSet = preparedStatement.executeQuery();
      List<VersementAllocation> versements = new ArrayList<>();
      while (resultSet.next()) {
        Log.debug("resultSet#next");
        //System.out.println("resultSet#next");
        versements.add(
                new VersementAllocation(new Montant(resultSet.getBigDecimal(2)),
                        resultSet.getDate(1).toLocalDate()));

      }
      return versements;
    } catch (SQLException e) {
      Log.error("SQLException: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public List<VersementParentEnfant> findVersementParentEnfant() {
    Log.debug("findVersementParentEnfant()");
    //System.out.println("findVersementParentEnfant()");
    Connection connection = activeJDBCConnection();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_ALL_VERSEMENTS_PARENTS_ENFANTS);
      ResultSet resultSet = preparedStatement.executeQuery();
      List<VersementParentEnfant> versements = new ArrayList<>();
      while (resultSet.next()) {
        Log.debug("resultSet#next");
        //System.out.println("resultSet#next");
        versements.add(
                new VersementParentEnfant(resultSet.getLong(1), resultSet.getLong(2),
                        new Montant(resultSet.getBigDecimal(3))));

      }
      return versements;
    } catch (SQLException e) {
      Log.error("SQLException: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public List<VersementParentParMois> findVersementParentEnfantParMois() {
    Log.info("findVersementParentEnfantParMois()");
    //System.out.println("findVersementParentEnfantParMois()");
    Connection connection = activeJDBCConnection();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_ALL_VERSEMENTS_PARENTS_ENFANTS_PAR_MOIS);
      ResultSet resultSet = preparedStatement.executeQuery();
      List<VersementParentParMois> versements = new ArrayList<>();
      while (resultSet.next()) {
        Log.debug("resultSet#next");
        //System.out.println("resultSet#next");
        versements.add(
                new VersementParentParMois(resultSet.getLong(1),
                        new Montant(resultSet.getBigDecimal(2)),
                        resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate()));

      }
      return versements;
    } catch (SQLException e) {
      Log.error("SQLException: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public boolean hasVersementsForAllocataire(long allocataireId) {
    Log.info("Check if allocataire has versements");
    //System.out.println("Check if allocataire has versements");
    Connection connection = activeJDBCConnection();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_ALLOCATAIRE_VERSEMENT);
      preparedStatement.setLong(1, allocataireId);
      ResultSet resultSet = preparedStatement.executeQuery();
      return resultSet.next(); // true if at least one versement found
    } catch (SQLException e) {
      Log.error("SQLException: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

}
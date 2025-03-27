package ch.hearc.cafheg.infrastructure.persistance;

import ch.hearc.cafheg.business.allocations.Allocation;
import ch.hearc.cafheg.business.allocations.Canton;
import ch.hearc.cafheg.business.common.Montant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllocationMapper extends Mapper {


  private static final String QUERY_FIND_ALL = "SELECT * FROM ALLOCATIONS";

  public List<Allocation> findAll() {
    System.out.println("Recherche de toutes les allocations");

    Connection connection = activeJDBCConnection();
    try {
      System.out.println("SQL: " + QUERY_FIND_ALL);
      PreparedStatement preparedStatement = connection
          .prepareStatement(QUERY_FIND_ALL);
      ResultSet resultSet = preparedStatement.executeQuery();
      List<Allocation> allocations = new ArrayList<>();
      while (resultSet.next()) {
        System.out.println("resultSet#next");
        allocations.add(
            new Allocation(new Montant(resultSet.getBigDecimal(2)),
                Canton.fromValue(resultSet.getString(3)), resultSet.getDate(4).toLocalDate(),
                resultSet.getDate(5) != null ? resultSet.getDate(5).toLocalDate() : null));
      }
      return allocations;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}

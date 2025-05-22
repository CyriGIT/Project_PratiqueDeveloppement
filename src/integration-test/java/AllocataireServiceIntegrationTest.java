import ch.hearc.cafheg.business.allocations.AllocataireService;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;
import ch.hearc.cafheg.infrastructure.persistance.Database;

import org.assertj.core.api.Assertions;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.nio.charset.StandardCharsets;
import org.dbunit.dataset.CompositeDataSet;

public class AllocataireServiceIntegrationTest {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private AllocataireService allocataireService;
    private AllocataireMapper allocataireMapper;
    private VersementMapper versementMapper;
    private IDatabaseTester databaseTester;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialisation du schéma et connexion à la base de données
        Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Database.setConnection(connection);  // Mettre la connexion à disposition des mappers

        try (Statement stmt = connection.createStatement()) {
            // Exécution du script schema.sql
            String schema = new String(
                    getClass().getResourceAsStream("/schema.sql").readAllBytes(),
                    StandardCharsets.UTF_8
            );

            for (String sql : schema.split(";")) {
                if (!sql.trim().isEmpty()) {
                    stmt.execute(sql);
                }
            }
        }

        // Configuration de DBUnit
        databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);

        // Initialisation des mappers et du service
        allocataireMapper = new AllocataireMapper();
        versementMapper = new VersementMapper();
        allocataireService = new AllocataireService(allocataireMapper, versementMapper);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Database.closeConnection();  // Fermer la connexion après chaque test
        if (databaseTester != null) {
            databaseTester.onTearDown();
        }
    }

    @Test
    public void testSuppressionAllocataireSansVersement() throws Exception {
        // Chargement des données de test
        IDataSet allocataireDataSet = new FlatXmlDataSetBuilder().build(
                getClass().getResourceAsStream("/allocataire.xml"));

        // Pour ce test, nous n'avons pas besoin de versements pour l'allocataire 2
        databaseTester.setDataSet(allocataireDataSet);
        databaseTester.onSetup();

        // Test de la suppression de l'allocataire 2 qui n'a pas de versement
        boolean resultat = allocataireService.deleteAllocataireByIdIfNoVersements(2L);

        // Vérification que la suppression a réussi
        Assertions.assertThat(resultat).isTrue();
        Assertions.assertThat(allocataireMapper.findById(2L)).isNull();

        // Créer une nouvelle connexion pour vérifier le résultat
        Connection newConnection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Database.setConnection(newConnection);
    }

    @Test
    public void testSuppressionAllocataireAvecVersement() throws Exception {
        // Chargement des données de test
        IDataSet allocataireDataSet = new FlatXmlDataSetBuilder().build(
                getClass().getResourceAsStream("/allocataire.xml"));
        IDataSet versementDataSet = new FlatXmlDataSetBuilder().build(
                getClass().getResourceAsStream("/versement.xml"));

        // Créer un jeu de données composite
        IDataSet compositeDataSet = new CompositeDataSet(
                new IDataSet[] {allocataireDataSet, versementDataSet}
        );
        databaseTester.setDataSet(compositeDataSet);
        databaseTester.onSetup();

        // Test de la suppression de l'allocataire 1 qui a un versement
        boolean resultat = allocataireService.deleteAllocataireByIdIfNoVersements(1L);

        // Vérification que la suppression a échoué
        Assertions.assertThat(resultat).isFalse();
        Assertions.assertThat(allocataireMapper.findById(1L)).isNotNull();

        // Créer une nouvelle connexion pour vérifier le résultat
        Connection newConnection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Database.setConnection(newConnection);
    }

    @Test
    public void testSuppressionAllocataireInexistant() throws Exception {
        // Chargement des données de test
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                getClass().getResourceAsStream("/allocataire.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        // Test de la suppression d'un allocataire inexistant
        Assertions.assertThatThrownBy(() -> allocataireService.deleteAllocataireByIdIfNoVersements(999L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Allocataire introuvable");
    }
}
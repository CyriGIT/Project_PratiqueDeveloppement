package ch.hearc.cafheg.business.allocations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import ch.hearc.cafheg.business.common.Montant;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class AllocationServiceTest {
  private AllocationService allocationService;

  private AllocataireMapper allocataireMapper;
  private AllocationMapper allocationMapper;

  @BeforeEach
  void setUp() {
    allocataireMapper = Mockito.mock(AllocataireMapper.class);
    allocationMapper = Mockito.mock(AllocationMapper.class);

    allocationService = new AllocationService(allocataireMapper, allocationMapper);
  }

  @Test
  void findAllAllocataires_GivenEmptyAllocataires_ShouldBeEmpty() {
    Mockito.when(allocataireMapper.findAll("Geiser")).thenReturn(Collections.emptyList());
    List<Allocataire> all = allocationService.findAllAllocataires("Geiser");
    assertThat(all).isEmpty();
  }

  @Test
  void findAllAllocataires_Given2Geiser_ShouldBe2() {
    Mockito.when(allocataireMapper.findAll("Geiser"))
        .thenReturn(Arrays.asList(new Allocataire(new NoAVS("1000-2000"), "Geiser", "Arnaud"),
            new Allocataire(new NoAVS("1000-2001"), "Geiser", "Aurélie")));
    List<Allocataire> all = allocationService.findAllAllocataires("Geiser");
    assertAll(() -> assertThat(all.size()).isEqualTo(2),
        () -> assertThat(all.get(0).getNoAVS()).isEqualTo(new NoAVS("1000-2000")),
        () -> assertThat(all.get(0).getNom()).isEqualTo("Geiser"),
        () -> assertThat(all.get(0).getPrenom()).isEqualTo("Arnaud"),
        () -> assertThat(all.get(1).getNoAVS()).isEqualTo(new NoAVS("1000-2001")),
        () -> assertThat(all.get(1).getNom()).isEqualTo("Geiser"),
        () -> assertThat(all.get(1).getPrenom()).isEqualTo("Aurélie"));
  }

  @Test
  void findAllocationsActuelles() {
    Mockito.when(allocationMapper.findAll())
        .thenReturn(Arrays.asList(new Allocation(new Montant(new BigDecimal(1000)), Canton.NE,
            LocalDate.now(), null), new Allocation(new Montant(new BigDecimal(2000)), Canton.FR,
            LocalDate.now(), null)));
    List<Allocation> all = allocationService.findAllocationsActuelles();
    assertAll(() -> assertThat(all.size()).isEqualTo(2),
        () -> assertThat(all.get(0).getMontant()).isEqualTo(new Montant(new BigDecimal(1000))),
        () -> assertThat(all.get(0).getCanton()).isEqualTo(Canton.NE),
        () -> assertThat(all.get(0).getDebut()).isEqualTo(LocalDate.now()),
        () -> assertThat(all.get(0).getFin()).isNull(),
        () -> assertThat(all.get(1).getMontant()).isEqualTo(new Montant(new BigDecimal(2000))),
        () -> assertThat(all.get(1).getCanton()).isEqualTo(Canton.FR),
        () -> assertThat(all.get(1).getDebut()).isEqualTo(LocalDate.now()),
        () -> assertThat(all.get(1).getFin()).isNull());
  }

  @Test
  @DisplayName("Vérifier que parent 1 est actif et le parent 2 inactif")
  void getParentDroitAllocation_WhenParent1IsActiveAndParent2Inactive_ShouldReturnParent1() {
    ParentDroitAllocationDemande demande = new ParentDroitAllocationDemande();
    demande.setParent1ActiviteLucrative(true);
    demande.setParent2ActiviteLucrative(false);
    demande.setParent1Salaire(BigDecimal.valueOf(1000.00));
    demande.setParent2Salaire(BigDecimal.valueOf(0.00));

    String resultat = allocationService.getParentDroitAllocation(demande);

    assertThat(resultat).isEqualTo("Parent1");
  }

  @Test
  @DisplayName("Si les deux parents ont une activité lucrative, celui qui a l'autorité parentale a les allocs")
  void getParentDroitAllocation_WhenBothParentsHaveActivityAndParent1HasParentalAuthority_ShouldReturnParent1() {
    ParentDroitAllocationDemande demande = new ParentDroitAllocationDemande();
    demande.setParent1ActiviteLucrative(true);
    demande.setParent2ActiviteLucrative(true);
    demande.setParent1AutoriteParentale(true);
    demande.setParent2AutoriteParentale(false);
    String resultat = allocationService.getParentDroitAllocation(demande);
    assertThat(resultat).isEqualTo("Parent1");
  }

  @Test
  @DisplayName("Si les deux parents ont une activité lucrative et l'autorité parentale et qu'ils sont séparés, celui qui vit avec l'enfant a le droit aux allocations")
  void getParentDroitAllocation_WhenBothParentsHaveActivityAndParentalAuthorityAndSeparated_ShouldReturnParent1() {
    ParentDroitAllocationDemande demande = new ParentDroitAllocationDemande();
    demande.setParent1ActiviteLucrative(true);
    demande.setParent2ActiviteLucrative(true);
    demande.setParent1AutoriteParentale(true);
    demande.setParent2AutoriteParentale(true);
    demande.setParentsEnsemble(false);
    demande.setParent1Residence("NE");
    demande.setParent2Residence("GE");
    demande.setEnfantResidence("NE");
    String resultat = allocationService.getParentDroitAllocation(demande);
    assertThat(resultat).isEqualTo("Parent1");
  }

  @Test
  @DisplayName("Si les deux parents travaillent, ont l'autorité parentale, vivent ensemble, celui qui travaille dans le canton de domicile de l'enfant a droit aux allocs")
    void getParentDroitAllocation_WhenBothParentsHaveActivityAndParentalAuthorityAndLiveTogether_ShouldReturnParent1() {
    ParentDroitAllocationDemande demande = new ParentDroitAllocationDemande();
    demande.setParent1ActiviteLucrative(true);
    demande.setParent2ActiviteLucrative(true);
    demande.setParent1AutoriteParentale(true);
    demande.setParent2AutoriteParentale(true);
    demande.setParentsEnsemble(true);
    demande.setParent1Residence("GE");
    demande.setParent2Residence("GE");
    demande.setParent1LieuActivite("NE");
    demande.setParent2LieuActivite("GE");
    demande.setEnfantResidence("NE");
    String resultat = allocationService.getParentDroitAllocation(demande);
    assertThat(resultat).isEqualTo("Parent1");
  }

/*
  @Test
  @DisplayName("Vérifier que parent 2 est actif et le parent 1 inactif")
  void getParentDroitAllocation_WhenParent2IsActiveAndHasBiggerSalary_ShouldReturnParent2() {
    ParentDroitAllocationDemande demande = new ParentDroitAllocationDemande();
    demande.setParent1ActiviteLucrative(false);
    demande.setParent2ActiviteLucrative(true);
    demande.setParent1Salaire(BigDecimal.valueOf(1000.00));
    demande.setParent2Salaire(BigDecimal.valueOf(2000.00));
    String resultat = allocationService.getParentDroitAllocation(demande);
    assertThat(resultat).isEqualTo("Parent2");
  }

  @Test
  @DisplayName("Vérifier que les deux parents sont actifs et P1 salaire > P2 salaire")
  void getParentDroitAllocation_WhenBothParentsAreInactiveButParent1HasBiggerSalary_ShouldReturnParent1(){
    ParentDroitAllocationDemande demande = new ParentDroitAllocationDemande();
    demande.setParent1ActiviteLucrative(true);
    demande.setParent2ActiviteLucrative(true);
    demande.setParent1Salaire(BigDecimal.valueOf(2000.00));
    demande.setParent2Salaire(BigDecimal.valueOf(1000.00));
    String resultat = allocationService.getParentDroitAllocation(demande);
    assertThat(resultat).isEqualTo("Parent1");
  }

  @Test
  @DisplayName("Vérifier que les deux parents sont inactifs et P1 salaire > P2 salaire")
  void getParentDroitAllocation_WhenBothParentsAreInactiveButParent2HasBiggerSalary_ShouldReturnParent2(){
    ParentDroitAllocationDemande demande = new ParentDroitAllocationDemande();
    demande.setParent1ActiviteLucrative(false);
    demande.setParent2ActiviteLucrative(false);
    demande.setParent1Salaire(BigDecimal.valueOf(2000.00));
    demande.setParent2Salaire(BigDecimal.valueOf(1000.00));
    String resultat = allocationService.getParentDroitAllocation(demande);
    assertThat(resultat).isEqualTo("Parent2");
  }

  @Test
  @DisplayName("Vérifier que les deux parents sont actifs avec un salaire égal")
  void getParentDroitAllocation_WhenBothParentsAreActiveWithSameSalary_ShouldReturnParent2(){
    Map<String, Object> params = new HashMap<>();
    params.put("parent1ActiveLucrative", true);
    params.put("parent2ActiveLucrative", true);
    params.put("parent1Salaire",BigDecimal.valueOf(1000.00));
    params.put("parent2Salaire",BigDecimal.valueOf(1000.00));
    String resultat = allocationService.getParentDroitAllocation(params);
    assertThat(resultat).isEqualTo("Parent2");
  }

  @Test
  @DisplayName("Tester la méthode sans paramètres")
  void getParentDroitAllocation_WhenNoParametersProvided_ShouldReturnDefaultToParent2() {
    Map<String, Object> params = new HashMap<>();
    String result = allocationService.getParentDroitAllocation(params);
    assertThat(result).isEqualTo("Parent2");
  }
*/
}




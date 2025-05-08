package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AllocationService {

  private static final String PARENT_1 = "Parent1";
  private static final String PARENT_2 = "Parent2";

  private final AllocataireMapper allocataireMapper;
  private final AllocationMapper allocationMapper;

  public AllocationService(
      AllocataireMapper allocataireMapper,
      AllocationMapper allocationMapper) {
    this.allocataireMapper = allocataireMapper;
    this.allocationMapper = allocationMapper;
  }

  public List<Allocataire> findAllAllocataires(String likeNom) {
    System.out.println("Rechercher tous les allocataires");
    return allocataireMapper.findAll(likeNom);
  }

  public List<Allocation> findAllocationsActuelles() {
    return allocationMapper.findAll();
  }

  public String getParentDroitAllocation(ParentDroitAllocationDemande demande) {
    System.out.println("Déterminer quel parent a le droit aux allocations");

    if (demande.isParent1ActiviteLucrative() && !demande.isParent2ActiviteLucrative()) {
      return PARENT_1;
    }

    if (demande.isParent2ActiviteLucrative() && !demande.isParent1ActiviteLucrative()) {
      return PARENT_2;
    }

    if (demande.isParent1ActiviteLucrative() && demande.isParent2ActiviteLucrative()) {
      return handleBothParentsActive(demande);
    }

    return PARENT_2; // Par défaut, retourner Parent2
  }

  private String handleBothParentsActive(ParentDroitAllocationDemande demande) {
    if (demande.isParent1AutoriteParentale() && !demande.isParent2AutoriteParentale()) {
      return PARENT_1;
    }

    if (!demande.isParent1AutoriteParentale() && demande.isParent2AutoriteParentale()) {
      return PARENT_2;
    }

    if (demande.isParent1AutoriteParentale() && demande.isParent2AutoriteParentale()) {
      if (!demande.isParentsEnsemble()) {
        return handleSeparatedParents(demande);
      }

      return handleParentsLivingTogether(demande);
    }

    return PARENT_2;
  }

  private String handleSeparatedParents(ParentDroitAllocationDemande demande) {
    if (demande.getParent1Residence().equals(demande.getEnfantResidence())) {
      return PARENT_1;
    }

    if (demande.getParent2Residence().equals(demande.getEnfantResidence())) {
      return PARENT_2;
    }

    return PARENT_2; // Par défaut
  }

  private String handleParentsLivingTogether(ParentDroitAllocationDemande demande) {
    if (demande.getParent1LieuActivite().equals(demande.getEnfantResidence())) {
      return PARENT_1;
    }

    if (demande.getParent2LieuActivite().equals(demande.getEnfantResidence())) {
      return PARENT_2;
    }

    if (!demande.isParent1isIndependent() && demande.isParent2isIndependent()) {
      return PARENT_1;
    }

    if (demande.isParent1isIndependent() && !demande.isParent2isIndependent()) {
      return PARENT_2;
    }

    return compareSalaires(demande);
  }

  private String compareSalaires(ParentDroitAllocationDemande demande) {
    return demande.getParent1Salaire().compareTo(demande.getParent2Salaire()) > 0 ? PARENT_1 : PARENT_2;
  }
}

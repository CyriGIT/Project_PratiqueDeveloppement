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
    String eR = demande.getEnfantResidence();
    Boolean p1AL = demande.isParent1ActiviteLucrative();
    String p1Residence = demande.getParent1Residence();
    Boolean p2AL = demande.isParent2ActiviteLucrative();
    String p2Residence = demande.getParent2Residence();
    Boolean pEnsemble = demande.isParentsEnsemble();
    BigDecimal salaireP1 = demande.getParent1Salaire();
    BigDecimal salaireP2 = demande.getParent2Salaire();

    if (p1AL && !p2AL) {
      return PARENT_1;
    }

    if (p2AL && !p1AL) {
      return PARENT_2;
    }

    return salaireP1.compareTo(salaireP2) > 0 ? PARENT_1 : PARENT_2;
  }
}

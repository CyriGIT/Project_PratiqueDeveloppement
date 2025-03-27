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

  public String getParentDroitAllocation(Map<String, Object> parameters) {
    System.out.println("Déterminer quel parent a le droit aux allocations");
    String eR = (String)parameters.getOrDefault("enfantResidence", "");
    Boolean p1AL = (Boolean)parameters.getOrDefault("parent1ActiviteLucrative", false);
    String p1Residence = (String)parameters.getOrDefault("parent1Residence", "");
    Boolean p2AL = (Boolean)parameters.getOrDefault("parent2ActiviteLucrative", false);
    String p2Residence = (String)parameters.getOrDefault("parent2Residence", "");
    Boolean pEnsemble = (Boolean)parameters.getOrDefault("parentsEnsemble", false);
    Number salaireP1 = (Number) parameters.getOrDefault("parent1Salaire", BigDecimal.ZERO);
    Number salaireP2 = (Number) parameters.getOrDefault("parent2Salaire", BigDecimal.ZERO);

    if(p1AL && !p2AL) {
      return PARENT_1;
    }

    if(p2AL && !p1AL) {
      return PARENT_2;
    }

    return salaireP1.doubleValue() > salaireP2.doubleValue() ? PARENT_1 : PARENT_2;
  }
}

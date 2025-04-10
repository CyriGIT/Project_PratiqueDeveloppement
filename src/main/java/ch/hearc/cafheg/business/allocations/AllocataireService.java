package ch.hearc.cafheg.business.allocations;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;

import java.util.List;

public class AllocataireService {
    private final AllocataireMapper allocataireMapper;
    private List<Allocataire> allocatairesActuels;

    public AllocataireService(AllocataireMapper allocataireMapper) {
        this.allocataireMapper = allocataireMapper;
    }

    public List<Allocataire> findAllAllocataires(String likeNom) {
        System.out.println("Rechercher tous les allocataires");
        return allocataireMapper.findAll(likeNom);
    }

    public void modifyAllocataire(Allocataire newAllocataire) {
        System.out.println("Modifier l'allocataire");
        allocatairesActuels = allocataireMapper.findAll(null);
        for (Allocataire a : allocatairesActuels) {
            if (a.getNoAVS().equals(newAllocataire.getNoAVS())) {
                if (a.getNom().equals(newAllocataire.getNom()) || a.getPrenom().equals(newAllocataire.getPrenom())) {
                    allocataireMapper.updateAllocataire(newAllocataire);
                    System.out.println("Allocataire modifié : " + newAllocataire.getNom() + " " + newAllocataire.getPrenom());
                } else {
                    System.out.println("Aucun changement d'allocataire n'a été effectué");
                }
                break;
            }
        }
    }
}



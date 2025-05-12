package ch.hearc.cafheg.business.allocations;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;
import ch.hearc.cafheg.utils.Log;

import java.util.List;
import java.util.NoSuchElementException;

public class AllocataireService {
    private final AllocataireMapper allocataireMapper;
    private final VersementMapper versementMapper;
    private List<Allocataire> allocatairesActuels;

    public AllocataireService(AllocataireMapper allocataireMapper, VersementMapper versementMapper) {
        this.allocataireMapper = allocataireMapper;
        this.versementMapper = versementMapper;
    }

    public List<Allocataire> findAllAllocataires(String likeNom) {
        System.out.println("Rechercher tous les allocataires");
        return allocataireMapper.findAll(likeNom);
    }

    public void modifyAllocataire(Allocataire newAllocataire) {
        Log.info("Modifier l'allocataire");
        //System.out.println("Modifier l'allocataire");
        allocatairesActuels = allocataireMapper.findAll(null);
        for (Allocataire a : allocatairesActuels) {
            if (a.getNoAVS().equals(newAllocataire.getNoAVS())) {
                if (a.getNom().equals(newAllocataire.getNom()) || a.getPrenom().equals(newAllocataire.getPrenom())) {
                    allocataireMapper.updateAllocataire(newAllocataire);
                    Log.info("Allocataire modifié : " + newAllocataire.getNom() + " " + newAllocataire.getPrenom());
                    //System.out.println("Allocataire modifié : " + newAllocataire.getNom() + " " + newAllocataire.getPrenom());
                } else {
                    Log.info("Aucun changement d'allocataire n'a été effectué");
                    //System.out.println("Aucun changement d'allocataire n'a été effectué");
                }
                break;
            }
        }
    }

    public boolean deleteAllocataireByIdIfNoVersements(long id) {
        Allocataire allocataire = allocataireMapper.findById(id);
        if (allocataire == null) {
            Log.warn("Allocataire introuvable");
            throw new NoSuchElementException("Allocataire introuvable");
        }
        if (versementMapper.hasVersementsForAllocataire(id)) {
            return false;
        }
        allocataireMapper.deleteById(id);
        return true;
    }


}



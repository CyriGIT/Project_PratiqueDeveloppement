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
        Log.info("Rechercher tous les allocataires");
        return allocataireMapper.findAll(likeNom);
    }

    public void modifyAllocataire(Allocataire newAllocataire) {
        Log.info("Modifier l'allocataire");
        allocatairesActuels = allocataireMapper.findAll(null);

        boolean nomChange = false;
        boolean prenomChange = false;

        for (Allocataire a : allocatairesActuels) {
            if (a.getNoAVS().equals(newAllocataire.getNoAVS())) {

                String nomDb = a.getNom().trim();
                String nomNew = newAllocataire.getNom().trim();
                String prenomDb = a.getPrenom().trim();
                String prenomNew = newAllocataire.getPrenom().trim();

                nomChange = !nomDb.equalsIgnoreCase(nomNew);
                prenomChange = !prenomDb.equalsIgnoreCase(prenomNew);

                if (nomChange || prenomChange) {
                    allocataireMapper.updateAllocataire(newAllocataire);
                    Log.info("Allocataire modifié : " + newAllocataire.getNom() + " " + newAllocataire.getPrenom());
                } else {
                    Log.warn("Aucun changement d'allocataire n'a été effectué");
                }
                break;
            }
        }
    }

    public boolean deleteAllocataireByIdIfNoVersements(long id) {
        Allocataire allocataire = allocataireMapper.findById(id);
        if (allocataire == null) {
            Log.warn("Allocataire introuvable" + " : " + id);
            throw new NoSuchElementException("Allocataire introuvable");
        }
        if (versementMapper.hasVersementsForAllocataire(id)) {
            return false;
        }
        allocataireMapper.deleteById(id);
        return true;
    }


}



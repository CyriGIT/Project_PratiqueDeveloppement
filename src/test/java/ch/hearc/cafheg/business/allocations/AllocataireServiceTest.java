package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;

import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import java.util.List;

class AllocataireServiceTest {
    private AllocataireService allocataireService;

    private AllocataireMapper allocataireMapper;
    private VersementMapper versementMapper;
    private AllocationMapper allocationMapper;

    /*@BeforeEach
    void setUp() {
        allocataireMapper = Mockito.mock(AllocataireMapper.class);
        allocationMapper = Mockito.mock(AllocationMapper.class);

        allocataireService = (new AllocataireService(allocataireMapper), new VersementMapper(versementMapper));
    }

    @Test
    @DisplayName("modifier un allocataire : si le nom ou le prénom est modifié, l'allocataire est modifié")
    void modifyAllocataire_IfNameOrFirstNameModified_ShouldModifyAllocataire() {
        Allocataire allocataire = new Allocataire(new NoAVS("1000-2000"), "Geiser", "Arnaud");
        Mockito.when(allocataireMapper.findAll(null)).thenReturn(List.of(allocataire));

        Allocataire newAllocataire = new Allocataire(new NoAVS("1000-2000"), "Geiser", "Aurélie");
        allocataireService.modifyAllocataire(newAllocataire);

        Mockito.verify(allocataireMapper).updateAllocataire(newAllocataire);
    }*/

}

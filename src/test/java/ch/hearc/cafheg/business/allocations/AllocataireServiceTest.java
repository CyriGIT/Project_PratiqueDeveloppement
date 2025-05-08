package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;

import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.NoSuchElementException;

class AllocataireServiceTest {
    private AllocataireService allocataireService;

    private AllocataireMapper allocataireMapper;
    private VersementMapper versementMapper;

    @BeforeEach
    void setUp() {
        allocataireMapper = Mockito.mock(AllocataireMapper.class);
        versementMapper = Mockito.mock(VersementMapper.class);

        allocataireService = new AllocataireService(allocataireMapper, versementMapper);
    }

    @Test
    @DisplayName("modifier un allocataire : si le nom ou le prénom est modifié, l'allocataire est modifié")
    void modifyAllocataire_IfNameOrFirstNameModified_ShouldModifyAllocataire() {
        Allocataire allocataire = new Allocataire(new NoAVS("1000-2000"), "Geiser", "Arnaud");
        Mockito.when(allocataireMapper.findAll(null)).thenReturn(List.of(allocataire));

        Allocataire newAllocataire = new Allocataire(new NoAVS("1000-2000"), "Geiser", "Aurélie");
        allocataireService.modifyAllocataire(newAllocataire);

        Mockito.verify(allocataireMapper).updateAllocataire(newAllocataire);
    }

    @Test
    @DisplayName("deleteAllocataireByIdIfNoVersements : l'allocataire est supprimé s'il n'a pas de versements")
    void deleteAllocataire_ShouldDeleteIfNoVersements() {
        long id = 1L;
        Allocataire allocataire = new Allocataire(new NoAVS("123.4567.8912.34"), "Dupont", "Alice");

        when(allocataireMapper.findById(id)).thenReturn(allocataire);
        when(versementMapper.hasVersementsForAllocataire(id)).thenReturn(false);

        boolean result = allocataireService.deleteAllocataireByIdIfNoVersements(id);

        assertThat(result).isTrue();
        verify(allocataireMapper).deleteById(id);
    }

    @Test
    @DisplayName("deleteAllocataireByIdIfNoVersements : ne supprime pas l'allocataire s'il a des versements")
    void deleteAllocataire_ShouldNotDeleteIfHasVersements() {
        long id = 2L;
        Allocataire allocataire = new Allocataire(new NoAVS("987.6543.2109.87"), "Martin", "Lucie");

        when(allocataireMapper.findById(id)).thenReturn(allocataire);
        when(versementMapper.hasVersementsForAllocataire(id)).thenReturn(true);

        boolean result = allocataireService.deleteAllocataireByIdIfNoVersements(id);

        assertThat(result).isFalse();
        verify(allocataireMapper, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("deleteAllocataireByIdIfNoVersements : lève une exception si l'allocataire est introuvable")
    void deleteAllocataire_ShouldThrowIfNotFound() {
        long id = 3L;

        when(allocataireMapper.findById(id)).thenReturn(null);

        assertThatThrownBy(() -> allocataireService.deleteAllocataireByIdIfNoVersements(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Allocataire introuvable");

        verify(allocataireMapper, never()).deleteById(anyLong());
    }

}

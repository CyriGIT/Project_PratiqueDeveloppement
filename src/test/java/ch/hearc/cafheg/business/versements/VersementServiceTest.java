package ch.hearc.cafheg.business.versements;

import static org.assertj.core.api.Assertions.assertThat;

import ch.hearc.cafheg.business.allocations.Allocataire;
import ch.hearc.cafheg.business.allocations.NoAVS;
import ch.hearc.cafheg.business.common.Montant;
import ch.hearc.cafheg.infrastructure.pdf.PDFExporter;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.EnfantMapper;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VersementServiceTest {

  private VersementService versementService;
  private VersementMapper versementMapper;
  private AllocataireMapper allocataireMapper;
  private EnfantMapper enfantMapper;
  private PDFExporter pdfExporter;

  @BeforeEach
  public void setUp() {
    versementMapper = Mockito.mock(VersementMapper.class);
    allocataireMapper = Mockito.mock(AllocataireMapper.class);
    enfantMapper = Mockito.mock(EnfantMapper.class);
    pdfExporter = new PDFExporter(enfantMapper);
    versementService = new VersementService(versementMapper, allocataireMapper, pdfExporter);
  }

  @Test
  void findSommeAllocationNaissanceParAnnee() {
    Mockito.when(versementMapper
        .findAllVersementAllocationNaissance()).thenReturn(
        Arrays.asList(new VersementAllocationNaissance(new Montant(new BigDecimal(2000)),
                LocalDate.of(2019, 1, 1)),
            new VersementAllocationNaissance(new Montant(new BigDecimal(2000)),
                LocalDate.of(2019, 2, 1)),
            new VersementAllocationNaissance(new Montant(new BigDecimal(5000)),
                LocalDate.of(2018, 1, 1))));

    Montant somme = versementService
        .findSommeAllocationNaissanceParAnnee(2019);

    assertThat(somme).isEqualTo(new Montant(new BigDecimal(4000)));
  }

  @Test
  void findSommeAllocationParAnnee() {
    Mockito.when(versementMapper
        .findAllVersementAllocation()).thenReturn(
        Arrays.asList(new VersementAllocation(new Montant(new BigDecimal(2000)),
                LocalDate.of(2019, 1, 1)),
            new VersementAllocation(new Montant(new BigDecimal(2000)),
                LocalDate.of(2019, 2, 1)),
            new VersementAllocation(new Montant(new BigDecimal(2000)),
                LocalDate.of(2019, 2, 1)),
            new VersementAllocation(new Montant(new BigDecimal(5000)),
                LocalDate.of(2018, 1, 1))));

    Montant somme = versementService
        .findSommeAllocationParAnnee(2019);

    assertThat(somme).isEqualTo(new Montant(new BigDecimal(6000)));
  }

  @Test
  void exportPDFAllocataire() {
    Mockito.when(versementMapper.findVersementParentEnfant()).thenReturn(
        Arrays.asList(new VersementParentEnfant(1L, 10L, new Montant(new BigDecimal(1000))),
            new VersementParentEnfant(1L, 11L, new Montant(new BigDecimal(500))),
            new VersementParentEnfant(1L, 11L, new Montant(new BigDecimal(1000))),
            new VersementParentEnfant(2L, 20L, new Montant(new BigDecimal(1000)))));
    Mockito.when(allocataireMapper.findById(1L))
        .thenReturn(new Allocataire(new NoAVS("2000-1000"), "Geiser", "Arnaud"));

    Mockito.when(enfantMapper.findById(10L))
        .thenReturn(new Enfant(new NoAVS("2000-1111"), "Geiser", "Chloé"));
    Mockito.when(enfantMapper.findById(11L))
        .thenReturn(new Enfant(new NoAVS("2000-1112"), "Geiser", "Tim"));

    byte[] bytes = versementService.exportPDFAllocataire(1L);

    assertThat(bytes.length).isNotNull();
  }

  @Test
  void exportPDFVersements() {
    Mockito.when(versementMapper.findVersementParentEnfantParMois()).thenReturn(
        Arrays.asList(new VersementParentParMois(1L, new Montant(new BigDecimal(1000)),
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1)),
            new VersementParentParMois(1L, new Montant(new BigDecimal(1000)),
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 1)),
            new VersementParentParMois(1L, new Montant(new BigDecimal(1000)),
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 1))));

    Mockito.when(allocataireMapper.findById(1L))
        .thenReturn(new Allocataire(new NoAVS("2000-1000"), "Geiser", "Arnaud"));

    byte[] bytes = versementService.exportPDFVersements(1L);

    assertThat(bytes.length).isNotNull();
  }

}
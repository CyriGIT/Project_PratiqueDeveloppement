package ch.hearc.cafheg.business.versements;

import ch.hearc.cafheg.business.common.Montant;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VersementAllocationNaissance {

  private final Montant montant;
  private final LocalDate dateVersement;

  public VersementAllocationNaissance(Montant montant, LocalDate dateVersement) {
    this.montant = montant;
    this.dateVersement = dateVersement;
  }

  public Montant getMontant() {
    return montant;
  }

  public LocalDate getDateVersement() {
    return dateVersement;
  }

  public static Montant sommeParAnnee(List<VersementAllocationNaissance> versements, int year) {
    BigDecimal somme = versements.stream()
        .filter(v -> v.getDateVersement().getYear() == year)
        .map(v -> v.getMontant().getValue())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    return new Montant(somme);
  }
}

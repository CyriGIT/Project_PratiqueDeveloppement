package ch.hearc.cafheg.business.allocations;

import ch.hearc.cafheg.business.common.Montant;
import java.time.LocalDate;

public class Allocation {

  private final Montant montant;
  private final Canton canton;
  private final LocalDate debut;
  private final LocalDate fin;

  public Allocation(Montant montant, Canton canton, LocalDate debut, LocalDate fin) {
    this.montant = montant;
    this.canton = canton;
    this.debut = debut;
    this.fin = fin;
  }

  public Montant getMontant() {
    return montant;
  }

  public Canton getCanton() {
    return canton;
  }

  public LocalDate getDebut() {
    return debut;
  }

  public LocalDate getFin() {
    return fin;
  }
}

package ch.hearc.cafheg.business.versements;

import ch.hearc.cafheg.business.common.Montant;
import java.time.LocalDate;

public class VersementParentParMois {

  private final long parentId;
  private final Montant montant;
  private final LocalDate dateVersement;
  private final LocalDate mois;

  public VersementParentParMois(long parentId,
      Montant montant, LocalDate dateVersement, LocalDate mois) {
    this.parentId = parentId;
    this.montant = montant;
    this.dateVersement = dateVersement;
    this.mois = mois;
  }

  public long getParentId() {
    return parentId;
  }

  public Montant getMontant() {
    return montant;
  }

  public LocalDate getMois() {
    return mois;
  }
}

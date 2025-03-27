package ch.hearc.cafheg.business.versements;

import ch.hearc.cafheg.business.common.Montant;

public class VersementParentEnfant {

  private final long parentId;
  private final long enfantId;
  private final Montant montant;

  public VersementParentEnfant(long parentId, long enfantId,
      Montant montant) {
    this.parentId = parentId;
    this.enfantId = enfantId;
    this.montant = montant;
  }

  public long getParentId() {
    return parentId;
  }

  public long getEnfantId() {
    return enfantId;
  }

  public Montant getMontant() {
    return montant;
  }
}

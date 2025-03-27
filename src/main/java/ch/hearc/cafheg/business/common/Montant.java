package ch.hearc.cafheg.business.common;

import java.math.BigDecimal;
import java.util.Objects;

public class Montant {

  public final BigDecimal value;

  public Montant(BigDecimal value) {
    this.value = value;
  }

  public BigDecimal getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    Montant montant = (Montant) o;
    return Objects.equals(getValue(), montant.getValue());
  }
}

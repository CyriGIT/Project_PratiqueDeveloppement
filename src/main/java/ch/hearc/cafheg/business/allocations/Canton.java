package ch.hearc.cafheg.business.allocations;

import java.util.stream.Stream;

public enum Canton {
  NE,
  BE,
  FR,
  GE,
  SH;

  // TODO: Ajouter les autres cantons..

  public static Canton fromValue(String value) {
    return Stream.of(Canton.values())
        .filter(c -> c.name().equals(value))
        .findAny()
        .orElse(null);
  }
}

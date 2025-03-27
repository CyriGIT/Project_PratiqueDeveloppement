package ch.hearc.cafheg.business.allocations;

public class Allocataire {

  private final NoAVS noAVS;
  private final String nom;
  private final String prenom;

  public Allocataire(NoAVS noAVS, String nom, String prenom) {
    this.noAVS = noAVS;
    this.nom = nom;
    this.prenom = prenom;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public NoAVS getNoAVS() {
    return noAVS;
  }
}

package ch.hearc.cafheg.business.allocations;

import java.math.BigDecimal;

public class ParentDroitAllocationDemande {
    private String enfantResidence;
    private boolean parent1ActiviteLucrative;
    private boolean parent1isIndependent;
    private String parent1LieuActivite;
    private String parent1Residence;
    private boolean parent1AutoriteParentale;
    private boolean parent2ActiviteLucrative;
    private boolean parent2isIndependent;
    private String parent2LieuActivite;
    private String parent2Residence;
    private boolean parent2AutoriteParentale;
    private boolean parentsEnsemble;
    private BigDecimal parent1Salaire;
    private BigDecimal parent2Salaire;

    // Getters et setters
    public String getEnfantResidence() {
        return enfantResidence;
    }

    public void setEnfantResidence(String enfantResidence) {
        this.enfantResidence = enfantResidence;
    }

    public boolean isParent1ActiviteLucrative() {
        return parent1ActiviteLucrative;
    }

    public void setParent1ActiviteLucrative(boolean parent1ActiviteLucrative) {
        this.parent1ActiviteLucrative = parent1ActiviteLucrative;
    }

    public boolean isParent1isIndependent() {
        return parent1isIndependent;
    }

    public void setParent1isIndependent(boolean parent1isIndependent) {
        this.parent1isIndependent = parent1isIndependent;
    }

    public String getParent1LieuActivite() {
        return parent1LieuActivite;
    }

    public void setParent1LieuActivite(String parent1LieuActivite) {
        this.parent1LieuActivite = parent1LieuActivite;
    }

    public String getParent1Residence() {
        return parent1Residence;
    }

    public void setParent1Residence(String parent1Residence) {
        this.parent1Residence = parent1Residence;
    }

    public boolean isParent1AutoriteParentale() {
        return parent1AutoriteParentale;
    }

    public void setParent1AutoriteParentale(boolean parent1AutoriteParentale) {
        this.parent1AutoriteParentale = parent1AutoriteParentale;
    }

    public boolean isParent2ActiviteLucrative() {
        return parent2ActiviteLucrative;
    }

    public void setParent2ActiviteLucrative(boolean parent2ActiviteLucrative) {
        this.parent2ActiviteLucrative = parent2ActiviteLucrative;
    }

    public boolean isParent2isIndependent() {
        return parent2isIndependent;
    }

    public void setParent2isIndependent(boolean parent2isIndependent) {
        this.parent2isIndependent = parent2isIndependent;
    }

    public String getParent2LieuActivite() {
        return parent2LieuActivite;
    }

    public void setParent2LieuActivite(String parent2LieuActivite) {
        this.parent2LieuActivite = parent2LieuActivite;
    }

    public String getParent2Residence() {
        return parent2Residence;
    }

    public boolean isParent2AutoriteParentale() {
        return parent2AutoriteParentale;
    }

    public void setParent2AutoriteParentale(boolean parent2AutoriteParentale) {
        this.parent2AutoriteParentale = parent2AutoriteParentale;
    }

    public void setParent2Residence(String parent2Residence) {
        this.parent2Residence = parent2Residence;
    }

    public boolean isParentsEnsemble() {
        return parentsEnsemble;
    }

    public void setParentsEnsemble(boolean parentsEnsemble) {
        this.parentsEnsemble = parentsEnsemble;
    }

    public BigDecimal getParent1Salaire() {
        return parent1Salaire;
    }

    public void setParent1Salaire(BigDecimal parent1Salaire) {
        this.parent1Salaire = parent1Salaire;
    }

    public BigDecimal getParent2Salaire() {
        return parent2Salaire;
    }

    public void setParent2Salaire(BigDecimal parent2Salaire) {
        this.parent2Salaire = parent2Salaire;
    }
}

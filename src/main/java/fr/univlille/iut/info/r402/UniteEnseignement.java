package fr.univlille.iut.info.r402;

public class UniteEnseignement {
    private final double note;
    private int id = 0;
    private UEAcquisition acquisition;

    public UniteEnseignement(int id, double note) {
        this.id = id;
        this.note = note;
    }

    public UEAcquisition getAcquisition() {
        return this.acquisition;
    }

    public void setAcquisition(UEAcquisition acquisition) {
        this.acquisition = acquisition;
    }

    public double getNote() {
        return note;
    }
}

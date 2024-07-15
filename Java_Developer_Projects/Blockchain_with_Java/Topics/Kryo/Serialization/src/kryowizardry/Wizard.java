package kryowizardry;

public class Wizard {
    private String name;
    private boolean isEvil;

    public Wizard(String name, boolean isEvil) {
        this.name = name;
        this.isEvil = isEvil;
    }

    public Wizard() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEvil() {
        return isEvil;
    }

    public void setEvil(boolean evil) {
        isEvil = evil;
    }
}

package educodedev.ejercicio02examen01.models;

import java.io.Serializable;

public class Coche implements Serializable {
private String piloto;
private String modelo;
private int vueltasCompletadas;

    public Coche(String piloto, String modelo, int vueltasCompletadas) {
        this.piloto = piloto;
        this.modelo = modelo;
        this.vueltasCompletadas = vueltasCompletadas;
    }

    public String getPiloto() {
        return piloto;
    }

    public void setPiloto(String piloto) {
        this.piloto = piloto;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getVueltasCompletadas() {
        return vueltasCompletadas;
    }

    public void setVueltasCompletadas(int vueltasCompletadas) {
        this.vueltasCompletadas = vueltasCompletadas;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "piloto='" + piloto + '\'' +
                ", modelo='" + modelo + '\'' +
                ", vueltasCompletadas=" + vueltasCompletadas +
                '}';
    }
}

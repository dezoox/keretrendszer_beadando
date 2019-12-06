package konyvtar.models;

import java.time.LocalDate;
import java.util.UUID;

public class Konyv {
    private UUID id;
    private String cim;
    private int oldalszam;
    private String szerzo;
    private Mufaj mufaj;
    private LocalDate megjelenes;

    public Konyv() {
        this.id = UUID.randomUUID();
    }

    public Konyv(String cim, int oldalszam, String szerzo, Mufaj mufaj, LocalDate megjelenes)
    {
        this();
        this.cim = cim;
        this.oldalszam = oldalszam;
        this.szerzo = szerzo;
        this.mufaj = mufaj;
        this.megjelenes = megjelenes;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public int getOldalszam() {
        return oldalszam;
    }

    public void setOldalszam(int oldalszam) {
        this.oldalszam = oldalszam;
    }

    public String getSzerzo() {
        return szerzo;
    }

    public void setSzerzo(String szerzo) {
        this.szerzo = szerzo;
    }

    public Mufaj getMufaj() {
        return mufaj;
    }

    public void setMufaj(Mufaj mufaj) {
        this.mufaj = mufaj;
    }

    public LocalDate getMegjelenes() {
        return megjelenes;
    }

    public void setMegjelenes(LocalDate megjelenes) {
        this.megjelenes = megjelenes;
    }
}

package services.impl;

import dao.KonyvDAO;
import konyvtar.exceptions.DateIsToLateException;
import konyvtar.exceptions.NoMatchingIDException;
import konyvtar.models.Konyv;
import services.KonyvService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

public class KonyvServiceImplementation implements KonyvService {

    private KonyvDAO dao;

    public KonyvServiceImplementation(KonyvDAO dao) {
        this.dao = dao;
    }

    public Collection<Konyv> getKonyvek() {
        return dao.readAllKonyv();
    }

    public Konyv getKonyv(UUID id) throws NoMatchingIDException {
        return dao.readKonyv(id);
    }

    public void addKonyv(Konyv konyv) throws DateIsToLateException {
        if(konyv.getMegjelenes().isAfter(LocalDate.now().plusYears(1))){
            throw new DateIsToLateException();
        }
        dao.createKonyv(konyv);
    }

    public void updateKonyv(Konyv konyv, UUID id) throws DateIsToLateException, NoMatchingIDException {
        if(konyv.getMegjelenes().isAfter(LocalDate.now().plusYears(1))){
            throw new DateIsToLateException();
        }
        dao.updateKonyv(konyv, id);
    }

    public void deleteKonyv(Konyv konyv) throws NoMatchingIDException {
        dao.deleteKonyv(konyv);
    }

    public void deleteKonyv(UUID id) throws NoMatchingIDException {
        Konyv result = getKonyv(id);
        dao.deleteKonyv(result);
    }
}


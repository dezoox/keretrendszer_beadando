package dao;

import konyvtar.exceptions.DateIsToLateException;
import konyvtar.exceptions.NoMatchingIDException;
import konyvtar.models.Konyv;

import java.util.Collection;
import java.util.UUID;

public interface KonyvDAO {

    //DB MŰVELETEK
    void createKonyv(Konyv konyv) throws DateIsToLateException;
    Collection<Konyv> readAllKonyv();
    Konyv readKonyv(UUID id) throws NoMatchingIDException;
    void updateKonyv(Konyv konyv, UUID id) throws NoMatchingIDException, DateIsToLateException;
    void deleteKonyv(Konyv konyv) throws NoMatchingIDException;
}

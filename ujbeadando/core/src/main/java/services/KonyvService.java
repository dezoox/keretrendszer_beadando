package services;

import konyvtar.exceptions.DateIsToLateException;
import konyvtar.exceptions.NoMatchingIDException;
import konyvtar.models.Konyv;

import java.util.Collection;
import java.util.UUID;

public interface KonyvService {

    Collection<Konyv> getKonyvek();
    Konyv getKonyv(UUID id) throws NoMatchingIDException;
    void addKonyv(Konyv konyv) throws DateIsToLateException;
    void updateKonyv(Konyv konyv, UUID id) throws DateIsToLateException, NoMatchingIDException;
    void deleteKonyv(Konyv konyv) throws NoMatchingIDException;
    void deleteKonyv(UUID id) throws NoMatchingIDException;
}

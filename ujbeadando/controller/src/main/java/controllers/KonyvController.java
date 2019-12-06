package controllers;

import konyvtar.exceptions.DateIsToLateException;
import konyvtar.exceptions.NoMatchingIDException;
import konyvtar.models.Konyv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import services.KonyvService;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static java.awt.PageAttributes.MediaType.A;

@RestController
public class KonyvController {
    private KonyvService service;

    public KonyvController(@Autowired KonyvService service) {
        this.service = service;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ResponseBody
    public String showNumberOfKonyvek(){
        return String.valueOf(service.getKonyvek().size());
    }

    @RequestMapping(value = "/konyvek", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Konyv> getAllKonyv(){
        return service.getKonyvek();
    }

    @RequestMapping(value = "/konyvek/add", method = RequestMethod.POST)
    @ResponseBody
    public Konyv addNewKonyv(@RequestBody Konyv konyv) throws DateIsToLateException, NoMatchingIDException {
        service.addKonyv(konyv);
        return service.getKonyv(konyv.getId());
    }

    @RequestMapping(value = "/konyvek/cim/{cim}", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Konyv> getKonyvByCim(@PathVariable String cim){
        Collection<Konyv> konyvek = service.getKonyvek();
        Collection<Konyv> result = new ArrayList<Konyv>();

        for (Konyv k: konyvek){
            if(k.getCim().equalsIgnoreCase(cim)){
                result.add(k);
            }
        }
        return result;
    }

    @RequestMapping(value = "/konyvek/mufaj/{mufaj}", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Konyv> getKonyvByMufaj(@PathVariable String mufaj){
        Collection<Konyv> konyvek = service.getKonyvek();
        Collection<Konyv> result = new ArrayList<Konyv>();

        for (Konyv k: konyvek){
            if(k.getMufaj().toString().equalsIgnoreCase(mufaj)){
                result.add(k);
            }
        }
        return result;
    }

    @RequestMapping(value = "konyvek/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Konyv getKonyvById(@PathVariable UUID id) throws NoMatchingIDException {
        return service.getKonyv(id);
    }

    @ExceptionHandler(NoMatchingIDException.class)
    @ResponseBody
    public String handleNoMatchingIDException(Exception e){
        return "Corresponding UUID does not exist. Maybe a typo? " + e.getMessage();
    }

    @ExceptionHandler(DateIsToLateException.class)
    @ResponseBody
    public String handleDateIsToLateException(Exception e){
        return "Date is invalid! Maybe the date you've entered is after 1 years of today's date? " + e.getMessage();
    }

    @RequestMapping(value = "/konyvek/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteKonyv(@PathVariable UUID id) throws NoMatchingIDException {
        service.deleteKonyv(id);
        return "Deleting succesful";
    }

    @RequestMapping(value="/konyvek/year/{year}", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Konyv> getBooksFromYear(@PathVariable int year){
        Collection<Konyv> books = service.getKonyvek();
        Collection<Konyv> thisYearBooks = new ArrayList<Konyv>();
        for (Konyv k: books){
            if(k.getMegjelenes().getYear() == year){
                thisYearBooks.add(k);
            }
        }
        return thisYearBooks;
    }

    @RequestMapping(value = "/konyvek/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Konyv updateKonyv(@PathVariable UUID id, @RequestBody Konyv konyv) throws DateIsToLateException, NoMatchingIDException {
        konyv.setId(id);
        service.updateKonyv(konyv, id);
        return konyv;
    }

}


package dao.impl.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dao.KonyvDAO;
import konyvtar.exceptions.DateIsToLateException;
import konyvtar.exceptions.NoMatchingIDException;
import konyvtar.models.Konyv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class KonyvDAOJSON implements KonyvDAO {
    File jsonFile;
    ObjectMapper mapper;

    public KonyvDAOJSON(File jsonFile) {
        this.jsonFile = jsonFile;

        if(!jsonFile.exists()){
            try {
                jsonFile.createNewFile();
                FileWriter writer = new FileWriter(jsonFile);
                writer.write("[]");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
    }

    public KonyvDAOJSON(String jsonFilepath) {
        this.jsonFile = new File(jsonFilepath);

        if(!jsonFile.exists()){
            try {
                jsonFile.createNewFile();
                FileWriter writer = new FileWriter(jsonFilepath);
                writer.write("[]");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
    }

    public void createKonyv(Konyv konyv) throws DateIsToLateException {
        Collection<Konyv> books = readAllKonyv();
        books.add(konyv);

        try{
            mapper.writeValue(jsonFile, books);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<Konyv> readAllKonyv() {
        Collection<Konyv> books = new ArrayList<Konyv>();
        try{
            books = mapper.readValue(jsonFile, new TypeReference<Collection<Konyv>>() {
            });
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    public Konyv readKonyv(UUID id) throws NoMatchingIDException {
        Collection<Konyv> books = readAllKonyv();

        for(Konyv konyv: books){
            if(konyv.getId().toString().equalsIgnoreCase(id.toString())){
                return konyv;
            }
        }
        throw new NoMatchingIDException();
    }

    public void updateKonyv(Konyv konyv, UUID id) throws NoMatchingIDException, DateIsToLateException {
        Konyv temp = readKonyv(id);
        deleteKonyv(temp);
        createKonyv(konyv);
    }

    public void deleteKonyv(Konyv konyv) throws NoMatchingIDException {
        Collection<Konyv> books = readAllKonyv();
        Collection<Konyv> result = new ArrayList<Konyv>();
        try {
            Konyv deleteThis = readKonyv(konyv.getId());

            for(Konyv a : books){
                if(!(a.getId().equals(deleteThis.getId()))){
                    result.add(a);
                }
            }

            mapper.writeValue(jsonFile, result);
        } catch (NoMatchingIDException noMatchingIdException) {
            throw noMatchingIdException;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

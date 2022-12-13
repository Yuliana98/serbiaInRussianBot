package com.backend.serbiaInRussianBot.service;

import com.backend.serbiaInRussianBot.db.CatalogRepository;
import com.backend.serbiaInRussianBot.entity.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service class. Using a new interface for data operations in the client (service)
 */
@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    public List<Catalog> getFullCatalog() {

        List<Catalog> fullCatalog = catalogRepository.findAll();

        if (fullCatalog.isEmpty())
            System.out.println("Catalog is empty");

        return fullCatalog;
    }

    public Catalog getAdById(Integer id) {

        Optional<Catalog> ad = catalogRepository.findById(id);

        return ad.orElse(null); //ad.isPresent() ? ad.get() : null;
    }

    public void saveAdChanges(Catalog ad) {

        catalogRepository.save(ad);
    }

    public void deleteAd(Catalog ad) {

        catalogRepository.delete(ad);
    }

    public void deleteAllCatalog() {

        catalogRepository.deleteAll();
    }

    //todo:
    public void insertAd(String adTitle, String city, String type, BigDecimal price, String description, BigDecimal phone) {

        //создание объекта и занесение результата в переменную ad
        Catalog ad = Catalog.builder()
                .title(adTitle)
                .city(city)
                .type(type)
//                .service(service)
//                .product(product)
                .price(price)
                .description(description)
                .phone(phone)
                .build();

        catalogRepository.save(ad);
    }
}

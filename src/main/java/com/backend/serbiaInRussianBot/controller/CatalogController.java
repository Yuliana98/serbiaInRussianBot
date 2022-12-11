package com.backend.serbiaInRussianBot.controller;

import com.backend.serbiaInRussianBot.entity.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.backend.serbiaInRussianBot.service.CatalogService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller. Processes client requests and returns a result. Describes the entry point.
 */
@RestController
@RequestMapping("/ad")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    /**
     *Adding an ad to the database.
     * The request starts with "/add" (after "/ad"), data is supplied as input,
     * only adTitle is requiredAdding an ad to the DB.
     * The request starts with "/add" (after "/ad"), data is supplied as input, only adTitle is required.
     * @param adTitle
     * @param city
     * @param type
     * @param service
     * @param product
     * @param price
     * @param description
     * @param phone
     * @return
     */
    @PostMapping("/add")
    public String insertAd(@RequestParam(value = "adTitle", required = false) String adTitle,
                           @RequestParam(value = "city", required = false) String city,
                           @RequestParam(value = "type", required = false) String type,
                           @RequestParam(value = "com/backend/serbiaInRussianBot/service", required = false) String service,
                           @RequestParam(value = "product", required = false) String product,
                           @RequestParam(value = "price", required = false) BigDecimal price,
                           @RequestParam(value = "description", required = false) String description,
                           @RequestParam(value = "phone", required = false) BigDecimal phone) {

        catalogService.insertAd(adTitle, city, type, service, product, price, description, phone);
        /**
         * todo: возвращать помимо строки еще и объект
         */
        return "Ad added!";
    }


    /**    todo: поиск по названию - выдает все с совпадающим названием, даже частичное!
     *     todo: поиск по типу запрашивает категорию, после начинается поиск по категории, если других фильтров нет, выдает все с данной категорией
     */
    /**
     * Search for ads in the DB.
     * The request starts with "/search" (after "/ad"), the input is any data of your choice,
     * you can search by any of the parameters, or by several at once, and also select the sorting option todo: sortBy.
     * If you do not specify parameters, all ads stored in the directory will be displayed.
     * Returns all ads matching the specified parameters.
     * @param adTitle
     * @param city
     * @param type
     * @param service
     * @param product
     * @return
     */
    @GetMapping("/search")
    public List<Catalog> CatalogByParams(@RequestParam(value = "adTitle", required = false) String adTitle,
                                         @RequestParam(value = "city", required = false) String city,
                                         @RequestParam(value = "type", required = false) String type,
                                         @RequestParam(value = "com/backend/serbiaInRussianBot/service", required = false) String service, //??
                                         @RequestParam(value = "product", required = false) String product) //??
//                                       @RequestParam(value = "sortBy", required = false) SortTypeEnum sort)
    {

        //todo: компаратор по цене, по дате публикации?, по рейтигу?
        /*
        CustomCatalogComparator customCatalogComparator = ComparatorUtil.getComparator(sort);*/

        List<Catalog> ads = catalogService.getFullCatalog(); //get a list of all ads in the directory

        //filter ads by each parameter
        List<Catalog> adsCollection = ads.stream()
                .filter(ad -> {    //one ad arrives from the ads collection
                    if (adTitle == null) {  //check if the ad name is received
                        return true; //if adTitle is empty, we pass through the filter each ad that will enter the filter, since all are suitable
                    } else {
                        return ad.getTitle().equals(adTitle); //check if the adTitle parameter matches what is written in the ad. If the parameter matches, the ad passes the filter.
                    }
                })
                .filter(ad -> {
                    if (city == null) {
                        return true;
                    } else {
                        return ad.getCity().equals(city);
                    }
                })
                .filter(ad -> {
                    if (type == null) {
                        return true;
                    } else {
                        return ad.getType().equals(type);
                    }
                })
                .filter(ad -> {
                    if (service == null) {
                        return true;
                    } else {
                        return ad.getService().equals(service);
                    }
                })
                .filter(ad -> {
                    if (product == null) {
                        return true;
                    } else {
                        return ad.getProduct().equals(product);
                    }
                })
//                .sorted(customCatalogComparator) //todo: sortBy
                .collect(Collectors.toList());   //collect all matching ads in a collection
        //todo: вернуть первые 10, затем следующие 10 итд.
        //.limit(10).collect(Collectors.toList()); //to return only the first 10 ads
        return adsCollection;
    }

    //todo: отправка изменений на модерацию
    /**
     * Changing the selected (by id) ad. The ad id and any parameters that need to be changed are passed as input.
     * The request starts with "/changeAd" (after "/Ad").
     * @param id
     * @param adTitle
     * @param city
     * @param type
     * @param service
     * @param product
     * @param price
     * @param description
     * @param phone
     * @return
     */
    @PutMapping("/changeAd")
    public Catalog changeAd(@RequestParam(value = "id") Integer id,
                            @RequestParam(value = "adTitle", required = false) String adTitle,
                            @RequestParam(value = "city", required = false) String city,
                            @RequestParam(value = "type", required = false) String type,
                            @RequestParam(value = "com/backend/serbiaInRussianBot/service", required = false) String service,
                            @RequestParam(value = "product", required = false) String product,
                            @RequestParam(value = "price", required = false) BigDecimal price,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "phone", required = false) BigDecimal phone) {

        Catalog adChange = catalogService.getAdById(id); //получаем объявление для изменения по ее ID

        //Checking which data is input, if the field is not empty, then a new name is set for this field, which is input
        if (adTitle != null) {
            adChange.setTitle(adTitle);
        }
        if (city != null) {
            adChange.setCity(city);
        }
        if (type != null) {
            adChange.setType(type);
        }
        if (service != null) {
            adChange.setService(service);
        }
        if (product != null) {
            adChange.setProduct(product);
        }
        if (price != null) {
            adChange.setPrice(price);
        }
        if (description != null) {
            adChange.setDescription(description);
        }
        if (phone != null) {
            adChange.setPhone(phone);
        }

        catalogService.saveAdChanges(adChange);

        return adChange;
    }

    //todo: отправка изменений на модерацию. Запрос причины удаления
    /**
     * Deleting the selected (by id) ad. Only the ad id is passed as input.
     * The request starts with "/deleteAd" (after "/ad").
     * @param id
     * @return
     */
    @PutMapping("/deleteAd")
    public String changeAd(@RequestParam(value = "id") Integer id) {

        Catalog adToDelete = catalogService.getAdById(id);
        catalogService.deleteAd(adToDelete);

        return "OK";
    }
}
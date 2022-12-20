package com.backend.serbiaInRussianBot.db;

import com.backend.serbiaInRussianBot.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {

}

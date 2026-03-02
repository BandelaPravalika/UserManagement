package com.company.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.company.dashboard.model.AssetExchange;

@Repository
public interface AssetExchangeRepository extends JpaRepository<AssetExchange, Long> {

    boolean existsByAssetTag(String assetTag);

    @Query("SELECT a FROM AssetExchange a LEFT JOIN FETCH a.vendor")
    List<AssetExchange> findAllWithVendor();
}
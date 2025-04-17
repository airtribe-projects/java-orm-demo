package com.airtribe.orm.demo.utils;

import com.airtribe.orm.demo.entities.mysql.StoredProductEntity;
import com.airtribe.orm.demo.entities.redis.CachedProductEntity;
import com.airtribe.orm.demo.models.requests.products.AddProductRequestDto;
import com.airtribe.orm.demo.models.responses.products.ProductDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.products.ProductDetailsResponseDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMappers {

    public StoredProductEntity mapToProductEntity(AddProductRequestDto addProductRequestDto) {
        return StoredProductEntity.builder()
                .productId(UUID.randomUUID().toString())
                .name(addProductRequestDto.getName())
                .price(addProductRequestDto.getPrice())
                .unit(addProductRequestDto.getUnit())
                .build();
    }

    public ProductDetailsResponseDto mapToProductDetailsResponse(StoredProductEntity storedProductEntity) {
        return ProductDetailsResponseDto.builder()
                .productId(storedProductEntity.getProductId())
                .name(storedProductEntity.getName())
                .price(storedProductEntity.getPrice())
                .unit(storedProductEntity.getUnit())
                .build();
    }

    public ProductDetailsResponseDto mapToProductDetailsResponseFromCache(CachedProductEntity cachedProductEntity) {
        return ProductDetailsResponseDto.builder()
                .productId(cachedProductEntity.getProductId())
                .name(cachedProductEntity.getName())
                .price(cachedProductEntity.getPrice())
                .unit(cachedProductEntity.getUnit())
                .build();
    }

    public ProductDeleteResponseDto mapToProductDeleteResponseDto(StoredProductEntity storedProductEntity) {
        return ProductDeleteResponseDto.builder()
                .name(storedProductEntity.getName())
                .isDeleted(storedProductEntity.isDeleted())
                .message("Product deleted successfully")
                .build();
    }

    public CachedProductEntity fromMySqlToRedisEntity(StoredProductEntity storedProductEntity) {
        return CachedProductEntity.builder()
                .productId(storedProductEntity.getProductId())
                .name(storedProductEntity.getName())
                .price(storedProductEntity.getPrice())
                .unit(storedProductEntity.getUnit())
                .isDeleted(storedProductEntity.isDeleted())
                .inventoryId(storedProductEntity.getInventory() != null ? storedProductEntity.getInventory().getInventoryId() : null)
                .build();
    }
}

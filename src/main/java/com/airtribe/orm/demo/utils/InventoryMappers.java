package com.airtribe.orm.demo.utils;

import com.airtribe.orm.demo.entities.mysql.StoredInventoryEntity;
import com.airtribe.orm.demo.entities.mysql.StoredProductEntity;
import com.airtribe.orm.demo.models.requests.inventories.AddInventoryRequestDto;
import com.airtribe.orm.demo.models.responses.inventories.InventoryDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.inventories.InventoryDetailsResponseDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InventoryMappers {
    private final ProductMappers productMappers;

    public InventoryMappers(ProductMappers productMappers) {
        this.productMappers = productMappers;
    }

    public StoredInventoryEntity mapToInventoryEntity(AddInventoryRequestDto addInventoryRequestDto, StoredProductEntity storedProductEntity) {
        return StoredInventoryEntity.builder()
                .inventoryId(UUID.randomUUID().toString())
                .inventoryName(addInventoryRequestDto.getInventoryName())
                .stock(addInventoryRequestDto.getStock())
                .inventoryUnit(addInventoryRequestDto.getInventoryUnit())
                .product(storedProductEntity)
                .build();
    }

    public InventoryDetailsResponseDto mapToInventorDetailsResponse(StoredInventoryEntity storedInventoryEntity) {
        return InventoryDetailsResponseDto.builder()
                .inventoryId(storedInventoryEntity.getInventoryId())
                .inventoryName(storedInventoryEntity.getInventoryName())
                .stock(storedInventoryEntity.getStock())
                .inventoryUnit(storedInventoryEntity.getInventoryUnit())
                .productDetail(productMappers.mapToProductDetailsResponse(storedInventoryEntity.getProduct()))
                .build();
    }

    public InventoryDeleteResponseDto mapToInventoryDeleteResponseDto(StoredInventoryEntity storedInventoryEntity) {
        return InventoryDeleteResponseDto.builder()
                .inventoryName(storedInventoryEntity.getInventoryName())
                .isDeleted(storedInventoryEntity.isDeleted())
                .message("Inventory deleted successfully")
                .build();
    }
}

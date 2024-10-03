package com.airtribe.orm.demo.services.inventories;

import com.airtribe.orm.demo.Exceptions.ResourceNotFoundException;
import com.airtribe.orm.demo.daos.InventoryDao;
import com.airtribe.orm.demo.daos.ProductDao;
import com.airtribe.orm.demo.entities.mysql.StoredInventoryEntity;
import com.airtribe.orm.demo.entities.mysql.StoredProductEntity;
import com.airtribe.orm.demo.models.requests.inventories.AddInventoryRequestDto;
import com.airtribe.orm.demo.models.requests.inventories.UpdateInventoryRequestDto;
import com.airtribe.orm.demo.models.responses.inventories.InventoryDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.inventories.InventoryDetailsResponseDto;
import com.airtribe.orm.demo.utils.InventoryMappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryDao inventoryDao;
    private final ProductDao productDao;
    private final InventoryMappers inventoryMappers;

    public InventoryServiceImpl(InventoryDao inventoryDao, ProductDao productDao,InventoryMappers inventoryMappers) {
        this.inventoryDao = inventoryDao;
        this.productDao = productDao;
        this.inventoryMappers = inventoryMappers;
    }


    @Override
    public InventoryDetailsResponseDto addInventory(AddInventoryRequestDto addInventoryRequestDto) {
        StoredProductEntity storedProductEntity = getProductEntity(addInventoryRequestDto.getProductId());
        StoredInventoryEntity storedInventoryEntity = inventoryMappers.mapToInventoryEntity(addInventoryRequestDto, storedProductEntity);
        storedInventoryEntity = inventoryDao.save(storedInventoryEntity);
        return inventoryMappers.mapToInventorDetailsResponse(storedInventoryEntity);
    }

    @Override
    public InventoryDetailsResponseDto updateInventory(UpdateInventoryRequestDto updateInventoryRequestDto) {
        StoredInventoryEntity storedInventoryEntity = getInventoryDetails(updateInventoryRequestDto.getInventoryId());
        storedInventoryEntity.setInventoryName(updateInventoryRequestDto.getInventoryName());
        storedInventoryEntity.setStock(updateInventoryRequestDto.getStock());
        storedInventoryEntity.setInventoryUnit(updateInventoryRequestDto.getInventoryUnit());
        storedInventoryEntity = inventoryDao.save(storedInventoryEntity);
        return inventoryMappers.mapToInventorDetailsResponse(storedInventoryEntity);
    }

    @Override
    public InventoryDeleteResponseDto removeInventory(String inventoryId) {
        StoredInventoryEntity storedInventoryEntity = getInventoryDetails(inventoryId);
        storedInventoryEntity.setDeleted(true);
        storedInventoryEntity = inventoryDao.save(storedInventoryEntity);
        return inventoryMappers.mapToInventoryDeleteResponseDto(storedInventoryEntity);
    }

    @Override
    public InventoryDetailsResponseDto getInventory(String inventoryId) {
        StoredInventoryEntity storedInventoryEntity = getInventoryDetails(inventoryId);
        return inventoryMappers.mapToInventorDetailsResponse(storedInventoryEntity);
    }

    @Override
    public InventoryDetailsResponseDto clearInventory(String inventoryId) {
        StoredInventoryEntity storedInventoryEntity = getInventoryDetails(inventoryId);
        storedInventoryEntity.setStock(0);
        storedInventoryEntity = inventoryDao.save(storedInventoryEntity);
        return inventoryMappers.mapToInventorDetailsResponse(storedInventoryEntity);
    }

    private StoredInventoryEntity getInventoryDetails(String inventoryId) {
        StoredInventoryEntity storedInventoryEntity = inventoryDao.findByInventoryId(inventoryId);
        if (Objects.isNull(storedInventoryEntity)) {
            throw ResourceNotFoundException.builder()
                    .errorCode("INVENTORY_NOT_FOUND")
                    .errorMessage("Inventory not found")
                    .build();
        }
        return storedInventoryEntity;
    }

    private StoredProductEntity getProductEntity(String productId) {
        StoredProductEntity storedProductEntity = productDao.findByProductId(productId);
        if (Objects.isNull(storedProductEntity)) {
            throw ResourceNotFoundException.builder()
                    .errorCode("PRODUCT_NOT_FOUND")
                    .errorMessage("PRODUCT not found")
                    .build();
        }
        return storedProductEntity;
    }
}

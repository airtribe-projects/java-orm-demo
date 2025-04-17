package com.airtribe.orm.demo.services.inventories;

import com.airtribe.orm.demo.models.requests.inventories.AddInventoryRequestDto;
import com.airtribe.orm.demo.models.requests.inventories.UpdateInventoryRequestDto;
import com.airtribe.orm.demo.models.responses.inventories.InventoryDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.inventories.InventoryDetailsResponseDto;

public interface InventoryService {
    InventoryDetailsResponseDto addInventory(AddInventoryRequestDto addInventoryRequestDto);

    InventoryDetailsResponseDto updateInventory(UpdateInventoryRequestDto updateInventoryRequestDto);

    InventoryDeleteResponseDto removeInventory(String inventoryId);

    InventoryDetailsResponseDto getInventory(String inventoryId);

    InventoryDetailsResponseDto clearInventory(String inventoryId);
}

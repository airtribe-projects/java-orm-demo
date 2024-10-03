package com.airtribe.orm.demo.controllers;

import com.airtribe.orm.demo.Exceptions.InvalidInputException;
import com.airtribe.orm.demo.Exceptions.ResourceNotFoundException;
import com.airtribe.orm.demo.constants.Constants;
import com.airtribe.orm.demo.enums.ResponseType;
import com.airtribe.orm.demo.models.requests.inventories.AddInventoryRequestDto;
import com.airtribe.orm.demo.models.requests.inventories.UpdateInventoryRequestDto;
import com.airtribe.orm.demo.models.requests.users.UserCreationRequestDto;
import com.airtribe.orm.demo.models.requests.users.UserUpdateRequestDto;
import com.airtribe.orm.demo.models.responses.inventories.InventoryDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.inventories.InventoryDetailsResponseDto;
import com.airtribe.orm.demo.models.responses.users.UserDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.users.UserDetailsResponseDto;
import com.airtribe.orm.demo.models.responses.base.GenericResponse;
import com.airtribe.orm.demo.services.inventories.InventoryService;
import com.airtribe.orm.demo.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final ValidationUtils validationUtils;

    public InventoryController(InventoryService inventoryService, ValidationUtils validationUtils) {
        this.inventoryService = inventoryService;
        this.validationUtils = validationUtils;
    }

    @GetMapping(path = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fetch Inventory Details", description = "Fetch the details of a inventory")
    public GenericResponse<InventoryDetailsResponseDto> fetchProductDetails(@RequestParam("inventory-id") String productId) {
        InventoryDetailsResponseDto inventoryDetailsResponseDto = null;
        try {
            validationUtils.validateId(productId);
            inventoryDetailsResponseDto = inventoryService.getInventory(productId);
        } catch (InvalidInputException e) {
            return GenericResponse.<InventoryDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<InventoryDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<InventoryDetailsResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(inventoryDetailsResponseDto)
                .build();
    }

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add new Inventory", description = "add a new inventory")
    public GenericResponse<InventoryDetailsResponseDto> addInventory(@RequestBody AddInventoryRequestDto addInventoryRequestDto) {
        InventoryDetailsResponseDto inventoryDetailsResponseDto = null;
        try {
            validationUtils.validateAddInventoryRequest(addInventoryRequestDto);
            inventoryDetailsResponseDto = inventoryService.addInventory(addInventoryRequestDto);

        } catch (InvalidInputException e) {
            return GenericResponse.<InventoryDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<InventoryDetailsResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(inventoryDetailsResponseDto)
                .build();
    }

    @Operation(summary = "Update Inventory", description = "Update the details of an inventory")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<InventoryDetailsResponseDto> updateUser(@RequestBody UpdateInventoryRequestDto updateInventoryRequestDto) {
        InventoryDetailsResponseDto inventoryDetailsResponseDto = null;
        try {
            validationUtils.validateUpdateInventoryRequest(updateInventoryRequestDto);
            inventoryDetailsResponseDto = inventoryService.updateInventory(updateInventoryRequestDto);

        } catch (InvalidInputException e) {
            return GenericResponse.<InventoryDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<InventoryDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<InventoryDetailsResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(inventoryDetailsResponseDto)
                .build();
    }

    @Operation(summary = "Delete Inventory", description = "delete the inventory")
    @PutMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<InventoryDeleteResponseDto> deleteUser(@RequestParam("inventory-id") String inventoryId) {
        InventoryDeleteResponseDto inventoryDeleteResponseDto = null;
        try {
            validationUtils.validateId(inventoryId);
            inventoryDeleteResponseDto = inventoryService.removeInventory(inventoryId);

        } catch (InvalidInputException e) {
            return GenericResponse.<InventoryDeleteResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<InventoryDeleteResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<InventoryDeleteResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(inventoryDeleteResponseDto)
                .build();
    }
}

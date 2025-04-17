package com.airtribe.orm.demo.utils;

import com.airtribe.orm.demo.Exceptions.InvalidInputException;
import com.airtribe.orm.demo.models.requests.inventories.AddInventoryRequestDto;
import com.airtribe.orm.demo.models.requests.inventories.UpdateInventoryRequestDto;
import com.airtribe.orm.demo.models.requests.orders.CreateOrderRequest;
import com.airtribe.orm.demo.models.requests.products.AddProductRequestDto;
import com.airtribe.orm.demo.models.requests.products.ProductUpdateRequestDto;
import com.airtribe.orm.demo.models.requests.users.UserCreationRequestDto;
import com.airtribe.orm.demo.models.requests.users.UserUpdateRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class ValidationUtils {
    public void validateUserCreationRequest(UserCreationRequestDto userCreationRequestDto) {
        isEmpty(userCreationRequestDto);
        validateUserName(userCreationRequestDto.getUsername());
        validateEmail(userCreationRequestDto.getEmail());
        validatePhoneNumber(userCreationRequestDto.getPhoneNumber());
    }

    public void validateUserUpdateRequest(UserUpdateRequestDto userUpdateRequestDto) {
        isEmpty(userUpdateRequestDto);
        validateId(userUpdateRequestDto.getUserId());
        validateEmail(userUpdateRequestDto.getEmail());
        validatePhoneNumber(userUpdateRequestDto.getPhoneNumber());
    }

    public void validateAddInventoryRequest(AddInventoryRequestDto addInventoryRequestDto) {
        isEmpty(addInventoryRequestDto);
        validateInventoryName(addInventoryRequestDto.getInventoryName());
    }

    public void validateUpdateInventoryRequest(UpdateInventoryRequestDto updateInventoryRequestDto) {
        isEmpty(updateInventoryRequestDto);
        validateInventoryName(updateInventoryRequestDto.getInventoryName());
    }

    public void validateAddProductRequest(AddProductRequestDto addProductRequestDto) {
        isEmpty(addProductRequestDto);
        validateProductName(addProductRequestDto.getName());
        validateProductPrice(addProductRequestDto.getPrice());
    }

    public void validateUpdateProductRequest(ProductUpdateRequestDto productUpdateRequestDto) {
        isEmpty(productUpdateRequestDto);
        validateProductName(productUpdateRequestDto.getName());
        validateProductPrice(productUpdateRequestDto.getPrice());
    }

    public void validateId(String id) {
        if (!StringUtils.hasText(id)) {
            throw InvalidInputException.builder()
                    .errorCode("INVALID_ID")
                    .errorMessage("The id is not valid")
                    .build();
        }
    }

    private void isEmpty(Object object) {
        if (Objects.isNull(object)) {
            throw InvalidInputException.builder()
                    .errorCode("INVALID_INPUT")
                    .errorMessage("Request Object Can't be Empty")
                    .build();
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            throw InvalidInputException.builder()
                    .errorCode("INVALID_INPUT")
                    .errorMessage("PhoneNumber is required")
                    .build();
        }
    }

    private void validateEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw InvalidInputException.builder()
                    .errorCode("INVALID_INPUT")
                    .errorMessage("Email is required")
                    .build();
        }
    }

    private void validateUserName(String userName) {
        if (!StringUtils.hasText(userName)) {
            throw InvalidInputException.builder()
                    .errorCode("INVALID_INPUT")
                    .errorMessage("UserName is required")
                    .build();
        }
    }

    private void validateInventoryName(String inventoryName) {
        if (!StringUtils.hasText(inventoryName)) {
            throw InvalidInputException.builder()
                    .errorCode("INVALID_INPUT")
                    .errorMessage("inventoryName is required")
                    .build();
        }
    }

    private void validateProductName(String productName) {
        if (!StringUtils.hasText(productName)) {
            throw InvalidInputException.builder()
                    .errorCode("INVALID_INPUT")
                    .errorMessage("product Name is required")
                    .build();
        }
    }

    private void validateProductPrice(Double price) {
        if (price == null || price <= 0.0) {
            throw InvalidInputException.builder()
                    .errorCode("INVALID_INPUT")
                    .errorMessage("Price can't be 0")
                    .build();
        }
    }

    public void validateCreateOrderRequest(CreateOrderRequest createOrderRequest) {
        validateId(createOrderRequest.getUserId());
        if (CollectionUtils.isEmpty(createOrderRequest.getProductIds())) {
            throw InvalidInputException.builder()
                    .errorCode("INVALID_INPUT")
                    .errorMessage("ProductIds can't be empty")
                    .build();
        }
    }
}

package com.airtribe.orm.demo.controllers;
import com.airtribe.orm.demo.Exceptions.InvalidInputException;
import com.airtribe.orm.demo.Exceptions.ResourceNotFoundException;
import com.airtribe.orm.demo.constants.Constants;
import com.airtribe.orm.demo.enums.ResponseType;
import com.airtribe.orm.demo.models.requests.products.AddProductRequestDto;
import com.airtribe.orm.demo.models.requests.products.ProductUpdateRequestDto;
import com.airtribe.orm.demo.models.responses.base.GenericResponse;
import com.airtribe.orm.demo.models.responses.products.ProductDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.products.ProductDetailsResponseDto;
import com.airtribe.orm.demo.services.products.ProductService;
import com.airtribe.orm.demo.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/product")
public class ProductController {

    private final ProductService productService;
    private final ValidationUtils validationUtils;

    public ProductController(ProductService productService, ValidationUtils validationUtils) {
        this.productService = productService;
        this.validationUtils = validationUtils;
    }

    @GetMapping(path = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fetch Product Details", description = "Fetch the details of a Product")
    public GenericResponse<ProductDetailsResponseDto> fetchUserDetails(@RequestParam("product-id") String productId) {
        ProductDetailsResponseDto productDetailsResponseDto = null;
        try {
            validationUtils.validateId(productId);
            productDetailsResponseDto = productService.getProductDetails(productId);
        } catch (InvalidInputException e) {
            return GenericResponse.<ProductDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<ProductDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<ProductDetailsResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(productDetailsResponseDto)
                .build();
    }

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "add product", description = "Add a new user")
    public GenericResponse<ProductDetailsResponseDto> createUser(@RequestBody AddProductRequestDto productRequestDto) {
        ProductDetailsResponseDto productDetailsResponseDto = null;
        try {
            validationUtils.validateAddProductRequest(productRequestDto);
            productDetailsResponseDto = productService.createProduct(productRequestDto);

        } catch (InvalidInputException e) {
            return GenericResponse.<ProductDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<ProductDetailsResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(productDetailsResponseDto)
                .build();
    }

    @Operation(summary = "Update Product", description = "Update the details of a Product")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<ProductDetailsResponseDto> updateProduct(@RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        ProductDetailsResponseDto productDetailsResponseDto = null;
        try {
            validationUtils.validateUpdateProductRequest(productUpdateRequestDto);
            productDetailsResponseDto = productService.updateProduct(productUpdateRequestDto);

        } catch (InvalidInputException e) {
            return GenericResponse.<ProductDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<ProductDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<ProductDetailsResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(productDetailsResponseDto)
                .build();
    }

    @Operation(summary = "Delete Product", description = "delete the Product")
    @PutMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<ProductDeleteResponseDto> deleteProduct(@RequestParam("product-id") String productId) {
        ProductDeleteResponseDto productDeleteResponseDto = null;
        try {
            validationUtils.validateId(productId);
            productDeleteResponseDto = productService.deleteProduct(productId);

        } catch (InvalidInputException e) {
            return GenericResponse.<ProductDeleteResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<ProductDeleteResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<ProductDeleteResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(productDeleteResponseDto)
                .build();
    }
}

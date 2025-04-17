package com.airtribe.orm.demo.services.products;


import com.airtribe.orm.demo.models.requests.products.AddProductRequestDto;
import com.airtribe.orm.demo.models.requests.products.ProductUpdateRequestDto;
import com.airtribe.orm.demo.models.responses.products.ProductDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.products.ProductDetailsResponseDto;

public interface ProductService {
    ProductDetailsResponseDto getProductDetails(String productId);
    ProductDetailsResponseDto createProduct(AddProductRequestDto addProductRequestDto);
    ProductDetailsResponseDto updateProduct(ProductUpdateRequestDto productUpdateRequestDto);
    ProductDeleteResponseDto deleteProduct(String productId);
}

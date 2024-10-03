package com.airtribe.orm.demo.services.products;

import com.airtribe.orm.demo.Exceptions.ResourceNotFoundException;
import com.airtribe.orm.demo.daos.ProductDao;
import com.airtribe.orm.demo.entities.mysql.StoredProductEntity;
import com.airtribe.orm.demo.entities.redis.CachedProductEntity;
import com.airtribe.orm.demo.models.requests.products.AddProductRequestDto;
import com.airtribe.orm.demo.models.requests.products.ProductUpdateRequestDto;
import com.airtribe.orm.demo.models.responses.products.ProductDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.products.ProductDetailsResponseDto;
import com.airtribe.orm.demo.utils.ProductMappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final ProductMappers productMapper;

    public ProductServiceImpl(ProductDao productDao, ProductMappers productMapper) {
        this.productDao = productDao;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDetailsResponseDto getProductDetails(String productId) {
        CachedProductEntity cachedProductEntity = productDao.getProductDetails(productId);
        return productMapper.mapToProductDetailsResponseFromCache(cachedProductEntity);
    }

    @Override
    public ProductDetailsResponseDto createProduct(AddProductRequestDto addProductRequestDto) {
        StoredProductEntity storedProductEntity = productMapper.mapToProductEntity(addProductRequestDto);
        StoredProductEntity savedProductEntity = productDao.save(storedProductEntity);
        return productMapper.mapToProductDetailsResponse(savedProductEntity);
    }

    @Override
    public ProductDetailsResponseDto updateProduct(ProductUpdateRequestDto productUpdateRequestDto) {
        StoredProductEntity storedProductEntity = getProductEntity(productUpdateRequestDto.getProductId());
        storedProductEntity.setName(productUpdateRequestDto.getName());
        storedProductEntity.setPrice(productUpdateRequestDto.getPrice());
        storedProductEntity.setUnit(productUpdateRequestDto.getUnit());
        StoredProductEntity updatedProductEntity = productDao.save(storedProductEntity);
        return productMapper.mapToProductDetailsResponse(updatedProductEntity);
    }

    @Override
    public ProductDeleteResponseDto deleteProduct(String productId) {
        StoredProductEntity storedProductEntity = getProductEntity(productId);
        storedProductEntity.setDeleted(true);
        storedProductEntity = productDao.save(storedProductEntity);
        return productMapper.mapToProductDeleteResponseDto(storedProductEntity);
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

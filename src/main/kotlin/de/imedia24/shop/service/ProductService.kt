package de.imedia24.shop.service

import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.AddProductRequest
import de.imedia24.shop.domain.product.PartialUpdateProductRequest
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {
        val productEntity = productRepository.findBySku(sku)
        return productEntity?.toProductResponse()
    }

    fun addProduct(request: AddProductRequest): ProductResponse {
        val productEntity = request.toProductEntity()
        val savedProductEntity = productRepository.save(productEntity)
        return savedProductEntity.toProductResponse()
    }

    fun updateProduct(sku: String, request: PartialUpdateProductRequest): Boolean {
        var existingProductEntity = productRepository.findBySku(sku) ?: return false

        request.name?.let { existingProductEntity.name = it }
        request.description?.let { existingProductEntity.description = it }
        request.price?.let { existingProductEntity.price = it }

        productRepository.save(existingProductEntity)
        return true
    }
}

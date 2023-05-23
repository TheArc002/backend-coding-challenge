package de.imedia24.shop.domain.product

import de.imedia24.shop.db.entity.ProductEntity
import java.math.BigDecimal
import java.time.ZonedDateTime

data class AddProductRequest(
    val sku: String,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stockQuantity: Int,
    val stockStatus: String
){
    companion object {
        fun AddProductRequest.toProductEntity() = ProductEntity(
            sku = sku,
            name = name,
            description = description ?: "",
            price = price,
            stockQuantity = stockQuantity,
            stockStatus = stockStatus,
            createdAt = ZonedDateTime.now(),
            updatedAt = ZonedDateTime.now()
        )
    }
}
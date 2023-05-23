package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.AddProductRequest
import de.imedia24.shop.domain.product.PartialUpdateProductRequest
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import javax.websocket.server.PathParam

@RestController
@Api(tags = ["Product API"])
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductsBySku(@PathVariable("sku") sku: String): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if (product != null) {
            ResponseEntity.ok(product)
        } else {
            ResponseEntity.notFound().build()
        }
    }


    @GetMapping("/products", produces = ["application/json;charset=utf-8"])
    fun findProductsBySkus(@RequestParam("skus") skus: List<String>): ResponseEntity<List<ProductResponse>> {
        logger.info("Request for products: $skus")

        val products = skus.mapNotNull { productService.findProductBySku(it) }
        return if (products.isNotEmpty()) {
            ResponseEntity.ok(products)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/products", consumes = ["application/json"], produces = ["application/json;charset=utf-8"])
    fun addProduct(@RequestBody request: AddProductRequest): ResponseEntity<Unit> {
        val product = productService.addProduct(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PatchMapping("/products/{sku}", consumes = ["application/json"], produces = ["application/json;charset=utf-8"])
    fun updateProduct(@PathVariable("sku") sku: String, @RequestBody request: PartialUpdateProductRequest): ResponseEntity<Unit> {
        val updated = productService.updateProduct(sku, request)
        return if (updated) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

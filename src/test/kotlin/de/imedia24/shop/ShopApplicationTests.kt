package de.imedia24.shop

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

@SpringBootTest
class ShopApplicationTests {

    private val productService: ProductService = mock(ProductService::class.java)
    private val productController = ProductController(productService)

    @Test
    fun `Test getByListSkus endpoint`() {
        val skus = listOf("123", "456", "789")
        val productResponses = listOf(
            ProductResponse("123", "Product 1", "Description 1", BigDecimal.valueOf(9.99)),
            ProductResponse("456", "Product 2", "Description 2", BigDecimal.valueOf(19.99)),
            ProductResponse("789", "Product 3", "Description 3", BigDecimal.valueOf(29.99))
        )

        `when`(productService.findProductsBySkus(anyList())).thenReturn(productResponses)

        val response: ResponseEntity<List<ProductResponse>> = productController.getByListSkus(skus)

        assert(response.statusCode == HttpStatus.OK)
        assert(response.body == productResponses)
    }

    @Test
    fun `Test updateProduct endpoint`() {
        val sku = "123"
        val request = PartialUpdateProductRequest(
            name = "Updated Product Name",
            description = "Updated product description",
            price = BigDecimal.valueOf(19.99)
        )

        `when`(productService.updateProduct(anyString(), any())).thenReturn(true)

        val response: ResponseEntity<Unit> = productController.updateProduct(sku, request)

        assert(response.statusCode == HttpStatus.OK)
    }
}

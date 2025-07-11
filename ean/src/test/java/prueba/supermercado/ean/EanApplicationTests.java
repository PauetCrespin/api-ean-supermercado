package prueba.supermercado.ean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import prueba.supermercado.ean.exceptions.ProductNotFoundException;
import prueba.supermercado.ean.model.ProductDetails;
import prueba.supermercado.ean.repository.ProductDetailsRepository;
import prueba.supermercado.ean.service.ValidationService;
import prueba.supermercado.ean.serviceImpl.ProductDetailsServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Clase de prueba para la aplicación EAN.
 */
@SpringBootTest(classes = EanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EanApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Mock
	private ProductDetailsRepository productDetailsRepository;

	@Mock
	private ValidationService validationService;

	@InjectMocks
	private ProductDetailsServiceImpl productDetailsService;

	private ProductDetails product;

	/**
	 * Configuración inicial antes de cada prueba.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		product = new ProductDetails("1234567890123", "1234567", "89012", "3");
		product.setId(1L);
	}

	/**
	 * Verifica que el contexto de la aplicación se carga correctamente.
	 */
	@Test
	void contextLoads() {
	}

	/**
	 * Prueba para obtener detalles del producto por su ID.
	 */
	@Test
	void testGetProductDetailsById() {
		when(productDetailsRepository.findById(1L)).thenReturn(Optional.of(product));

		Optional<ProductDetails> foundProduct = productDetailsService.getProductDetailsById(1L);

		assertTrue(foundProduct.isPresent());
		assertEquals("1234567890123", foundProduct.get().getEan());
	}

	/**
	 * Prueba para manejar el caso cuando los detalles del producto no se encuentran
	 * por su ID.
	 */
	@Test
	void testGetProductDetailsByIdNotFound() {
		when(productDetailsRepository.findById(1L)).thenReturn(Optional.empty());

		Optional<ProductDetails> foundProduct = productDetailsService.getProductDetailsById(1L);

		assertFalse(foundProduct.isPresent());
	}

	/**
	 * Prueba para crear detalles del producto.
	 */
	@Test
	void testCreateProductDetails() {
		when(productDetailsRepository.save(product)).thenReturn(product);

		ProductDetails createdProduct = productDetailsService.createProductDetails(product);

		assertNotNull(createdProduct);
		assertEquals("1234567890123", createdProduct.getEan());
	}

	/**
	 * Prueba para actualizar detalles del producto.
	 */
	@Test
	void testUpdateProductDetails() {
		ProductDetails updatedProduct = new ProductDetails("9876543210986", "9876543", "21098", "6");
		updatedProduct.setId(1L);

		when(productDetailsRepository.findById(1L)).thenReturn(Optional.of(product));
		when(productDetailsRepository.save(any(ProductDetails.class))).thenReturn(updatedProduct);

		ProductDetails result = productDetailsService.updateProductDetails(1L, updatedProduct);

		assertNotNull(result);
		assertEquals("9876543210986", result.getEan());
	}

	/**
	 * Prueba para manejar el caso cuando los detalles del producto no se encuentran
	 * al intentar actualizar.
	 */
	@Test
	void testUpdateProductDetailsNotFound() {
		ProductDetails updatedProduct = new ProductDetails("9876543210986", "9876543", "21098", "6");
		updatedProduct.setId(1L);

		when(productDetailsRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundException.class,
				() -> productDetailsService.updateProductDetails(1L, updatedProduct));
	}

	/**
	 * Prueba para eliminar detalles del producto.
	 */
	@Test
	void testDeleteProductDetails() {
		when(productDetailsRepository.existsById(1L)).thenReturn(true);

		assertDoesNotThrow(() -> productDetailsService.deleteProductDetails(1L));
	}

	/**
	 * Prueba para manejar el caso cuando los detalles del producto no se encuentran
	 * al intentar eliminar.
	 */
	@Test
	void testDeleteProductDetailsNotFound() {
		when(productDetailsRepository.existsById(1L)).thenReturn(false);

		assertThrows(ProductNotFoundException.class, () -> productDetailsService.deleteProductDetails(1L));
	}

	/**
	 * Prueba de integración para el endpoint de creación de detalles del producto.
	 */
	@Test
	void testCreateProductDetailsIntegration() {
		String url = "http://localhost:" + port + "/api/productdetails/create";

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ProductDetails> entity = new HttpEntity<>(product, headers);

		ResponseEntity<ProductDetails> response = restTemplate.exchange(url, HttpMethod.POST, entity,
				ProductDetails.class);

		assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("1234567890123", response.getBody().getEan());
	}

}

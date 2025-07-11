package prueba.supermercado.ean.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import prueba.supermercado.ean.model.ProductDetails;

import java.util.Optional;

/**
 * Repositorio para acceder a ProductDetails.
 * 
 * @author Pau Crespo
 */
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {
    Optional<ProductDetails> findByEan(String ean);
}
package prueba.supermercado.ean.service;

import java.util.List;
import java.util.Optional;

import prueba.supermercado.ean.model.ProductDetails;

/**
 * Servicio que maneja la lógica de ProductDetails
 * 
 * @author Pau Crespo
 */
public interface ProductDetailsService {

    /**
     * Obtiene todos los ProductDetails.
     * 
     * @return Una lista de ProductDetails.
     */
    List<ProductDetails> getAllProductDetails();

    /**
     * Obtiene los detalles de un producto por su ID.
     *
     * @param id ID del producto.
     * @return Un Optional con los detalles del producto, si existe.
     */
    Optional<ProductDetails> getProductDetailsById(Long id);

    /**
     * Obtiene los detalles de un producto por su EAN.
     *
     * @param ean EAN del producto.
     * @return Un Optional con los detalles del producto, si existe.
     */
    Optional<ProductDetails> getProductDetailsByEan(String ean);

    /**
     * Crea un nuevo ProductDetails.
     *
     * @param productDetails Los detalles del producto a crear.
     * @return Los detalles del producto creado.
     */
    ProductDetails createProductDetails(ProductDetails productDetails);

    /**
     * Crea un nuevo ProductDetails.
     *
     * @param productDetails Los detalles del producto a crear.
     * @return Los detalles del producto creado.
     */
    ProductDetails saveProductDetails(ProductDetails productDetails);

    /**
     * Actualiza los detalles de un producto existente.
     *
     * @param id             ID del producto a actualizar.
     * @param productDetails Los nuevos detalles del producto.
     * @return Los detalles del producto actualizado.
     */
    ProductDetails updateProductDetails(Long id, ProductDetails productDetails);

    /**
     * Elimina un producto por su ID.
     *
     * @param id ID del producto a eliminar.
     */
    void deleteProductDetails(Long id);
}
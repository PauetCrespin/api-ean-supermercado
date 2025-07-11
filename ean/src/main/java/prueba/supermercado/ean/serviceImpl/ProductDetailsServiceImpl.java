package prueba.supermercado.ean.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prueba.supermercado.ean.exceptions.ProductNotFoundException;
import prueba.supermercado.ean.model.ProductDetails;
import prueba.supermercado.ean.repository.ProductDetailsRepository;
import prueba.supermercado.ean.service.ProductDetailsService;
import prueba.supermercado.ean.service.ValidationService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio que maneja la lógica de negocio de los detalles
 * de los productos.
 */
@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private ValidationService validationService;

    /**
     * Obtiene todos los ProductDetails.
     * 
     * @return Una lista de ProductDetails.
     */
    public List<ProductDetails> getAllProductDetails() {
        return productDetailsRepository.findAll();
    }

    /**
     * Obtiene los detalles de un producto por su ID.
     *
     * @param id ID del producto.
     * @return Un Optional con los detalles del producto, si existe.
     */
    public Optional<ProductDetails> getProductDetailsById(Long id) {
        return productDetailsRepository.findById(id);
    }

    /**
     * Obtiene los detalles de un producto por su EAN.
     *
     * @param ean EAN del producto.
     * @return Un Optional con los detalles del producto, si existe.
     */
    public Optional<ProductDetails> getProductDetailsByEan(String ean) {
        return productDetailsRepository.findByEan(ean);
    }

    /**
     * Crea un nuevo ProductDetails.
     *
     * @param productDetails Los detalles del producto a crear.
     * @return Los detalles del producto creado.
     */
    public ProductDetails createProductDetails(ProductDetails productDetails) {
        return saveProductDetails(productDetails);
    }

    /**
     * Crea un nuevo ProductDetails.
     *
     * @param productDetails Los detalles del producto a crear.
     * @return Los detalles del producto creado.
     */
    public ProductDetails saveProductDetails(ProductDetails productDetails) {
        validationService.validateProductDetails(productDetails);
        String ean = productDetails.getProviderCode() + productDetails.getProductCode()
                + productDetails.getDestination();
        productDetails.setEan(ean);
        String destinationName = validationService.getDestinationName(productDetails.getDestination());
        productDetails.setDestination(destinationName);
        return productDetailsRepository.save(productDetails);
    }

    /**
     * Actualiza los detalles de un producto existente.
     *
     * @param id             ID del producto a actualizar.
     * @param productDetails Los nuevos detalles del producto.
     * @return Los detalles del producto actualizado.
     */
    public ProductDetails updateProductDetails(Long id, ProductDetails productDetails) {
        return productDetailsRepository.findById(id)
                .map(existingProductDetails -> {
                    existingProductDetails.setEan(productDetails.getEan());
                    existingProductDetails.setProviderCode(productDetails.getProviderCode());
                    existingProductDetails.setProductCode(productDetails.getProductCode());
                    existingProductDetails.setDestination(productDetails.getDestination());
                    return saveProductDetails(existingProductDetails);
                })
                .orElseThrow(() -> new ProductNotFoundException(
                        "No se ha encontrado los detalles del Producto con la siguiente id: " + id));
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id ID del producto a eliminar.
     */
    public void deleteProductDetails(Long id) {
        if (!productDetailsRepository.existsById(id)) {
            throw new ProductNotFoundException(
                    "No se ha encontrado los detalles del Producto con la siguiente id: " + id);
        }
        productDetailsRepository.deleteById(id);
    }
}

package prueba.supermercado.ean.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prueba.supermercado.ean.exceptions.ProductNotFoundException;
import prueba.supermercado.ean.model.ProductDetails;
import prueba.supermercado.ean.service.ProductDetailsService;

import java.util.List;

/**
 * REST controller para controlar ProductDetails.
 * 
 * @author Pau Crespo
 */
@RestController
@RequestMapping("/api/productdetails")
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productDetailsService;

    /**
     * Obtiene todos los ProductDetails.
     *
     * @return Lista con todos los detalles del producto.
     */
    @GetMapping
    public List<ProductDetails> getAllProductDetails() {
        return productDetailsService.getAllProductDetails();
    }

    /**
     * Consulta un Product Details mediante su ID.
     * <p>
     * Esta llamada devolvera un JSON con la información del objeto filtrado si es
     * correcta.
     * Esta llamada devolvera una excepción cuándo la consulta no sea correcta, como
     * por
     * ejemplo un campo incorrecto.
     *
     * @param id ID del producto.
     * 
     * @return JSON con los datos del objecto filtrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetails> getProductDetailsById(@PathVariable Long id) {
        return productDetailsService.getProductDetailsById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Consulta un Product Details mediante su EAN.
     * <p>
     * Esta llamada devolvera un JSON con la información del objeto filtrado si es
     * correcta.
     * Esta llamada devolvera una excepción cuándo la consulta no sea correcta, como
     * por
     * ejemplo un campo incorrecto.
     *
     * @return JSON con los datos del objecto filtrado
     */
    @GetMapping("/ean/{ean}")
    public ResponseEntity<ProductDetails> getProductDetailsByEan(@PathVariable String ean) {
        return productDetailsService.getProductDetailsByEan(ean)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creación del objeto Product Details.
     * <p>
     * Esta llamada devolvera un JSON con la información del objeto creado si es
     * correcta.
     * Esta llamada devolvera una excepción cuándo la creación no sea correcta, como
     * por
     * ejemplo un campo incorrecto.
     *
     * @return JSON con los datos del objecto creado
     */
    @PostMapping("/create")
    public ResponseEntity<ProductDetails> createProductDetails(@RequestBody @Validated ProductDetails productDetails) {
        ProductDetails savedProductDetails = productDetailsService.createProductDetails(productDetails);
        return new ResponseEntity<>(savedProductDetails, HttpStatus.CREATED);
    }

    /**
     * Actualiza el objeto Product Details filtrado por el ID
     * <p>
     * Esta llamada devolvera un JSON con la información del objeto actualizado si
     * es
     * correcta.
     * Esta llamada devolvera una excepción cuándo algún dato para la actualización
     * no sea correcta, por
     * ejemplo un campo incorrecto.
     *
     * @param id el ID del objeto que queremos actualizar
     * @return JSON con los datos del objecto actualizado
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDetails> updateProductDetails(@PathVariable Long id,
            @RequestBody @Validated ProductDetails productDetails) {

        return new ResponseEntity<>(productDetailsService.updateProductDetails(id, productDetails), HttpStatus.OK);

    }

    /**
     * Elimina el objeto Product Details filtrado por el ID
     * <p>
     * Esta llamada devolvera un mensaje dependiendo si la llamada ha sido correcta
     * o no.
     * Esta llamada devolvera una excepción cuándo algún dato para la elimiación
     * no sea correcta, por
     * ejemplo un campo incorrecto.
     *
     * @param id el ID del objeto que queremos actualizar
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductDetails(@PathVariable Long id) {
        try {
            productDetailsService.deleteProductDetails(id);
            return ResponseEntity.ok("El Product Details se ha eliminado correctamente");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

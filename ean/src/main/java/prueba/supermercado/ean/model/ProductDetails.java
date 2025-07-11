package prueba.supermercado.ean.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "PRODUCT_DETAILS")
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ean;

    @NotBlank
    private String providerCode;

    @NotBlank
    private String productCode;

    @NotBlank
    @Size(min = 1, max = 45)
    private String destination;

    /**
     * Constructor vacío requerido por JPA.
     */
    public ProductDetails() {
    }

    /**
     * Constructor con todos los parámetros.
     *
     * @param id           Código ID del producto.
     * @param ean          Código EAN del producto.
     * @param providerCode Código del proveedor del producto.
     * @param productCode  Código del producto.
     * @param destination  Destino del producto.
     */
    public ProductDetails(String ean, String providerCode, String productCode, String destination) {
        this.ean = ean;
        this.providerCode = providerCode;
        this.productCode = productCode;
        this.destination = destination;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}

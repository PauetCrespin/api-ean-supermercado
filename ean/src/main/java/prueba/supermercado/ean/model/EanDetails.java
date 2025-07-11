package prueba.supermercado.ean.model;

public class EanDetails {
    private String providerCode;
    private String productCode;
    private String destination;

    // Constructor sin argumentos
    public EanDetails() {
    }

    // Constructor con argumentos
    public EanDetails(String providerCode, String productCode, String destination) {
        this.providerCode = providerCode;
        this.productCode = productCode;
        this.destination = destination;
    }

    // Getters and Setters

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
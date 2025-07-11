package prueba.supermercado.ean.service;

import org.springframework.stereotype.Service;

import prueba.supermercado.ean.model.ProductDetails;

@Service
public class ValidationService {
    public void validateProductDetails(ProductDetails productDetails) {
        validateProviderCode(productDetails.getProviderCode());
        validateProductCode(productDetails.getProductCode());
        validateDestination(productDetails.getDestination());
    }

    private void validateProviderCode(String providerCode) {
        if (providerCode == null || !providerCode.matches("\\d{7}")) {
            throw new IllegalArgumentException("El código del proveedor debe ser numérico y tener 7 dígitos.");
        }
    }

    private void validateProductCode(String productCode) {
        if (productCode == null || !productCode.matches("\\d{5}")) {
            throw new IllegalArgumentException("El código del producto debe ser numérico y tener 5 dígitos.");
        }
    }

    private void validateDestination(String destination) {
        if (destination == null || !destination.matches("\\d")) {
            throw new IllegalArgumentException("El destino debe ser un solo dígito numérico.");
        }
    }

    public String getDestinationName(String destination) {
        switch (destination) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
                return "Tiendas supermercado España";
            case "6":
                return "Tiendas supermercado Portugal";
            case "8":
                return "Almacenes";
            case "9":
                return "Oficinas supermercado";
            case "0":
                return "Colmenas";
            default:
                throw new IllegalArgumentException("Dígito de destino inválido.");
        }
    }
}

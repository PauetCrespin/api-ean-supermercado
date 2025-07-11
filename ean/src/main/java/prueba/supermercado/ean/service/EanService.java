package prueba.supermercado.ean.service;

import org.springframework.stereotype.Service;

import prueba.supermercado.ean.exceptions.InvalidEanException;
import prueba.supermercado.ean.model.EanDetails;

/**
 * Clase del Servicio para EAN.
 * 
 * @author Pau Crespo
 */
@Service
public class EanService {

    public EanDetails getEanDetails(String ean) {

        if (!ean.matches("\\d{13}")) {
            throw new InvalidEanException("Formato inválido del EAN");
        }

        String provider = ean.substring(0, 7);
        String productCode = ean.substring(7, 12);
        char destinationCode = ean.charAt(12);

        String destination;
        switch (destinationCode) {
            case '0':
                destination = "Colmenas";
                break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
                destination = "Supermercado España";
                break;
            case '6':
                destination = "Supermercado Portugal";
                break;
            case '8':
                destination = "Almacenes";
                break;
            case '9':
                destination = "Oficinas";
                break;
            default:
                throw new InvalidEanException("Dígito de destino inválido.");

        }

        return new EanDetails(provider, productCode, destination);

    }
}
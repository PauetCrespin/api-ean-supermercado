package prueba.supermercado.ean.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prueba.supermercado.ean.model.EanDetails;
import prueba.supermercado.ean.service.EanService;

/**
 * REST controller para controlar el api de EAN.
 * 
 * @author Pau Crespo
 */
@RestController
@RequestMapping("/api/ean")
public class EanController {

    @Autowired
    private EanService eanService;

    @GetMapping("/{ean}")
    public EanDetails getEanDetails(@PathVariable String ean) {
        return eanService.getEanDetails(ean);
    }
}

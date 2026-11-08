
package be.iccbxl.pid.reservations_springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import be.iccbxl.pid.reservations_springboot.model.Representation;
import be.iccbxl.pid.reservations_springboot.model.RepresentationService;

@Controller
public class RepresentationController {

    @Autowired
    private RepresentationService service;

    @GetMapping("/representations")
    public String index(Model model) {

        List<Representation> representations = service.getAll();

        model.addAttribute("representations", representations);
        model.addAttribute("title", "Liste des représentations");

        return "representation/index";
    }

    @GetMapping("/representations/{id}")
    public String show(Model model, @PathVariable("id") String id) {

        Representation representation = service.get(id);

        if (representation == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Représentation introuvable"
            );
        }

        model.addAttribute("representation", representation);

        if (representation.getWhen() != null) {
            model.addAttribute("date",
                representation.getWhen().toLocalDate());

            model.addAttribute("heure",
                representation.getWhen().toLocalTime());
        }

        model.addAttribute("title", "Fiche d'une représentation");

        return "representation/show";
    }
}

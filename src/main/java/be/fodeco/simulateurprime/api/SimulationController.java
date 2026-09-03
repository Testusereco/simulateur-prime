package be.fodeco.simulateurprime.api;

import be.fodeco.simulateurprime.AccesSimulateur;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SimulationController {

    private final AccesSimulateur accesSimulateur = new AccesSimulateur();

    @PostMapping("/acces")
public AccesResponse acces(@RequestBody AccesRequest request) {
    boolean autorise = accesSimulateur.estEligible(request.age());
    String message = autorise ? null : "Citoyen inéligible : âge hors limites";
    return new AccesResponse(autorise, message);
}
}
package be.fodeco.simulateurprime.api;

import be.fodeco.simulateurprime.AccesSimulateur;
import be.fodeco.simulateurprime.SimulationOrchestrator;
import be.fodeco.simulateurprime.SimulationResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SimulationController {

    private final AccesSimulateur accesSimulateur = new AccesSimulateur();
    private final SimulationOrchestrator orchestrator = new SimulationOrchestrator();

    @PostMapping("/acces")
public AccesResponse acces(@RequestBody AccesRequest request) {
    boolean autorise = accesSimulateur.estEligible(request.age());
    String message = autorise ? null : "Citoyen inéligible : âge hors limites";
    return new AccesResponse(autorise, message);
}
@PostMapping("/simulation")
public SimulationResult simulation(@RequestBody SimulationRequest request) {
    return orchestrator.orchestrer(
        request.age(), request.a(), request.b(), request.c(),
        request.d(), request.e(), request.f(), request.g(), request.h()
    );
}
}
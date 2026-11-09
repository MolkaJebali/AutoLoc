package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.iEmployeService;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Employés", description = "API de gestion des employés (CRUD + affectation / mutation agence)")
public class EmployeRestController {

    private final iEmployeService employeService;

    @Operation(summary = "Récupérer tous les employés", description = "Retourne la liste complète des employés enregistrés.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des employés récupérée avec succès")
    })
    @GetMapping({"", "/retrieve-all-employes"})
    public ResponseEntity<List<Employe>> getAllEmployes() {
        return ResponseEntity.ok(employeService.retrieveAllEmployes());
    }

    @Operation(summary = "Récupérer un employé par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employé trouvé"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable")
    })
    @GetMapping({"/{id}", "/retrieve-employe/{id}"})
    public ResponseEntity<Employe> getEmployeById(
            @Parameter(description = "Identifiant unique de l'employé", required = true, example = "1")
            @PathVariable("id") Long id) {
        Employe e = employeService.retrieveEmploye(id);
        return e == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(e);
    }

    @Operation(summary = "Ajouter un nouvel employé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employé créé avec succès")
    })
    @PostMapping({"", "/add-employe"})
    public ResponseEntity<Employe> addEmploye(@RequestBody Employe employe) {
        return new ResponseEntity<>(employeService.addEmploye(employe), HttpStatus.CREATED);
    }

    @Operation(summary = "Ajouter une liste d'employés en lot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employés créés avec succès")
    })
    @PostMapping({"/batch", "/add-employes"})
    public ResponseEntity<List<Employe>> addEmployes(@RequestBody List<Employe> employes) {
        return new ResponseEntity<>(employeService.addEmployes(employes), HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour un employé existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employé mis à jour avec succès")
    })
    @PutMapping({"", "/update-employe"})
    public ResponseEntity<Employe> updateEmploye(@RequestBody Employe employe) {
        return ResponseEntity.ok(employeService.updateEmploye(employe));
    }

    @Operation(summary = "Mettre à jour un employé par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employé mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Employe> updateEmployeById(
            @Parameter(description = "Identifiant de l'employé à mettre à jour", required = true, example = "1")
            @PathVariable("id") Long id,
            @RequestBody Employe employe) {
        employe.setIdEmploye(id);
        return ResponseEntity.ok(employeService.updateEmploye(employe));
    }

    @Operation(summary = "Supprimer un employé par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employé supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable")
    })
    @DeleteMapping({"/{id}", "/remove-employe/{id}"})
    public ResponseEntity<Void> deleteEmploye(
            @Parameter(description = "Identifiant unique de l'employé à supprimer", required = true, example = "1")
            @PathVariable("id") Long id) {
        if (employeService.retrieveEmploye(id) == null) {
            return ResponseEntity.notFound().build();
        }
        employeService.removeEmploye(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario RH : mutation d'un agent vers une autre agence (@ManyToOne)
    // ─────────────────────────────────────────────────────────────────────────

    @Operation(
        summary = "Désaffecter un employé de son agence",
        description = "Scénario RH – étape 1 : retire le lien agence de l'employé " +
                      "(met id_agence à NULL). Un employé ne peut appartenir qu'à une " +
                      "seule agence à la fois ; toute mutation passe d'abord par cette " +
                      "désaffectation."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employé désaffecté avec succès"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable")
    })
    @PutMapping("/{idEmploye}/desaffecter")
    public ResponseEntity<Employe> desaffecterEmploye(
            @Parameter(description = "ID de l'employé à désaffecter", required = true, example = "1")
            @PathVariable Long idEmploye) {
        return ResponseEntity.ok(employeService.desaffecterEmploye(idEmploye));
    }

    @Operation(
        summary = "Affecter un employé à une agence",
        description = "Scénario RH – étape 2 : rattache l'employé à l'agence cible. " +
                      "À appeler après desaffecter (ou lorsque l'employé n'a pas encore " +
                      "d'agence)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employé affecté avec succès"),
            @ApiResponse(responseCode = "404", description = "Employé ou agence introuvable")
    })
    @PutMapping("/{idEmploye}/affecter/{idAgence}")
    public ResponseEntity<Employe> affecterEmploye(
            @Parameter(description = "ID de l'employé à affecter", required = true, example = "1")
            @PathVariable Long idEmploye,
            @Parameter(description = "ID de l'agence cible", required = true, example = "2")
            @PathVariable Long idAgence) {
        return ResponseEntity.ok(employeService.affecterEmploye(idEmploye, idAgence));
    }
}


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
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.iVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Véhicules", description = "API de gestion des véhicules (opérations CRUD)")
public class VehiculeRestController {

    private final iVehiculeService vehiculeService;

    @Operation(summary = "Récupérer tous les véhicules", description = "Retourne la liste complète de tous les véhicules enregistrés dans le système.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des véhicules récupérée avec succès")
    })
    @GetMapping({"", "/retrieve-all-vehicules"})
    public ResponseEntity<List<Vehicule>> getAllVehicules() {
        List<Vehicule> vehicules = vehiculeService.retrieveAllVehicules();
        return ResponseEntity.ok(vehicules);
    }

    @Operation(summary = "Récupérer un véhicule par son ID", description = "Retourne les détails d'un véhicule à partir de son identifiant unique.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Véhicule trouvé avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucun véhicule trouvé avec l'identifiant fourni")
    })
    @GetMapping({"/{id}", "/retrieve-vehicule/{id}"})
    public ResponseEntity<Vehicule> getVehiculeById(
            @Parameter(description = "Identifiant unique du véhicule", required = true, example = "1")
            @PathVariable("id") Long id) {
        Vehicule v = vehiculeService.retrieveVehicule(id);
        if (v == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(v);
    }

    @Operation(summary = "Ajouter un nouveau véhicule", description = "Crée et enregistre un nouveau véhicule dans le catalogue.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Véhicule créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données du véhicule invalides")
    })
    @PostMapping({"", "/add-vehicule"})
    public ResponseEntity<Vehicule> addVehicule(@RequestBody Vehicule vehicule) {
        Vehicule saved = vehiculeService.addVehicule(vehicule);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Ajouter une liste de véhicules", description = "Enregistre un ensemble de véhicules en une seule requête (création en lot).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Véhicules créés avec succès")
    })
    @PostMapping({"/batch", "/add-vehicules"})
    public ResponseEntity<List<Vehicule>> addVehicules(@RequestBody List<Vehicule> vehicules) {
        List<Vehicule> savedList = vehiculeService.addVehicules(vehicules);
        return new ResponseEntity<>(savedList, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour un véhicule existant", description = "Modifie les caractéristiques d'un véhicule existant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Véhicule mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Véhicule non trouvé pour la mise à jour")
    })
    @PutMapping({"", "/update-vehicule"})
    public ResponseEntity<Vehicule> updateVehicule(@RequestBody Vehicule vehicule) {
        Vehicule updated = vehiculeService.updateVehicule(vehicule);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Mettre à jour un véhicule par son ID", description = "Modifie les caractéristiques d'un véhicule identifié par son ID dans l'URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Véhicule mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Véhicule non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> updateVehiculeById(
            @Parameter(description = "Identifiant du véhicule à mettre à jour", required = true, example = "1")
            @PathVariable("id") Long id,
            @RequestBody Vehicule vehicule) {
        vehicule.setIdVehicule(id);
        Vehicule updated = vehiculeService.updateVehicule(vehicule);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Supprimer un véhicule par ID", description = "Supprime définitivement un véhicule à partir de son identifiant unique.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Véhicule supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Véhicule non trouvé")
    })
    @DeleteMapping({"/{id}", "/remove-vehicule/{id}"})
    public ResponseEntity<Void> deleteVehicule(
            @Parameter(description = "Identifiant unique du véhicule à supprimer", required = true, example = "1")
            @PathVariable("id") Long id) {
        Vehicule v = vehiculeService.retrieveVehicule(id);
        if (v == null) {
            return ResponseEntity.notFound().build();
        }
        vehiculeService.removeVehicule(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario : rattachement d'un véhicule à une agence (@ManyToOne)
    // ─────────────────────────────────────────────────────────────────────────

    @Operation(
        summary = "Affecter un véhicule à une agence",
        description = "Scénario : un véhicule neuf arrive et est rattaché à l'agence " +
                      "désignée. Met à jour la clé étrangère id_agence dans la table véhicule."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Véhicule affecté avec succès"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")
    })
    @PutMapping("/{idVehicule}/affecter/{idAgence}")
    public ResponseEntity<Vehicule> affecterVehicule(
            @Parameter(description = "ID du véhicule à rattacher", required = true, example = "1")
            @PathVariable Long idVehicule,
            @Parameter(description = "ID de l'agence cible", required = true, example = "2")
            @PathVariable Long idAgence) {
        return ResponseEntity.ok(vehiculeService.affecterVehicule(idVehicule, idAgence));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scénario siège bébé : gestion des équipements optionnels (@ManyToMany)
    // ─────────────────────────────────────────────────────────────────────────

    @Operation(
        summary = "Affecter un équipement à un véhicule",
        description = "Scénario siège bébé : l'agent installe l'équipement sur le " +
                      "véhicule avant le départ du client. Insère une ligne dans la " +
                      "table de jointure vehicule_equipement."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Équipement ajouté au véhicule avec succès"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")
    })
    @PutMapping("/{idVehicule}/affecter-equipement/{idEquipement}")
    public ResponseEntity<Vehicule> affecterEquipement(
            @Parameter(description = "ID du véhicule", required = true, example = "1")
            @PathVariable Long idVehicule,
            @Parameter(description = "ID de l'équipement à installer", required = true, example = "1")
            @PathVariable Long idEquipement) {
        return ResponseEntity.ok(vehiculeService.affecterEquipement(idVehicule, idEquipement));
    }

    @Operation(
        summary = "Désaffecter un équipement d'un véhicule",
        description = "Scénario siège bébé : l'agent retire l'équipement au retour du " +
                      "client. Supprime la ligne correspondante dans vehicule_equipement."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Équipement retiré du véhicule avec succès"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")
    })
    @DeleteMapping("/{idVehicule}/desaffecter-equipement/{idEquipement}")
    public ResponseEntity<Vehicule> desaffecterEquipement(
            @Parameter(description = "ID du véhicule", required = true, example = "1")
            @PathVariable Long idVehicule,
            @Parameter(description = "ID de l'équipement à retirer", required = true, example = "1")
            @PathVariable Long idEquipement) {
        return ResponseEntity.ok(vehiculeService.desaffecterEquipement(idVehicule, idEquipement));
    }
}

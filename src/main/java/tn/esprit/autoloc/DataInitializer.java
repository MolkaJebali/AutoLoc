package tn.esprit.autoloc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final VehiculeRepository vehiculeRepository;

    public DataInitializer(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public void run(String... args) {
        if (vehiculeRepository.count() == 0) {
            log.info("Insertion des véhicules de démonstration au démarrage...");

            Vehicule v1 = new Vehicule(
                    null,
                    "234-TN-5678",
                    "Renault",
                    "Clio 5",
                    CategorieVehicule.CITADINE,
                    new BigDecimal("90.00"),
                    StatutVehicule.DISPONIBLE
            );

            Vehicule v2 = new Vehicule(
                    null,
                    "235-TN-1234",
                    "Peugeot",
                    "3008",
                    CategorieVehicule.SUV,
                    new BigDecimal("160.00"),
                    StatutVehicule.DISPONIBLE
            );

            Vehicule v3 = new Vehicule(
                    null,
                    "236-TN-9876",
                    "Volkswagen",
                    "Passat",
                    CategorieVehicule.BERLINE,
                    new BigDecimal("190.00"),
                    StatutVehicule.MAINTENANCE
            );

            vehiculeRepository.saveAll(List.of(v1, v2, v3));
            log.info("3 véhicules de démonstration insérés avec succès !");
        } else {
            log.info("Base déjà initialisée : {} véhicule(s) existant(s).", vehiculeRepository.count());
        }
    }
}

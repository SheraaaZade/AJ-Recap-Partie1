package domaine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Camion {
    private String immatriculation;
    private Gabarit gabarit;
    private SortedMap<LocalDate, Trajet> trajets = new TreeMap<>();

    public enum Gabarit {
        DEUX_ESSIEUX(16400, 20), TROIS_ESSIEUX(22000, 30),
        QUATRE_ESSIEUX(28600, 40);
        private int chargeMaximale;
        private int nbMaxCaisses;

        Gabarit(int chargeMaximale, int nbMaxCaisses) {
            this.chargeMaximale = chargeMaximale;
            this.nbMaxCaisses = nbMaxCaisses;
        }
    }

    public Camion(String immatriculation, Gabarit gabarit) {
        this.immatriculation = immatriculation;
        this.gabarit = gabarit;
    }

    /**
     * ajoute un trajet pour le camion
     *
     * @param trajet le trajet à ajouter
     * @return false
     * la date du jour n'est pas antérieure à la date du trajet
     * - s'il y a déjà un trajet prévu ce jour-là pour le camion
     * - s'il y a déjà un trajet prévu la veille et que la ville d'arrivée de ce trajet ne correspond
     * pas à la ville de départ du trajet à ajouter
     * - s'il y a déjà un trajet prévu le lendemain et que la ville de départ de ce trajet ne correspond
     * pas à la ville d'arrivée du trajet à ajouter
     * - s'il y a trop de palettes à transporter par rapport à la capacité du camion
     * - si le poids total à transporter est supérieur à la charge maximale du camion
     */
    public boolean ajouterTrajet(Trajet trajet) {
        LocalDate dateActuelle = LocalDate.now();
        if (!dateActuelle.isBefore(trajet.getDate())) return false;
        if (this.gabarit.chargeMaximale < trajet.calculerPoidsTotal()) return false;
        if (this.gabarit.nbMaxCaisses < trajet.nbCaisses()) return false;

        if (trajet.getDate().isBefore(dateActuelle)) return false;

        if (trajet.getDate().plusDays(1).equals(trajet.getDate())
                && !trajet.getVilleArrivee().equals(trajet.getVilleDepart()))
            return false;
        if (trajet.getDate().minusDays(1).equals(trajet.getDate())
                && !trajet.getVilleDepart().equals(trajet.getVilleArrivee()))
            return false;
        trajets.put(dateActuelle, trajet);
        return true;
    }

    /**
     * renvoie, s'il existe, le trajet prévu à la date passée en paramètre
     *
     * @param dateTrajet la date du trajet recherché
     * @return le trajet prévu à cette date s'il existe et null sinon
     */
    public Trajet trouverTrajet(LocalDate dateTrajet) {
        if (trajets.containsKey(dateTrajet))
            return trajets.get(dateTrajet);
        return null;
    }


    /**
     * recherche le premier trajet, dont la date est ultérieure à la date du jour, auquel la caisse peut être
     * ajoutée et, s'il en a un, lui ajoute la caisse.
     *
     * @param caisse caisse à ajouter
     * @return false s'il n'y a pas de trajet auquel la caisse peut être ajoutée
     * @date date du trajet recherché pour ajouter la caisse
     */
    public boolean ajouterCaisse(Caisse caisse) {
        Trajet trajet = rechercherTrajet(caisse);
        if (trajet == null) return false;
        return trajet.ajouter(caisse);
    }

    /**
     * renvoie, s'il existe, le premier trajet, dont la date est ultérieure à la date du jour, auquella caisse
     * peut lui être ajoutée
     *
     * @param caisse
     * @return null s'il n'y a pas de trajet auquel la caisse peut être ajoutée
     */
    private Trajet rechercherTrajet(Caisse caisse) {
        Trajet trajet = null;

        for (Trajet trajetPrevu : trajets.values()) {
            if (trajetPrevu.peutAjouter(caisse)
                    && trajetPrevu.nbCaisses() < gabarit.nbMaxCaisses
                    && trajetPrevu.calculerPoidsTotal() + caisse.getPoids() <= gabarit.chargeMaximale) {
                if (trajet == null || trajetPrevu.getDate().isBefore(trajet.getDate())) {
                    trajet = trajetPrevu;
                }
            }
        }
        return trajet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camion camion = (Camion) o;
        return Objects.equals(immatriculation, camion.immatriculation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(immatriculation);
    }

}

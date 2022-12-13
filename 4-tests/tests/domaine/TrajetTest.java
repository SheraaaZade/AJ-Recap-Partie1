package domaine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TrajetTest {
    @Test
    @DisplayName("Throw IAE if parameter is null")
    public void peutAjouter1(){
        Trajet trajet = new Trajet("1654", LocalDate.MAX,"Bruxelles", "Sydney");
        assertThrows(IllegalArgumentException.class, () ->trajet.peutAjouter(null));
    }

    @Test
    @DisplayName("return false if the destination of the car isn't the same of the traject")
    public void peutAjouter2(){
        Trajet trajet = new Trajet("1654", LocalDate.MAX,"Bruxelles", "Sydney");
        Caisse caisse = new Caisse("123",LocalDate.MAX,"Paris", "Meknes",2000.3);
        assertFalse(trajet.peutAjouter(caisse));
    }

    @Test
    @DisplayName("return true if the car is added")
    public void peutAjouter3(){
        Trajet trajet = new Trajet("1654", LocalDate.MAX,"Bruxelles", "Sydney");
        Caisse caisse = new Caisse("123",LocalDate.MAX,"Paris", "Meknes",2000.3);
        assertFalse(trajet.peutAjouter(caisse));
    }

}

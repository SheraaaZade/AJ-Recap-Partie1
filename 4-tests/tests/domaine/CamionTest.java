package domaine;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import java.time.LocalDate;
import static org.junit.Assert.assertFalse;

public class CamionTest {
    @Test
    @DisplayName("test of that doesn't add a new trajet if contains a lot of caisses than the capacity of the camion ")
    public void ajouterTrajet(){
        Trajet trajet = Mockito.mock(Trajet.class);
        Mockito.when(trajet.calculerPoidsTotal()).thenReturn(6000.1);
        Mockito.when(trajet.getDate()).thenReturn(LocalDate.now().plusDays(5));
        Mockito.when(trajet.nbCaisses()).thenReturn(500);

        Camion camion = new Camion("KVH-144",501,6000);
        assertFalse(camion.ajouterTrajet(trajet));
    }
}

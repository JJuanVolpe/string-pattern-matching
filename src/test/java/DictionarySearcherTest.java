import org.junit.jupiter.api.Test;
import org.jvolpe.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class DictionarySearcherTest {
    /*
    * Obtengo las cadenas similares de un conjunto de palabras empleando una ventana de criterio de comparación (Tamaño de caracteres a comparar con mínimo 1)
    * */
    private Set<String> getSimilares(String word, int windowSize, List<String> words) {
        Map<String, List<String>> dict = Qgrama.buildDictionary(words, windowSize);
        Qgrama<String> qg = new Qgrama<>(word, windowSize);

        Set<String> similares = new HashSet<>();
        for (String q : qg) {
            similares.addAll(Qgrama.search(dict, q));
        }
        return similares;
    }

    @Test
    void testSimilarityWithWindow2() {
        List<String> words = Arrays.asList("Glucosa", "Gluten", "Glicerol", "Casa");
        Set<String> similares = getSimilares("Glucosa", 2, words);

        // Con ventana=2, deben aparecer estas coincidencias
        assertTrue(similares.contains("Gluten"), "Gluten debería ser similar con window=2");
        assertTrue(similares.contains("Glicerol"), "Glicerol debería ser similar con window=2");
        assertTrue(similares.contains("Casa"), "Casa debería ser similar con window=2");
        assertFalse(similares.contains("Murcielago"), "Murcielago no debería estar");
    }

    @Test
    void testSimilarityWithWindow3() {
        List<String> words = Arrays.asList("Glucosa", "Gluten", "Glicerol", "Casa");
        Set<String> similares = getSimilares("Glucosa", 3, words);
        System.out.println(similares);
        // Con ventana=3, no deben aparecer coincidencias con Glucosa
        assertTrue(similares.contains("Gluten"), "Gluten no debería ser similar con window=3");
        assertFalse(similares.contains("Glicerol"), "Glicerol no debería ser similar con window=3");
        assertFalse(similares.contains("Casa"), "Casa no debería ser similar con window=3");
        assertFalse(similares.contains("Murcielago"), "Murcielago no debería estar");
    }
}

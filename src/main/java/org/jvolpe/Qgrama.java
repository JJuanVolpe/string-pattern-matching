package org.jvolpe;

import java.util.*;

public class Qgrama<T> implements Iterable<T> {

    private final String all_word;
    private final int window;

    public Qgrama(String word, int window_size) {
        if (window_size <= 0) {
            throw new IllegalArgumentException("El tamaño de ventana debe ser mayor a 0");
        }
        // agregamos símbolos de inicio y fin
        this.all_word = "#" + word + "$";
        this.window = window_size;
    }

    // Clase anidada que itera sobre los Q-gramas
    private class Part implements Iterator<String> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex + window <= all_word.length();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No hay más Q-gramas");
            }
            String qgram = all_word.substring(currentIndex, currentIndex + window);
            currentIndex++;
            return qgram;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>) new Part();
    }

    @Override
    public String toString() {
        return all_word;
    }

    // Construcción de diccionario generalizado para Q ≥ 1
    public static Map<String, List<String>> buildDictionary(List<String> words, int qSize) {
        if (qSize <= 0) {
            throw new IllegalArgumentException("Q debe ser mayor a 0");
        }
        Map<String, List<String>> dictionary = new HashMap<>();

        for (String word : words) {
            Qgrama<String> qg = new Qgrama<>(word, qSize);
            for (String qgram : qg) {
                // ignorar q-gramas que contengan '#' o '$'
                if (!qgram.contains("#") && !qgram.contains("$")) {
                    dictionary.computeIfAbsent(qgram, k -> new ArrayList<>()).add(word);
                }
            }
        }
        return dictionary;
    }

    // Búsqueda de palabras que contengan un Q-grama dado
    public static List<String> search(Map<String, List<String>> dictionary, String qgram) {
        // ignorar q-gramas que contengan '#' o '$'
        if (qgram.contains("#") || qgram.contains("$")) {
            return Collections.emptyList();
        }
        return dictionary.getOrDefault(qgram, Collections.emptyList());
    }
}

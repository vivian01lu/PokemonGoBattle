package com.impact.pokemon;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Class responsible for loading Pokémon data from a CSV file and providing access to the Pokémon objects.
 */
@Component
public class PokemonData {

    private List<Pokemon> pokemonList; // List to hold all Pokémon objects

    /**
     * Constructor that initializes the PokemonData instance by loading and parsing the CSV file.
     *
     * @throws IOException if the CSV file cannot be found or read.
     */
    public PokemonData() throws IOException {
        // File object representing the CSV file
        File file = new ClassPathResource("data/pokemon.csv").getFile();
        parsePokemonData(file);
        verifyParsedData(); // Verify if parsing was successful
    }

    /**
     * Retrieves the list of all Pokémon.
     *
     * @return List of Pokémon objects.
     */
    public List<Pokemon> getAllPokemon() {
        return pokemonList;
    }

    /**
     * Finds a Pokémon by its name.
     *
     * @param name The name of the Pokémon to search for.
     * @return The corresponding Pokémon object, or null if not found.
     */
    public Pokemon findByName(String name) {
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().equalsIgnoreCase(name)) {
                return pokemon; // Return the Pokémon if the name matches
            }
        }
        return null; // Return null if no match is found
    }

    /**
     * Verifies that the Pokémon data was parsed correctly.
     */
    private void verifyParsedData() {
        if (pokemonList == null || pokemonList.isEmpty()) {
            System.err.println("No data was parsed. Please check the CSV file.");
        } else {
            System.out.println("Successfully parsed " + pokemonList.size() + " Pokémon.");
            // Print a few sample records to verify
            for (int i = 0; i < Math.min(5, pokemonList.size()); i++) {
                System.out.println(pokemonList.get(i));
            }
        }
    }

    /**
     * Parses Pokémon data from the specified CSV file.
     *
     * @param file The CSV file to parse.
     */
    private void parsePokemonData(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            // Create CsvToBeanBuilder passing the file reader as a parameter
            CsvToBean<Pokemon> csvToBean = new CsvToBeanBuilder<Pokemon>(fileReader)
                    .withType(Pokemon.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            pokemonList = csvToBean.parse(); // Parse the CSV data
        } catch (IOException e) {
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("An error occurred while processing CSV data: " + e.getMessage());
        }
    }
}

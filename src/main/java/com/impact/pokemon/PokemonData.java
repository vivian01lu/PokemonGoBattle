package com.impact.pokemon;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * !! Feel free to change everything about this !!
 * This could be a class to hold all the Pokemon objects loaded from CSV,
 * but there are many ways to do it.
 */
@Component
public class PokemonData {

    private  List<Pokemon> pokemonList;
    private final File file;

    public PokemonData() throws IOException {
        file = new ClassPathResource("data/pokemon.csv").getFile();
        parsePokemonData(file);
        // Verify if parsing was successful
        verifyParsedData();
    }
    public List<Pokemon> getAllPokemon() {
        return pokemonList;
    }
    public Pokemon findByName(String name) {
        //based on its name return this Pokemon object
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().equals(name)){
                return pokemon;
            }
        }
        return null;
    }

    private void verifyParsedData() {
        if (pokemonList == null || pokemonList.isEmpty()) {
            System.err.println("No data was parsed. Please check the CSV file.");
        } else {
            System.out.println("Successfully parsed " + pokemonList.size() + " Pokémon.");
            // Print a few sample records to verify
            for (int i = 0; i < Math.min(5, pokemonList.size()); i++) {
                System.out.println(pokemonList.get(i).toString());
            }
        }
    }
    private void parsePokemonData(File file) throws IOException{

        try{
            // create CsvToBeanBuilder passing - file reader as a parameter
            CsvToBean<Pokemon> csvToBean = new CsvToBeanBuilder<Pokemon>(new FileReader(file))
                    .withType(Pokemon.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            pokemonList = csvToBean.parse();

        } catch (FileNotFoundException e) {
            System.err.println("The specified file was not found: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("An error occurred while processing CSV data: " + e.getMessage());
        }

    }




}


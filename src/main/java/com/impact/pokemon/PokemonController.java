package com.impact.pokemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@RestController
public class PokemonController {

    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    @Resource
    private PokemonData data;

    @GetMapping("/all-pokemons")
    public List<Pokemon> getAllPokemons() {
        logger.info("Fetching all Pokémon data.");
        return data.getAllPokemon();
    }

    @GetMapping("attack")
    public Map<String, Object> attack(@RequestParam String pokemonAName, @RequestParam String pokemonBName) throws IOException {
        logger.info("Requested pokemonA: {}, pokemonB: {}", pokemonAName, pokemonBName);

        //get the corresponding Pokemons based on their names
        Pokemon pokemonA = data.findByName(pokemonAName);
        Pokemon pokemonB = data.findByName(pokemonBName);

        if(pokemonA == null || pokemonB == null) {
            logger.info("Pokemon not found");
            return Map.of("Error", "Pokemon not found");
        }

        // Determine which Pokemon goes first based on Speed (or random if equal)
        Pokemon first, second;
        if (pokemonA.getSpeed() > pokemonB.getSpeed()) {
            first = pokemonA;
            second = pokemonB;
        }else if (pokemonA.getSpeed() < pokemonB.getSpeed()) {
            first = pokemonB;
            second = pokemonA;
        }else {
            //random
            first = (Math.random() < 0.5)?pokemonA:pokemonB;
            second = (first == pokemonA)?pokemonB:pokemonA;
        }

        //begin battle
        while (pokemonA.getHitPoints() > 0 && pokemonB.getHitPoints() > 0) {
            // First attacks second
            int damage = BattleUtils.calculateDamage(first, second);
            if (second.getHitPoints() <= damage) {
                second.setHitPoints(0); // Prevent negative hit points
            } else {
                second.setHitPoints(second.getHitPoints() - damage);
            }

            // Check if second is defeated
            if (second.getHitPoints() <= 0) {
               return Map.of(  "winner", first.getName(),  "remainingHitPoints", first.getHitPoints());
            }
            //or Pokemon Second attacks Pokemon First
            damage = BattleUtils.calculateDamage(second, first);
            if (first.getHitPoints() <= damage) {
                first.setHitPoints(0); // Prevent negative hit points
            } else {
                first.setHitPoints(first.getHitPoints() - damage);
            }

            // Check if first is defeated
            if (first.getHitPoints() <= 0) {
                return Map.of(
                        "winner", second.getName(),
                        "remainingHitPoints", second.getHitPoints()
                );
            }

        }
        // Fallback

        return Map.of("error", "Unexpected error in battle simulation.");
    }
}

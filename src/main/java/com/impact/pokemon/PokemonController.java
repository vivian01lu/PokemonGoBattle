package com.impact.pokemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class PokemonController {

    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    @Resource
    private PokemonData data;

    /**
     * Fetches a list of all Pokémon.
     *
     * @return a list of Pokémon
     */
    @GetMapping("/all-pokemons")
    public List<Pokemon> getAllPokemons() {
        logger.info("Fetching all Pokémon data.");
        return data.getAllPokemon();
    }

    /**
     * Simulates a battle between two Pokémon.
     *
     * @param pokemonAName the name of the first Pokémon
     * @param pokemonBName the name of the second Pokémon
     * @return the battle result including the winner and remaining hit points
     */
    @GetMapping("/attack")
    public Map<String, Object> attack(@RequestParam String pokemonAName, @RequestParam String pokemonBName) {
        logger.info("Requested Pokémon: {} vs. {}", pokemonAName, pokemonBName);

        Pokemon pokemonA = data.findByName(pokemonAName);
        Pokemon pokemonB = data.findByName(pokemonBName);

        if (pokemonA == null || pokemonB == null) {
            logger.warn("One or both Pokémon not found: {}, {}", pokemonAName, pokemonBName);
            return Map.of("error", "One or both Pokémon not found.");
        }

        logger.info("Hit points before battle: {}: {}, {}: {}", pokemonA.getName(), pokemonA.getHitPoints(), pokemonB.getName(), pokemonB.getHitPoints());

        // Determine the order of attack based on speed
        Pokemon[] attackers = determineAttackOrder(pokemonA, pokemonB);

        // Begin battle and return the result
        return executeBattle(attackers[0], attackers[1]);
    }

    /**
     * Determines the attack order based on Pokémon speed.
     *
     * @param pokemonA the first Pokémon
     * @param pokemonB the second Pokémon
     * @return an array containing the first and second Pokémon in the order of attack
     */
    private Pokemon[] determineAttackOrder(Pokemon pokemonA, Pokemon pokemonB) {
        if (pokemonA.getSpeed() > pokemonB.getSpeed()) {
            return new Pokemon[]{pokemonA, pokemonB};
        } else if (pokemonA.getSpeed() < pokemonB.getSpeed()) {
            return new Pokemon[]{pokemonB, pokemonA};
        } else {
            // Randomize order when speeds are equal
            return Math.random() < 0.5 ? new Pokemon[]{pokemonA, pokemonB} : new Pokemon[]{pokemonB, pokemonA};
        }
    }

    /**
     * Executes the battle logic between the two Pokémon.
     *
     * @param first  the first Pokémon
     * @param second the second Pokémon
     * @return the battle result including the winner and remaining hit points
     */
    private Map<String, Object> executeBattle(Pokemon first, Pokemon second) {
        while (first.getHitPoints() > 0 && second.getHitPoints() > 0) {
            // First attacks second
            performAttack(first, second);

            if (second.getHitPoints() <= 0) {
                logger.info("Winner: {}, Remaining HP: {}", first.getName(), first.getHitPoints());
                return Map.of("winner", first.getName(), "remainingHitPoints", first.getHitPoints());
            }

            // Second attacks first
            performAttack(second, first);

            if (first.getHitPoints() <= 0) {
                logger.info("Winner: {}, Remaining HP: {}", second.getName(), second.getHitPoints());
                return Map.of("winner", second.getName(), "remainingHitPoints", second.getHitPoints());
            }
        }

        if(first.getHitPoints() <= 0 || second.getHitPoints() <= 0){
            return  Map.of("error", "Pokemon's HitPoints should be positive to battle");
        }

        logger.error("Unexpected error in battle simulation.");
        return Map.of("error", "Error in battle simulation.");
    }

    /**
     * Handles the attack logic and applies damage to the defending Pokémon.
     *
     * @param attacker the attacking Pokémon
     * @param defender the defending Pokémon
     */
    private void performAttack(Pokemon attacker, Pokemon defender) {
        int damage = BattleUtils.calculateDamage(attacker, defender);
        applyDamage(defender, damage);
    }

    /**
     * Applies damage to the given Pokémon and updates its hit points.
     *
     * @param pokemon the Pokémon to apply damage to
     * @param damage  the amount of damage to apply
     */
    private void applyDamage(Pokemon pokemon, int damage) {
        int remainingHitPoints = Math.max(0, pokemon.getHitPoints() - damage);
        pokemon.setHitPoints(remainingHitPoints);
        logger.info("Applied damage: {} to {}. Remaining HP: {}", damage, pokemon.getName(), remainingHitPoints);
    }
}

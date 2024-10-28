package com.impact.pokemon;

import java.util.HashMap;
import java.util.Map;

public class BattleUtils {

    private static final int BASE_DAMAGE = 50;
    private static final Map<String, Map<String, Double>> effectivenessChart = new HashMap<>();

    static {
        // Initialize the effectiveness chart
        effectivenessChart.put("Fire", Map.of(
                "Grass", 2.0,
                "Water", 0.5,
                "Normal", 1.0 // default effectiveness for other types
        ));
        effectivenessChart.put("Water", Map.of(
                "Fire", 2.0,
                "Electric", 0.5,
                "Normal", 1.0 // default effectiveness for other types
        ));
        effectivenessChart.put("Grass", Map.of(
                "Electric", 2.0,
                "Fire", 0.5,
                "Normal", 1.0 // default effectiveness for other types
        ));
        effectivenessChart.put("Electric", Map.of(
                "Water", 2.0,
                "Grass", 0.5,
                "Normal", 1.0 // default effectiveness for other types
        ));
    }

    /**
     * Calculates the damage dealt by the attacker to the defender.
     *
     * @param attacker the Pokémon attacking
     * @param defender the Pokémon being attacked
     * @return the calculated damage
     */
    public static int calculateDamage(Pokemon attacker, Pokemon defender) {
        double effectiveness = getEffectivenessModifier(attacker.getType(), defender.getType());
        double rawDamage = BASE_DAMAGE * ((double) attacker.getAttack() / defender.getDefense());
        return (int) Math.round(rawDamage * effectiveness);
    }

    /**
     * Retrieves the effectiveness modifier based on Pokémon types.
     *
     * @param attackerType the type of the attacking Pokémon
     * @param defenderType the type of the defending Pokémon
     * @return the effectiveness modifier
     */
    public static double getEffectivenessModifier(String attackerType, String defenderType) {
        return effectivenessChart
                .getOrDefault(attackerType, Map.of())
                .getOrDefault(defenderType, 1.0);
    }
}

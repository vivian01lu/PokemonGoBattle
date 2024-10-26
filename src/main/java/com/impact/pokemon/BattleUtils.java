package com.impact.pokemon;

public class BattleUtils {
    public static int calculateDamage(Pokemon attacker, Pokemon defender) {
        double effectiveness =  getEffectivenessModifier(attacker.getType(), defender.getType());
        double rawDamage = 50 * ((double)attacker.getAttack()/defender.getDefense());
        return (int)Math.round(rawDamage*effectiveness);
    }

    public static double getEffectivenessModifier(String attackerType, String defenderType) {
        switch (attackerType) {
            case "Fire":
                if (defenderType.equals("Grass")) return 2.0;
                if (defenderType.equals("Water")) return 0.5;
                return 1.0;

            case "Water":
                if (defenderType.equals("Fire")) return 2.0;
                if (defenderType.equals("Electric")) return 0.5;
                return 1.0;

            case "Grass":
                if (defenderType.equals("Electric")) return 2.0;
                if (defenderType.equals("Fire")) return 0.5;
                return 1.0;

            case "Electric":
                if (defenderType.equals("Water")) return 2.0;
                if (defenderType.equals("Grass")) return 0.5;
                return 1.0;

            default:
                return 1.0;
        }

    }

}

package com.impact.pokemon;

import com.opencsv.bean.CsvBindByName;

public class Pokemon {

    @CsvBindByName(column = "#")
    private int id;

    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "Type")
    private String type;

    @CsvBindByName(column = "Total")
    private int total;

    @CsvBindByName(column = "HitPoints")
    private int hitPoints;

    @CsvBindByName(column = "Attack")
    private int attack;

    @CsvBindByName(column = "Defense")
    private int defense;

    @CsvBindByName(column = "SpecialAttack")
    private int specialAttack;

    @CsvBindByName(column = "SpecialDefense")
    private int specialDefense;

    @CsvBindByName(column = "Speed")
    private int speed;

    @CsvBindByName(column = "Generation")
    private int generation;

    @CsvBindByName(column = "Legendary")
    private boolean legendary;

    // Constructors, Getters, and Setters

    public Pokemon() {
    }

    // Add constructor to initialize all fields (if needed)
    public Pokemon(int id, String name, String type, int total, int hitPoints, int attack,
                   int defense, int specialAttack, int specialDefense, int speed,
                   int generation, boolean legendary) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.total = total;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.generation = generation;
        this.legendary = legendary;
    }
    //getter methods
    public int getAttack(){
        return this.attack;
    }
    public int getDefense(){
        return this.defense;
    }
    public String getType() {
        return this.type;
    }

    public double getEffectivenessModifier(String attackerType, String defenderType) {
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
    public double calculateDamage(Pokemon attacker, Pokemon defender) {
        double effectiveness =  getEffectivenessModifier(attacker.getType(), defender.getType());
        return 50 * ((double)attacker.getAttack()/defender.getDefense());
    }

}

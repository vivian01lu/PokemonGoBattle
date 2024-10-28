package com.impact.pokemon;

import com.opencsv.bean.CsvBindByName;

/**
 * Represents a Pokémon with various attributes that can be populated from a CSV file.
 */
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

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getTotal() {
        return total;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getGeneration() {
        return generation;
    }

    public boolean isLegendary() {
        return legendary;
    }

    // Setter methods
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
}

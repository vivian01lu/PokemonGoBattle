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
    public String getName() {
        return this.name;
    }
    public int getHitPoints() {
        return this.hitPoints;
    }
    public int getSpeed() {
        return this.speed;
    }
    public void setHitPoints(int hitPoints){
        this.hitPoints = hitPoints;
    }
}

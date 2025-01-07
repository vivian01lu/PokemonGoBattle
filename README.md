### Overview

This project simulates a Pokémon battle to determine which Pokémon would emerge victorious based on their attributes. The focus is on battle mechanics, not on Pokémon abilities, creating a fun and interactive way to explore Pokémon matchups.

![Illumise](https://img.pokemondb.net/sprites/home/normal/illumise.png) **vs** ![Charmeleon](https://img.pokemondb.net/sprites/home/normal/charmeleon.png)

### Features  
- **Languages Used:** Java & JavaScript  
- **Frameworks:** Spring Boot & Vue.js  
### Requirements
- Parse Pokémon data from src/main/resources/data/pokemon.csv and create objects for each Pokémon.
- Implement unique behavior for the effectiveness modifier based on Pokémon types:
-**Fire**:  2× effective vs. Grass, 0.5× effective vs. Water, neutral vs. Electric
-**Water**: 2× effective vs. Fire, 0.5× effective vs. Electric, neutral vs. Grass
-**Grass**: 2× effective vs. Electric, 0.5× effective vs. Fire, neutral vs. Water
-**Electric**: 2× effective vs. Water, 0.5× effective vs. Grass, neutral vs. Fire
### Gameplay Logic
- **Create one API endpoint `/attack` which:**
  - Accept 2 pokemon as input parameters
  - Let each pokemon continuously attack the other one at a time, until one of the two pokemon run out of hit points.
  - The pokemon with the highest _Speed_ will always attack first. If two pokemon have the same speed, a random one goes first
  - A pokemon who has run out of hit points is considered the loser.
  - The attack damage can be calculated as follows `50 x (attack of attacking pokemon / defense of defending pokemon) * effectiveness modifier` (You're welcome to use a different formula to calculate attack damage based on the pokemon.csv) 
  - Return which of the 2 pokemon is the winner and its remaining HitPoints (HP) after damage has been calculated
### Frontend Features
- Allows users to select or input two Pokémon.
- Displays the winning Pokémon, including its image

### Tasks
1. Create Pokémon classes to represent all fields in the CSV file.
2. Build the /attack API endpoint in Java (Spring Boot).
3. Design an interactive frontend using Vue.js for Professor Oak to choose Pokémon.
4. Write unit tests to ensure the correctness of each component.
### How to Run the Project
- Load the Pokémon data when the application starts (in-memory data storage).
- Use the provided Gradle wrapper to build and run the project:
  - Run the server:
    ```
    ./gradlew bootRun
    ```
    Once you see a message like Started SpringPokemonApplication..., open your browser at http://localhost:8080.
- To run unit tests:
  ```
    ./gradlew test
  ```


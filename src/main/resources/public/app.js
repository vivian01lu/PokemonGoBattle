const app = Vue.createApp({
    setup() {
        // Reactive state variables
        const pokemons = Vue.ref([]);
        const selectedPokemons = Vue.ref([]);
        const winnerImage = Vue.ref("");
        const winnerMessage = Vue.ref("");

        // Function to handle errors and update the UI
        function handleError(errorMessage) {
            winnerMessage.value = errorMessage;
            winnerImage.value = ""; // Clear image on error
        }

        /**
         * Fetch all Pokémon data from the server.
         */
        async function fetchPokemons() {
            try {
                const response = await fetch("/all-pokemons");
                // Check if the response is OK
                if (response.ok) {
                    pokemons.value = await response.json();
                } else {
                    // Handle the error case without throwing an exception
                    handleError("Failed to fetch battle result. Please try again later.");
                }
            } catch (error) {
                handleError("An error occurred while fetching Pokémon data: " + error.message);
            }
        }

        /**
         * Conduct a battle between two selected Pokémon.
         */
        async function battle() {
            if (selectedPokemons.value.length === 2) {
                try {
                    const response = await fetch(`/attack?pokemonAName=${selectedPokemons.value[0]}&pokemonBName=${selectedPokemons.value[1]}`);
                    // Check if the response is OK
                    if (response.ok) {
                        const data = await response.json();
                        processBattleResult(data);
                    } else {
                        // Handle the error case without throwing an exception
                        handleError("Failed to fetch battle result. Please try again later.");
                    }
                } catch (error) {
                    handleError("An error occurred while trying to fetch the battle result: " + error.message);
                }
            } else {
                handleError("Please select two Pokémon to battle!");
            }
        }

        /**
         * Process the battle result and update the UI accordingly.
         * @param {Object} data - The battle result data
         */
        function processBattleResult(data) {
            const { winner, remainingHitPoints, error } = data;

            if (winner) {
                winnerMessage.value = `${winner} wins with ${remainingHitPoints} hit points left!`;
                winnerImage.value = getPokemonImage(winner);
            } else {
                handleError("An error occurred: " + (error || "unknown error"));
            }
        }

        /**
         * Get the image URL for a given Pokémon name.
         * @param {string} pokemonName - The name of the Pokémon
         * @return {string} The image URL
         */
        function getPokemonImage(pokemonName) {
            return `https://img.pokemondb.net/sprites/home/normal/${pokemonName.toLowerCase()}.png`;
        }

        /**
         * Toggle the selection of a Pokémon for battle.
         * @param {string} pokemonName - The name of the Pokémon
         */
        function togglePokemonSelection(pokemonName) {
            const index = selectedPokemons.value.indexOf(pokemonName);
            if (index > -1) {
                selectedPokemons.value.splice(index, 1); // Deselect
            } else {
                if (selectedPokemons.value.length < 2) {
                    selectedPokemons.value.push(pokemonName); // Select
                } else {
                    alert("You can only select 2 Pokémon for battle.");
                }
            }
        }

        // Fetch Pokémon data on app setup
        fetchPokemons();

        return {
            pokemons,
            selectedPokemons,
            getPokemonImage,
            winnerImage,
            winnerMessage,
            togglePokemonSelection,
            battle,
        };
    }
});

// Mount the app to the DOM
app.mount('#app');

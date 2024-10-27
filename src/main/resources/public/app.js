const app = Vue.createApp({
    setup() {
        // let pokemonName = Vue.ref('________');
        let pokemons = Vue.ref([]);
        const selectedPokemons = Vue.ref([]);
        const winnerMessage = Vue.ref("");

        function battle() {
            if (selectedPokemons.value.length === 2) {
                fetch(`/attack?pokemonAName=${selectedPokemons.value[0]}&pokemonBName=${selectedPokemons.value[1]}`)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`Server responded with status ${response.status}`);
                        }
                        return response.json();
                    })
                    .then(data => {
                        if (data.winner) {
                            winnerMessage.value = `${data.winner} wins with ${data.remainingHitPoints} hit points left!`;
                        } else {
                            winnerMessage.value = "An error occurred: " + (data.error || "unknown error");
                        }
                    })
                    .catch(error => {
                        console.error("Fetch error:", error);
                        winnerMessage.value = "An error occurred while trying to fetch the battle result.";
                    });
            } else {
                winnerMessage.value = "Please select two Pokémon to battle!";
            }
        }
        //use fetch() to get data from the backend database
        function fetchPokemons() {
            fetch("/all-pokemons")
                .then(response=> response.json())
                .then(data =>{
                    pokemons.value = data;
                });
        }

        // Handle checkbox change
        function togglePokemonSelection(pokemonName) {
            const index = selectedPokemons.value.indexOf(pokemonName);
            if(index > -1){
                // Deselect if already selected
                selectedPokemons.value.splice(index, 1);
            }else{
                // Select if not already selected
                if (selectedPokemons.value.length < 2) {
                    selectedPokemons.value.push(pokemonName);

                }else{ // Provide feedback if trying to select more than 2
                    alert("You can only select 2 Pokémon for battle.");
                }
            }
        }


        fetchPokemons();

        return {
            pokemons,
            selectedPokemons,
            winnerMessage,
            togglePokemonSelection,
            battle,
        }
    }
});
app.mount('#app');

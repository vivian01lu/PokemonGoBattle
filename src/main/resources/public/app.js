
const app = Vue.createApp({
    setup() {
        // let pokemonName = Vue.ref('________');
        let pokemons = Vue.ref([]);
        const selectedPokemons = Vue.ref([]);
        let winnerImage = Vue.ref("");
        const winnerMessage = Vue.ref("");


        function battle() {
            if (selectedPokemons.value.length === 2) {
                fetch(`/attack?pokemonAName=${selectedPokemons.value[0]}&pokemonBName=${selectedPokemons.value[1]}`)
                    .then(response => {
                        return response.json();
                    })
                    .then(data => {
                        if (data.winner) {
                            winnerMessage.value = `${data.winner} wins with ${data.remainingHitPoints} hit points left!`;
                            winnerImage.value = `https://img.pokemondb.net/sprites/home/normal/${data.winner.toLowerCase()}.png`;
                        } else {
                            winnerMessage.value = "An error occurred: " + (data.error || "unknown error");
                            winnerImage.value = ""; // Clear image if there’s an error
                        }
                    })
                    .catch(error => {
                        winnerMessage.value = "An error occurred while trying to fetch the battle result.";
                        winnerImage.value = "";
                    });
            } else {
                winnerMessage.value = "Please select two Pokémon to battle!";
                winnerImage.value = "";
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

        function resetBattle() {
            selectedPokemons.value = []; // Clear selected Pokémons
            winnerMessage.value = ""; // Clear winner message
            winnerImage.value = ""; // Clear winner image

        }


        fetchPokemons();

        return {
            pokemons,
            resetBattle,
            selectedPokemons,
            winnerImage,
            winnerMessage,
            togglePokemonSelection,
            battle,
        }
    }
});
app.mount('#app');

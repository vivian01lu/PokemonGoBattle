const app = Vue.createApp({
    setup() {
        let pokemonName = Vue.ref('________');
        let pokemons = Vue.ref([]);

        function battle () {
            fetch("/attack?pokemonA=Bulbasaur&pokemonB=Ivysaur")
                .then(response => response.json())
                .then(data => {
                    pokemonName.value = data.winner;
                    console.log(`This pokemon has ${data.hitPoints} hit points.`);
                })
        }
        //use fetch() to get data from the backend database
        function fetchPokemons() {
            fetch("/all-pokemons")
                .then(response=> response.json())
                .then(data =>{
                    pokemons.value = data;
                });
        }


        fetchPokemons();

        return {
            pokemons,
            pokemonName,
            battle,
        }
    }
});
app.mount('#app');

package com.impact.pokemon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Map;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the PokemonController, testing the battle logic between Pokémon.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokemonControllerTest {

    private final TestRestTemplate rest; // REST template for making test requests

    /**
     * Constructor for initializing the TestRestTemplate with the local server port.
     *
     * @param port The local server port assigned for testing.
     */
    PokemonControllerTest(@LocalServerPort int port) {
        rest = new TestRestTemplate(new RestTemplateBuilder().rootUri(format("http://localhost:%d", port)));
    }

    /**
     * Provides a stream of test cases for Pokémon battles.
     *
     * @return A stream of TestCase objects.
     */
    static Stream<TestCase> pokemonBattleCases() {
        return Stream.of(
                new TestCase("Bulbasaur", "Charmander", "Charmander", 39),
                new TestCase("Ivysaur", "Charmeleon", "Charmeleon", 58),
                new TestCase("Venusaur", "Charmander", "Venusaur", 80),
                new TestCase("Charmeleon", "Wartortle", "Wartortle", 39),
                new TestCase("Pidgeotto", "Rattata", "Pidgeotto", 12),
                new TestCase("Sandslash", "Nidorino", "Sandslash", 59),
                new TestCase("Paras", "Venonat", "Venonat", 60),
                new TestCase("Clefairy", "Clefable", "Clefable", 95)

        );
    }

    /**
     * Parameterized test for Pokémon battles using predefined test cases.
     *
     * @param testCase The test case containing Pokémon names and expected results.
     */
    @ParameterizedTest
    @MethodSource("pokemonBattleCases")
    void testPokemonBattles(TestCase testCase) {
        // Construct the URL for the attack endpoint with query parameters
        String url = String.format("/attack?pokemonAName=%s&pokemonBName=%s", testCase.pokemon1, testCase.pokemon2);

        // Execute the GET request and obtain the response
        Map<String, Object> response = rest.getForObject(url, Map.class);

        System.out.println("Response: " + response);
        // Validate that the expected winner and remaining hit points match the response
        assertEquals(testCase.expectedWinner, response.get("winner"));
        assertEquals(testCase.expectedHitPoints, response.get("remainingHitPoints"));
    }

    /**
     * Helper class to represent a test case for Pokémon battles.
     */
    static class TestCase {
        String pokemon1;          // Name of the first Pokémon
        String pokemon2;          // Name of the second Pokémon
        String expectedWinner;    // Expected winner's name
        int expectedHitPoints;    // Expected hit points of the winner

        /**
         * Constructor to initialize the test case data.
         *
         * @param pokemon1 The name of the first Pokémon.
         * @param pokemon2 The name of the second Pokémon.
         * @param expectedWinner The expected winner's name.
         * @param expectedHitPoints The expected hit points of the winner.
         */
        TestCase(String pokemon1, String pokemon2, String expectedWinner, int expectedHitPoints) {
            this.pokemon1 = pokemon1;
            this.pokemon2 = pokemon2;
            this.expectedWinner = expectedWinner;
            this.expectedHitPoints = expectedHitPoints;
        }
    }
}

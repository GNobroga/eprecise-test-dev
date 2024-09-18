package br.com.eprecise;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ExampleTest {

    @Test
    @DisplayName("Simple Example")
    public void test() {
        assertEquals(1, 1);
    }

}

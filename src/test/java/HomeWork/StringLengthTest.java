package HomeWork;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringLengthTest {

    @Test

    public void testStringLength(){
        String test ="Любое слово";
        int length = test.length();
        assertTrue( length > 15, "Длинна строки меньше 15");

    }
}

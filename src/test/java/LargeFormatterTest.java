import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LargeFormatterTest {

    LargeFormatter lf;

    @BeforeEach
    void setUp() {
        lf = new LargeFormatter();
    }

    @Test
    void tinyNumberTest(){
        double n = 0.1;
        String s = "0.10";
        assertEquals(s, lf.formatBigNumber(n));
    }

    @Test
    void smallNumberTest(){
        double n = 10;
        String s = "10";
        assertEquals(s, lf.formatBigNumber(n));
    }

    @Test
    void kTest(){
        double n = 1000;
        String s = "1k";
        assertEquals(s, lf.formatBigNumber(n));
    }

    @Test
    void mTest(){
        double n = 10230000;
        String s = "10.23M";
        assertEquals(s, lf.formatBigNumber(n));
    }
}
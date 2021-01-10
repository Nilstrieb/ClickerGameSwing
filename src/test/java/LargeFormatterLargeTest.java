import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LargeFormatterLargeTest {

    LargeFormatter lf;

    @BeforeEach
    void setUp() {
        lf = new LargeFormatter();
    }

    @Test
    void normal(){
        double n = 100000;
        String s = "100k";
        assertEquals(s, lf.formatBigNumber(n));
    }

    @Test
    void smallestSci(){
        double n = 1000000;
        String s = "1.00E6";
        assertEquals(s, lf.formatBigNumber(n));
    }

    @Test
    void biggerSci(){
        double n = 10000000;
        String s = "1.00E7";
        assertEquals(s, lf.formatBigNumber(n));
    }

    @Test
    void decimalSci(){
        double n = 16900000;
        String s = "1.69E7";
        assertEquals(s, lf.formatBigNumber(n));
    }

    @Test
    void pointNine(){
        double n = 9780000;
        String s = "9.78E6";
        assertEquals(s, lf.formatBigNumber(n));
    }
}
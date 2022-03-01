import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.*;

public class Prog31Test {

    @Test
    public void testStopWhenExceed100()
    {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        StandardInputStream in = new StandardInputStream();
        System.setIn(in);

        // action
        in.inputln("30");
        in.inputln("30");
        in.inputln("30");
        in.inputln("30");
        Prog31.main(null);

        // assertion
        String[] prints = bos.toString().split("\n");
        assertEquals("合計は120です", prints[prints.length - 2]);
        assertEquals("プログラムを終了します", prints[prints.length - 1]);

        // undo the binding in System
        System.setOut(originalOut);
    }

    @Test
    public void testDoNotStopWhenEqual100()
    {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        StandardInputStream in = new StandardInputStream();
        System.setIn(in);

        // action
        in.inputln("100");
        in.inputln("1");
        Prog31.main(null);

        // assertion
        String[] prints = bos.toString().split("\n");
        assertEquals("合計は101です", prints[prints.length - 2]);
        assertEquals("プログラムを終了します", prints[prints.length - 1]);

        // undo the binding in System
        System.setOut(originalOut);
    }

    @Test
    public void testDoNotStopWithManyInputs()
    {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        StandardInputStream in = new StandardInputStream();
        System.setIn(in);

        // action
        for(int i=0; i<10000; i++) {
            in.inputln("0");
        }
        in.inputln("101");
        Prog31.main(null);

        // assertion
        String[] prints = bos.toString().split("\n");
        assertEquals("合計は101です", prints[prints.length - 2]);
        assertEquals("プログラムを終了します", prints[prints.length - 1]);

        // undo the binding in System
        System.setOut(originalOut);
    }
}

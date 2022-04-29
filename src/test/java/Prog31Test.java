import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.lang.AssertionError;
import java.io.*;

public class Prog31Test {

    InputStream originalIn;
    PrintStream originalOut;
    ByteArrayOutputStream bos;
    StandardInputStream in;
    
    @BeforeEach
    void before() {
       //back up binding
       originalIn  = System.in;
       originalOut = System.out;
       //modify binding
       bos = new ByteArrayOutputStream();
       System.setOut(new PrintStream(bos));
        
       in = new StandardInputStream();
       System.setIn(in);
    }
    
    @AfterEach
    void after() {
       System.setOut(originalOut);
       System.setIn(originalIn);
    }
    
    @Test
    public void testFinalSumPrint()
    {
        // action
        in.inputln("99");
        in.inputln("1");
        in.inputln("1");
        in.inputln("1");
        Prog31.main(null);

        // assertion
        String[] prints = bos.toString().split(System.lineSeparator());
        String l2Str = prints[prints.length - 2];

        try {
            assertTrue(l2Str.substring(0,3).equals("合計は"), "「合計は」の文字列がありません!");
            assertTrue(l2Str.substring(l2Str.length()-2).equals("です" ),
                       "「合計は」から始まるprintの最後が「です」になっていません!"
            );
            assertNotEquals(l2Str,"合計は100です","合計値が100を超える前に終了しました!");
            assertNotEquals(l2Str,"合計は102です","合計値が100を超えてもまだ繰り返します!");
        } catch (AssertionError err) {
            after();
            throw err;     
        }
    }

    @Test
    public void testDoNotStopWithManyInputs()
    {
        // action
        for(int i=0; i<10000; i++) {
            in.inputln("0");
        }
        in.inputln("101");
        in.inputln("200");
        Prog31.main(null);

        // assertion
        String[] prints = bos.toString().split(System.lineSeparator());
        try {
            assertEquals("合計は101です", prints[prints.length - 2],
                         "最大繰り返し回数がコード中で決められています! (合計値で繰り返すかどうかを決めるべき)"
            );
        } catch(AssertionError err) {
            after();
            throw err;     
        }
    }

    @Test
    public void testInitialization()
    {
        // action
        in.inputln("1");
        in.inputln("10");
        in.inputln("10");
        in.inputln("100"); // Prog31.main is expected to exit after this keyboard input
        Prog31.main(null);
        
        // assertion
        String[] prints = bos.toString().split(System.lineSeparator());
        try {
            assertEquals("合計は11です", prints[3],
                         "誤った値での初期化、又は合計の計算式に間違いがあるかもしれません"
            ); // checking 2nd SumPrintline(result of two additions )
        }catch (AssertionError err){
            after();
            throw err;   
        }
    }    

    @Test
    public void testSecondPrint()
    {
        // action
        in.inputln("1");
        in.inputln("10");
        in.inputln("100");
        Prog31.main(null);
        
        // assertion
        String[] prints = bos.toString().split(System.lineSeparator());
        try {
            assertEquals("正の整数を入力してください", prints[2],
                         "正の整数を入力してください」のprint文の使い方が不正、又は文字が完全一致していません"
            ); // cheking 2nd prompt message
        }catch (AssertionError e){
            after();
            throw e;   
        }
    }    

    @Test
    public void testLastPrint()
    {
        // action
        in.inputln("100");
        in.inputln("100");
        Prog31.main(null);
        
        // assertion
        String[] prints = bos.toString().split(System.lineSeparator());
        try {
            assertEquals("プログラムを終了します", prints[prints.length - 1],
                         "【最優先】「プログラムを終了します」の一文が無い、又は文字が完全一致しません"
            );
        }catch (AssertionError err){
            after();
            throw err;   
        }
    }
}

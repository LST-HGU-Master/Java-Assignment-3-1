import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.*;
/**
 * @version (20230417)
 *   supporting both println and print("\n") on Windows
 *  @version(20261008) revised 
 **/
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
        in.inputln("1"); // ここで合計101となり終了すべき
        in.inputln("1");
        Prog31.main(null);

        String output = bos.toString().replace("\r\n", "\n");
        
        assertTrue(output.contains("合計は101です"), 
            "合計値が101になった際の出力「合計は101です」が見つかりません。"
        );
        assertFalse(output.contains("合計は102です"), 
            "合計値が100を超えても処理を継続しています。"
        );
    }


    @Test
    public void testDoNotStopWithManyInputs()
    {
        // 100回の0入力（ループ回数固定対策）
        for (int i = 0; i < 100; i++) {
            in.inputln("0");
        }
        in.inputln("101");
        in.inputln("200");
        Prog31.main(null);

        String output = bos.toString();

        assertTrue(output.contains("合計は101です"), 
            "繰り返し処理の回数が固定化されているか、0の加算で正しくループが継続されていません。"
        );
    }

    @Test
    public void testInitialization()
    {
        in.inputln("1");
        in.inputln("10");
        in.inputln("100"); // 101で終了
        Prog31.main(null);

        String output = bos.toString();

        assertTrue(output.contains("合計は1です"), "最初の入力「1」に対する「合計は1です」が出力されていません。");
        assertTrue(output.contains("合計は11です"), "2回目の入力「10」に対する「合計は11です」が出力されていません。変数の初期化や加算式を確認してください。");
    } 

    @Test
    public void testSecondPrint()
    {
        // action
        in.inputln("1");
        in.inputln("10");
        in.inputln("100");
        Prog31.main(null);

        String output = bos.toString();

        assertTrue(output.contains("正の整数を入力してください"), 
            "「正の整数を入力してください」のメッセージがない、または文字が完全一致していません。"
        );
    }    

    @Test
    public void testLastPrint()
    {
        // action
        in.inputln("100");
        in.inputln("100");
        Prog31.main(null);

        // assertion
        String output = bos.toString();

        assertTrue(output.contains("プログラムを終了します"), 
            "「プログラムを終了します」の一文がない、または文字が完全一致しません。"
        );
    }
}

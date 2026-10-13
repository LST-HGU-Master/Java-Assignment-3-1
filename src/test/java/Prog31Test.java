import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.*;
/**
 * @version (20230417)
 *   supporting both println and print("\n") on Windows
 *  @version(20261013) revised 
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
    public void testBoundaryValue100() {
        in.inputln("50");
        in.inputln("50"); // 合計100（ここではまだ終了してはいけない）
        in.inputln("1");  // 合計101（ここで終了）
        in.inputln("99"); // 終了後に読み込まれないはずの入力
        Prog31.main(null);

        String output = bos.toString().replace("\r\n", "\n");

        assertTrue(output.contains("合計は100です"), "合計がちょうど100の時は処理を終了せず、次の入力を受け付ける必要があります（> 100 の条件を確認してください）。");
        assertTrue(output.contains("合計は101です"), "合計が100を超えた（101）時点で処理を終了してください。");
        assertFalse(output.contains("合計は200です"), "合計が100を超えても処理が継続しています。");
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
    public void testPrintsAndEnding() {
        in.inputln("101");
        Prog31.main(null);

        String[] lines = bos.toString().replace("\r\n", "\n").split("\n");

        boolean foundPrompt = false;
        boolean foundEnding = false;

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.equals("正の整数を入力してください")) 
                foundPrompt = true;
            if (trimmed.equals("プログラムを終了します")) 
                foundEnding = true;
        }

        assertTrue(foundPrompt, "「正の整数を入力してください」の後に改行（println）を入れて出力してください。");
        assertTrue(foundEnding, "「プログラムを終了します」の一文がない、または文字が完全一致しません。");
    }
}

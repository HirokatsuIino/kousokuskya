import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
/**
 * ファイルの読み込みサンプル
 *　文字コードを指定してテキストファイルを読み込む
 */
public class FileTextRead {
  public static void main(String[] args) {

    // 読み込むファイルの名前
    String inputFileName = "C:\\temp\\test2.txt";
    // ファイルオブジェクトの生成
    File inputFile = new File(inputFileName);
    try {
      // 入力ストリームの生成（文字コード指定）
      FileInputStream fis = new FileInputStream(inputFile);
      InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
      BufferedReader br = new BufferedReader(isr);
      // テキストファイルからの読み込み
      String msg;
      while ( ( msg = br.readLine()) != null ) {
        System.out.println(msg);
      }

      // 後始末
      br.close();
      // エラーがあった場合は、スタックトレースを出力
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}

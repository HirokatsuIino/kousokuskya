import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.io.IOException;

public class CsvRead {

    public static void main(String args[]) {

        try {
            //ファイルを読み込む
            FileReader fr = new FileReader("D:\\注文取消通知.msg");
//            FileReader fr = new FileReader("C:\\USDJPYpro5.csv");
            BufferedReader br = new BufferedReader(fr);


            //ファイル種別ごとに処理を分ける
            //読み込んだファイルを１行ずつ処理する
            String line;
            StringTokenizer token;
            while ((line = br.readLine()) != null) {
                //区切り文字","で分割する
                token = new StringTokenizer(line, ",");

                //分割した文字を画面出力する
                while (token.hasMoreTokens()) {
                    System.out.println(token.nextToken());
                }
                System.out.println("**********");
            }

            //終了処理
            br.close();

        } catch (IOException ex) {
            //例外発生時処理
            ex.printStackTrace();
        }
    }

}

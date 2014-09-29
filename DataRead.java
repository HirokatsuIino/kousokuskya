import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class DataRead {

    public static void main(String args[]) {

        //注文取消通知ファイルを読み込む
        try {
              FileReader fr_a = new FileReader("D:\\注文取消通知.txt");
              System.out.println("ファイルは存在します");

            BufferedReader br = new BufferedReader(fr_a);

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

                    //文字列検索と抜出関数へ
                    DataRead(line) ;

                }
                System.out.println("**********");
            }

            //終了処理
            br.close();
        }catch(FileNotFoundException e){
            System.out.printf("ファイルが開けません。","+",e);
        } catch (IOException ex) {
            //例外発生時処理
            ex.printStackTrace();
            System.out.printf("ファイルが開けません。","+",ex);
        }

    }


	private static void DataRead(String line) {

    	//項目の読み込みと比較
    	//メール内容の：の左側のみを読み込む　表示する。
    	String sDataItem[]={
					    	"[注文内容]",
					    	"注文パターン",
					    	"日付",
					    	"時刻",
					    	"通貨ペア",
					    	"（ＯＣＯ第1注文）",
					    	"注文番号1",
					    	"新規決済区分",
			    	"売買区分1",
					    	"注文数量1",
					    	"注文タイプ1",
					    	"注文価格1",
					    	"有効期限1",
					    	"（ＯＣＯ第2注文）",
					    	"新規決済区分",
					    	"売買区分2",
					    	"注文数量2",
					    	"注文タイプ2",
					    	"注文価格2",
					    	"有効期限2"
					    	};

    	String sOrderItem="[注文内容]";

		String s4 = "：";
		int record=0;
//    	while(line != null){
			//文字列完全一致
//    		if(sOrderItem.equals(line)) {
			//文字列の一部一致
    			//メール内容の：う含む項目があれり、：の左側と一致してれば、右側を抜出し、別の配列に格納する。
    			if(line.matches(".*" + s4 + ".*")){
        		    System.out.printf("文字列一部一致です。");
//    			if(sDataItem[record].matches(".*" + s4 + ".*")){
        			int flg_nmaches = 1;// 部分一致です
    			}
    			else {
    			    int flg_Unmaches = 1;// 部分一致ではありません
    		    }
//    		}
     		record++;
	}
}


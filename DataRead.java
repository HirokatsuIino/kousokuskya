import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class DataRead {




    @SuppressWarnings("null")
	public static void main(String args[]) {


    	//項目の読み込みと比較
    	//メール内容の：の左側のみを読み込む　表示する。
    	String sDataItemOrder1[]={
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


		String s4 = "：";
		int record=0;
		int iLineLength=0;
		int iTargetItem=0;

		String[] strLine ;//指定文字で分割した文字を入れる

    	//注文取消通知ファイルを読み込む
        try {
              FileReader fr_a = new FileReader("D:\\注文取消通知.txt");
              System.out.println("ファイルは存在します");

            BufferedReader br = new BufferedReader(fr_a);

            //ファイル種別ごとに処理を分ける
            //読み込んだファイルを１行ずつ処理する
            String line;
//            StringTokenizer token;
            int index;
//        	String strItemLeftKoumoku[] = null;
        	int iItemLeftKoumokuIndex=0;
//        	String strItemRightKoumoku[] = null;
        	int iItemRightKoumokuIndex=0;

        	String[] strItemLeftKoumoku; // 型宣言
        	strItemLeftKoumoku = new String[100]; // 要素数の確定
        	String[] strItemRightKoumoku; // 型宣言
        	strItemRightKoumoku = new String[100]; // 要素数の確定

            while ((line = br.readLine()) != null) {
            	//文字列の一部一致
    			//メール内容の：を含む項目があれり、：の左側と一致してれば、右側を抜出し、別の配列に格納する。
    			if(line.matches(".*" + s4 + ".*")){
        		    System.out.printf("文字列一部一致です。");
        			// 指定した文字より後ろの文字取り出し
        		    index = line.indexOf("：");

        		    //項目の取り出し
        		    strItemLeftKoumoku[iItemLeftKoumokuIndex] = line.substring(0, index);
        		    System.out.println("取り出し文字列->" + strItemLeftKoumoku[iItemLeftKoumokuIndex]);
        		    //アイテムの取り出し
        		    strItemRightKoumoku[iItemRightKoumokuIndex] = line.substring(index+1);
        		    System.out.println("取り出し文字列->" +  strItemRightKoumoku[iItemRightKoumokuIndex]);

        		    iItemLeftKoumokuIndex++;
        		    iItemRightKoumokuIndex++;


    			}
    		    iItemLeftKoumokuIndex++;
    		    iItemRightKoumokuIndex++;
            }

            //終了処理
            br.close();
        } catch (FileNotFoundException e){
            System.out.printf("ファイルが開けません。","+",e);
        } catch (IOException ex) {
            //例外発生時処理
            ex.printStackTrace();
            System.out.printf("ファイルが開けません。","+",ex);
        }

    }
}


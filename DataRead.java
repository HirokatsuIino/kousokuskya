import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;







import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;



public class DataRead {




	public static void main(String args[]) throws IOException {


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
		boolean  bFlg=true;

		String[] strLine ;//指定文字で分割した文字を入れる

    	//注文取消通知ファイルを読み込む
              FileReader fr_a = new FileReader("D:\\注文取消通知.txt");

            BufferedReader br = new BufferedReader(fr_a);

            //ファイル種別ごとに処理を分ける
            //読み込んだファイルを１行ずつ処理する
            String line;
            int index;
        	int iItemLeftKoumokuIndex=0;
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


        		    /*　追記書き込み項目をテキストファイルへ書き込む
					*
        		     * */
        		    try{
        		        File file = new File("C:\\temp\\test2.txt");
        		        FileWriter filewriter = new FileWriter(file , true);

//        		        filewriter.write("'");
        		          filewriter.write(strItemRightKoumoku[iItemRightKoumokuIndex]);
//          		        filewriter.write("'");
           		        filewriter.write(",");
//     		           filewriter.write("\r\n");

        		          filewriter.close();
        		      }catch(IOException e){
        		        System.out.println(e);
        		      }
    			}
            }
            //*テキストファイルから読み込む
			FileTextRead();

		    /*　追記型で、テキストファイルからエクセルファイルへ書き込む
			*
		     * */
            //メールの内容をテキストファイルへ書き込む　OK

            //読み込んだ内容をエクセルファイルへ追記型で書き込む

	}
	/*テキストファイルから読み込む
	 *
	 * */
    private static void FileTextRead() {
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
	    System.out.println("テキストファイルからの読み込み文字列\r\n");
	      while ( ( msg = br.readLine()) != null ) {
	        System.out.println(msg);

	        //テキストファイルから読み込んだら、エクセルへそのまま書き込む
	        ExcelWriteTest(msg);
	      }

	      // 後始末
	      br.close();
	      // エラーがあった場合は、スタックトレースを出力
	    } catch(Exception e) {
	      e.printStackTrace();
	    }
}

   private static  boolean ExcelWriteTest(String strItemLeftKoumoku) throws FileNotFoundException  {
        // 新規にワークブックをメモリ上に作成
        final HSSFWorkbook workbook = new HSSFWorkbook();

        int cnt = 0;
        int sheetNo = 1;
        // シートの作成
        final HSSFSheet worksheet = workbook.createSheet();
        // シート名に日本語を使う場合は明示的にUTF-8
//        workbook.setSheetName(sheetNo, "シート" + sheetNo, HSSFWorkbook.ENCODING_UTF_8);

        // 行 x 列で埋める
        String val;
        int rowIdx = 0;
        short colIdx = 0;
//        for (int rowIdx = 0; rowIdx < 10; rowIdx++) {
            final HSSFRow row = worksheet.createRow(rowIdx);

            val = strItemLeftKoumoku;
           //カンマ区切りにする。
            String[] strs = strItemLeftKoumoku.split(",");
            for (colIdx = 0; colIdx < strs.length; colIdx++ ) {
                // 日本語をセットするためにはUTF-16を指定する必要あり
                final HSSFCell cell = row.createCell(colIdx);
                cell.setEncoding(HSSFCell.ENCODING_UTF_16);

                cell.setCellValue(strs[colIdx]);
                System.out.println(String.format("分割後 %d 個目の文字列 -> %s", colIdx+1, strs[colIdx]));
                // 引数の型を認識してセルに値をセットする。

            }

        // ファイルへ保存
        final OutputStream os = new BufferedOutputStream(
                new FileOutputStream("テスト2.xls"));
        try {
            workbook.write(os);
        } catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        finally {
            try {
				os.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
        }
		return(true);
    }


}

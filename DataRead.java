import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
              System.out.println("ファイルは存在します");

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

        		    bFlg =  ExcelWriteTest(strItemLeftKoumoku[iItemLeftKoumokuIndex]);
    			}
    		    iItemLeftKoumokuIndex++;
    		    iItemRightKoumokuIndex++;
            }
//		    bFlg =  ExcelWriteTest(strItemLeftKoumoku);
            //終了処理
            br.close();
    }

   private static  boolean ExcelWriteTest(String strItemLeftKoumoku) throws FileNotFoundException  {
        // 新規にワークブックをメモリ上に作成
        final HSSFWorkbook workbook = new HSSFWorkbook();

        int cnt = 0;
        int sheetNo = 1;
        // シートの作成
        final HSSFSheet worksheet = workbook.createSheet();
        // シート名に日本語を使う場合は明示的にUTF-16を指定する必要あり。
//        workbook.setSheetName(sheetNo, "シート" + sheetNo, HSSFWorkbook.ENCODING_UTF_16);

        // 行 x 列で埋める
        String val;
        int rowIdx = 0;
        short colIdx = 0;
//        for (int rowIdx = 0; rowIdx < 10; rowIdx++) {
            final HSSFRow row = worksheet.createRow(rowIdx);
//            for (short colIdx = 0; colIdx < 20; colIdx++) {
                final HSSFCell cell = row.createCell(colIdx);
                // 日本語をセットするためにはUTF-16を指定する必要あり
                cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//                val = String.format(val, strItemLeftKoumoku);
                val = strItemLeftKoumoku;
                // 引数の型を認識してセルに値をセットする。
                cell.setCellValue(val);

//                cnt += 1;
//            }
//      }

        // ファイルへ保存
        final OutputStream os = new BufferedOutputStream(
                new FileOutputStream("テスト1.xls"));
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









//private static boolean checkBeforeWritefile(File file){
//  if (file.exists()){
//    if (file.isFile() && file.canWrite()){
//      return true;
//    }
//  }
//
//  return false;
//}


//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.poi.ss.usermodel.Workbook;
//
//public class Sample1_1{
//  public static void main(String[] args){
//    HSSFWorkbook wb1 = new HSSFWorkbook();
//    XSSFWorkbook wb2 = new XSSFWorkbook();
//
//    Workbook wb3 = new HSSFWorkbook();
//    Workbook wb4 = new XSSFWorkbook();
//  }
//}
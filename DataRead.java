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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;


import src.MailItem;

public class DataRead {




	public static void main(String args[]) throws IOException {


//    	//項目の読み込みと比較
    	//メール内容の：の左側のみを読み込む　表示する。
		MailItem();

		String s4 = "：";
		int record=0;
		int iLineLength=0;
		int iTargetItem=0;
		boolean  bFlg=true;

		String[] strLine ;//指定文字で分割した文字を入れる


		//指定デレクトリからファイル名取得
			String path = "D:\\filelist";
		    File dir = new File(path);
		    File[] files = dir.listFiles();
		    for (int i = 0; i < files.length; i++) {
		        File file = files[i];
		        System.out.println((i + 1) + ":    " + file);



              //ファイル名検索に正規表現を使用
//              String str = "注文訂正通知";
              String regex = "注文受付通知|注文取消通知|注文訂正通知|約定通知";
              Pattern p = Pattern.compile(regex);
              String strflies = file.toString();
              Matcher m = p.matcher(strflies);

              if (m.find()){
                System.out.println("マッチしました");
//                //もし、マッチしたのが、注文受付なら
//                int strMacheUketukeFlg = 1; //注文受付
//              //もし、マッチしたのが、注文訂正なら
//                int strMacheTeiseiFlg = 2; //注文訂正

                String matchstr = m.group();
                System.out.println(matchstr + "の部分にマッチしました");
                if (matchstr.equals("注文受付通知")) {
                    System.out.println("注文受付通知です。");
                }
                else if(matchstr.equals("注文取消通知")) {
                    System.out.println("注文取消通知です。");
                }
                else if(matchstr.equals("注文訂正通知")) {
                    System.out.println("注文訂正通知です。");
                }
                else if(matchstr.equals("約定通知")) {
                    System.out.println("約定通知です。");
                }
              }else{
                System.out.println("マッチしません");
              }
		    }
	    	//注文取消通知ファイルを読み込む
            FileReader fr_a = new FileReader("D:\\注文取消通知.txt");
            BufferedReader br = new BufferedReader(fr_a);

            //ファイル種別ごとに処理を分ける
            //読み込んだファイルを１行ずつ処理する
            String line;
            int index;
        	int iItemLeftKoumokuIndex=0;
        	int iItemRightKoumokuIndex=0;
        	int iConnectFlg; //データベースアクセス結果値格納用フラグ
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


        		    /* DBへ接続し書き込み実施
        		     * */
        		    iConnectFlg = MySqlConnection();

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
	private static void MailItem() {
	    //注文受付通知
		String sTyumonUketuke[]=
		{
			"注文パターン",
			"日付",
			"時刻",
			"通貨ペア",
			"注文番号1",
			"新規決済区分1",
			"売買区分1",
			"注文数量1",
			"注文タイプ1",
			"注文価格1",
			"有効期限1",
			"注文番号2",
			"新規決済区分2",
			"売買区分2",
			"注文数量2",
			"注文タイプ2",
			"注文価格2",
			"有効期限2",
			"注文番号",
			"日付",
			"注文数量",
			"注文価格"
		};


	    //	注文訂正通知
		String sTyumonTeisei[]=
		{
				"注文パターン",
				"日付",
				"時刻",
				"通貨ペア",
				"注文番号1",
				"新規決済区分1",
				"売買区分1",
				"注文数量1",
				"注文タイプ1",
				"注文価格1",
				"有効期限1",
				"注文番号2",
				"新規決済区分2",
				"売買区分2",
				"注文数量2",
				"注文タイプ2",
				"注文価格2",
				"有効期限2",
				"注文番号",
				"日付",
				"注文数量",
				"注文価格",
				"注文価格1",
				"有効期限1",
				"注文価格2",
				"有効期限2"
				};

	    //注文取消通知
		String sTyumonTrikeshi[]=
		{
		"注文パターン",
		"日付",
		"時刻",
		"通貨ペア",
		"注文番号1",
		"新規決済区分1",
		"売買区分1",
		"注文数量1",
		"注文タイプ1",
		"注文価格1",
		"有効期限1",
		"注文番号2",
		"新規決済区分2",
		"売買区分2",
		"注文数量2",
		"注文タイプ2",
		"注文価格2",
		"有効期限2"
		};

	    //約定通知
		String sDataItemOrder1[]={
				"注文番号",
				"日付",
				"時刻",
				"新規決済区分",
				"通貨ペア",
				"注文数量",
				"約定価格",
				"注文パターン",
				"日付",
				"時刻",
				"通貨ペア",
				"注文番号1",
				"新規決済区分1",
				"売買区分1",
				"注文数量1",
				"注文タイプ1",
				"注文価格1",
				"有効期限1",
				"注文番号2",
				"新規決済区分2",
				"売買区分2",
				"注文数量2",
				"注文タイプ2",
				"注文価格2",
				"有効期限2",
				"注文番号",
				"日付",
				"注文数量",
				"注文価格"
					    	};
	    }
	/*メールをテキストファイルへ貼り付けた、ファイルから読み込む
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


   /* DBへ接続し書き込み関数
    * */
   private static  int MySqlConnection() {
	        Connection con = null;

	        PreparedStatement ps = null;

	        try {

	            // ドライバクラスをロード
	            Class.forName("com.mysql.jdbc.Driver");

	            // データベースへ接続
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/mysql","root","root");

	            // SQL文を作成(テスト)
	            String sql ="SELECT" +
	            " address_id  " +
	          " FROM " +
	            " sakila.address" ;

	            // ステートメントオブジェクトを生成
	            ps = con.prepareStatement(sql);

	            // クエリーを実行して結果セットを取得
	            ResultSet rs = ps.executeQuery();

	            // 検索された行数分ループ
	            while(rs.next()) {
	                // nameデータを取得
	                String name = rs.getString("address_id");
	                // データの表示
	                System.out.println("address_id;"+" "+name);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                // close処理
	                if(ps != null){
	                    ps.close();
	                }
	                // close処理
	                if(con != null){
	                    con.close();
	                }
	            } catch(SQLException e){
	                e.printStackTrace();
	            }
	        }
	        return(0);
	    }

}

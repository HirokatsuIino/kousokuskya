
/*
 * クラス名　：Sample.java
 *
 * 作成日　 ：2007/09/07
 * 作成者　 ：アイオステクノロジー
 * 最終更新日：2007/09/07
 * 最終更新者：アイオステクノロジー
 *
 */



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class  MySqlConnection {

    public static void main(String[] args) {

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
    }
}
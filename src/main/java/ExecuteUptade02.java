import java.sql.*;

public class ExecuteUptade02 {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_dt",
                "techpro","password");
        Statement st =connection.createStatement();

        //ÖRNEK2:developers tablosuna yeni bir developer ekleyiniz.
        String sql1 = "INSERT INTO developers(name,salary,prog_lang) " +
                "VALUES('Jack Sparrow',5000,'Java')";
        int inserted = st.executeUpdate(sql1);
        System.out.println("Eklenen Kayit Sayisi: "+inserted);
        //tüm kayıtları görelim.
        ResultSet rs =st.executeQuery("SELECT * FROM developers");
        while (rs.next()){

            System.out.println("isim : "+rs.getString("name")+" maaş : "+rs.getDouble("salary"));

        }

        //ÖRNEK3:developers tablosundan id'si 9 olanı siliniz.
       int deleted = st.executeUpdate("DELETE FROM developers WHERE id=9");

        System.out.println("------------Silme Islemden Sonra--------------");
        System.out.println("Silinen Kayit Sayisi: "+deleted);
        ResultSet rs2 =st.executeQuery("SELECT * FROM developers");
        while (rs2.next()){
            System.out.println("isim : "+rs2.getString("name")+" maaş : "+rs2.getDouble("salary"));
        }
        //ÖRNEK3:developers tablosundan id'si 9 olanı siliniz.
        int deleted2 = st.executeUpdate("DELETE FROM developers WHERE id>14");

        System.out.println("------------Silme Islemden Sonra--------------");
        System.out.println("Silinen Kayit Sayisi: "+deleted2);
        ResultSet rs3 =st.executeQuery("SELECT * FROM developers");
        while (rs3.next()){
            System.out.println("isim : "+rs3.getString("name")+" maaş : "+rs3.getDouble("salary"));
        }
    }
}

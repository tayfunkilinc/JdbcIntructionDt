import java.sql.*;

//executeUpdate():DML için kullanılır; INSERT INTO,UPDATE,DELETE
//return:(int) sorgunun sonucundan etkilenen kayıt sayısını verir
public class ExecuteUptade01 {
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_dt",
                "techpro","password");
        Statement st =connection.createStatement();
        //kayitlarin tamamini gorelim
        System.out.println("-------------------UPDATE ONCESI--------------------------");
        ResultSet rs = st.executeQuery("SELECT * FROM developers");
        while (rs.next()){
            System.out.println("isim: "+ rs.getString("name")+" maas: "+rs.getDouble("salary"));
        }
        //  ÖRNEK1:developers tablosunda maaşı ortalama maaştan az olanların maaşını ortalama maaş ile güncelleyiniz
        String sql1 = "UPDATE developers SET salary=(SELECT AVG(salary) FROM developers)" +
                "WHERE salary < (SELECT AVG(salary) FROM developers)";
        int upadated = st.executeUpdate(sql1);
        System.out.println("Guncellenen Kayit Sayisi: " +upadated);

        System.out.println("-------------------UPDATE SONRASI--------------------------");
        ResultSet rs2 = st.executeQuery("SELECT * FROM developers");
        while (rs2.next()){
            System.out.println("isim: "+ rs2.getString("name")+" maas: "+rs2.getDouble("salary"));
        }
    }
}

import java.sql.*;

/*
PreparedStatement; önceden derlenmiş tekrar tekrar kullanılabilen
parametreli sorgular oluşturmamızı ve çalıştırmamızı sağlar.

PreparedStatement Statement ı extend eder(statementın gelişmiş halidir.)
 */

public class PreparedStatement1 {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_dt",
                "techpro","password");
        Statement st =connection.createStatement();

        //ÖRNEK1: bolumler tablosunda Matematik bölümünün taban_puanı'nı 475 olarak güncelleyiniz.
//        String sql1 = "UPDATE bolumler SET taban_puani=475 WHERE bolum ILIKE 'Matematik'";
//        int updated = st.executeUpdate(sql1);
//        System.out.println("updated: "+updated);

        //**Prepared Statement kullanarak bolumler tablosunda Matematik bölümünün taban_puanı'nı 499 olarak güncelleyiniz.

        //parametreli sorugumuzu hazirlayalim
        String sql2 = "UPDATE bolumler SET taban_puani=? WHERE bolum ILIKE ?";
        PreparedStatement prst1= connection.prepareStatement(sql2); // onceden derlenmis dinamik bir sorgu olusturur

        prst1.setInt(1,499); //1. ? yerine 499 degerini yazmis olduk
        prst1.setString(2, "Matematik"); // 2. ? yerine Matematik degerini atamis olduk
        //UPDATE bolumler SET taban_puani=499 WHERE bolum ILIKE Matematik  ---- sorgumu bu hala dinamik olarak getirmis olduk
        prst1.executeUpdate(); //olusturmus oldugumuz dinamik sorgumuzu calistirmis olduk

        //Prepared Statement kullanarak bolumler tablosunda
        // Edebiyat bölümünün taban_puanı'nı 450 olarak güncelleyiniz.
        prst1.setInt(1,450);
        prst1.setString(2, "edebiyat");
        //UPDATE bolumler SET taban_puani=450 WHERE bolum ILIKE Edebiyat
        prst1.executeUpdate(); // derlenmis olan sorguyu calistirma islemini yapiyor

        //Örnek 3:Prepared Statement kullanarak bolumler tablosuna
        // Yazılım Mühendisliği(id=5006,taban_puanı=475, kampus='Merkez') bölümünü ekleyiniz.
        String sql3 = "INSERT INTO bolumler VALUES(?,?,?,?)";
        PreparedStatement prst2 = connection.prepareStatement(sql3);
        prst2.setInt(1,5006);
        prst2.setString(2,"Yazilim Muhendisligi");
        prst2.setInt(3,475);
        prst2.setString(4,"Merkez");
        prst2.executeUpdate();

        st.close();
        prst1.close();
        prst2.close();
        connection.close();
    }
}

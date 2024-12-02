import java.sql.*;

public class ExecuteQuery01 {

    public static void main(String[] args) throws SQLException {


        //n02-ADIM:bağlantıyı oluşturma: db url,kullanıcı adı,password
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_dt",
                "techpro","password");


        //n03-ADIM:Statement oluşturma:SQL ifadelerini veritabanına
        //iletir ve çalıştırır
        Statement st =connection.createStatement();


        //n04-ADIM
        System.out.println("--------------------ÖRNEK 1------------------------");
        //ÖRNEK 1:id'si 5 ile 10 arasında olan ülkelerin "country_name" bilgisini listeleyiniz.
        String sql1="SELECT country_name FROM countries WHERE id BETWEEN 5 AND 10";
        boolean query1=st.execute(sql1);
        System.out.println("query1 = " + query1);

        /*
        verileri alabilmek için:
         JDBC kullanarak veri çekme işlemi sonrasında
        veri listelemek için ResultSet sınıfı kullanılır.

        SQL sorgusu çalıştırıldıktan sonra veritabanından alınan
        verileri saklar. Verilerin arasında gitmemizi sağlar.
        Adv NOT: Veriler üzerinde dolaşmak için next, first, last, previous,
        absolute gibi metotlara sahiptir. Bunun için ayarlama gereklidir.
         */

        ResultSet rs =st.executeQuery(sql1);//default:forward only

        while (rs.next()){
            System.out.println("ülke adı : "+rs.getString("country_name"));
          //  System.out.println("ülke adı : "+rs.getString(1));
        }

        System.out.println("--------------------ÖRNEK 2------------------------");

        //ÖRNEK 2: phone_code'u 200 den büyük olan ülkelerin "phone_code" ve "country_name" bilgisini listeleyiniz.

        ResultSet rs2=st.executeQuery("SELECT phone_code,country_name FROM countries " +
                                          "WHERE phone_code>200 ORDER BY phone_code");

        while (rs2.next()){
//            System.out.println("ülke adı : "+rs2.getString("country_name")+
//                               "telefon kodu : "+rs2.getInt("phone_code"));

            System.out.println("ülke adı : "+rs2.getString(2)+
                    "telefon kodu : "+rs2.getInt(1));
        }

        System.out.println("--------------------ÖRNEK 3------------------------");
        //ÖRNEK 3:developers tablosunda "salary" değeri minimum olan developerların tüm bilgilerini gösteriniz.
        ResultSet rs3= st.executeQuery("SELECT * FROM developers " +
                                           "WHERE salary=(SELECT MIN(salary) FROM developers)");

        while (rs3.next()){
            System.out.println("id : "+rs3.getInt("id")+
                               " --- isim : "+rs3.getString("name")+
                                " --- maas : "+rs3.getDouble("salary")+
                                 " --- prog-dili : "+rs3.getString("prog_lang") );
        }

        System.out.println("--------------------ÖDEV------------------------");
        //ÖDEV:Puanı taban puanlarının ortalamasından yüksek olan öğrencilerin isim ve puanlarını listeleyiniz.
        ResultSet rs4 = st.executeQuery("SELECT isim, puan FROM ogrenciler " +
                "WHERE puan > (SELECT AVG(taban_puani) FROM bolumler) ORDER BY puan DESC");
        while (rs4.next()){
            System.out.println("isim: " + rs4.getString("isim")+"-----puani: "+rs4.getInt("puan"));
        }

        st.close();
        connection.close();

    }


}

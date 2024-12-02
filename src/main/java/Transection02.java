import java.sql.*;

/*
Transaction: DB de en küçük işlem birimi(parçalanamaz,atomik)
Bazı durumlarda transaction yönetimini ele alabiliriz.
Bir veya birden fazla işlemi bir araya getirerek tek bir transaction başlatabiliriz.
Bu işlemlerden en az 1 i başarısız olduğunda yada herhangi bir koşulda
transactionı ROLLBACK ile geri alabiliriz
İşlemlerin tamamı başarılı olduğunda ise COMMIT ile onaylayarak
transactionı sonlandırıp değişiklikleri kalıcı hale getirebiliriz.

 */

public class Transection02 {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_dt",
                "techpro","password");

        Statement st =connection.createStatement();

        st.execute("CREATE TABLE IF NOT EXISTS hesaplar2 (hesap_no INT UNIQUE, isim VARCHAR(50), bakiye REAL)");

        String sql1 = "INSERT INTO hesaplar2 VALUES (?,?,?) ";
        PreparedStatement prst1 = connection.prepareStatement(sql1);
        prst1.setInt(1, 1234);
        prst1.setString(2,"Fred");
        prst1.setDouble(3,9000);
        prst1.executeUpdate();

        prst1.setInt(1, 5678);
        prst1.setString(2,"Barnie");
        prst1.setDouble(3,6000);
        prst1.executeUpdate();

        //TASK: hesap no:1234 ten hesap no:5678 e 1000$ para transferi olsun.
        //ayri iki hesabin update islemlerini birbiri ile bagimli oldugu icin tek bir transaction altinda toplayalim

        ///buraya kadar bir tana transaction ile yaptik bundan sonrasini kendimiz baska transaction icinde yapacagiz
        try {
            connection.setAutoCommit(false); // transaction yonetimi bizde, yeni bir transaction baslattik
            String sql2 = "UPDATE hesaplar2 SET bakiye=bakiye+? WHERE hesap_no=?";
            //1.islem-gonderen hesabin bakiyesi azalacak
            PreparedStatement prst2 = connection.prepareStatement(sql2);
            prst2.setDouble(1, -1000);
            prst2.setInt(2, 1234);
            prst2.executeUpdate();

            //NOT
            /*Savepoint sp = connection.setSavepoint(); // buraya bir kayit noktasi olusturularak bu naktaya donus yapabiliriz ayni transaction icinde olmak kosuluyla
            connection.rollback(sp); // rollback ile sp kayit noktasina donus yapilabilir*/

            //sistemde hata olustu sunucu coktu bu durumda ne olacak bakiye dustu ama gonderim islemini yapamadi
            if (true) { //bunu hatayi gozlemlemek icin yazdik
                throw new RuntimeException();
            }
            //burda artik hata olusmasi durumunda para transfer durumu iptal edilip islemler hic yapilmamis gibi eski durumuna geri donderdi

            //2.islem - alici hesabin bakiyesi artacak
            prst2.setDouble(1, 1000);
            prst2.setDouble(2, 5678);
            prst2.executeUpdate();

            prst2.close();

            //tum islemler basarili ise
            System.out.println("Islem Basarili");
            connection.commit(); // buraya kadar sorunsuz gelindiyse onaylayoruz ve transaction kapatilmasini saglar
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Sistemde Hata Olustu");
            connection.rollback(); // enson commitlenen asamaya geri doner (parametrelisi ise istenen noktaya doner save point ile olusturulan)-- burada hata olusmus ise eski duruma geri donuyoruz
            //islemlerden en az biri basarisiz ise o halde hepsini iptal eder tum islemleri geri alir transaction icindeki islemleri
        }
    }
}

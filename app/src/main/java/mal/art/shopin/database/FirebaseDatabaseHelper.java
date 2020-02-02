package mal.art.shopin.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseHelper {

  public DatabaseReference getDbRef() {
    return FirebaseDatabase.getInstance("https://shopin-f7853.firebaseio.com").getReference("Product");
  }
}

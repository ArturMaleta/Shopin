package mal.art.shopin.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseDatabaseHelper {
  val dbRef: DatabaseReference
    get() = FirebaseDatabase.getInstance("https://shopin-f7853.firebaseio.com").getReference(
      "ShoppingList")
}
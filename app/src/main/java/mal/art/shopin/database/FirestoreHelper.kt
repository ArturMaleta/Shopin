package mal.art.shopin.database

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreHelper {

  companion object {
    val dbReference = Firebase.firestore
  }
}
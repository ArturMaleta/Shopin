package mal.art.shopin.adapter.interfaces

import com.google.firebase.firestore.DocumentSnapshot

interface OnItemClickListener {
  fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int)
}
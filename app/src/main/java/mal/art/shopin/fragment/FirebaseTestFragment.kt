package mal.art.shopin.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import mal.art.shopin.R
import mal.art.shopin.adapter.ShoppingListAdapter
import mal.art.shopin.model.ProductModel
import mal.art.shopin.viewModel.ProductViewModel

class FirebaseTestFragment: Fragment(R.layout.firebase_test_fragment_layout) {

  private lateinit var productViewModel: ProductViewModel
  private lateinit var adapter: ShoppingListAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeRecyclerViewAndViewModel()
  }

  private fun initializeRecyclerViewAndViewModel() {
    productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
    val query = productViewModel.getShoppingListFirestoreReference().orderBy("productName", Query.Direction.DESCENDING)

    val options = FirestoreRecyclerOptions.Builder<ProductModel>()
      .setQuery(query, ProductModel::class.java)
      .build()

    adapter = ShoppingListAdapter(options)
    val recyclerView = view!!.findViewById<RecyclerView>(R.id.firebase_test_fragment_recycler_view)
    recyclerView.let {
      it.layoutManager = LinearLayoutManager(activity)
      it.adapter = adapter
    }
  }

  override fun onStart() {
    super.onStart()
    adapter.startListening()
  }

  override fun onStop() {
    super.onStop()
    adapter.stopListening()
  }

  companion object {
    const val TAG = "FIREBASE_TAG"
  }
}
package mal.art.shopin.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Query
import mal.art.shopin.R
import mal.art.shopin.adapter.ShoppingListAdapter
import mal.art.shopin.extensions.lazyActivityViewModels
import mal.art.shopin.model.ProductModel
import mal.art.shopin.viewModel.ProductViewModel

class ShoppingListFragment(private val shoppingListName: String): Fragment(R.layout.shopping_list_fragment_layout) {

  private val productViewModel: ProductViewModel by lazyActivityViewModels {
    ProductViewModel(activity!!.application)
  }

  private lateinit var _adapter: ShoppingListAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeRecyclerViewAndViewModel()
  }

  private fun initializeRecyclerViewAndViewModel() {
    val query = productViewModel.getShoppingListFirestoreReference(shoppingListName).orderBy("productCategory", Query.Direction.ASCENDING)

    val options = FirestoreRecyclerOptions.Builder<ProductModel>()
      .setQuery(query, ProductModel::class.java)
      .build()

    _adapter = ShoppingListAdapter(options)
    val recyclerView = view!!.findViewById<RecyclerView>(R.id.shopping_list_fragment_recycler_view)
    recyclerView.let {
      it.layoutManager = LinearLayoutManager(activity)
      it.adapter = _adapter
      it.addItemDecoration(DividerItemDecoration(it.context, 1))
    }

    ItemTouchHelper(object : SimpleCallback(0, LEFT or RIGHT) {
      override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
      }

      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        _adapter.deleteProduct(viewHolder.adapterPosition)
      }

    }).attachToRecyclerView(recyclerView)
  }

  override fun onStart() {
    super.onStart()
    _adapter.startListening()
  }

  override fun onStop() {
    super.onStop()
    _adapter.stopListening()
  }

  companion object {
    const val TAG = "SHOPPING_LIST_FRAGMENT_TAG"
  }
}
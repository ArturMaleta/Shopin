package mal.art.shopin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import mal.art.shopin.R
import mal.art.shopin.adapter.ShoppingListViewAdapter
import mal.art.shopin.model.Product
import mal.art.shopin.viewModel.ShoppingListViewModel
import java.util.ArrayList

class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list) {

  private var listOfProducts: MutableList<Product> = ArrayList()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val listName: String = arguments!!.getString("listName")!!

    val recyclerView = view.findViewById<RecyclerView>(R.id.shopping_list_recycler_view)

    val adapter = ShoppingListViewAdapter(activity)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(activity)

    val shoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel::class.java)

    shoppingListViewModel.getShoppingList(listName)!!.observe(this, Observer { shoppingList: DataSnapshot ->
      adapter.getProducts(shoppingList)
      for (date in shoppingList.children) {
        date.getValue(Product::class.java)?.let { listOfProducts.add(it) }
      }
    })

    super.onViewCreated(view, savedInstanceState)
  }

  companion object {
    const val TAG = "SHOPPING_LIST_TAG"
  }
}
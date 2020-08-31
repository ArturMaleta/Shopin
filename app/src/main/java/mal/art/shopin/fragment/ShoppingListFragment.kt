package mal.art.shopin.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import mal.art.shopin.R
import mal.art.shopin.adapter.ShoppingListViewAdapter
import mal.art.shopin.fragment.helper.changeFragment
import mal.art.shopin.model.Product
import mal.art.shopin.viewModel.ShoppingListViewModel
import java.util.ArrayList

class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list), ShoppingListViewAdapter.OnProductClickListener {

  private var listOfProducts: MutableList<Product> = ArrayList()
  private lateinit var listName: String

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    listName = arguments!!.getString("listName")!!
    initializeViewModel()
    initializeFAB()

    super.onViewCreated(view, savedInstanceState)
  }

  private fun initializeViewModel() {
    val recyclerView = view!!.findViewById<RecyclerView>(R.id.shopping_list_recycler_view)

    val adapter = ShoppingListViewAdapter(activity, this)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(activity)

    val shoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel::class.java)

    shoppingListViewModel.getShoppingList(listName)!!.observe(this, Observer { shoppingList: DataSnapshot ->
      adapter.getProducts(shoppingList)
      for (date in shoppingList.children) {
        date.getValue(Product::class.java)?.let { listOfProducts.add(it) }
      }
    })
  }

  private fun initializeFAB() {
    val fab: View = activity?.findViewById(R.id.shopping_list_fab) ?: FloatingActionButton(context)
    fab.setOnClickListener {
      addProductToShoppingList()
    }
  }

  private fun addProductToShoppingList() {
    val bundle = Bundle()
    bundle.putString("shoppingListName", listName)

    val showProducts = ProductsListFragment()
    showProducts.arguments = bundle

    showProducts.changeFragment(
      activity!!.supportFragmentManager,
      R.id.shopping_list_fragment_container,
      showProducts,
      ProductsListFragment.TAG
    )
  }

  override fun onProductClick(position: Int) {
    Toast.makeText(activity, listOfProducts[position].productName, Toast.LENGTH_SHORT).show()
  }

  companion object {
    const val TAG = "SHOPPING_LIST_TAG"
  }
}
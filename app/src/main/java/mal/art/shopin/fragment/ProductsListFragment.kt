package mal.art.shopin.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mal.art.shopin.R
import mal.art.shopin.adapter.ProductListAdapter
import mal.art.shopin.model.Product
import mal.art.shopin.viewModel.ProductViewModel
import java.util.ArrayList

class ProductsListFragment : Fragment(R.layout.products_list_fragment_layout), ProductListAdapter.OnAddProductListener {

  private var productsList: MutableList<Product> = ArrayList()

  private lateinit var productViewModel: ProductViewModel

  private var shoppingListName: String = ""

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initializeViewModel()
    shoppingListName = arguments!!.getString("shoppingListName")!!
  }

  private fun initializeViewModel() {
    val recyclerView = view!!.findViewById<RecyclerView>(R.id.products_list_recycler_view)

    val adapter = ProductListAdapter(activity, this)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(activity)

    productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
    productViewModel.allProducts.observe(this, Observer { products: List<Product>? ->
      adapter.setProducts(products)
      productsList.addAll(products!!)
    })
  }

  override fun onProductClick(position: Int) {
    openProductsDetailsDialog(position)
  }

  private fun openProductsDetailsDialog(position: Int) {
    val bundle = Bundle()
    bundle.putString("shoppingListName", shoppingListName)
    bundle.putString("productName", productsList[position].productName)
    bundle.putString("productCategory", productsList[position].productCategory)

    val openAddProductToShoppingListFragment = AddProductToShoppingListFragment()
    openAddProductToShoppingListFragment.arguments = bundle

    openAddProductToShoppingListFragment.show(activity!!.supportFragmentManager, AddProductToShoppingListFragment.TAG)
  }

  companion object {
    const val TAG = "PRODUCTS_LIST_FRAGMENT"
  }
}
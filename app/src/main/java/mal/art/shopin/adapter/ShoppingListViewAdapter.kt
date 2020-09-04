package mal.art.shopin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.database.DataSnapshot
import mal.art.shopin.R
import mal.art.shopin.adapter.ShoppingListViewAdapter.ShoppingListViewHolder
import mal.art.shopin.model.Product
import mal.art.shopin.model.ProductCategoryEnum

class ShoppingListViewAdapter(
  context: Context?,
  private val onProductClickListener: OnProductClickListener
) : RecyclerView.Adapter<ViewHolder>() {

  inner class ShoppingListViewHolder(
    view: View,
    private val onProductClickListener: OnProductClickListener
  ) : ViewHolder(view), View.OnClickListener {
    var productNameTv: TextView = view.findViewById(R.id.shopping_list_product_name_txtView)
    var productQuantity: TextView = view.findViewById(R.id.shopping_list_product_quantity_txtView)
    var productUnitTv: TextView = view.findViewById(R.id.shopping_list_product_unit_txtView)

    override fun onClick(v: View?) {
      onProductClickListener.onProductClick(adapterPosition)
    }

    init {
      initializeOnClickListener(view, this)
    }
  }

  inner class HeaderViewHolder(view: View) : ViewHolder(view) {
    var productCategory: TextView = view.findViewById(R.id.shopping_list_header_txtView)
  }

  private val inflater: LayoutInflater = LayoutInflater.from(context)
  private val shoppingList: MutableList<Product?>? = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    when (viewType) {
      TYPE_PRODUCT -> return ShoppingListViewHolder(inflater.inflate(R.layout.shopping_list_product_recyclerview_item, parent, false), onProductClickListener)
      TYPE_HEADER -> return HeaderViewHolder(inflater.inflate(R.layout.shopping_list_header_recyclerview_item, parent, false))
    }

    throw Exception("Wrong data")
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    if (shoppingList != null) {
      val currShoppingList = shoppingList[position]

      when (holder) {
        is HeaderViewHolder -> holder.productCategory.text = currShoppingList!!.productCategory.toString()
        is ShoppingListViewHolder -> {
          holder.productNameTv.text = currShoppingList!!.productName
          holder.productQuantity.text = currShoppingList.quantity.toString()
          holder.productUnitTv.text = currShoppingList.productUnit
        }
      }
    }
  }

  fun getProducts(shoppingList: DataSnapshot) {
    for (temp in shoppingList.children) {
      val productName = temp.key
      val productCategory = temp.child("productCategory").value.toString()
      val productUnit = temp.child("productUnit").value.toString()
      val productQuantity = temp.child("quantity").value.toString().toInt()
      val shoppingStatus = temp.child("shoppingStatus").value.toString()

      val product = Product(productName, productCategory, productQuantity, productUnit, shoppingStatus)

      this.shoppingList!!.add(product)
    }
    notifyDataSetChanged()
  }

  override fun getItemCount(): Int {
    return shoppingList?.size ?: 0
  }

  override fun getItemViewType(position: Int): Int {
    when (position) {
      0 -> return TYPE_HEADER
    }

    return TYPE_PRODUCT
  }

  private fun initializeOnClickListener(v: View, vh: ShoppingListViewHolder) {
    v.setOnClickListener(vh)
  }

  interface OnProductClickListener {
    fun onProductClick(position: Int)
  }

  companion object {
    const val TYPE_HEADER = 0
    const val TYPE_PRODUCT = 1
  }
}
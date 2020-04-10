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

class ShoppingListViewAdapter (
  context: Context?
) : RecyclerView.Adapter<ShoppingListViewHolder>() {

  inner class ShoppingListViewHolder (
    view: View
  ) : ViewHolder(view) {
    var productNameTv : TextView = view.findViewById(R.id.shopping_list_product_name_txtView)
    var productQuantity : TextView = view.findViewById(R.id.shopping_list_product_quantity_txtView)
    var productUnitTv : TextView = view.findViewById(R.id.shopping_list_product_unit_txtView)
  }

  private val inflater : LayoutInflater = LayoutInflater.from(context)
  private val shoppingList : MutableList<Product?>? = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
    val v = inflater.inflate(R.layout.products_shopping_list_recyclerview_item, parent, false)
    return ShoppingListViewHolder(v)
  }

  override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
    if (shoppingList != null) {
      val currShoppingList = shoppingList[position]
      holder.productNameTv.text = currShoppingList!!.productName
      holder.productQuantity.text = currShoppingList.quantity.toString()
      holder.productUnitTv.text = currShoppingList.productUnit
    } else {
      holder.productNameTv.text = "Nie ma produktów"
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
}
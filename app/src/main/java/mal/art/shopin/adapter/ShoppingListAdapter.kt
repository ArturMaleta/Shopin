package mal.art.shopin.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import mal.art.shopin.R
import mal.art.shopin.model.ProductModel

class ShoppingListAdapter(
  options: FirestoreRecyclerOptions<ProductModel>
) : FirestoreRecyclerAdapter<ProductModel, ShoppingListAdapter.ShoppingListViewHolder>(options) {

  inner class ShoppingListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var productNameTv: TextView = view.findViewById(R.id.product_name_txtView)
    var quantityTv: TextView = view.findViewById(R.id.quantity_txtView)
    var unitTv: TextView = view.findViewById(R.id.unit_txtView)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
    return ShoppingListViewHolder(LayoutInflater.from(parent.context)
      .inflate(R.layout.shopping_list_recyclerview_item, parent, false))
  }

  override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int, productModel: ProductModel) {
    holder.productNameTv.text = productModel.productName
    holder.quantityTv.text = productModel.quantity.toString()
    holder.unitTv.text = productModel.productUnit
  }

  fun deleteProduct(position: Int) {
    snapshots.getSnapshot(position).reference.delete()
  }
}
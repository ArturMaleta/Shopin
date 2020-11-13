package mal.art.shopin.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import mal.art.shopin.R
import mal.art.shopin.model.ProductModel
import mal.art.shopin.model.ProductCategoryEnum
import mal.art.shopin.model.ProductUnitEnum
import mal.art.shopin.viewModel.ShoppingListViewModel

class AddProductToShoppingListFragment : DialogFragment() {
  private var screenWidth: Int = 0
  private var screeHeight: Int = 0
  private var shoppingListViewModel: ShoppingListViewModel? = null

  private lateinit var shoppingListName: String
  private lateinit var productName: String
  private lateinit var productCategory: String

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    getScreenMetrics()
    initializeProductViewModel()

    return LayoutInflater.from(activity).inflate(R.layout.add_product_to_shopping_list_fragment_layout, null)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    shoppingListName = arguments!!.getString("shoppingListName")!!
    productName = arguments!!.getString("productName")!!
    productCategory = arguments!!.getString("productCategory")!!

    val productNameTv = view.findViewById<TextView>(R.id.to_shopping_list_product_name)
    productNameTv.text = productName

    val productQuantityEt = view.findViewById<EditText>(R.id.to_shopping_list_product_quantity)

    val productUnitSp = view.findViewById<Spinner>(R.id.to_shopping_list_product_unit_spinner)
    productUnitSp.adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, ProductUnitEnum.values())

    val saveShoppingListBtn: Button = view.findViewById(R.id.add_product_to_shopping_list_btn)
    saveShoppingListBtn.setOnClickListener {

      val product = ProductModel(productName, productCategory as ProductCategoryEnum, productQuantityEt.text.toString().toInt(),
                                                                         productUnitSp.selectedItem.toString(), resources.getString(R.string.default_shopping_status))

      shoppingListViewModel!!.addProductToShoppingList(shoppingListName, product)
      this.dismiss()
    }

    super.onViewCreated(view, savedInstanceState)
  }

  private fun initializeProductViewModel() {
    shoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel::class.java)
  }

  override fun onResume() {
    super.onResume()
    setDialogParams()
  }

  private fun setDialogParams() {
    dialog!!.window?.let {
      val dm = DisplayMetrics()
      activity!!.windowManager.defaultDisplay.getMetrics(dm)
      val params = it.attributes
      params.width = (screenWidth * 0.85).toInt()
      params.height = (screeHeight * 0.4).toInt()
      it.attributes = params
    }
  }

  private fun getScreenMetrics() {
    val dm = DisplayMetrics()
    activity!!.windowManager.defaultDisplay.getMetrics(dm)
    screenWidth = dm.widthPixels
    screeHeight = dm.heightPixels
  }

  companion object {
    const val TAG = "ADD_PRODUCT_TO_SHOPPING_LIST_TAG"
  }
}
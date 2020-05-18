package mal.art.shopin.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import mal.art.shopin.R
import mal.art.shopin.model.Product
import mal.art.shopin.model.ProductCategoryEnum
import mal.art.shopin.viewModel.ProductViewModel

class AddNewProductFragment : DialogFragment() {
  private var screenWidth: Int = 0
  private var screeHeight: Int = 0
  private var productViewModel: ProductViewModel? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    getScreenMetrics()
    initializeProductViewModel()

    return LayoutInflater.from(activity).inflate(R.layout.add_new_product_fragment_layout, null)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val productName: EditText = view.findViewById(R.id.product_name_to_save)

    val productCategory: Spinner = view.findViewById(R.id.add_new_product_spinner)
    productCategory.adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, ProductCategoryEnum.values())

    val saveProductBtn = view.findViewById<Button>(R.id.save_button)
    saveProductBtn.setOnClickListener {
      saveProduct(productName, productCategory)
    }
  }

  private fun saveProduct(productName: EditText, productCategory: Spinner) {
    val product = Product(productName.text.toString(), productCategory.selectedItem.toString())
    productViewModel!!.insert(product)
    Log.d("PRODUKT ZAPISANY", product.productName + " " + product.productCategory)
    this.dismiss()
  }

  private fun initializeProductViewModel() {
    productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
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
    const val TAG = "ADD_NEW_PRODUCT_FRAGMENT_TAG"
  }
}
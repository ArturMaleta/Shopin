package mal.art.shopin.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import mal.art.shopin.R
import mal.art.shopin.fragment.AddNewProductFragment

class MainScreen : AppCompatActivity(R.layout.activity_main_screen) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val goToShoppingListBtn: Button = findViewById(R.id.goToShoppingList_btn)
    val addNewProduct: Button = findViewById(R.id.addProduct_btn)

    goToShoppingListBtn.setOnClickListener { v: View? ->
      val shoppingList = ListOfShoppingLists()
      shoppingList.goToListOfShoppingLists(v!!)
    }

    addNewProduct.setOnClickListener {
      val addNewProductFragment = AddNewProductFragment()
      addNewProductFragment.show(supportFragmentManager, AddNewProductFragment.TAG)
    }
  }
}
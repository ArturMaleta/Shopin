package mal.art.shopin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import mal.art.shopin.R;
import mal.art.shopin.model.Product;
import mal.art.shopin.viewModel.ProductViewModel;

public class MainScreen extends AppCompatActivity {

  public static final int NEW_PRODUCT_ACTIVITY_REQUEST_CODE = 1;

  ImageView opacityToBackgroundImage;

  Button goToShoppingList;

  Button goToProductsList;

  Button addNewProduct;

  private ProductViewModel productViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_screen);

    // później graficznie ogarnę tę przezroczystość, żeby tylko zdjęcie ładować, a nie jeszcze ustawiać mu opacity
    opacityToBackgroundImage = findViewById(R.id.opacity_background_image);
    opacityToBackgroundImage.setImageAlpha(80);

    goToShoppingList = findViewById(R.id.goToShoppingList_btn);
    goToShoppingList.setOnClickListener(v -> {
      ShoppingList shoppingList = new ShoppingList();
      shoppingList.goToShoppingList(v);
    });

    goToProductsList = findViewById(R.id.createShoppingList_btn);
    goToProductsList.setOnClickListener(v -> {
      ProductsList productsList = new ProductsList();
      productsList.goToProductsList(v);
    });

    addNewProduct = findViewById(R.id.addProduct_btn);
    addNewProduct.setOnClickListener(v -> {
      Intent goToAddNewProductIntent = new Intent(MainScreen.this, AddNewProduct.class);
      startActivityForResult(goToAddNewProductIntent, NEW_PRODUCT_ACTIVITY_REQUEST_CODE);
    });

    productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == NEW_PRODUCT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
      Product product = data.getParcelableExtra(AddNewProduct.SAVE_PRODUCT_REPLY);
      productViewModel.insert(product);

      Log.d("PRODUKT ZAPISANY", product.getProductName() + " " + product.getProductCategory());
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    View overlay = findViewById(R.id.main_screen_layout);
    overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }
}
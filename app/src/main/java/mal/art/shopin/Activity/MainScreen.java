package mal.art.shopin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import mal.art.shopin.Model.Product;
import mal.art.shopin.R;
import mal.art.shopin.ViewModel.ProductViewModel;

public class MainScreen extends AppCompatActivity {

  public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

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
    goToShoppingList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.goToShoppingList(v);
      }
    });

    goToProductsList = findViewById(R.id.createShoppingList_btn);
    goToProductsList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ProductsList productsList = new ProductsList();
        productsList.goToProductsList(v);
      }
    });

    addNewProduct = findViewById(R.id.addProduct_btn);
    addNewProduct.setOnClickListener(new View.OnClickListener() {  // zamień to na lambdę
      @Override
      public void onClick(View v) {
        Intent goToAddNewProductIntent = new Intent(MainScreen.this, AddNewProduct.class);
        startActivityForResult(goToAddNewProductIntent, NEW_WORD_ACTIVITY_REQUEST_CODE);
      }
    });

    productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
  }

  @Override
  protected void onStart() {
    super.onStart();
    View overlay = findViewById(R.id.main_screen_layout);
    overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
      Product product = data.getParcelableExtra(AddNewProduct.SAVE_PRODUCT_REPLY);
      productViewModel.insert(product);

      Log.d("PRODUKT ZAPISANY", product.getProductName() + " " + product.getProductCategory());
    }
  }
}
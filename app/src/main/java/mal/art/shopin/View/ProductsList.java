package mal.art.shopin.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import mal.art.shopin.Adapter.ProductListAdapter;
import mal.art.shopin.Model.Product;
import mal.art.shopin.R;
import mal.art.shopin.ViewModel.ProductViewModel;

public class ProductsList extends AppCompatActivity {

  private RecyclerView recyclerView;
  private List<Product> productsList = new ArrayList<>();
  private ImageView opacityToBackgroundImage;
  private ProductViewModel mProductViewModel;
  private DatabaseReference dbRef = FirebaseDatabase.getInstance("https://shopin-f7853.firebaseio.com").getReference("Product");

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_products_list);
    recyclerView = findViewById(R.id.product_list_recycler_view);

    // should be done during graphic design
    opacityToBackgroundImage = findViewById(R.id.opacity_background_image);
    opacityToBackgroundImage.setImageAlpha(80);

    final ProductListAdapter adapter = new ProductListAdapter(this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    mProductViewModel.getAllProducts().observe(this, products -> {
      adapter.setProducts(products);
      Log.d("1 PRODUKT", products.get(0).getProductName());
      Log.d("1 PRODUKT KATEGORIA", products.get(0).getCategory());
      Log.d("2 PRODUKT", products.get(1).getProductName());
      Log.d("2 PRODUKT KATEGORIA", products.get(1).getCategory());
      Log.d("3 PRODUKT", products.get(2).getProductName());
      Log.d("3 PRODUKT KATEGORIA", products.get(2).getCategory());
      Log.d("4 PRODUKT", products.get(3).getProductName());
      Log.d("4 PRODUKT KATEGORIA", products.get(3).getCategory());
      Log.d("5 PRODUKT", products.get(4).getProductName());
      Log.d("5 PRODUKT KATEGORIA", products.get(4).getCategory());
      Log.d("6 PRODUKT", products.get(5).getProductName());
      Log.d("6 PRODUKT KATEGORIA", products.get(5).getCategory());
    });
  }

  public void goToProductsList(View view) {
    Intent productsListIntent = new Intent(view.getContext(), ProductsList.class);
    view.getContext().startActivity(productsListIntent);
  }


  //    public void listProductsFromDb() {
  //        ValueEventListener productListener = new ValueEventListener() {
  //            @Override
  //            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
  //            // Finish reading data from db - https://www.youtube.com/watch?v=eCfJMseN0-8
  //            }
  //
  //            @Override
  //            public void onCancelled(@NonNull DatabaseError databaseError) {
  //
  //            }
  //        };
  //        dbReference.addValueEventListener(productListener);
  //    }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      hideSystemUI();
    }
  }

  private void hideSystemUI() {
    View decorView = getWindow().getDecorView();
    decorView.setSystemUiVisibility(
      View.SYSTEM_UI_FLAG_IMMERSIVE
        // Set the content to appear under the system bars so that the
        // content doesn't resize when the system bars hide and show.
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        // Hide the nav bar and status bar
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }
}

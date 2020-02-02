package mal.art.shopin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import mal.art.shopin.Adapter.ProductListAdapter;
import mal.art.shopin.Model.Product;
import mal.art.shopin.R;
import mal.art.shopin.ViewModel.ProductViewModel;

public class ProductsList extends AppCompatActivity implements ProductListAdapter.OnAddProductListener {

  public static final int REQUEST_CODE = 1;

  private RecyclerView recyclerView;

  private List<Product> productsList = new ArrayList<>();

  private ImageView opacityToBackgroundImage;

  private ProductViewModel productViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_products_list);
    recyclerView = findViewById(R.id.product_list_recycler_view);

    // should be done during graphic design
    opacityToBackgroundImage = findViewById(R.id.opacity_background_image);
    opacityToBackgroundImage.setImageAlpha(80);

    final ProductListAdapter adapter = new ProductListAdapter(this, this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    productViewModel.getAllProducts().observe(this, products -> {
      adapter.setProducts(products);
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    View overlay = findViewById(R.id.product_list_layout);
    overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }

  public void goToProductsList(View view) {
    Intent productsListIntent = new Intent(view.getContext(), ProductsList.class);
    view.getContext().startActivity(productsListIntent);
  }

  @Override
  public void onProductClick(int position) {
    // Dokończ to wywoływanie okna do dodawania produktu do listy zakupów. REQUEST_CODE jest placeholderem
    // jak sprawdzić który produkt jest klikalny - czyli do czego robić odowłanie .get(position)
    //    productsList.get(position);
    //    Intent intent = new Intent(ProductsList.this, AddProductToShoppingList.class);
    //    startActivityForResult(intent, REQUEST_CODE);

    Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
  }
}

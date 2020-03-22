package mal.art.shopin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import mal.art.shopin.R;
import mal.art.shopin.adapter.ProductListAdapter;
import mal.art.shopin.model.Product;
import mal.art.shopin.repository.ProductRepository;
import mal.art.shopin.viewModel.ProductViewModel;

public class ProductsList extends AppCompatActivity implements ProductListAdapter.OnAddProductListener {

  public static final int PRODUCT_ADD_REQUEST_CODE = 1;

  private List<Product> productsList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_products_list);
    RecyclerView recyclerView = findViewById(R.id.product_list_recycler_view);

    final ProductListAdapter adapter = new ProductListAdapter(this, this);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    productViewModel.getAllProducts().observe(this, products -> {
      adapter.setProducts(products);
      productsList.addAll(products);
    });
  }

  public void goToProductsList(View view) {
    Intent productsListIntent = new Intent(view.getContext(), ProductsList.class);
    view.getContext().startActivity(productsListIntent);
  }

  @Override
  public void onProductClick(int position) {
    String productName = productsList.get(position).getProductName();
    String productCategory = productsList.get(position).getProductCategory();
    Intent intent = new Intent(ProductsList.this, AddProductToShoppingList.class);
    intent.putExtra("productNameToAdd", productName);
    intent.putExtra("productCategoryToAdd", productCategory);
    startActivityForResult(intent, PRODUCT_ADD_REQUEST_CODE);
//    Toast.makeText(ProductsList.this, "click " + productName, Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == PRODUCT_ADD_REQUEST_CODE && resultCode == RESULT_OK) {
      String productName = data.getStringExtra("productName");
      String productCategory = data.getStringExtra("productCategory");
      int productQuantity = Integer.valueOf(data.getStringExtra("productQuantity"));
      String productUnit = data.getStringExtra("productUnit");
      String shoppingStatus = "pending";

      ProductRepository.insertToShoppingList(productName, productCategory, productQuantity, productUnit, shoppingStatus);

      Log.d("PRODUKT ZAPISANY DO FB", productName + " " + productQuantity + " " + productUnit);
    }
  }
}

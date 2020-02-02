package mal.art.shopin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import mal.art.shopin.R;

public class ShoppingList extends AppCompatActivity {

  ImageView opacityToBackgroundImage;

  private DatabaseReference dbRef = FirebaseDatabase.getInstance("https://shopin-f7853.firebaseio.com").getReference("Product");

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_list);

    // should be done during graphic design
    opacityToBackgroundImage = findViewById(R.id.opacity_background_image);
    opacityToBackgroundImage.setImageAlpha(80);
  }

  @Override
  protected void onStart() {
    super.onStart();
    View overlay = findViewById(R.id.shopping_list_layout);
    overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }

  public void goToShoppingList(View view) {
    Intent shoppingListIntent = new Intent(view.getContext(), ShoppingList.class);
    view.getContext().startActivity(shoppingListIntent);
  }
}

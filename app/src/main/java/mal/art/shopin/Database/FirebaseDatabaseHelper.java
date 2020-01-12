package mal.art.shopin.Database;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import mal.art.shopin.Model.Product;

public class FirebaseDatabaseHelper {

    private DatabaseReference dbRef;
    private List<String> productsList = new ArrayList<>();

    public FirebaseDatabaseHelper() {
        dbRef = FirebaseDatabase.getInstance("https://shopin-f7853.firebaseio.com").getReference("Product");
    }

    public void readProductsFromDb() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productsList.clear();
                for (DataSnapshot productNameSnapshot : dataSnapshot.getChildren()) {
                    Product mProduct = productNameSnapshot.getValue(Product.class);
                    String productName = mProduct.getProductName();
                    productsList.add(productName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

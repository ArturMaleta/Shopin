package mal.art.shopin.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "products")
public class Product {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "product_name")
    public String productName;

    @ColumnInfo(name = "category")
    public String category;

    @Ignore
    @ColumnInfo(name = "quantity")
    public int quantity;

    @Ignore
    public Product() {

    }

    public Product(String productName, String category) {
        this.productName = productName;
        this.category = category;
    }

    public Product(String productName, String category, int quantity) {
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product name='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

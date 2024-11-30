package com.example.admin2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";
    private FirebaseFirestore db;
    private LinearLayout layoutCartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        layoutCartItems = findViewById(R.id.layoutCartItems);

        loadCartItems();
    }

    private void loadCartItems() {
        db.collection("cart")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Map<String, Object> cartItemData = documentSnapshot.getData();
                            String cartItemId = documentSnapshot.getId();
                            addCartItemView(cartItemData, cartItemId);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting cart items", e);
                    }
                });
    }

    private void addCartItemView(Map<String, Object> cartItemData, String cartItemId) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 20);

        LinearLayout cartItemLayout = new LinearLayout(this);
        cartItemLayout.setLayoutParams(layoutParams);
        cartItemLayout.setOrientation(LinearLayout.VERTICAL);

        // Display cart item details
        for (Map.Entry<String, Object> entry : cartItemData.entrySet()) {
            TextView textView = new TextView(this);
            textView.setText(entry.getKey() + ": " + entry.getValue());
            if (entry.getKey().equals("product") ||
                    entry.getKey().equals("price") ||
                    entry.getKey().equals("fullName") ||
                    entry.getKey().equals("contactNumber") ||
                    entry.getKey().equals("address")) {
                textView.setTextColor(ContextCompat.getColor(this, R.color.black));
            }
            cartItemLayout.addView(textView);
        }

        Button btnDelete = new Button(this);
        btnDelete.setText("Delete");
        btnDelete.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCartItem(cartItemId);
            }
        });
        btnDelete.setBackgroundColor(ContextCompat.getColor(this, R.color.button));
        btnDelete.setTextColor(ContextCompat.getColor(this, R.color.white));
        cartItemLayout.addView(btnDelete);

        layoutCartItems.addView(cartItemLayout);
    }

    private void deleteCartItem(String cartItemId) {
        db.collection("cart").document(cartItemId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                        layoutCartItems.removeAllViews();
                        loadCartItems();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error deleting item", e);
                        Toast.makeText(MainActivity.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

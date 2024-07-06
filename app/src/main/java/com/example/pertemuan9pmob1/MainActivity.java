package com.example.pertemuan9pmob1;

import android.content.ClipData;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import android.app.ProgressDialog;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private AdapterList myAdapter;
    private List<ItemList> itemList;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.floatAddNews);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading...");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        myAdapter = new AdapterList(itemList);
        recyclerView.setAdapter(myAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddPage = new Intent(MainActivity.this, NewsAdd.class);
                startActivity(toAddPage);
            }
        });

        myAdapter.setOnItemClickListener(new AdapterList.OnItemClickListener() {
            @Override
            public void onItemClick(ItemList item) {
                Intent intent = new Intent(MainActivity.this, NewsDetail.class);
                intent.putExtra("title", item.getJudul());
                intent.putExtra("desc", item.getSubJudul());
                intent.putExtra("imageUrl", item.getImageUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {
        progressDialog.show();
        db.collection("news")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            itemList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemList item = new ItemList(
                                        document.getString("title"),
                                        document.getString("desc"),
                                        document.getString("imageUrl")
                                );
                                item.setId(document.getId());
                                itemList.add(item);
                                Log.d("data", document.getId() + " => " + document.getData());
                            }
                            myAdapter.notifyDataSetChanged();
                        } else {
                            Log.w("data", "Error getting documents.", task.getException());
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}
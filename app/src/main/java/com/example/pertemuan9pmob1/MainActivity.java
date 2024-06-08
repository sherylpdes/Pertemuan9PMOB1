package com.example.pertemuan9pmob1;

import android.content.ClipData;
import android.os.Bundle;

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

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterList myAdapter;
    private List<ItemList> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        itemList = new ArrayList<>();
        itemList.add(new ItemList("Atomic Habits", "Penulis: James Clear\n" +
                "Tahun Terbit:m2019", "https://www.bigw.com.au/medias/sys_master/images/images/h26/he2/32214790078494.jpg"));
        itemList.add(new ItemList("Start With Why", "Penulis: Simon Sinek\n" +
                "Tahun Terbit: 2009", "https://m.media-amazon.com/images/I/71M1P287BjL._SY425_.jpg"));
        itemList.add(new ItemList("Deep Work", "Penulis: Cal Newport\n" +
                "Tahun Terbit: 2016", "https://m.media-amazon.com/images/I/61TkOFwquPL._SY425_.jpg"));
        itemList.add(new ItemList("The 7 Habits of Highly Effective People", "Penulis: Stephen Covey\n" +
                "Tahun Terbit: 1989", "https://m.media-amazon.com/images/I/71jBBvNvxoL._SY425_.jpg"));

        myAdapter = new AdapterList(itemList);
        recyclerView.setAdapter((RecyclerView.Adapter) myAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
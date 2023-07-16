package com.example.testsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class SearchActivity extends AppCompatActivity {

//    private FirebaseDatabase database;
    RecyclerView recview;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("");

        recview =(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

//        recyclerViewResult = findViewById(R.id.recyclerViewResult);
//        recyclerViewResult.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<item_list> options =
                new FirebaseRecyclerOptions.Builder<item_list>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("item_list").orderByChild("p_name"), item_list.class)
                        .build();

        adapter = new CustomAdapter(options);
        recview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //어뎁터 갱신 시작
        adapter.startListening();
        }

    @Override
    protected void onStop() {
        super.onStop();
        //어뎁터 갱신 중지
        adapter.stopListening();
        }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

            MenuItem item = menu.findItem(R.id.action_search);

            SearchView searchView=(SearchView)item.getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu((menu));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cart) {
            Intent intent = new Intent(SearchActivity.this, MyPageActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<item_list> options =
                new FirebaseRecyclerOptions.Builder<item_list>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("item_list").orderByChild("p_name").startAt(s).endAt(s + "\uf8ff"), item_list.class)
                        .build();

        adapter = new CustomAdapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);
    }
}


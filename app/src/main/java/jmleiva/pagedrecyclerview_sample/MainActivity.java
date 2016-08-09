package jmleiva.pagedrecyclerview_sample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import jmleiva.pagedrecyclerview.PagedRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements PagedRecyclerViewAdapter.Paginator {

    RecyclerView recyclerView;
    SampleAdapter adapter;
    List<String> data;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        initData();

        adapter = new SampleAdapter();
        adapter.setPaginator(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        handler = new Handler();
    }

    private void initData()
    {
        data = new ArrayList<>();

        for(int i = 0; i < 400; i++)
        {
            data.add(Integer.toString(i));
        }
    }

    @Override
    public void loadNewPage()
    {
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                List<String> subData = new ArrayList<>(data.subList(0, 50));
                data.removeAll(subData);

                adapter.addData(subData);
                adapter.stopLoading();
            }
        }, 1000);
    }

    @Override
    public boolean hasMoreData()
    {
        return !data.isEmpty();
    }
}
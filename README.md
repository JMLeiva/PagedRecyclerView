# PagedRecyclerView
RecyclerView adapter with Paging capabilities for Android.
It has all the capabilities of a RecyclerView, but this adapter add easily support for paged (indeterminate lenght) lists.
You can customize the loading layout, and all the data process is done through a simple Paginator interface.

##How to use

####1) Add the library to your proyect

TODO

####2) Create a layout with a RecyclerView in it

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="jmleiva.pagedrecyclerview_sample.MainActivity">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/my_recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scrollbars="vertical"/>
</RelativeLayout>
```

####3) Create a subclass of PagedViewHolder for the "normal" rows, and other for the "loading" row. You can use the base PagedViewHolder instead of subclassing it if your rows are simple enought, and don't need any custom setup.

```
public class SampleHolder
{
    public static class Text extends PagedViewHolder
    {
        public TextView textView;

        public Text(View itemView)
        {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textView);
        }
    }
}
```

####4) Create a subclass of PagedRecyclerViewAdapter, and override the necessary methods

```
public class SampleAdapter extends PagedRecyclerViewAdapter<SampleHolder.Text, PagedViewHolder>
{
    protected List<String> data;

    public SampleAdapter()
    {
        data = new ArrayList<>();
    }

    public void addData(List<String> moreData)
    {
        data.addAll(moreData);
    }

    @Override
    protected int getPagedItemCount()
    {
        return data.size();
    }

    @Override
    protected int getPagedViewType(int position)
    {
       return 0;
    }

    @Override
    protected SampleHolder.Text onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_layout, parent, false);
        return new SampleHolder.Text(view);
    }

    @Override
    protected PagedViewHolder onCreateLoadingViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basic_loading_layout, parent, false);
        return new PagedViewHolder(view);
    }

    @Override
    protected void onBindNormalViewHolder(SampleHolder.Text holder, int position)
    {
        holder.textView.setText(data.get(position));
    }

    @Override
    protected void onBindLoadingViewHolder(PagedViewHolder holder) {
        // Do nothing here
    }
}
```

####5) Add your adapter to the RecyclerView, and set the Paginator, and implement its methods.

```
adapter = new SampleAdapter();
adapter.setPaginator(this);
recyclerView.setAdapter(adapter);
```

####6) After loadMoreData is called in the paginator, and it is done loading the new data, call stopLoading on the adapter.

```
@Override
public void loadNewPage()
{
    doSomeJobToLoadMoreData();
    ...
    ...
    ...
    adapter.stopLoading();
}
```

Remember tha all paginator callbacks are done on the mainThread, so if you have to do some heavy work to get more data (read from a Database, do some network request, etc) you must start that job in another thread.
StopLoading is NOT thread safe, so it must be called in the main thread.
Using an AsyncTask is a good idea here, but as long as you do the work outside the main thread, and call stopLoading() inside it, it is fine.

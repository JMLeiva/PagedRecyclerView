package jmleiva.pagedrecyclerview_sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import jmleiva.pagedrecyclerview.PagedRecyclerViewAdapter;
import jmleiva.pagedrecyclerview.PagedViewHolder;


/*
This file is part of PagedRecyclerView

PagedRecyclerView is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Foobar is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
*/

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
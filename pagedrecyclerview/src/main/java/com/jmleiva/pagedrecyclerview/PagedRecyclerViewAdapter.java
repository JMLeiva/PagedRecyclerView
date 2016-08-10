package com.jmleiva.pagedrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

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

public abstract class PagedRecyclerViewAdapter<T extends PagedViewHolder, E extends PagedViewHolder> extends RecyclerView.Adapter<PagedViewHolder>
{
    public interface Paginator
    {
        void loadNewPage();
        boolean hasMoreData();
    }

    private final static int NORMAL_VIEW_TYPE = 0;
    private final static int LOADING_VIEW_TYPE = 0x70000000;

    /*
    Proxy for getItemCount. Must return the current ammount of items in the RecyclerView
     */
    protected abstract int getPagedItemCount();

    /*
    Proxy for getItemViewType. Must return the current type for a certain position
     */
    protected int getPagedViewType(int position)
    {
        return 0;
    }

    /*
    Proxy for onCreateViewHolder for normal items. Must initialize the view, and return a PagedViewHolder or a subclass.
     */
    protected  abstract T onCreateNormalViewHolder(ViewGroup parent, int viewType);

    /*
    Proxy for onCreateViewHolder for loading item. Must initialize the view, and return a PagedViewHolder or a subclass.
    */
    protected abstract E onCreateLoadingViewHolder(ViewGroup parent, int viewType);

    /*
    Proxy for onBindViewHolder for normal items. Must bind the data in the holder for a certain position
     */
    protected abstract void onBindNormalViewHolder(T holder, int position);

    /*
    Proxy for onBindViewHolder for loading item. Must bind the data in the holder for the loading item.
     */
    protected abstract void onBindLoadingViewHolder(E holder);


    private boolean loading;
    private Paginator paginator;

    public PagedRecyclerViewAdapter()
    {
        loading = false;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    /*
    Call this after loadNewPage has been called on the paginator. Al long as this is no called
    The adapter will continue to be in a loading state.
     */
    public void stopLoading()
    {
        loading = false;
        notifyDataSetChanged();
    }


    private boolean hasMoreData()
    {
        if(paginator != null)
        {
            return  paginator.hasMoreData();
        }

        return false;
    }

    @Override
    final public PagedViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType >= NORMAL_VIEW_TYPE && viewType < LOADING_VIEW_TYPE)
        {
            return onCreateNormalViewHolder(parent, viewType - NORMAL_VIEW_TYPE);
        }
        else
        {
            return onCreateLoadingViewHolder(parent, viewType - LOADING_VIEW_TYPE);
        }
    }

    @Override
    final public void onBindViewHolder(PagedViewHolder holder, int position)
    {
        if(hasMoreData() && position >= getPagedItemCount())
        {
            if(!loading)
            {
                loading = true;

                if(paginator != null)
                {
                    paginator.loadNewPage();
                }
            }

            onBindLoadingViewHolder((E)holder);
        }
        else
        {

            onBindNormalViewHolder((T) holder, position);
        }
    }

    @Override
    final public int getItemViewType(int position)
    {
        if(hasMoreData() && position >= getPagedItemCount())
        {
            return getPagedViewType(position) + LOADING_VIEW_TYPE;
        }
        else
        {
            return getPagedViewType(position) + NORMAL_VIEW_TYPE;
        }

    }

    @Override
    final public int getItemCount()
    {
        return getPagedItemCount() + (hasMoreData() ? 1 : 0);
    }
}
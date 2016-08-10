package com.jmleiva.pagedrecyclerview_sample;

import android.view.View;
import android.widget.TextView;
import com.jmleiva.pagedrecyclerview.PagedViewHolder;
import jmleiva.pagedrecyclerview_sample.R;

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
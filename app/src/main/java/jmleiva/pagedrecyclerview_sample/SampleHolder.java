package jmleiva.pagedrecyclerview_sample;

import android.view.View;
import android.widget.TextView;

import jmleiva.pagedrecyclerview.PagedViewHolder;

/**
 * Created by juanleiva on 8/9/2016.
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
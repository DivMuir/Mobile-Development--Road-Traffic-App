package davidmuir.mobileplaformdevelopmentcourseworkdavidmuir;

/**
 * Created by David on 28/03/2018.
 */



import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.renderscript.Sampler;
import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ReportList extends BaseAdapter implements Filterable
{
    Context context;
    int layoutId;
    List<ReportDisplay> data;
    List<ReportDisplay> mStringFilterList;

    ValueFilter myFilter;

    public ReportList( Context context, int layoutId, List<ReportDisplay> data)
    {
        this.context = context;
        this.layoutId = layoutId;
        this.data = data;
        mStringFilterList = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ReportDisplay getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, layoutId,null);
        TextView locationText = (TextView)v.findViewById(R.id.locationText);
        TextView date = (TextView)v.findViewById(R.id.iddateText);
        TextView days = (TextView)v.findViewById(R.id.daysTextID);

        locationText.setText(data.get(position).title);
        date.setText(data.get(position).startDate + " - " + data.get(position).endDate );

        long mDays = data.get(position).getLengthOfRW();
        days.setText(Long.toString(mDays));

        if (mDays < 7)
        {
            days.setTextColor(Color.GREEN);
        }
        else if (mDays >= 7 && mDays < 14)
        {
            days.setTextColor(Color.rgb(255,191,0));
        }
        else if (mDays > 14)
        {
            days.setTextColor(Color.RED);
        }

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            TextView desc = (TextView)v.findViewById(R.id.DescriptionID);

            if (data.get(position).getWorks() != null)
            {
                desc.setText("Works: " + data.get(position).getWorks() +"\n \n"+ "Traffic Management: " + data.get(position).getManagement());

                if (data.get(position).getDiversionInfo()!=null)
                {
                    desc.setText("Works: " + data.get(position).getWorks() + "\n \n" + "Traffic Management: " + data.get(position).getManagement() + "\n \n" + "Diversion: " + data.get(position).getDiversionInfo());
                }
            }
            else
            {
                desc.setText(data.get(position).desc);
            }
        }

        v.setTag(data.get(position));

        return v;
    }

    public void remove(Object item)
    {
        data.remove(item);
    }


    @Override
    public Filter getFilter()
    {
        if (myFilter == null)
        {
            myFilter = new ValueFilter();
        }

        return myFilter;
    }




    private class ValueFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() >0)
            {
                List<ReportDisplay> filteredList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size();i++)
                {
                    if (mStringFilterList.get(i).title.toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        ReportDisplay item = mStringFilterList.get(i);

                        filteredList.add(item);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }
            else
            {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data = (ArrayList<ReportDisplay>)results.values;
            notifyDataSetChanged();
        }
    }
}

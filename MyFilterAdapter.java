import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MyFilterAdapter extends BaseAdapter implements Filterable {
    List<String> originalList;
    List<String> list;
    Context context;

    public MyFilterAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.originalList = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(list.get(position));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    List<String> resultValue = new ArrayList<>();
                    for (String s : originalList) {
                        if (s.contains(constraint))
                            resultValue.add(s);
                    }
                    filterResults.values = resultValue;
                    filterResults.count = resultValue.size();
                } else {
                    filterResults.values = originalList;
                    filterResults.count = originalList.size();
                }
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}

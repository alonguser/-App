package com.example.WordNotes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.WordNotes.entity.WordBean;
import com.example.WordNotes.R;

import java.util.List;

public class MyBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<WordBean> listData;
    int star1= R.mipmap.star1,star00=R.mipmap.star0;

    public MyBaseAdapter(Context mContext, List<WordBean> listData) {
        this.mContext = mContext;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null){
            view=View.inflate(mContext,R.layout.item_layout,null);
            holder=new ViewHolder();
            holder.TVtitle=(TextView) view.findViewById(R.id.title_id);
            holder.starid=(ImageView) view.findViewById(R.id.star0_id);
            view.setTag(holder);
        }else {
            holder=(ViewHolder) view.getTag();
        }
        holder.TVtitle.setText(listData.get(i).getWordname());
        if (listData.get(i).getStar()==1) {
            holder.starid.setBackgroundResource(star1);
        }else {
            holder.starid.setBackgroundResource(star00);
        }
        return view;
    }
    class ViewHolder{
        TextView TVtitle;
        ImageView starid;

    }
}

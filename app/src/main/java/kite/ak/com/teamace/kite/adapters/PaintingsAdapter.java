package kite.ak.com.teamace.kite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.android.commons.utils.Views;
import com.bumptech.glide.Glide;


import java.util.Arrays;

import kite.ak.com.teamace.kite.R;


public class PaintingsAdapter extends ItemsAdapter<Painting> implements View.OnClickListener {

    public PaintingsAdapter(Context context) {
        super(context);
        setItemsList(Arrays.asList(Painting.getAllPaintings(context.getResources())));
    }

    @Override
    protected View createView(Painting item, int pos, ViewGroup parent, LayoutInflater inflater) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.image = Views.find(view, R.id.list_item_image);
        vh.image.setOnClickListener(this);
        vh.title = Views.find(view, R.id.list_item_title);
        view.setTag(vh);

        return view;
    }

    @Override
    protected void bindView(Painting item, int pos, View convertView) {
        ViewHolder vh = (ViewHolder) convertView.getTag();

        vh.image.setTag(R.id.list_item_image, item);
        Glide.with(convertView.getContext())
                .load(R.drawable.party)
                .dontTransform()
                .dontAnimate()
                .into(vh.image);
        vh.title.setText(item.getTitle());
    }

    @Override
    public void onClick(View view) {
//        Painting item = (Painting) view.getTag(R.id.list_item_image);
//        if (view.getContext() instanceof UnfoldableDetailsActivity) {
//            ((UnfoldableDetailsActivity) view.getContext()).openDetails(view, item);
//        } else if (view.getContext() instanceof FoldableListActivity) {
//            Toast.makeText(view.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
//        }
    }

    private static class ViewHolder {
        ImageView image;
        TextView title;
    }

}

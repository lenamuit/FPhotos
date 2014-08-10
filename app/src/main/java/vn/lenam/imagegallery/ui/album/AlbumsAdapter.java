package vn.lenam.imagegallery.ui.album;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import vn.lenam.imagegallery.R;
import vn.lenam.imagegallery.api.model.GraphAlbum;

/**
 * Created by Le Nam on 09-Aug-14.
 */
public class AlbumsAdapter extends ArrayAdapter<GraphAlbum> {

    public AlbumsAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.item_album, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tvName.setText(getItem(position).getName());
        return convertView;
    }

    static class Holder {
        @InjectView(R.id.tv_name)
        TextView tvName;

        public Holder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

package top.itmp.jiandan2.view.floorview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import top.itmp.jiandan2.R;

/**
 * Created by hz on 16/6/3.
 */
public class SubFloorFactory {

    public View buildSubFloor(Commentable commentable, ViewGroup viewGroup){
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_comment_floor, null);
        RelativeLayout show = (RelativeLayout)view.findViewById(R.id.show_sub_floor_content);
        RelativeLayout hide = (RelativeLayout)view.findViewById(R.id.hide_sub_floor_content);
        show.setVisibility(View.VISIBLE);
        hide.setVisibility(View.GONE);

        TextView floorNum = (TextView)view.findViewById(R.id.sub_floor_num);
        TextView username = (TextView)view.findViewById(R.id.sub_floor_username);
        TextView content = (TextView)view.findViewById(R.id.sub_floor_content);
        floorNum.setText(String.valueOf(commentable.getCommentFloorNum()));
        username.setText(commentable.getAuthorName());
        content.setText(commentable.getCommentContent());
        return view;
    }

    public View buildSubHideFloor(Commentable commentable, ViewGroup viewGroup){
        LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_comment_floor, null);
        RelativeLayout show = (RelativeLayout)view.findViewById(R.id.show_sub_floor_content);
        RelativeLayout hide = (RelativeLayout)view.findViewById(R.id.hide_sub_floor_content);
        show.setVisibility(View.GONE);
        hide.setVisibility(View.VISIBLE);

        TextView hide_text = (TextView)view.findViewById(R.id.hide_text);
        hide_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_comment_down_arrow, 0, 0, 0);
        view.findViewById(R.id.hide_pb).setVisibility(View.GONE);
        return view;
    }
}

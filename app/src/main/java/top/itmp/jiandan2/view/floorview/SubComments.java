package top.itmp.jiandan2.view.floorview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hz on 16/6/3.
 */
public class SubComments {

    private List<? extends Commentable> list;

    public SubComments(List<? extends Commentable> commentables){
        if(commentables != null){
            list = new ArrayList<>(commentables);
        }else {
            list = null;
        }
    }

    public int size(){
        return list == null ? 0 : list.size();
    }

    public int getFloorNum(){
        return list.get(list.size() - 1).getCommentFloorNum();
    }

    public Commentable get(int index){
        return list.get(index);
    }

    public Iterator<? extends Commentable> iterator(){
        return list == null ? null : list.iterator();
    }

}

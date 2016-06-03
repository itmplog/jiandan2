package top.itmp.jiandan2.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.itmp.jiandan2.R;
import top.itmp.jiandan2.callback.LoadCompleteCallBack;
import top.itmp.jiandan2.callback.LoadResultCallBack;
import top.itmp.jiandan2.model.Comment4FreshNews;
import top.itmp.jiandan2.model.Commentator;
import top.itmp.jiandan2.net.Request4CommentList;
import top.itmp.jiandan2.net.Request4FreshNewsCommentList;
import top.itmp.jiandan2.net.RequestManager;
import top.itmp.jiandan2.ui.PushCommentActivity;
import top.itmp.jiandan2.utils.ImageLoadProxy;
import top.itmp.jiandan2.utils.String2TimeUtils;
import top.itmp.jiandan2.utils.UI;
import top.itmp.jiandan2.view.floorview.FloorView;
import top.itmp.jiandan2.view.floorview.SubComments;
import top.itmp.jiandan2.view.floorview.SubFloorFactory;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private ArrayList<Commentator> commentators;
    private ArrayList<Comment4FreshNews> commentators4FreshNews;

    private Activity mActivity;
    private String thread_key;
    private String thread_id;
    private LoadResultCallBack mLoadResultCallBack;
    private boolean isFromFreshNews;

    public CommentAdapter(Activity activity, String thread_key, boolean isFromFreshNews, LoadResultCallBack loadResultCallBack) {
        mActivity = activity;
        this.thread_key = thread_key;
        this.isFromFreshNews = isFromFreshNews;
        mLoadResultCallBack = loadResultCallBack;
        if (isFromFreshNews) {
            commentators4FreshNews = new ArrayList<>();
        } else {
            commentators = new ArrayList<>();
        }
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case Commentator.TYPE_HOT:
            case Commentator.TYPE_NEW:
                return new CommentViewHolder(mActivity.getLayoutInflater().inflate(R.layout
                        .item_comment_flag, parent, false));
            case Commentator.TYPE_NORMAL:
                return new CommentViewHolder(mActivity.getLayoutInflater().inflate(R.layout.item_comment, parent,
                        false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {

        Commentator commentator;
        if (isFromFreshNews) {
            commentator = commentators4FreshNews.get(position);
        } else {
            commentator = commentators.get(position);
        }

        switch (commentator.getType()) {
            case Commentator.TYPE_HOT:
                holder.tv_flag.setText("热门评论");
                break;
            case Commentator.TYPE_NEW:
                holder.tv_flag.setText("最新评论");
                break;
            case Commentator.TYPE_NORMAL:
                final Commentator comment = commentator;
                holder.tv_name.setText(commentator.getName());

                holder.tv_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(mActivity)
                                .setTitle(comment.getName())
                                .setItems(R.array.comment_dialog, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            //评论
                                            case 0:
                                                Intent intent = new Intent
                                                        (mActivity, PushCommentActivity.class);
                                                intent.putExtra("parent_id", comment.getPost_id());
                                                intent.putExtra("thread_id", thread_id);
                                                intent.putExtra("parent_name", comment
                                                        .getName());
                                                mActivity.startActivityForResult(intent, 0);
                                                break;
                                            case 1:
                                                //复制到剪贴板
                                                ClipboardManager clip = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                                                clip.setPrimaryClip(ClipData.newPlainText
                                                        (null, comment.getMessage()));
                                                UI.ShortToast("复制成功!");
                                                break;
                                        }
                                    }
                                })
                                .create().show();
                    }
                });

                    if(isFromFreshNews)
                    {
                        Comment4FreshNews commentators4FreshNews = (Comment4FreshNews) commentator;
                        holder.tv_content.setText(commentators4FreshNews.getCommentContent());
                        ImageLoadProxy.displayHeaderIcon(commentators4FreshNews.getAvatar_url(), holder.img_header);

                        holder.tv_time.setText(String2TimeUtils
                                .dateString2GoodExperienceFormat(commentators4FreshNews.getDate()));
                        if (commentators4FreshNews.getVote_positive() > 0) {
                            holder.oo.setTextColor(Color.RED);
                            holder.oo.setText("OO " + commentators4FreshNews.getVote_positive());
                        }
                        if (commentators4FreshNews.getVote_negative() > 0) {
                            holder.xx.setTextColor(Color.BLUE);
                            holder.xx.setText("XX " + commentators4FreshNews.getVote_negative());
                        }

                    }

                    else

                    {
                        String timeString = commentator.getCreated_at().replace("T", " ");
                        timeString = timeString.substring(0, timeString.indexOf("+"));
                        holder.tv_time.setText(String2TimeUtils.dateString2GoodExperienceFormat(timeString));
                        holder.tv_content.setText(commentator.getMessage());
                        ImageLoadProxy.displayHeaderIcon(commentator.getAvatar_url(), holder.img_header);
                    }

                    //有楼层,盖楼
                    if(commentator.getFloorNum()>1)

                    {
                        SubComments subComments;
                        if (isFromFreshNews) {
                            subComments = new SubComments(addFloors4FreshNews((Comment4FreshNews) commentator));
                        } else {
                            subComments = new SubComments(addFloors(commentator));
                        }

                        holder.floors_parent.setComments(subComments);
                        holder.floors_parent.setFactory(new SubFloorFactory());
                        holder.floors_parent.setBoundDrawer(mActivity.getResources().getDrawable(
                                R.drawable.bg_comment));
                        holder.floors_parent.init();
                    }

                    else

                    {
                        holder.floors_parent.setVisibility(View.GONE);
                    }

                    break;
                }

        }

        private List<Comment4FreshNews> addFloors4FreshNews (Comment4FreshNews commentator){
            return commentator.getParentComments();
        }

        private List<Commentator> addFloors (Commentator commentator){
            //只有一层
            if (commentator.getFloorNum() == 1) {
                return null;
            }
            List<String> parentIds = Arrays.asList(commentator.getParents());
            ArrayList<Commentator> commentators = new ArrayList<>();
            for (Commentator comm : this.commentators) {
                if (parentIds.contains(comm.getPost_id())) {
                    commentators.add(comm);
                }
            }
            Collections.reverse(commentators);
            return commentators;
        }

        @Override
        public int getItemCount () {
            if (isFromFreshNews) {
                return commentators4FreshNews.size();
            } else {
                return commentators.size();
            }
        }

        @Override
        public int getItemViewType ( int position){
            if (isFromFreshNews) {
                return commentators4FreshNews.get(position).getType();
            } else {
                return commentators.get(position).getType();
            }
        }

    public void loadData() {
        RequestManager.addRequest(new Request4CommentList(Commentator.getUrlCommentList(thread_key), new Response
                .Listener<ArrayList<Commentator>>() {
            @Override
            public void onResponse(ArrayList<Commentator> response) {

                if (response.size() == 0) {
                    mLoadResultCallBack.onSuccess(LoadResultCallBack.SUCCESS_NULL, null);
                } else {
                    commentators.clear();

                    ArrayList<Commentator> hotCommentator = new ArrayList<>();
                    ArrayList<Commentator> normalComment = new ArrayList<>();

                    //添加热门评论
                    for (Commentator commentator : response) {
                        if (commentator.getTag().equals(Commentator.TAG_HOT)) {
                            hotCommentator.add(commentator);
                        } else {
                            normalComment.add(commentator);
                        }
                    }

                    //添加热门评论标签
                    if (hotCommentator.size() != 0) {
                        Collections.sort(hotCommentator);
                        Commentator hotCommentFlag = new Commentator();
                        hotCommentFlag.setType(Commentator.TYPE_HOT);
                        hotCommentator.add(0, hotCommentFlag);
                        commentators.addAll(hotCommentator);
                    }

                    //添加最新评论及标签
                    if (normalComment.size() != 0) {
                        Commentator newCommentFlag = new Commentator();
                        newCommentFlag.setType(Commentator.TYPE_NEW);
                        commentators.add(newCommentFlag);
                        Collections.sort(normalComment);
                        commentators.addAll(normalComment);
                    }

                    notifyDataSetChanged();
                    mLoadResultCallBack.onSuccess(LoadResultCallBack.SUCCESS, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLoadResultCallBack.onError(LoadResultCallBack.ERROR, error.getMessage());
            }
        }, new LoadCompleteCallBack() {
            @Override
            public void loadComplete(Object obj) {
                thread_id = (String) obj;
            }
        }), mActivity);
    }


    public void loadData4FreshNews() {
        RequestManager.addRequest(new Request4FreshNewsCommentList(Comment4FreshNews.getUrlComments(thread_key), new Response
                .Listener<ArrayList<Comment4FreshNews>>() {
            @Override
            public void onResponse(ArrayList<Comment4FreshNews> response) {

                if (response.size() == 0) {
                    mLoadResultCallBack.onSuccess(LoadResultCallBack.SUCCESS_NULL, null);
                } else {
                    commentators4FreshNews.clear();

                    //如果评论条数大于6，就选择positive前6作为热门评论
                    if (response.size() > 6) {
                        Comment4FreshNews comment4FreshNews = new Comment4FreshNews();
                        comment4FreshNews.setType(Comment4FreshNews.TYPE_HOT);
                        commentators4FreshNews.add(comment4FreshNews);

                        Collections.sort(response, new Comparator<Comment4FreshNews>() {
                            @Override
                            public int compare(Comment4FreshNews lhs, Comment4FreshNews rhs) {
                                return lhs.getVote_positive() <= rhs.getVote_positive() ? 1 :
                                        -1;
                                //return lhs.getVote_positive() == rhs.getVote_positive() ? -1 : lhs.getVote_positive()
                                //  - rhs.getVote_positive();
                            }
                        });

                        List<Comment4FreshNews> subComments = response.subList(0, 6);

                        for (Comment4FreshNews subComment : subComments) {
                            subComment.setTag(Comment4FreshNews.TAG_HOT);
                        }
                        commentators4FreshNews.addAll(subComments);
                    }

                    Comment4FreshNews comment4FreshNews = new Comment4FreshNews();
                    comment4FreshNews.setType(Comment4FreshNews.TYPE_NEW);
                    commentators4FreshNews.add(comment4FreshNews);

                    Collections.sort(response);

                    for (Comment4FreshNews comment4Normal : response) {
                        if (comment4Normal.getTag().equals(Comment4FreshNews.TAG_NORMAL)) {
                            commentators4FreshNews.add(comment4Normal);
                        }
                    }

                    notifyDataSetChanged();
                    mLoadResultCallBack.onSuccess(LoadResultCallBack.SUCCESS, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLoadResultCallBack.onError(LoadResultCallBack.ERROR, error.getMessage());
            }
        }, new LoadCompleteCallBack() {
            @Override
            public void loadComplete(Object obj) {
                thread_id = (String) obj;
            }
        }), mActivity);
    }

    public String getThreadId() {
        return thread_id;
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.tv_name)
        TextView tv_name;
        @Nullable
        @BindView(R.id.tv_content)
        TextView tv_content;
        @Nullable
        @BindView(R.id.tv_flag)
        TextView tv_flag;
        @Nullable
        @BindView(R.id.tv_time)
        TextView tv_time;
        @Nullable
        @BindView(R.id.img_header)
        ImageView img_header;
        @Nullable
        @BindView(R.id.floors_parent)
        FloorView floors_parent;
        @Nullable
        @BindView(R.id.xx)
        TextView xx;
        @Nullable
        @BindView(R.id.oo)
        TextView oo;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setIsRecyclable(false);
        }
    }
}
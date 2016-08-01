package com.douncoding.busnotifier.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.Route;

import java.util.ArrayList;

/**
 * 즐겨찾기 목록
 *
 * TODO MVP
 */
public class BookmarkListView extends RelativeLayout {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BookmarkAdapter mAdapter;

    ArrayList<Route> mBookmarkList = new ArrayList<>();

    public BookmarkListView(Context context) {
        super(context);
        init();
    }

    public BookmarkListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_bookmark, this);

        mRecyclerView = (RecyclerView)findViewById(R.id.item_list);

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new BookmarkAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(48));

        test();
    }

    private void test() {
        Route route = new Route();
        route.setRouteName("50");
        route.setRouteType("경기 안성시 일반버스");

        addBookmark(route);
        addBookmark(route);
        addBookmark(route);
        addBookmark(route);
    }

    public void addBookmark(Route route) {
        mBookmarkList.add(route);
        mAdapter.notifyDataSetChanged();
    }

    OnListener onListener;
    public interface OnListener {
        void onItemClick(View view, Route route);
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.DataHolder> {

        class DataHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
            TextView mRouteNumber;
            TextView mRouteDesc;
            ImageView mMoreButton;

            PopupMenu mPopupMenu;

            public DataHolder(View itemView) {
                super(itemView);

                mRouteNumber = (TextView)itemView.findViewById(R.id.number_txt);
                mRouteDesc = (TextView)itemView.findViewById(R.id.desc_txt);
                mMoreButton = (ImageView)itemView.findViewById(R.id.route_more_btn);

                // 추가기능 (팝업메뉴) 생성
                mPopupMenu = new PopupMenu(getContext(), mMoreButton);
                mPopupMenu.getMenuInflater().inflate(R.menu.bookmark_more_menu, mPopupMenu.getMenu());
                mPopupMenu.setOnMenuItemClickListener(DataHolder.this);

                mMoreButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupMenu.show();
                    }
                });

                // 전체 클릭 -> 노선 세부정부
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onListener != null)
                            onListener.onItemClick(view, mBookmarkList.get(getPosition()));
                    }
                });
            }

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                int position = getPosition();

                // 팝업 메뉴의 삭제버튼 선택
                if (id == R.id.bookmark_del) {
                    mBookmarkList.remove(position);
                    notifyItemRemoved(position);

                    //TODO 비즈니스 처리 (데이터 삭제)
                }

                return true;
            }
        }

        @Override
        public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_bookmark_list_item, parent, false);
            return new DataHolder(view);
        }

        @Override
        public void onBindViewHolder(DataHolder holder, int position) {
            Route route = mBookmarkList.get(position);

            if (route != null) {
                holder.mRouteNumber.setText(route.getRouteName());
                holder.mRouteDesc.setText(route.getRouteType());
            }
        }

        @Override
        public int getItemCount() {
            return mBookmarkList.size();
        }
    }

    /**
     * 아이템간의 공백 설정
     */
    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mVerticalSpaceHeight;

        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }
}

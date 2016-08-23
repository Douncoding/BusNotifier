package com.douncoding.busnotifier.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.repository.BookmarkRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * 최근 검색기록
 *
 *
 * TODO 1.MVP 로컬 데이터 저장/읽기 등.. 영속성 처리
 */
public class RecentSearchLogView extends RelativeLayout {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecentSearchLogAdapter mAdapter;

    ArrayList<Route> mRouteList;

    BookmarkRepository bookmarkRepository;

    public RecentSearchLogView(Context context) {
        super(context);
        init();

    }

    public RecentSearchLogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_recentlog, this);

        mRecyclerView = (RecyclerView)findViewById(R.id.item_list);

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RecentSearchLogAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        bookmarkRepository = BookmarkRepository.getInstance(getContext());
        update();
    }

    public void update() {
        List<Route> list = bookmarkRepository.getList(BookmarkRepository.TYPE_RECENT);
        setDataStore(list);
    }

    /**
     * 신규 검색기록 추가
     * @param routes 최근 검색목록에 추가할 노선 클래스
     */
    private void setDataStore(List<Route> routes) {
        mRouteList = new ArrayList<>(routes);
        mAdapter.notifyDataSetChanged();
    }

    OnListener onListener;
    public interface OnListener {
        // 즐겨찾기 등록 시 외부 처리로직 구현
        void onBookmarkClick(View view, Route route);
        // 아이템 클릭 시 외부 처리로직 구현
        void onItemClicked(View view, Route route);
    }
    
    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }
    
    class RecentSearchLogAdapter extends RecyclerView.Adapter<RecentSearchLogAdapter.DataHolder> {

        public class DataHolder extends RecyclerView.ViewHolder {
            ImageView mBookmarkButton;
            TextView mRouteText;
            ImageView mClearButton;

            public DataHolder(View itemView) {
                super(itemView);

                mBookmarkButton = (ImageView)itemView.findViewById(R.id.bookmark_btn);
                mRouteText = (TextView)itemView.findViewById(R.id.route_txt);
                mClearButton = (ImageView)itemView.findViewById(R.id.clear_btn);
                
                // 즐겨찾기 버튼 클릭
                mBookmarkButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getPosition();
                        Route route = mRouteList.get(position);
                        bookmarkRepository.add(route, BookmarkRepository.TYPE_BOOKMARK);

                        mRouteList.remove(position);
                        notifyItemRemoved(position);

                        if (onListener != null) {
                            onListener.onBookmarkClick(view, route);
                        }
                    }
                });
                
                // 최근 검색기록 삭제
                mClearButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getPosition();

                        bookmarkRepository.del(mRouteList.get(position));

                        mRouteList.remove(position);
                        notifyItemRemoved(position);
                    }
                });
            }
        }

        @Override
        public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_recentlog_list_item, parent, false);

            return new DataHolder(view);
        }

        @Override
        public void onBindViewHolder(DataHolder holder, int position) {
            final Route route = mRouteList.get(position);
            
            holder.mRouteText.setText(route.getRouteName());

            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onListener != null) {
                        onListener.onItemClicked(view, route);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mRouteList.size();
        }
    }
}

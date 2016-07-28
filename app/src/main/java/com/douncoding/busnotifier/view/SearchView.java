package com.douncoding.busnotifier.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.douncoding.busnotifier.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 검색
 */
public class SearchView extends RelativeLayout {

    EditText mEditText;
    ImageView mImageView;

    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_serach, this);

        mEditText = (EditText)findViewById(R.id.target_edit);
        mImageView = (ImageView)findViewById(R.id.search_btn);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onListener != null) {
                    onListener.onSearchClick(view, mEditText.getText().toString());
                }
            }
        });
    }

    OnListener onListener;
    public interface OnListener {
        void onSearchClick(View view, String target);
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }
}

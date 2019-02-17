package util;

import android.view.View;

public interface ItemClickListener {
    void onItemClick(View child, int position);

    void onItemLongClick(View child, int position);
}

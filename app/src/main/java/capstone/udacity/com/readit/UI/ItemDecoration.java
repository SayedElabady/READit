package capstone.udacity.com.readit.UI;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import capstone.udacity.com.readit.R;

/**
 * Created by Sayed El-Abady on 2/10/2018.
 */
public class ItemDecoration extends RecyclerView.ItemDecoration {

    private final int decorationHeight , decorationRight, decorationEnd;
    private Context context;
    private boolean isSpecialityItem;
    public ItemDecoration(Context context) {
        this.context = context;
        decorationHeight = context.getResources().getDimensionPixelSize(R.dimen.decoration_height);
        decorationRight = context.getResources().getDimensionPixelSize(R.dimen.decoration_right);
        decorationEnd = 10;
        isSpecialityItem = false;
    }

    public ItemDecoration(Context context, int decorationEnd, boolean isSpecialityItem) {
        this.isSpecialityItem = isSpecialityItem;
        this.decorationEnd = decorationEnd;
        this.context = context;
        decorationHeight = context.getResources().getDimensionPixelSize(R.dimen.decoration_height);
        decorationRight = context.getResources().getDimensionPixelSize(R.dimen.decoration_right);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent != null && view != null) {

            int itemPosition = parent.getChildAdapterPosition(view);
            int totalCount = parent.getAdapter().getItemCount();

            if (itemPosition >= 0 && itemPosition < totalCount - 1) {
                outRect.bottom = decorationHeight;
            }
            if (isSpecialityItem){
                outRect.right = decorationEnd;
                outRect.bottom = decorationHeight;
            }
        }
//            if (itemPosition >= 0 && itemPosition < totalCount - 1) {
//                outRect.right = decorationRight;
//            }
//
//        }

    }
}

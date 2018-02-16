package capstone.udacity.com.readit.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import capstone.udacity.com.readit.Adapters.BooksAdapter;
import capstone.udacity.com.readit.Database.BooksDBHelper;
import capstone.udacity.com.readit.Helper.Tags;
import capstone.udacity.com.readit.Listeners.OnBookClicked;
import capstone.udacity.com.readit.MainActivity;
import capstone.udacity.com.readit.Models.Book;
import capstone.udacity.com.readit.R;
import capstone.udacity.com.readit.UI.ItemDecoration;

/**
 * Created by Sayed El-Abady on 2/9/2018.
 */

public class FavouriteFragment extends Fragment implements OnBookClicked {

    @BindView(R.id.favourite_rec)
    RecyclerView favouriteRec;
    Unbinder unbinder;
    BooksAdapter booksAdapter;
    ItemDecoration itemDecoration;
    BooksDBHelper booksDBHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View favouriteFragment = inflater.inflate(R.layout.favourite_fragment, container, false);
        unbinder = ButterKnife.bind(this, favouriteFragment);
        booksDBHelper = new BooksDBHelper(getContext());
        initFavouriteRecycler();
        return favouriteFragment;

    }

    private void initFavouriteRecycler() {
        booksAdapter = new BooksAdapter(this);
        favouriteRec.setAdapter(booksAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setItemPrefetchEnabled(false);
        favouriteRec.setLayoutManager(layoutManager);
        itemDecoration = new ItemDecoration(getContext());
        favouriteRec.addItemDecoration(itemDecoration);
        booksAdapter.setBooks(booksDBHelper.getFavouriteBooks());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(Book book) {
        ViewBookFragment viewBookFragment = new ViewBookFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("book" , book);
        bundle.putSerializable("isFavourited" , true);
        viewBookFragment.setArguments(bundle);
        ((MainActivity) getActivity()).addFragment(viewBookFragment , Tags.VIEW_BOOK_DETAILS_TAG);
    }
}

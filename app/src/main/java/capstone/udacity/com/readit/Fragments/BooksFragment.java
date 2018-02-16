package capstone.udacity.com.readit.Fragments;

import android.content.ContentProvider;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import capstone.udacity.com.readit.Adapters.BooksAdapter;
import capstone.udacity.com.readit.BusinessLayer.Firebase;
import capstone.udacity.com.readit.Helper.Tags;
import capstone.udacity.com.readit.Listeners.OnBookClicked;
import capstone.udacity.com.readit.Listeners.OnFetchingCompleted;
import capstone.udacity.com.readit.MainActivity;
import capstone.udacity.com.readit.Models.Book;
import capstone.udacity.com.readit.R;
import capstone.udacity.com.readit.UI.ItemDecoration;
import capstone.udacity.com.readit.Widget.BooksService;

/**
 * Created by Sayed El-Abady on 2/9/2018.
 */

public class BooksFragment extends Fragment  implements OnBookClicked{
    FloatingActionButton addBookFab;
    RecyclerView booksRecycler;
    BooksAdapter booksAdapter;
    Firebase firebase;
    ItemDecoration itemDecoration;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View booksView = inflater.inflate(R.layout.books_fragment , container , false);
        initViews(booksView);
        firebase = new Firebase();
        addBookFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBookFragment addBookFragment = new AddBookFragment();
                ((MainActivity) getActivity())
                        .addFragment(addBookFragment , Tags.ADD_BOOK_TAG);
            }
        });
        setBooksRecycler();

        return booksView;
    }
    private void setBooksRecycler(){

        firebase.getBooks(new OnFetchingCompleted() {
            @Override
            public void onDataFetched(List<Book> books) {
                booksAdapter.setBooks(books);
                BooksService.startUpdateService(context , (ArrayList<Book>) books);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getContext() , "there was error fetching the data, " + errorMessage , Toast.LENGTH_LONG).show();
                BooksService.startUpdateService(context , new ArrayList<Book>());
            }
        });
    }
    private void initViews(View booksView){
        addBookFab = booksView.findViewById(R.id.add_book_fab);
        booksRecycler = booksView.findViewById(R.id.books_rec);
        booksAdapter = new BooksAdapter(this);
        booksRecycler.setAdapter(booksAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setItemPrefetchEnabled(false);
        booksRecycler.setLayoutManager(layoutManager);
        itemDecoration = new ItemDecoration(getContext());
        booksRecycler.addItemDecoration(itemDecoration);
    }

    @Override
    public void onClick(Book book) {
        ViewBookFragment viewBookFragment = new ViewBookFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("book" , book);
        bundle.putSerializable("isFavourited" , false);
        viewBookFragment.setArguments(bundle);
        ((MainActivity) getActivity()).addFragment(viewBookFragment , Tags.VIEW_BOOK_DETAILS_TAG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}

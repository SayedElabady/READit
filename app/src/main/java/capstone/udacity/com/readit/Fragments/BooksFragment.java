package capstone.udacity.com.readit.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * Created by Sayed El-Abady on 2/9/2018.
 */

public class BooksFragment extends Fragment  implements OnBookClicked{
    FloatingActionButton addBookFab;
    RecyclerView booksRecycler;
    BooksAdapter booksAdapter;
    Firebase firebase;
    ItemDecoration itemDecoration;
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
        booksAdapter = new BooksAdapter(this);
        booksRecycler.setAdapter(booksAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setItemPrefetchEnabled(false);
        booksRecycler.setLayoutManager(layoutManager);
        itemDecoration = new ItemDecoration(getContext());
        booksRecycler.addItemDecoration(itemDecoration);
        firebase.getBooks(new OnFetchingCompleted() {
            @Override
            public void onDataFetched(List<Book> books) {
                booksAdapter.setBooks(books);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
    private void initViews(View booksView){
        addBookFab = booksView.findViewById(R.id.add_book_fab);
        booksRecycler = booksView.findViewById(R.id.books_rec);

    }

    @Override
    public void onClick(Book book) {
        
    }
}

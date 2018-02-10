package capstone.udacity.com.readit.Adapters;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import capstone.udacity.com.readit.Listeners.OnBookClicked;
import capstone.udacity.com.readit.Listeners.OnRecyclerItemClicked;
import capstone.udacity.com.readit.Models.Book;
import capstone.udacity.com.readit.R;

/**
 * Created by Sayed El-Abady on 2/10/2018.
 */

public class BooksAdapter extends SectioningAdapter implements OnRecyclerItemClicked {

    List<Book> books;
    List<Section> sections = new ArrayList<>();
    OnBookClicked bookListener;
    @Override
    public void onItemClicked(int position) {
        bookListener.onClick(books.get(position));
    }
    public BooksAdapter(OnBookClicked bookListener){
        this.bookListener = bookListener;;
    }
    private class Section {
        String alpha;
        List<Book> books = new ArrayList<>();
    }
    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder implements View.OnClickListener{
        TextView bookName;
        OnRecyclerItemClicked listener ;
        public ItemViewHolder(View itemView , OnRecyclerItemClicked listener) {
            super(itemView);
            this.listener = listener;
            bookName = itemView.findViewById(R.id.book_name_text);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClicked(getAdapterPosition());
        }
    }
    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder{
        TextView sectionChar;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            sectionChar = itemView.findViewById(R.id.section_char);

        }
    }
    public static Comparator<Book> nameComparator = new Comparator<Book>() {
        @Override
        public int compare(Book e1, Book e2) {
            return  (e1.getBookName().compareTo( e2.getBookName()));
        }
    };
    public void setBooks(List<Book> books) {
        this.books = books;
        Collections.sort(books , nameComparator);
        sections.clear();
        char alpha = 0;

        Section currentSection = null;
        for (Book book : books) {
            if (Character.toUpperCase(book.getBookName().charAt(0)) != alpha) {
                if (currentSection != null) {
                    sections.add(currentSection);
                }

                currentSection = new Section();
                alpha = Character.toUpperCase(book.getBookName().charAt(0));
                currentSection.alpha = String.valueOf(alpha);
            }

            if (currentSection != null) {
                currentSection.books.add(book);
            }
        }

        if (currentSection != null) {
            sections.add(currentSection);
        }
        try {

            notifyAllSectionsDataSetChanged();
        } catch (Exception e) {
        }
    }
    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).books.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.books_item, parent, false);
        return new ItemViewHolder(v, this);

    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.books_header, parent, false);
        return new HeaderViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        Section s = sections.get(sectionIndex);
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        Book book = s.books.get(itemIndex);
        holder.bookName.setText(book.getBookName());
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        Section s = sections.get(sectionIndex);
        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;
        hvh.sectionChar.setText(s.alpha);

    }
}

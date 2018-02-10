package capstone.udacity.com.readit.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sayed El-Abady on 2/9/2018.
 */

public class Book implements Parcelable {
    private String bookName;
    private String description;
    private String category;
    private String imageUri;
    private String ownerID;
    int id;
    public Book(){}
    protected Book(Parcel in) {
        bookName = in.readString();
        description = in.readString();
        category = in.readString();
        imageUri = in.readString();
        ownerID = in.readString();
        id = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }




    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.bookName);
        parcel.writeString(this.description);
        parcel.writeString(this.category);
        parcel.writeString(this.imageUri);
        parcel.writeString(this.ownerID);
        parcel.writeInt(id);

    }
}

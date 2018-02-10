package capstone.udacity.com.readit.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sayed El-Abady on 2/2/2018.
 */

public class Account implements Parcelable {
    String address;
    String name;
    String email;
    String uID;

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String phone;
    String password;
    public Account(Parcel in) {
        this.email = in.readString();
        this.address = in.readString();
        this.password = in.readString();
        this.phone = in.readString();
        this.name = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    public Account(){}
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.email);
        parcel.writeString(this.address);
        parcel.writeString(this.password);
        parcel.writeString(this.phone);
        parcel.writeString(this.name);
    }
}

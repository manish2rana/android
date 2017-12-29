package com.ecs.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Manish on 29-12-2017.
 */

public class ContactDetail implements Parcelable{

    private String contactName;
    private String contactNumber;

    public ContactDetail(){}
    protected ContactDetail(Parcel in) {
        contactName = in.readString();
        contactNumber = in.readString();
    }

    public static final Creator<ContactDetail> CREATOR = new Creator<ContactDetail>() {
        @Override
        public ContactDetail createFromParcel(Parcel in) {
            return new ContactDetail(in);
        }

        @Override
        public ContactDetail[] newArray(int size) {
            return new ContactDetail[size];
        }
    };

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(contactName);
        parcel.writeString(contactNumber);
    }
}

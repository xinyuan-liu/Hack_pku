package com.ipaulpro.afilechooserexample;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by apple on 16/4/10.
 */
public class c implements Parcelable {
    public String path;
    public int score;
    public c(String p, int s)
    {
        path = p;
        score = s;
    }
    public int describeContents()
    {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(path);
        out.writeInt(score);
    }
    static class Sort implements Comparator {
        public int compare(Object o1, Object o2) {
            c s1 = (c) o1;
            c s2 = (c) o2;
            if (s1.score > s2.score)
                return 1;
            return 0;
        }
    }
    public static final Parcelable.Creator<c> CREATOR = new Parcelable.Creator<c>()
    {
        public  c createFromParcel(Parcel in)
        {
            String a = in.readString();
            int b = in.readInt();
            return new c(a,b);
        }
        public c[] newArray(int size)
        {
            return new c[size];
        }
    };
}

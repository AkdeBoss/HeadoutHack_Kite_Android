package kite.ak.com.teamace.kite.adapters;

import android.content.res.Resources;
import android.content.res.TypedArray;

import kite.ak.com.teamace.kite.R;


public class Painting {

    private final String title;
    private final String year;
    private final String location;

    private Painting(String title, String year, String location) {

        this.title = title;
        this.year = year;
        this.location = location;
    }



    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getLocation() {
        return location;
    }

    public static Painting[] getAllPaintings(Resources res) {
        String[] titles = {"Daily Bread","A Cafe","Concorde Cuppa Beverages Pvt Ltd (Big Sirls)","Gokul CafÃ©","Muffets and Tuffets"};
        String[] years = {"2015","2015","2015"};
        String[] locations = {"\"7th Cross Road, Koramangala 3 Block, Koramangala, Bengaluru","11, 7th Cross Road, Jakasandra Block, Koramangala, Bengaluru","99, Ground Floor, 4th B Block, Near-BDA Complex, Koramangala, Bengaluru","No. 154, 8th Main Road, 3rdBlock, Koramangala, Bengaluru","43, 8th B Main Road, Koramangala 4th Block, Koramangala, Bengaluru","82, 7th Cross Road, Koramangala 4-B Block, Koramangala 4th Block, Koramangala, Bengaluru"};


        int size = titles.length;
        Painting[] paintings = new Painting[size];

        for (int i = 0; i < size; i++) {
            paintings[i] = new Painting(titles[i], "2015", locations[i]);
        }


        return paintings;
    }

}

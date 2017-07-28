package util;

import android.provider.BaseColumns;

/**
 * Created by Fugi on 7/27/2017.
 */

public class Contract{
    public static class TABLE_ARTICLES implements BaseColumns {
        //Added image column to store image url from json results
        public static final String TABLE_NAME = "news";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_DESC = "desc";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_IMAGE = "img";
    }
}

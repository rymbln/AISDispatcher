package com.example.dispatchermobile;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import com.example.dispatchermobile.models.CompanyItem;
import com.example.dispatchermobile.models.TaskItem;

import java.util.ArrayList;


public class SuggestionProvider extends ContentProvider {

    private static final String tag = "SuggestUrlProvider";
    public static String AUTHORITY = "com.example.DispatcherMobile.SuggestionProvider";

    private static final int SEARCH_SUGGEST = 0;
    private static final int SHORTCUT_REFRESH = 1;
    private static final UriMatcher sURIMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher =
                new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHORITY,
                SearchManager.SUGGEST_URI_PATH_QUERY,
                SEARCH_SUGGEST);
        matcher.addURI(AUTHORITY,
                SearchManager.SUGGEST_URI_PATH_QUERY +
                        "/*",
                SEARCH_SUGGEST);
        matcher.addURI(AUTHORITY,
                SearchManager.SUGGEST_URI_PATH_SHORTCUT,
                SHORTCUT_REFRESH);
        matcher.addURI(AUTHORITY,
                SearchManager.SUGGEST_URI_PATH_SHORTCUT +
                        "/*",
                SHORTCUT_REFRESH);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sURIMatcher.match(uri)) {
            case SEARCH_SUGGEST:
                if (selectionArgs == null) {
                    throw new IllegalArgumentException(
                            "selectionArgs must be provided for the Uri: " + uri);
                }
                return getSuggestions(selectionArgs[0]);
            case SHORTCUT_REFRESH:
                return null;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }


    private static final String[] COLUMNS = {
            "_id",  // must include this column
            SearchManager.SUGGEST_COLUMN_TEXT_1,
            SearchManager.SUGGEST_COLUMN_TEXT_2,
            SearchManager.SUGGEST_COLUMN_INTENT_DATA,
            SearchManager.SUGGEST_COLUMN_INTENT_ACTION,
            SearchManager.SUGGEST_COLUMN_SHORTCUT_ID
    };

    private Cursor getSuggestions(String query) {
        if (query == null) return null;
        else {
            MatrixCursor cursor = new MatrixCursor(COLUMNS);
            if (Common.ACTIVE_SCREEN == 1) {
                ArrayList<TaskItem> tasks = MyApplication.getDataProvider().getSearchForTasks(query);
                if (tasks.size() > 0) {
                    for (TaskItem item : tasks) {
                        cursor.addRow(createRow1(item));
                    }
                }
            } else {
                ArrayList<CompanyItem> companies = MyApplication.getDataProvider().getSearchForCompanies(query);
                if (companies.size() > 0) {
                    for (CompanyItem item : companies) {
                        cursor.addRow(createRow2(item));
                    }
                }
            }
            return cursor;
        }

    }

    private Object[] createRow1(TaskItem item) {
        return columnValuesOfQuery(item.getTaskID(),
                "android.intent.action.VIEW",
                item.getTaskID(),
                item.getCompanyName() + " " + item.getDeliveryTime(),
                item.getAddress());
    }

    private Object[] createRow2(CompanyItem item) {
        return columnValuesOfQuery(item.getCompanyID(),
                "android.intent.action.VIEW",
                item.getCompanyID(),
                item.getCompanyName(),
                item.getAddress());
    }

    //
    private Object[] columnValuesOfQuery(String query,
                                         String intentAction,
                                         String url,
                                         String text1,
                                         String text2) {
        return new String[]{
                query,     // _id
                text1,     // text1
                text2,     // text2
                url,
                // intent_data (included when clicking on item)
                intentAction, //action
                SearchManager.SUGGEST_NEVER_MAKE_SHORTCUT
        };
    }


    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case SEARCH_SUGGEST:
                return SearchManager.SUGGEST_MIME_TYPE;
            case SHORTCUT_REFRESH:
                return SearchManager.SHORTCUT_MIME_TYPE;
            default:
                throw
                        new IllegalArgumentException("Unknown URL " + uri);
        }
    }
}

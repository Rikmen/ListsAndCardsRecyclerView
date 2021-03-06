//package com.hathy.listsandcards;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.NoConnectionError;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//
//public class ActivityChanell extends Activity {
//    private static final String TAG = ActivityChanell.class.getSimpleName();
//    private RecyclerView rv;
//    // private CircleProgressBar mPrgLoading;
//
//    // Create arraylist variable to store video data before get video duration
//    private ArrayList<HashMap<String, String>> mTempVideoData = new ArrayList<>();
//    // Create arraylist variable to store final data
//    private ArrayList<HashMap<String, String>> mVideoData     = new ArrayList<>();
//    private String[] mChannelNamesSt;
//    private String[] mVideoTypesSt;
//    private String[] mChannelIdsSt;
//    private ArrayList<String> mChannelNames, mVideoTypes, mChannelIds;
//    private String mNextPageToken = "";
//    private int mNumberOfLooping = 0;
//    private String mVideoIds = "";
//    private String mDuration = "00:00";
//    private boolean mIsFirstVideo = true;
//    private boolean mIsAppFirstLaunched = true;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.recyclerview_activity);
//
//        mVideoData = new ArrayList<>();
//        rv=(RecyclerView)findViewById(R.id.rv);
//        // Get channels data from strings.xml
//        mChannelNamesSt  = getResources().getStringArray(R.array.channel_names);
//        mVideoTypesSt    = getResources().getStringArray(R.array.video_types);
//        mChannelIdsSt    = getResources().getStringArray(R.array.channel_ids);
//
//        mChannelNames = new ArrayList<>(Arrays.asList(mChannelNamesSt));
//        mVideoTypes    = new ArrayList<>(Arrays.asList(mVideoTypesSt));
//        mChannelIds    = new ArrayList<>(Arrays.asList(mChannelIdsSt));
//        //  mPrgLoading = (CircleProgressBar) findViewById(R.id.prgLoading);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        rv.setLayoutManager(llm);
//        rv.setHasFixedSize(true);
//
//        initializeData();
////        initializeAdapter();
//    }
//
//    private void initializeData(){
//        for(int j = 0; j < mChannelIds.size(); j++) {
//            // Create array variable to store first video id of the videos channel
//            final String[] videoId = new String[1];
//
//            // Create variable to store youtube api url
//            String url;
//            // Get video type
//            final int mVideoType = Integer.parseInt(mVideoTypes.get(1));
//            // Check whether it is channel or playlist
//            if(mVideoType == 2) {
//                // Youtube API url for playlist
//                url = Utils.API_YOUTUBE + Utils.FUNCTION_PLAYLIST_ITEMS_YOUTUBE +
//                        Utils.PARAM_PART_YOUTUBE + "snippet,id&" +
//                        Utils.PARAM_FIELD_PLAYLIST_YOUTUBE + "&" +
//                        Utils.PARAM_KEY_YOUTUBE + getResources().getString(R.string.youtube_apikey) + "&" +
//                        Utils.PARAM_PLAYLIST_ID_YOUTUBE + mChannelIds.get(1) + "&" +
//                        Utils.PARAM_PAGE_TOKEN_YOUTUBE + mNextPageToken + "&" +
//                        Utils.PARAM_MAX_RESULT_YOUTUBE + Utils.ARG_NUMBER_OF_NEW_VIDEO;
//
//            }else {
//                // Youtube API url for channel
//                url = Utils.API_YOUTUBE + Utils.FUNCTION_SEARCH_YOUTUBE +
//                        Utils.PARAM_PART_YOUTUBE + "snippet,id&" + Utils.PARAM_ORDER_YOUTUBE + "&" +
//                        Utils.PARAM_TYPE_YOUTUBE + "&" +
//                        Utils.PARAM_FIELD_SEARCH_YOUTUBE + "&" +
//                        Utils.PARAM_KEY_YOUTUBE + getResources().getString(R.string.youtube_apikey) + "&" +
//                        Utils.PARAM_CHANNEL_ID_YOUTUBE + mChannelIds.get(1) + "&" +
//                        Utils.PARAM_PAGE_TOKEN_YOUTUBE + mNextPageToken + "&" +
//                        Utils.PARAM_MAX_RESULT_YOUTUBE + Utils.ARG_NUMBER_OF_NEW_VIDEO;
//            }
//
//            // Get youtube channel or playlist name
//            final String channelName = mChannelNames.get(1);
//            JsonObjectRequest request = new JsonObjectRequest(url, null,
//                    new Response.Listener<JSONObject>() {
//                        JSONArray dataItemArray;
//                        JSONObject itemIdObject, itemSnippetObject, itemSnippetThumbnailsObject,
//                                itemSnippetResourceIdObject;
//
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            // Set mVideoIds empty, we need video ID to get video duration
//                            mVideoIds = "";
//                            // Create array variable to store first video id of the videos channel
//                            final String[] videoId = new String[1];
//
//                            // Create variable to store youtube api url
//                            String url;
//                            // Check whether it is channel or playlist (in this case is "Acoustic Covers")
//                            if(mVideoType == 2) {
//                                // Youtube API url for playlist
//                                url = Utils.API_YOUTUBE + Utils.FUNCTION_PLAYLIST_ITEMS_YOUTUBE +
//                                        Utils.PARAM_PART_YOUTUBE + "snippet,id&" +
//                                        Utils.PARAM_FIELD_PLAYLIST_YOUTUBE + "&" +
//                                        Utils.PARAM_KEY_YOUTUBE + getResources().getString(R.string.youtube_apikey) + "&" +
//                                        Utils.PARAM_PLAYLIST_ID_YOUTUBE + mChannelId + "&" +
//                                        Utils.PARAM_PAGE_TOKEN_YOUTUBE + mNextPageToken + "&" +
//                                        Utils.PARAM_MAX_RESULT_YOUTUBE + Utils.PARAM_RESULT_PER_PAGE;
//                            }else {
//                                // Youtube API url for channel
//                                url = Utils.API_YOUTUBE + Utils.FUNCTION_SEARCH_YOUTUBE +
//                                        Utils.PARAM_PART_YOUTUBE + "snippet,id&" + Utils.PARAM_ORDER_YOUTUBE + "&" +
//                                        Utils.PARAM_TYPE_YOUTUBE + "&" +
//                                        Utils.PARAM_FIELD_SEARCH_YOUTUBE + "&" +
//                                        Utils.PARAM_KEY_YOUTUBE + getResources().getString(R.string.youtube_apikey) + "&" +
//                                        Utils.PARAM_CHANNEL_ID_YOUTUBE + mChannelId + "&" +
//                                        Utils.PARAM_PAGE_TOKEN_YOUTUBE + mNextPageToken + "&" +
//                                        Utils.PARAM_MAX_RESULT_YOUTUBE + Utils.PARAM_RESULT_PER_PAGE;
//                            }
//
//                            JsonObjectRequest request = new JsonObjectRequest(url, null,
//                                    new Response.Listener<JSONObject>() {
//                                        JSONArray dataItemArray;
//                                        JSONObject itemIdObject, itemSnippetObject, itemSnippetThumbnailsObject,
//                                                itemSnippetResourceIdObject;
//                                        @Override
//                                        public void onResponse(JSONObject response) {
//                                            // To make sure Activity is still in the foreground
//                                            Context activity = getApplicationContext();
//                                            if(activity != null){
//                                                try {
//                                                    // Get all Items json Array from server
//                                                    dataItemArray = response.getJSONArray(Utils.ARRAY_ITEMS);
//
//                                                    if (dataItemArray.length() > 0) {
//                                                        haveResultView();
//                                                        // количество видео
//                                                        for (int i = 0; i < dataItemArray.length(); i++) {
//                                                            HashMap<String, String> dataMap = new HashMap<>();
//
//                                                            // Detail Array per Item
//                                                            JSONObject itemsObject = dataItemArray.getJSONObject(i);
//                                                            // Array snippet to get title and thumbnails
//                                                            itemSnippetObject = itemsObject.
//                                                                    getJSONObject(Utils.OBJECT_ITEMS_SNIPPET);
//
//                                                            if(mVideoType == 2){
//                                                                // Get video ID in playlist
//                                                                itemSnippetResourceIdObject = itemSnippetObject.
//                                                                        getJSONObject(Utils.OBJECT_ITEMS_SNIPPET_RESOURCEID);
//                                                                dataMap.put(Utils.KEY_VIDEO_ID, itemSnippetResourceIdObject.getString(Utils.KEY_VIDEO_ID));
//                                                                videoId[0] = itemSnippetResourceIdObject.getString(Utils.KEY_VIDEO_ID);
//
//                                                                // Concat all video IDs and use it as parameter to
//                                                                // get all video durations.
//                                                                mVideoIds = mVideoIds + itemSnippetResourceIdObject.getString(Utils.KEY_VIDEO_ID) + ",";
//                                                            }else {
//                                                                // Get video ID in channel
//                                                                itemIdObject = itemsObject.
//                                                                        getJSONObject(Utils.OBJECT_ITEMS_ID);
//                                                                dataMap.put(Utils.KEY_VIDEO_ID,
//                                                                        itemIdObject.getString(Utils.KEY_VIDEO_ID));
//                                                                videoId[0] = itemIdObject.getString(Utils.KEY_VIDEO_ID);
//
//                                                                // Concat all video IDs and use it as parameter to
//                                                                // get all video durations.
//                                                                mVideoIds = mVideoIds + itemIdObject.getString(Utils.KEY_VIDEO_ID) + ",";
//                                                            }
//
//                                                            // When fragment first created display first video to
//                                                            // video player.
//                                                            if(mIsFirstVideo && i == 0) {
//                                                                mIsFirstVideo = false;
//                                                                mCallback.onVideoSelected(videoId[0]);
//                                                            }
//
//                                                            // Get video title
//                                                            dataMap.put(Utils.KEY_TITLE,
//                                                                    itemSnippetObject.getString(Utils.KEY_TITLE));
//
//                                                            // Convert ISO 8601 date to string
//                                                            String formattedPublishedDate = Utils.formatPublishedDate(
//                                                                    getApplicationContext(),
//                                                                    itemSnippetObject.getString(Utils.KEY_PUBLISHEDAT));
//
//                                                            // Get published date
//                                                            dataMap.put(Utils.KEY_PUBLISHEDAT, formattedPublishedDate);
//
//                                                            // Get video thumbnail
//                                                            itemSnippetThumbnailsObject = itemSnippetObject.
//                                                                    getJSONObject(Utils.OBJECT_ITEMS_SNIPPET_THUMBNAILS);
//                                                            itemSnippetThumbnailsObject = itemSnippetThumbnailsObject.
//                                                                    getJSONObject
//                                                                            (Utils.OBJECT_ITEMS_SNIPPET_THUMBNAILS_MEDIUM);
//                                                            dataMap.put(Utils.KEY_URL_THUMBNAILS,
//                                                                    itemSnippetThumbnailsObject.getString
//                                                                            (Utils.KEY_URL_THUMBNAILS));
//
//                                                            // Store video data temporarily to get video duration
//                                                            mTempVideoData.add(dataMap);
//                                                        }
//
//                                                        // After finish getting video IDs, titles, and thumbnails
//                                                        // now get video duration.
//                                                      //  getDuration();
//
//                                                        // Condition if dataItemArray == result perpage it means maybe
//                                                        // server still have data
//                                                        if (dataItemArray.length() == Utils.PARAM_RESULT_PER_PAGE) {
//                                                            // To get next page data youtube have parameter Next Page Token
//                                                            mNextPageToken = response.getString(Utils.ARRAY_PAGE_TOKEN);
//
//                                                            // No data anymore in this URL
//                                                        } else {
//                                                            // Clear mNextPageToken
//                                                            mNextPageToken = "";
//                                                           // disableLoadmore();
//                                                        }
//
//                                                        // If success get data, it means next it is not first time again.
//                                                        mIsAppFirstLaunched = false;
//
//                                                        // Data from server already load all or no data in server
//                                                    }
////                                                    else {
////                                                        if (mIsAppFirstLaunched &&
////                                                                mAdapterList.getAdapterItemCount() <= 0) {
////                                                            noResultView();
////                                                        }
//                                                      //  disableLoadmore();
//                                                    }
//
//                                                } catch (JSONException e) {
//                                                    Log.d(Utils.TAG_PONGODEV + TAG, "JSON Parsing error: " +
//                                                            e.getMessage());
//                                                    //mPrgLoading.setVisibility(View.GONE);
//                                                }
//                                               // mPrgLoading.setVisibility(View.GONE);
//                                            }
//                                        }
//                                    },
//
//                                    new Response.ErrorListener() {
//
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//                                            // To make sure Activity is still in the foreground
//                                            Context activity = getApplicationContext();
//                                            if(activity != null){
//                                                Log.d(Utils.TAG_PONGODEV + TAG, "on Error Response: " + error.getMessage());
//                                                // "try-catch" To handle when still in process and then application closed
//                                                try {
//                                                    String msgSnackBar;
//                                                    if (error instanceof NoConnectionError) {
//                                                        msgSnackBar = getResources().getString(R.string.no_internet_connection);
//                                                    } else {
//                                                        msgSnackBar = getResources().getString(R.string.response_error);
//                                                    }
//
//                                                    // To handle when no data in mAdapter and then get error because no
//                                                    // connection or problem in server.
//                                                    if (mVideoData.size() == 0) {
//                                                       // retryView();
//
//                                                        // Condition when loadmore, it have data when loadmore then
//                                                        // get error because no connection.
//                                                    }
////                                                    else {
////                                                        mAdapterList.setCustomLoadMoreView(null);
////                                                        mAdapterList.notifyDataSetChanged();
////                                                    }
//
//                                                    //Utils.showSnackBar(getActivity(), msgSnackBar);
//                                                   // mPrgLoading.setVisibility(View.GONE);
//
//                                                } catch (Exception e) {
//                                                    Log.d(Utils.TAG_PONGODEV + TAG, "failed catch volley " + e.toString());
//                                                    //mPrgLoading.setVisibility(View.GONE);
//                                                }
//                                            }
//                                        }
//                                    }
//                            );
//                            request.setRetryPolicy(new DefaultRetryPolicy(Utils.ARG_TIMEOUT_MS,
//                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                            MySingleton.getInstance(getApplicationContext()).getRequestQueue().add(request);
//        ///////////////////////////////////////////////////////////////////////////////////////
//        ///////////////////////////////////////////////////////////////////////////////////////
//        ///////////////////////////////////////////////////////////////////////////////////////
//
//    }
//
////    private void initializeAdapter(){
////        RVAdapter adapter = new RVAdapter(getApplicationContext(),mTempVideoData, new CustomItemClickListener() {
////            @Override
////            public void onItemClick(View v, int position) {
////                Toast.makeText(RecyclerViewActivity.this, "Clicked Item: "+position,Toast.LENGTH_SHORT).show();
////            }
////        });
////        rv.setAdapter(adapter);
////    }
//}

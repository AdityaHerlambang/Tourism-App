package com.artace.tourism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.artace.tourism.adapter.TourAdapter;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.constant.Field;
import com.artace.tourism.model.ModelTour;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProviderTourFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProviderTourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProviderTourFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String url, TAG = "ProviderTour";
    TourAdapter adapterTour;
    List<ModelTour> dataListTours = new ArrayList<ModelTour>();
    RecyclerView toursRecyclerView;
    FrameLayout rootView;
    FloatingActionButton fabPlus;

    private OnFragmentInteractionListener mListener;

    public ProviderTourFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProviderTourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProviderTourFragment newInstance(String param1, String param2) {
        ProviderTourFragment fragment = new ProviderTourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_provider_tour, container, false);
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_provider_tour, container, false);
        setRecyclerTour();
        actionFabPlus();
        return rootView;
    }

    public void actionFabPlus(){
        fabPlus = rootView.findViewById(R.id.provider_tour_plusTour);
        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateTourActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    public void setRecyclerTour(){
        toursRecyclerView = (RecyclerView) rootView.findViewById(R.id.provider_tour_RecyclerTour);
        adapterTour = new TourAdapter(getContext(), dataListTours);
        toursRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        toursRecyclerView.setAdapter(adapterTour);

        loadDataTour();
    }

    public void loadDataTour(){
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(Field.getLoginSharedPreferences(), Context.MODE_PRIVATE);
        String idProvider = sharedpreferences.getString("provider_id",null);
        url = DatabaseConnection.getTourProvider(idProvider);

        Map<String,String> params = new HashMap<String, String>();
        params.put("emptyvalue","emptyvalue");
        Log.d(TAG,params.toString());
        Log.d(TAG,url);

        StringPostRequest strReq = new StringPostRequest();
        strReq.sendRequest(Request.Method.GET,getContext(), params, url, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                dataListTours.clear();
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                ModelTour modelTour = new ModelTour();
                                modelTour.setId(obj.getString("id"));
                                modelTour.setName(obj.getString("name"));
                                modelTour.setImage(obj.getString("image"));
                                modelTour.setShort_description(obj.getString("short_description"));
                                modelTour.setDuration_day(obj.getInt("duration_hour"));
                                modelTour.setDuration_hour(obj.getInt("duration_day"));
                                modelTour.setAdult_price(obj.getInt("adult_price"));
                                modelTour.setLocation(obj.getString("location"));
                                modelTour.setCustomStatus("fragment");
                                modelTour.setStatus(obj.getString("status"));
                                if (modelTour.getStatus().equals("1")){
                                    dataListTours.add(modelTour);
                                }


                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }finally {
                                adapterTour.notifyItemChanged(i);
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG,"2 = " + e.getMessage().toString());
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String message) {
                Log.e(TAG,"Ada ERROR : "+message);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

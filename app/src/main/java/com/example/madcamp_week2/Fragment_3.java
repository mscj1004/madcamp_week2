package com.example.madcamp_week2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_3 newInstance(String param1, String param2) {
        Fragment_3 fragment = new Fragment_3();
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
        View rootView = inflater.inflate(R.layout.fragment_3, container, false);
        Spinner topsptype = rootView.findViewById(R.id.sp_top_type);
        Spinner topspcolor = rootView.findViewById(R.id.sp_top_color);
        Spinner botsptype = rootView.findViewById(R.id.sp_bottom_type);
        Spinner botspcolor = rootView.findViewById(R.id.sp_bottom_color);
        Spinner accsptype = rootView.findViewById(R.id.sp_accessory_type);
        Spinner accspcolor = rootView.findViewById(R.id.sp_accessory_color);
        Spinner outsptype = rootView.findViewById(R.id.sp_top_color);
        Spinner outspcolor = rootView.findViewById(R.id.sp_outer_color);


        // Inflate the layout for this fragment

        String a = (String) topsptype.getSelectedItem();

        return rootView;
    }
}
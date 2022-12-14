package com.example.subwaymateui.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.subwaymateui.Notifications;
import com.example.subwaymateui.R;
import com.example.subwaymateui.Search_Station;
import com.example.subwaymateui.Settings;
import com.example.subwaymateui.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private Search_Station ss = new Search_Station();

    private FragmentHomeBinding binding;
    private ImageButton announcement_button;
    private ImageButton settings_button;
    private SearchView fragment_home_searchView;  //
    private WebView fragment_home_weather_webView;
    private String url = "https://www.weather.go.kr/w/index.do";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fragment_home_weather_webView = (WebView) view.findViewById(R.id.fragment_home_weather_webView);
        WebSettings webSettings = fragment_home_weather_webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        fragment_home_weather_webView.setWebViewClient(new WebViewClient());
        fragment_home_weather_webView.loadUrl(url);


        announcement_button = (ImageButton) view.findViewById(R.id.announcement_button);
        announcement_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HOME FRAGMENT?????? ???????????? IMAGEBUTTON??? ???????????? ???????????? ????????? ????????????
                Intent intent = new Intent(getActivity(), Notifications.class);
                startActivity(intent);
                Log.d("MAIN_ANNOUNCEMENT", "onClick: ????????????");
            }
        });

        settings_button = (ImageButton) view.findViewById(R.id.settings_button);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HOME FRAGMENT?????? ???????????? IMAGEBUTTON??? ???????????? ???????????? ????????? ????????????
                Log.d("MAIN_SETTINGS", "onClick: ????????????");
                Intent intent = new Intent(getActivity(), Settings.class);
                startActivity(intent);
            }
        });

        // searchview ??????
        fragment_home_searchView = (SearchView) view.findViewById(R.id.fragment_home_searchView);
        fragment_home_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override  //???????????? ????????? ?????????
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(getActivity(), Search_Station.class);
                intent.putExtra("searchingstation", s);
                startActivity(intent);
                return true;
            }

            @Override  //????????? ????????? ?????? ?????????
            public boolean onQueryTextChange(String s) {
                //System.out.println(s);
                return true;
            }

        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
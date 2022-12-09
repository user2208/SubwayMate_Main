package com.example.subwaymateui;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Route_Algorithm extends AppCompatActivity {
    private ImageButton search_back_button;
    private Button least_time_button;
    private Button least_distance_button;
    private Button least_money_button;

    List<List<String>> list = null;

    private String departure = "";
    private String arrival = "";


    Graph g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_route);

        departure = getIntent().getStringExtra("dep");
        arrival = getIntent().getStringExtra("arr");

        TextView departure_view = (TextView) findViewById(R.id.departure_view);
        departure_view.setText(departure);
        TextView arrival_view = (TextView) findViewById(R.id.arrival_view);
        arrival_view.setText(arrival);

        g = new Graph(Integer.parseInt(departure), Integer.parseInt(arrival));
        try {
            list = readDataFromCsv();
        } catch (IOException e) {
            e.printStackTrace();
        }



        least_time_button = (Button) findViewById(R.id.least_time_button);
        least_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                for (List<String> item : list) {
                    if (i == 0) {
                        i += 1;
                    }else {
                        g.input(Integer.valueOf(item.get(0)),Integer.valueOf(item.get(1)),Integer.valueOf(item.get(2)));
                    }
                }
                makeRoute(g);
            }
        });
        least_distance_button = (Button) findViewById(R.id.least_distance_button);
        least_distance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                for (List<String> item : list) {
                    if (i == 0) {
                        i += 1;
                    }else {
                        g.input(Integer.valueOf(item.get(0)),Integer.valueOf(item.get(1)),Integer.valueOf(item.get(3)));
                    }
                }
                makeRoute(g);
            }
        });
        least_money_button = (Button) findViewById(R.id.least_money_button);
        least_money_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                for (List<String> item : list) {
                    if (i == 0) {
                        i += 1;
                    }else {
                        g.input(Integer.valueOf(item.get(0)),Integer.valueOf(item.get(1)),Integer.valueOf(item.get(4)));
                    }
                }
                makeRoute(g);
            }
        });


        search_back_button = (ImageButton) findViewById(R.id.search_back_button);
        search_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onPause();
                onBackPressed();
                //finish();
            }
        });

    }


    static class Graph{
        int num_station;
        int m_station; //간선 개수
        int departures; //출발지
        int arrivals; //도착지

        int min_route = Integer.MAX_VALUE;  //도착지까지 역개수

        Stack<Integer> route = new Stack<>();
        Stack<Integer> saved_weight = new Stack<>(); //거리 합산을 위한 스택

        boolean visited[] = new boolean[1000];
        int[][] weight = new int[1000][1000];

        public Graph(int departures, int arrivals) {
            this.departures = departures;
            this.arrivals = arrivals;
        }

        public void input(int current, int next, int weight) {
            this.weight[current][next] = weight;
            this.weight[next][current] = weight;
        }




        public void algorithm(int current, int arrivals, Stack<Integer> route, int[][] weight, boolean[] visited, Stack<Integer> saved_weight) {
            visited[current] = true;
            route.push(current);

            if (current == arrivals) {
                if (route.size() <= min_route) {
                    min_route = route.size();
                    for (Integer i : route) {
                        System.out.print(i + "  ");
                    }
                    int result_weight = 0;
                    for (Integer w : saved_weight) {
                        result_weight += w;
                    }
                    System.out.println();
                    System.out.println("==총 가중치: " + result_weight + "============="  + "\n==경로도출 완료=============");

                }
            }
            for (int i = 101; i < 904; i++) {
                if ((weight[current][i] != 0) && (visited[i] == false)) {
                    if (route.size() < min_route) {
                        addWeight(current, i);
                        algorithm(i, arrivals, route, weight, visited, saved_weight);
                        visited[i] = false;
                    }
                }
            }
            if (!saved_weight.isEmpty()) {
                saved_weight.pop();//
            }
            route.pop();

        }
        public void addWeight(int current, int i) {
            saved_weight.push(weight[current][i]);
        }


    }
    public void makeRoute(Graph g){
        System.out.println("===========================================");
        g.algorithm(g.departures, g.arrivals, g.route, g.weight, g.visited, g.saved_weight);
        System.out.println("DONE");
    }
    public List<List<String>> readDataFromCsv() throws IOException {
        InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.stations));
        BufferedReader reader = new BufferedReader(is);
        CSVReader read = new CSVReader(reader);
        String[] nextLine = null;
        //
        List<List<String>> list = new ArrayList<List<String>>();
        String line = "";
        while ((line = reader.readLine()) != null){
            List<String> stringList = new ArrayList<>();
            String stringArray[] = line.split(",");
            stringList = Arrays.asList(stringArray);
            list.add(stringList);
        }

        return list;

    }
}

package com.piedpipergeeks.aviate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.GridLabelRenderer;


public class Analysis extends AppCompatActivity {
    LineGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        int eventattenders,groupparticipants;
        eventattenders=15;
        groupparticipants=20;
        GraphView graphView=(GraphView)findViewById(R.id.graph_GraphView);
        series=new LineGraphSeries<>();
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMinY(0);


        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setXAxisBoundsManual(true);
        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Group Members");
        gridLabel.setVerticalAxisTitle("Member attending events");
        gridLabel.setLabelsSpace(4);
        gridLabel.setNumHorizontalLabels(6);
        gridLabel.setNumVerticalLabels(6);
//        gridLabel.setGridColor();
//        graphView.getViewport().setScrollable(true); // enables horizontal scrolling
//        graphView.getViewport().setScrollableY(true); // enables vertical scrolling
//        graphView.getViewport().setScalable(true); // enables horizontal zooming and scrolling
//        graphView.getViewport().setScalableY(true); // enables vertical zooming and scrolling


        for(int i=0;i<10;i++)
        {
            eventattenders+=1;
            groupparticipants+=2;
            series.appendData(new DataPoint(eventattenders,groupparticipants),true,100);
        }
        graphView.getViewport().setMaxY(groupparticipants+10);
        graphView.getViewport().setMaxX(eventattenders+10);
        graphView.addSeries(series);

    }
}

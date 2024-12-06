package com.example.instituteofthesouthpacific;

/**
 * Created by admin on 2018-07-19.
 */

import androidx.appcompat.app.AppCompatActivity;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewCoursesActivity extends AppCompatActivity {
    private String source = null;
    private String programTitle = null;
    private ArrayList<Course>[] courses;
    private int numberOfSections;

    public final static String ITEM_TITLE = "cid";
    public final static String ITEM_CAPTION = "cname";

    public Map<String, String> createItem(String cid, String cname) {
        Map<String, String> item = new HashMap<String, String>();
        item.put(ITEM_TITLE, cid);
        item.put(ITEM_CAPTION, cname);
        return item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses);

        Bundle extras = getIntent().getExtras();
        source = extras.getString("source");
        programTitle = extras.getString("programTitle");
        TextView title = (TextView) findViewById(R.id.programTitle);
        title.setText(programTitle);

        numberOfSections = 0;
        StringTokenizer st = new StringTokenizer(source);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.equals("<semester>"))
                numberOfSections++;
        }

        courses = new ArrayList[numberOfSections];
        parseXML(source);

        List<Map<String, String>> data = new LinkedList<Map<String, String>>();

        for (int i = 0; i < courses.length; i++) {
            for (int j = 0; j < courses[i].size(); j++) {
                data.add(createItem(courses[i].get(j).getCid(), courses[i].get(j).getCname()));
            }
        }

        ListView coursesListView = (ListView) findViewById(R.id.coursesListView);
        coursesListView.setAdapter(new CourseAdapter(this, data, R.layout.coursesrowlist,
                new String[]{ITEM_TITLE, ITEM_CAPTION}, new int[]{R.id.subtextid, R.id.textid}));
        coursesListView.setTextFilterEnabled(true);
        coursesListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                HashMap<String, String> item = (HashMap<String, String>) parent.getItemAtPosition(position);
                String title = item.get(ITEM_TITLE);

                Course c = null;
                boolean b = false;
                for (int i = 0; i < courses.length && !b; i++) {
                    for (int j = 0; j < courses[i].size() && !b; j++) {
                        c = courses[i].get(j);
                        if (c.getCid().equals(title)) {
                            b = true;
                        }
                    }
                }

                String cid = c.getCid();
                String cname = c.getCname();
                String credit = c.getCredit();
                String lect = c.getLect();
                String lab = c.getLab();

                Intent courseScreen = new Intent(getApplicationContext(), CourseActivity.class);
                courseScreen.putExtra("cid", cid);
                courseScreen.putExtra("cname", cname);
                courseScreen.putExtra("credit", credit);
                courseScreen.putExtra("lect", lect);
                courseScreen.putExtra("lab", lab);
                startActivity(courseScreen);
            }
        });
    }

    public void parseXML(String src) {
        try {
            StringReader sr = new StringReader(src);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(sr);

            String cid = "";
            String cname = "";
            String credit = "";
            String lect = "";
            String lab = "";
            int eventType = xpp.getEventType();
            int semester = -1;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        name = xpp.getName();
                        if ("semester".equals(name)) {
                            semester++;
                            courses[semester] = new ArrayList<>();
                        } else if ("cid".equals(name)) {
                            cid = xpp.nextText();
                        } else if ("cname".equals(name)) {
                            cname = xpp.nextText();
                        } else if ("credit".equals(name)) {
                            credit = xpp.nextText();
                        } else if ("lect".equals(name)) {
                            lect = xpp.nextText();
                        } else if ("lab".equals(name)) {
                            lab = xpp.nextText();
                            courses[semester].add(new Course(cid, cname, credit, lect, lab));
                        }
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Log the parsed courses
        for (int i = 0; i < courses.length; i++) {
            for (int j = 0; j < courses[i].size(); j++) {
                Log.d("ViewCoursesActivity", "Course: " + courses[i].get(j).toString());
            }
        }
    }


}

package com.example.instituteofthesouthpacific;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    public static List<Course> parseCourses(String xml) {
        List<Course> courseList = new ArrayList<>();

        try {
            // Use an XML parser (e.g., XmlPullParser) to parse the XML response
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            Course currentCourse = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = null;

                try {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            tagName = parser.getName();

                            if ("course".equals(tagName)) {
                                currentCourse = new Course("", "", 0, 0, 0); // Create a new course object
                            } else if ("id".equals(tagName) && currentCourse != null) {
                                currentCourse.setId(parser.nextText());
                            } else if ("name".equals(tagName) && currentCourse != null) {
                                currentCourse.setName(parser.nextText());
                            } else if ("lectures".equals(tagName) && currentCourse != null) {
                                currentCourse.setLectures(Integer.parseInt(parser.nextText()));
                            } else if ("labs".equals(tagName) && currentCourse != null) {
                                currentCourse.setLabs(Integer.parseInt(parser.nextText()));
                            } else if ("credits".equals(tagName) && currentCourse != null) {
                                currentCourse.setCredits(Integer.parseInt(parser.nextText()));
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            tagName = parser.getName();
                            if ("course".equals(tagName) && currentCourse != null) {
                                courseList.add(currentCourse); // Add the parsed course to the list
                            }
                            break;
                    }
                } catch (XmlPullParserException e) {
                    // Ignore errors related to unknown attributes or tags
                    Log.w("XML Parsing", "Ignored XML tag or attribute", e);
                }

                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courseList;
    }
}


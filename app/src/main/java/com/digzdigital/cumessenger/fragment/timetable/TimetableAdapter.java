package com.digzdigital.cumessenger.fragment.timetable;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.digzdigital.cumessenger.data.db.model.Course;
import com.digzdigital.cumessenger.data.db.model.RowObject;

import java.util.ArrayList;

import de.codecrafters.tableview.TableDataAdapter;


class TimetableAdapter extends TableDataAdapter<RowObject> {

    TimetableAdapter(Context context, ArrayList<RowObject> rowObjects) {
        super(context, rowObjects);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        RowObject rowObject = getRowData(rowIndex);
        View renderedView = null;
        Course course;
        switch (columnIndex) {

            case 0:
                TextView textView = new TextView(getContext());
                textView.setText(rowObject.getRowHeader());
                renderedView = textView;
                break;
            case 1:
                try {
                    if (rowObject.getCourses().get(0) != null) {
                        course = rowObject.getCourses().get(0);
                        renderedView = createImageView(course);
                    } else {
                        course = new Course();
                        course.setCourseCode("");
                        renderedView = (createImageView(course));
                    }
                } catch (Exception ignore) {
                    course = new Course();
                    course.setCourseCode("");
                    renderedView = (createImageView(course));
                }
                break;
            case 2:
                try {
                    if (rowObject.getCourses().get(1) != null) {
                        course = rowObject.getCourses().get(1);
                        renderedView = createImageView(course);
                    } else {
                        course = new Course();
                        course.setCourseCode("");

                        renderedView = (createImageView(course));
                    }

                } catch (Exception ignore) {
                    course = new Course();
                    course.setCourseCode("");
                    renderedView = (createImageView(course));
                }
                break;
            case 3:
                try {
                    if (rowObject.getCourses().get(2) != null) {
                        course = rowObject.getCourses().get(2);
                        renderedView = createImageView(course);
                    } else {
                        course = new Course();
                        course.setCourseCode("");
                        renderedView = (createImageView(course));
                    }

                } catch (Exception ignore) {
                    course = new Course();
                    course.setCourseCode("");
                    renderedView = (createImageView(course));
                }
                break;
            case 4:
                try {
                    if (rowObject.getCourses().get(3) != null) {
                        course = rowObject.getCourses().get(3);
                        renderedView = createImageView(course);
                    } else {
                        course = new Course();
                        course.setCourseCode("");
                        renderedView = (createImageView(course));
                    }

                } catch (Exception ignore) {
                    course = new Course();
                    course.setCourseCode("");
                    renderedView = (createImageView(course));
                }
                break;
            case 5:
                try {
                    if (rowObject.getCourses().get(4) != null) {
                        course = rowObject.getCourses().get(4);
                        renderedView = createImageView(course);
                    } else {
                        course = new Course();
                        course.setCourseCode("");
                        renderedView = (createImageView(course));
                    }

                } catch (Exception ignore) {
                    course = new Course();
                    course.setCourseCode("");
                    renderedView = (createImageView(course));
                }
                break;
            case 6:
                try {
                    if (rowObject.getCourses().get(5) != null) {
                        course = rowObject.getCourses().get(5);
                        renderedView = createImageView(course);
                    } else {
                        course = new Course();
                        course.setCourseCode("");
                        renderedView = (createImageView(course));
                    }

                } catch (Exception ignore) {
                    course = new Course();
                    course.setCourseCode("");
                    renderedView = (createImageView(course));
                }
                break;
            case 7:
                try {
                    if (rowObject.getCourses().get(6) != null) {
                        course = rowObject.getCourses().get(6);
                        renderedView = createImageView(course);
                    } else {
                        course = new Course();
                        course.setCourseCode("");
                        renderedView = (createImageView(course));
                    }

                } catch (Exception ignore) {
                    course = new Course();
                    course.setCourseCode("");
                    renderedView = (createImageView(course));
                }
                break;
        }
        return renderedView;
    }

    private ImageView createImageView(Course course) {

        ImageView imageView = new ImageView(getContext());
        String name = course.getCourseCode();

        if (name != null) imageView.setImageDrawable(createDrawable(name));
        else imageView.setImageDrawable(createDrawable(""));

        return imageView;
    }

    private TextDrawable createDrawable(String name) {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT

        int color1 = generator.getColor(name);
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .toUpperCase()
                .width(100)
                .height(60)
                .withBorder(4)
                .endConfig()
                .rect();
        return builder.build(name, color1);
    }
}

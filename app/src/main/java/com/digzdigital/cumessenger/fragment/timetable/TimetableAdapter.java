package com.digzdigital.cumessenger.fragment.timetable;

import android.content.Context;
import android.media.Image;
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

/**
 * Created by Digz on 28/02/2017.
 */

public class TimetableAdapter extends TableDataAdapter<RowObject> {

    private Context context;
    public TimetableAdapter(Context context, ArrayList<RowObject> rowObjects){
        super(context, rowObjects);
    }
    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        RowObject rowObject = getRowData(rowIndex);
        View renderedView = null;
        switch (columnIndex){
            case 0:
                TextView textView = new TextView(getContext());
                textView.setText(rowObject.getRowHeader());
                renderedView = textView;
                break;
            case 1:
                renderedView = createImageView(rowObject.getCourses().get(0));
                break;
            case 2:
                renderedView = createImageView(rowObject.getCourses().get(1));
                break;
            case 3:
                renderedView = createImageView(rowObject.getCourses().get(2));
                break;
            case 4:
                renderedView = createImageView(rowObject.getCourses().get(3));
                break;
            case 5:
                renderedView = createImageView(rowObject.getCourses().get(4));
                break;
            case 6:
                renderedView = createImageView(rowObject.getCourses().get(5));
                break;
            case 7:
                renderedView = createImageView(rowObject.getCourses().get(6));
                break;
        }
        return renderedView;
    }

    private ImageView createImageView(Course course){
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(createDrawable(course.getCourseTitle()));
        return imageView;
    }
    private TextDrawable createDrawable(String name){
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getRandomColor();
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .width(60)
                .height(60)
                .withBorder(4)
                .endConfig()
                .rect();
        return builder.build(name, color1);
    }
}

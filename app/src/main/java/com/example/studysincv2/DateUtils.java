package com.example.studysincv2;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String formatDate(String originalDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        Date date;
        try {
            date = originalFormat.parse(originalDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return originalDate; // Return original date if parsing fails
        }
        return targetFormat.format(date);
    }
}

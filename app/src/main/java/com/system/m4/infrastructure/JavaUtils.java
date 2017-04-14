package com.system.m4.infrastructure;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.ParameterizedType;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eferraz on 14/04/17.
 * Classes de utilitarios
 */

public final class JavaUtils {

    /**
     *
     */
    public static class DateUtil {

        public static final String DD_MM_YYYY = "dd/MM/yyyy";
        public static final String MMMM_DE_YYYY = "MMMM 'de' yyyy";
        public static final String YYYY_MM_DD = "yyyy/MM/dd";
        public static final String MM_YYYY = "MM/yyyy";
        public static final String DD_DE_MMMM_DE_YYYY = "dd 'de' MMMM 'de' yyyy";

        public static String format(Date date, String template) {
            if (date == null) {
                return "Not defined";
            }
            final String format = new SimpleDateFormat(template, new Locale("pt", "BR")).format(date);
            return format.substring(0, 1).toUpperCase() + format.substring(1);
        }

        public static String format(Date date) {
            return format(date, DD_MM_YYYY);
        }

        public static Date parse(String date) throws ParseException {
            return parse(date, DD_MM_YYYY);
        }

        public static Date parse(String date, String template) {
            try {
                return new SimpleDateFormat(template, new Locale("pt", "BR")).parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        public static Date getActualMaximum(int year, int month) {
            Calendar cInit = Calendar.getInstance();
            cInit.set(year, month, 1); // DATE = 1 Evita avancar o mes (31)
            cInit.set(Calendar.DATE, cInit.getActualMaximum(Calendar.DATE));
            cInit.set(Calendar.HOUR_OF_DAY, cInit.getActualMaximum(Calendar.HOUR_OF_DAY));
            cInit.set(Calendar.MINUTE, cInit.getActualMaximum(Calendar.MINUTE));
            cInit.set(Calendar.SECOND, cInit.getActualMaximum(Calendar.SECOND));
            return cInit.getTime();
        }

        public static Date getActualMinimum(int year, int month) {
            Calendar cInit = Calendar.getInstance();
            cInit.set(year, month, cInit.getActualMinimum(Calendar.DATE));
            cInit.set(Calendar.HOUR_OF_DAY, cInit.getActualMinimum(Calendar.HOUR_OF_DAY));
            cInit.set(Calendar.MINUTE, cInit.getActualMinimum(Calendar.MINUTE));
            cInit.set(Calendar.SECOND, cInit.getActualMinimum(Calendar.SECOND));
            return cInit.getTime();
        }

        public static int compare(Date dInit, Date dEnd) {

            Calendar cInit = Calendar.getInstance();
            cInit.setTime(parse(format(dInit, DD_MM_YYYY), DD_MM_YYYY));

            Calendar cEnd = Calendar.getInstance();
            cEnd.setTime(parse(format(dEnd, DD_MM_YYYY), DD_MM_YYYY));

            return cInit.compareTo(cEnd);
        }

        public static int get(int constantCalendar, Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(constantCalendar);
        }

        public static Date getDate(int year, int month, int day){
            Calendar cInit = Calendar.getInstance();
            cInit.set(year, month, day);
            cInit.set(Calendar.HOUR_OF_DAY, 0);
            cInit.set(Calendar.MINUTE, 0);
            cInit.set(Calendar.SECOND, 0);
            return cInit.getTime();
        }
    }

    /**
     *
     */
    public static class NumberUtil {

        public static String currencyFormat(String value) {
            return currencyFormat(Double.valueOf(value));
        }

        public static String currencyFormat(Double value) {
            final NumberFormat instance = NumberFormat.getInstance(new Locale("pt", "BR"));
            instance.setMinimumFractionDigits(2);
            instance.setMinimumIntegerDigits(1);
            return "R$ " + instance.format(value);
        }

        public static String percentFormat(Double percentValue) {
            final NumberFormat instance = NumberFormat.getInstance(new Locale("pt", "BR"));
            instance.setMinimumFractionDigits(1);
            instance.setMinimumIntegerDigits(1);
            instance.setMaximumFractionDigits(2);
            return "(" + instance.format(percentValue) + "%)";
        }

        public static Double calcPercent(Double total, Double parcial) {
            if (total == 0){
                return 0D;
            }
            return parcial * 100 / total;
        }
    }

    /**
     *
     */
    public static class StringUtil {

        public static boolean isEmpty(String string) {
            return string == null || string.trim().isEmpty();
        }
    }

    /**
     *
     */
    public static class AndroidUtil {

        public static int getPixel(Context context, float dp) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return (int) (dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        }

        public static int getDP(Context context, float px) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return (int) (px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        }

        public static void showDatePicker(Context context, DatePickerDialog.OnDateSetListener onDateSetListener) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(context, onDateSetListener, year, month, day).show();
        }
    }


    public static class ClassUtil {

        public static Class getTClass(Object object) {
            final ParameterizedType type = (ParameterizedType) object.getClass().getGenericSuperclass();
            return (Class) (type).getActualTypeArguments()[0];
        }
    }
}
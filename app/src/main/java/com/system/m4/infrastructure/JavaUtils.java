package com.system.m4.infrastructure;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.google.firebase.database.FirebaseDatabase;
import com.system.m4.R;
import com.system.m4.views.vos.VOInterface;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
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

        public static final String MMMM_DE_YYYY = "MMMM',' yyyy";

        public static final String YYYY_MM_DD = "yyyy/MM/dd";

        public static final String DD_DE_MMMM_DE_YYYY = "dd 'de' MMMM 'de' yyyy";

        public static final String DD = "dd";
        public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";
        private static final String MMM = "MMM";

        public static Date parse(String date, String template) {
            try {
                return new SimpleDateFormat(template, Locale.getDefault()).parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        public static String format(Date date, String template) {
            if (date == null) {
                return null;
            }
            final String format = new SimpleDateFormat(template, Locale.getDefault()).format(date);
            return format.substring(0, 1).toUpperCase() + format.substring(1);
        }

        public static int get(int constantCalendar, Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(constantCalendar);
        }

        public static Date getDate(int year, int month, int day) {
            return parse(String.format(Locale.getDefault(), "%d/%d/%d", day, month, year), DD_MM_YYYY);
        }

        @NotNull
        public static String getMonth(int month) {
            Calendar instance = Calendar.getInstance();
            instance.set(Calendar.MONTH, month);
            return format(instance.getTime(), MMM);
        }

        @NotNull
        public static Date getDate(@Nullable Date date, int day) {
            return getDate(get(Calendar.YEAR, date), get(Calendar.MONTH, date) + 1, day);
        }
    }

    /**
     *
     */
    public static class NumberUtil {

        public static String currencyFormat(Double value) {
            if (value != null) {
                final NumberFormat instance = NumberFormat.getInstance();
                instance.setMinimumFractionDigits(2);
                instance.setMinimumIntegerDigits(1);
                return "R$ " + instance.format(value);
            } else return null;
        }

        public static String valueFormat(Double value) {
            if (value != null) {
                final NumberFormat instance = NumberFormat.getInstance();
                instance.setMaximumFractionDigits(0);
                instance.setMinimumIntegerDigits(1);
                return instance.format(value);
            } else return null;
        }

        public static Double removeFormat(String valueFormatted) {
            try {
                final NumberFormat format = NumberFormat.getNumberInstance();
                if (format instanceof DecimalFormat) {
                    ((DecimalFormat) format).setParseBigDecimal(true);
                }
                return format.parse(valueFormatted.replaceAll("[^\\d.,]", "")).doubleValue();
            } catch (ParseException e) {
                return null;
            }
        }
    }

    /**
     *
     */
    public static class StringUtil {

        public static boolean isEmpty(String string) {
            return string == null || string.trim().isEmpty();
        }

        public static String formatEmpty(String s) {
            if (TextUtils.isEmpty(s)) {
                return Constants.EMPTY_FIELD;
            } else {
                return s;
            }
        }
    }

    /**
     *
     */
    public static class AndroidUtil {

        public static void showDatePicker(Context context, Date date, DatePickerDialog.OnDateSetListener onDateSetListener) {
            final Calendar c = Calendar.getInstance();

            if (date != null) {
                c.setTime(date);
            }

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(context, onDateSetListener, year, month, day).show();
        }

        public static void showAlertDialog(Context context, @StringRes int stringMessage, DialogInterface.OnClickListener onClickListener) {
            new AlertDialog.Builder(context).setMessage(stringMessage)
                    .setPositiveButton(R.string.system_action_ok, onClickListener).create().show();
        }

        public static void showAlertDialog(Context context, String stringMessage, DialogInterface.OnClickListener onClickListener) {
            new AlertDialog.Builder(context).setMessage(stringMessage)
                    .setPositiveButton(R.string.system_action_ok, onClickListener).create().show();
        }
    }


    public static class ClassUtil {
        public static boolean isEmpty(VOInterface vo) {
            return vo == null || vo.getKey() == null || vo.getKey().isEmpty();
        }
    }

    public static class FirebaseUtil {

        public static void enableOffline(String flavor) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            //FirebaseDatabase.getInstance().getReference(flavor).keepSynced(true);
        }
    }
}
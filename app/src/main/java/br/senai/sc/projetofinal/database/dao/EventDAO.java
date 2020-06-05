package br.senai.sc.projetofinal.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import br.senai.sc.projetofinal.database.DBGateway;
import br.senai.sc.projetofinal.database.entity.EventEntity;
import br.senai.sc.projetofinal.models.Event;

public class EventDAO {

    private final String SQL_GET_ALL = "SELECT * FROM " + EventEntity.TABLE_NAME;
    private DBGateway dbGateway;

    public EventDAO(Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean save(Event event) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventEntity.COLUMN_NAME_NAME, event.getName());
        contentValues.put(EventEntity.COLUMN_NAME_DATE, event.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        contentValues.put(EventEntity.COLUMN_NAME_PLACE, event.getPlace());
        if(event.getId() > 0) {
            return dbGateway.getDatabase().update(
                    EventEntity.TABLE_NAME,
                    contentValues,
                    EventEntity._ID + "=?",
                    new String[]{String.valueOf(event.getId())}) > 0;
        }

        return dbGateway.getDatabase().insert(
                EventEntity.TABLE_NAME,
                null,
                contentValues) > 0;
    }

    public List<Event> getAll() {
        List<Event> events = new ArrayList<Event>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_GET_ALL, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventEntity._ID));
            String name = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_NAME));
            LocalDate date = LocalDate.parse(cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_DATE)));
            String place = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_PLACE));
            events.add(new Event(id, name, date, place));
        }
        return events;
    }

    public boolean delete(Event event) {
        return dbGateway.getDatabase().delete(
                EventEntity.TABLE_NAME,
                EventEntity._ID + "=?",
                new String[]{String.valueOf(event.getId())}
        ) > 0;
    }
}

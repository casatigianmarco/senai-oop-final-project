package br.senai.sc.projetofinal.database.entity;

import android.provider.BaseColumns;

public final class EventEntity implements BaseColumns {

    private EventEntity() {}

    public static final String TABLE_NAME = "events";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_PLACE = "place";

}

package br.senai.sc.projetofinal.database.contract;

import br.senai.sc.projetofinal.database.entity.EventEntity;

public final class EventContract {

    private EventContract() {}

    public static final String createTable() {
        return "CREATE TABLE " + EventEntity.TABLE_NAME + " (" +
                EventEntity._ID + " INTEGER PRIMARY KEY," +
                EventEntity.COLUMN_NAME_NAME + " TEXT," +
                EventEntity.COLUMN_NAME_DATE + " TEXT," +
                EventEntity.COLUMN_NAME_PLACE + " TEXT)";
    }

    public static final String deleteTable() {
        return "DROP TABLE IF EXISTS " + EventEntity.TABLE_NAME;
    }

}

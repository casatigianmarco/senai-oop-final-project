package br.senai.sc.projetofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.senai.sc.projetofinal.models.Event;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE_NEW_EVENT = 1;
    private final int RESULT_CODE_NEW_EVENT = 11;
    private final int REQUEST_CODE_EDIT_EVENT = 2;
    private final int RESULT_CODE_EDIT_EVENT = 12;
    private final int MENU_ITEM_DELETE = 1;

    private ListView listViewEvents;
    private ArrayAdapter<Event> arrayAdapterEvent;
    int eventId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");
        listViewEvents = findViewById(R.id.listView_events);
        arrayAdapterEvent = new ArrayAdapter<Event>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                new ArrayList<Event>()
        );
        listViewEvents.setAdapter(arrayAdapterEvent);
        defineOnClickListenerListView();
        defineContextMenuListenerListView();
    }

    private void defineOnClickListenerListView() {
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event clickedEvent = arrayAdapterEvent.getItem(position);
                Intent intent = new Intent(MainActivity.this, NewEventActivity.class);
                intent.putExtra("eventToEdit", clickedEvent);
                startActivityForResult(intent, REQUEST_CODE_EDIT_EVENT);
            }
        });
    }

    private void defineContextMenuListenerListView() {
        listViewEvents.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(Menu.NONE, MENU_ITEM_DELETE, Menu.NONE, "Deletar");
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
        switch (item.getItemId()) {
            case MENU_ITEM_DELETE:
                int id = deleteEvent(position);
                Toast.makeText(
                        MainActivity.this,
                        "Eliminado evento com id " + id + ".",
                        Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private int deleteEvent(int position) {
        Event event = arrayAdapterEvent.getItem(position);
        arrayAdapterEvent.remove(event);
        return eventId;
    }

    public void onClickNewEvent(View view) {
        Intent intent = new Intent(MainActivity.this, NewEventActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NEW_EVENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_NEW_EVENT && resultCode == RESULT_CODE_NEW_EVENT) {
            Event newEvent = (Event) data.getExtras().getSerializable("newEvent");
            newEvent.setId(++eventId);
            this.arrayAdapterEvent.add(newEvent);
            Toast.makeText(
                    MainActivity.this,
                    "Adicionado novo evento com id " + eventId + ".",
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == REQUEST_CODE_EDIT_EVENT && resultCode == RESULT_CODE_EDIT_EVENT) {
            Event editedEvent = (Event) data.getExtras().getSerializable("editedEvent");
            for (int i = 0; i < arrayAdapterEvent.getCount(); i++) {
                Event currentEvent = arrayAdapterEvent.getItem(i);
                if (currentEvent.getId() == editedEvent.getId()) {
                    arrayAdapterEvent.remove(currentEvent);
                    arrayAdapterEvent.insert(editedEvent, i);
                    break;
                }
            }
            Toast.makeText(
                    MainActivity.this,
                    "Editado evento com id " + editedEvent.getId() + ".",
                    Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

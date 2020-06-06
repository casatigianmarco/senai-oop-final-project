package br.senai.sc.projetofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.senai.sc.projetofinal.database.dao.EventDAO;
import br.senai.sc.projetofinal.models.Event;

public class MainActivity extends AppCompatActivity {

    private final int MENU_ITEM_DELETE = 1;

    private ListView listViewEvents;
    private EditText editTextFilterName;
    private ArrayAdapter<Event> arrayAdapterEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");
        listViewEvents = findViewById(R.id.listView_events);
        editTextFilterName = findViewById(R.id.editText_filterName);
        defineOnClickListenerListView();
        defineContextMenuListenerListView();
        defineTextChangedListenerFilterName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventDAO eventDAO = new EventDAO(getBaseContext());
        populateAdapter(eventDAO.getAll());
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

    private void populateAdapter(List<Event> events) {
        arrayAdapterEvent = new ArrayAdapter<Event>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                events
        );
        listViewEvents.setAdapter(arrayAdapterEvent);
    }

    private void defineOnClickListenerListView() {
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event clickedEvent = arrayAdapterEvent.getItem(position);
                Intent intent = new Intent(MainActivity.this, NewEventActivity.class);
                intent.putExtra("eventToEdit", clickedEvent);
                startActivity(intent);
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

    private void defineTextChangedListenerFilterName() {
        editTextFilterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String filter = s.toString().trim();
                if(!filter.isEmpty()) {
                    EventDAO eventDAO = new EventDAO(getBaseContext());
                    populateAdapter(eventDAO.getByName(filter));
                } else {
                    EventDAO eventDAO = new EventDAO(getBaseContext());
                    populateAdapter(eventDAO.getAll());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int deleteEvent(int position) {
        Event event = arrayAdapterEvent.getItem(position);
        EventDAO eventDAO = new EventDAO(getBaseContext());
        eventDAO.delete(event);
        populateAdapter(eventDAO.getAll());
        return event.getId();
    }

    public void onClickNewEvent(View view) {
        Intent intent = new Intent(MainActivity.this, NewEventActivity.class);
        startActivity(intent);
    }

    public void onClickAsc(View view) {
        EventDAO eventDAO = new EventDAO(getBaseContext());
        populateAdapter(eventDAO.orderByNameAsc());
    }

    public void onClickDesc(View view) {
        EventDAO eventDAO = new EventDAO(getBaseContext());
        populateAdapter(eventDAO.orderByNameDesc());
    }

}

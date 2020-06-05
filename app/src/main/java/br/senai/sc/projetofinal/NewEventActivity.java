package br.senai.sc.projetofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import br.senai.sc.projetofinal.database.dao.EventDAO;
import br.senai.sc.projetofinal.models.Event;

public class NewEventActivity extends AppCompatActivity {

    private final int RESULT_CODE_NEW_EVENT = 11;
    private final int RESULT_CODE_EDIT_EVENT = 12;

    private int eventId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        loadClickedEvent();
    }

    public void loadClickedEvent() {
        Intent intent = getIntent();
        if( intent != null && intent.getExtras() != null
            && intent.getExtras().get("eventToEdit") != null) {
            Event event = (Event) intent.getExtras().get("eventToEdit");
            EditText editTextName = findViewById(R.id.editText_name);
            EditText editTextDate = findViewById(R.id.editText_date);
            EditText editTextPlace = findViewById(R.id.editText_place);
            editTextName.setText(event.getName());
            editTextDate.setText(event.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            editTextPlace.setText(event.getPlace());
            eventId = event.getId();
            setTitle("Editar Evento");
        } else {
            setTitle("Novo Evento");
        }
    }

    public void onClickCancel(View view) {
        finish();
    }

    public void onClickSave(View view) {
        EditText editTextName = findViewById(R.id.editText_name);
        EditText editTextDate = findViewById(R.id.editText_date);
        EditText editTextPlace   = findViewById(R.id.editText_place);

        String name = editTextName.getText().toString();
        String place = editTextPlace.getText().toString();
        String dateString = editTextDate.getText().toString();
        if (!name.trim().isEmpty() && !dateString.isEmpty() && !place.trim().isEmpty()) {
            try {
                LocalDate date = LocalDate.parse(editTextDate.getText().toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Toast.makeText(
                        NewEventActivity.this,
                        "Salvar ...",
                        Toast.LENGTH_SHORT).show();
                Event event = new Event(eventId, name, date, place);
                EventDAO eventDAO = new EventDAO(getBaseContext());
                if(eventDAO.save(event)) {
                    finish();
                } else {
                    Toast.makeText(
                            NewEventActivity.this,
                            "Erro ao salvar o evento!",
                            Toast.LENGTH_LONG);
                }

            } catch (DateTimeParseException e) {
                Toast.makeText(
                        NewEventActivity.this,
                        "Formato de data inválido. Tente novamente. (Ex. 01/01/2020)",
                        Toast.LENGTH_LONG).show();
            }
        } else if (name.trim().isEmpty()) {
            Toast.makeText(
                    NewEventActivity.this,
                    "O campo nome é obrigatório.",
                    Toast.LENGTH_LONG).show();
        } else if (dateString.isEmpty()) {
            Toast.makeText(
                    NewEventActivity.this,
                    "O campo data é obrigatório.",
                    Toast.LENGTH_LONG).show();
        } else if (place.trim().isEmpty()) {
            Toast.makeText(
                    NewEventActivity.this,
                    "O campo lugar é obrigatório.",
                    Toast.LENGTH_LONG).show();
        }
    }
}

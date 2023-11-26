package com.example.ecogotas_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Estadistica extends AppCompatActivity {

    private EditText consumoAnteriorEditText;
    private RadioGroup tipoConsumoRadioGroup;
    private RadioButton usoPersonalRadioButton;
    private RadioButton lavadoAutosRadioButton;
    private RadioButton sistemaRiegoRadioButton;
    private TextView resultadoTextView;
    private Spinner personasSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_estadistica);

        initViews();
        setupPersonasSpinner();
        tipoConsumoRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.usoPersonalRadioButton) {
                    personasSpinner.setVisibility(View.VISIBLE);
                } else {
                    personasSpinner.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initViews() {
        consumoAnteriorEditText = findViewById(R.id.edtConsumoAgua);
        tipoConsumoRadioGroup = findViewById(R.id.tipoConsumoRadioGroup);
        usoPersonalRadioButton = findViewById(R.id.usoPersonalRadioButton);
        lavadoAutosRadioButton = findViewById(R.id.lavadoAutosRadioButton);
        sistemaRiegoRadioButton = findViewById(R.id.sistemaRiegoRadioButton);
        resultadoTextView = findViewById(R.id.ResCalcuEsta);
        personasSpinner = findViewById(R.id.personasSpinner);
    }

    private void setupPersonasSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.opciones_personas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personasSpinner.setAdapter(adapter);
        personasSpinner.setSelection(0);
    }

    public void calcularConsumo(View view) {
        try {
        double consumoAnterior = Double.parseDouble(consumoAnteriorEditText.getText().toString());
        double factorMultiplicador = obtenerFactorMultiplicador();
        double consumoProyectado = consumoAnterior * factorMultiplicador;

        if (usoPersonalRadioButton.isChecked()) {
            int numeroPersonas = Integer.parseInt(personasSpinner.getSelectedItem().toString());

            // Definir límites de consumo para cada opción
            double limite1Persona = 5.0;
            double limite2Personas = 10.0;
            double limite3Personas = 12.0;
            double limite4Personas = 16.0;
            double limite5OMasPersonas = 26.0; // Puedes ajustar este valor

            // Verificar si se sobrepasa el límite y mostrar mensaje correspondiente
            if (numeroPersonas == 1 && consumoProyectado > limite1Persona) {
                mostrarAdvertenciaDesperdicio();
            } else if (numeroPersonas == 2 && consumoProyectado > limite2Personas) {
                mostrarAdvertenciaDesperdicio();
            } else if (numeroPersonas == 3 && consumoProyectado > limite3Personas) {
                mostrarAdvertenciaDesperdicio();
            } else if (numeroPersonas == 4 && consumoProyectado > limite4Personas) {
                mostrarAdvertenciaDesperdicio();
            } else if (numeroPersonas >= 5 && consumoProyectado > limite5OMasPersonas) {
                mostrarAdvertenciaDesperdicio();
            } else {
                mostrarMensajeExcelente();
            }
        }  else if (lavadoAutosRadioButton.isChecked()) {
            double limiteLavadoAutos = 240.0; // Límite de consumo de agua para el lavado de autos

            // Verificar si se sobrepasa el límite y mostrar mensaje correspondiente
            if (consumoProyectado > limiteLavadoAutos) {
                mostrarAdvertenciaDesperdicio();
                // Puedes agregar un método para ahorrar agua en el lavado de autos
                // por ejemplo: mostrarMetodoAhorrarAguaLavadoAutos();
            } else {
                mostrarMensajeExcelente();
            }
        } else if (sistemaRiegoRadioButton.isChecked()) {
            double limiteSistemaRiego = 16.0; // Límite de consumo de agua para el sistema de riego

            // Verificar si se sobrepasa el límite y mostrar mensaje correspondiente
            if (consumoProyectado > limiteSistemaRiego) {
                mostrarAdvertenciaDesperdicio();
                // Puedes agregar un método para ahorrar agua en el sistema de riego
                // por ejemplo: mostrarMetodoAhorrarAguaRiego();
            } else {
                mostrarMensajeExcelente();
            }
        }
    }catch (NumberFormatException e) {
        // Manejo de excepción si hay un error al convertir a double o int
        e.printStackTrace();
        mostrarError("Error al ingresar valores numéricos.");
    } catch (Exception e) {
        // Manejo de otras excepciones
        e.printStackTrace();
        mostrarError("Error inesperado.");
    }
}

    private double obtenerFactorMultiplicador() {
        if (usoPersonalRadioButton.isChecked()) {
            return Double.parseDouble(personasSpinner.getSelectedItem().toString());
        } else if (lavadoAutosRadioButton.isChecked()) {
            return 0.5;
        } else if (sistemaRiegoRadioButton.isChecked()) {
            return 2.0;
        }
        return 1.0; // Default si no se selecciona nada
    }

    private void mostrarResultado(double consumoProyectado) {
        StringBuilder mensaje = new StringBuilder();

        consumoProyectado = calcularConsumoProyectado();

        if (usoPersonalRadioButton.isChecked()) {
            int numeroPersonas = Integer.parseInt(personasSpinner.getSelectedItem().toString());
            double limite = obtenerLimiteSegunPersonas(numeroPersonas);

            if (consumoProyectado > limite) {
                mensaje.append("¡Advertencia! Estás consumiendo demasiado agua. Si continúas así, tu consumo el próximo mes aumentará.\n");
            } else {
                mensaje.append("¡Excelente! Sigue manteniéndote así, estás haciendo un uso eficiente del agua.\n");
            }
        } else if (lavadoAutosRadioButton.isChecked()) {
            double limiteLavadoAutos = 240.0;

            if (consumoProyectado > limiteLavadoAutos) {
                mensaje.append("¡Advertencia! Estás consumiendo demasiado agua en el lavado de autos. Si continúas así, tu consumo el próximo mes aumentará.\n");
            } else {
                mensaje.append("¡Excelente! Sigue manteniéndote así, estás haciendo un uso eficiente del agua en el lavado de autos.\n");
            }
        } else if (sistemaRiegoRadioButton.isChecked()) {
            double limiteSistemaRiego = 16.0;

            if (consumoProyectado > limiteSistemaRiego) {
                mensaje.append("¡Advertencia! Estás consumiendo demasiada agua en el sistema de riego. Si continúas así, tu consumo el próximo mes aumentará.\n");
            } else {
                mensaje.append("¡Excelente! Sigue manteniéndote así, estás haciendo un uso eficiente del agua en el sistema de riego.\n");
            }
        }

        mensaje.append(String.format("El consumo proyectado para el próximo mes es: %.2f metros cúbicos", consumoProyectado));

        resultadoTextView.setText(mensaje.toString());
    }
    private void mostrarAdvertenciaDesperdicio() {
        resultadoTextView.append("\n¡Advertencia! Estás consumiendo demasiado agua. Si continúas así, tu consumo el próximo mes aumentará.");

        // Hacer visible el botón de opciones
        Button botonOpciones = findViewById(R.id.botonOpciones);
        botonOpciones.setVisibility(View.VISIBLE);

        // También podrías agregar un Listener al botón para manejar la acción de clic
        botonOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agregar aquí la lógica para abrir el nuevo activity con las opciones
                abrirNuevoActivityConOpciones();
            }
        });
    }
    private void abrirNuevoActivityConOpciones() {
        // Aquí puedes iniciar un nuevo Intent para abrir el nuevo activity
        Intent intent = new Intent(Estadistica.this, Advertencia.class);
        startActivity(intent);
    }
    private void mostrarMensajeExcelente() {
        resultadoTextView.append("\n¡Excelente! Sigue manteniéndote así, estás haciendo un uso eficiente del agua.");
    }
    private double obtenerLimiteSegunPersonas(int numeroPersonas) {
        // Definir límites de consumo para cada opción
        double limite1Persona = 5.0;
        double limite2Personas = 10.0;
        double limite3Personas = 12.0;
        double limite4Personas = 16.0;
        double limite5OMasPersonas = 26.0; // Puedes ajustar este valor

        // Devolver el límite según el número de personas
        if (numeroPersonas == 1) {
            return limite1Persona;
        } else if (numeroPersonas == 2) {
            return limite2Personas;
        } else if (numeroPersonas == 3) {
            return limite3Personas;
        } else if (numeroPersonas == 4) {
            return limite4Personas;
        } else {
            return limite5OMasPersonas;
        }
    }
    private void mostrarError(String mensaje) {
        resultadoTextView.setText("Error: " + mensaje);
    }
    private double calcularConsumoProyectado() {
        // Agrega la lógica necesaria para calcular el consumo proyectado
        double consumoAnterior = Double.parseDouble(consumoAnteriorEditText.getText().toString());
        double factorMultiplicador = obtenerFactorMultiplicador();
        return consumoAnterior * factorMultiplicador;
    }
}

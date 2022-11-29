package com.example.calculadoradegorgeta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {

    private val CONTA: String = "CONTA"
    private val PERCENTUAL: String = "PERCENTUAL"

    private var conta: Double = 0.0
    private var percentual: Double = 7.0

    private lateinit var contaEditText: EditText
    private lateinit var gorjeta5EditText: EditText
    private lateinit var gorjeta10EditText: EditText
    private lateinit var gorjeta15EditText: EditText
    private lateinit var percentualSeekBar: SeekBar
    private lateinit var percentualEditText: EditText
    private lateinit var gorjetaEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contaEditText = findViewById(R.id.contaEditText)
        gorjeta5EditText = findViewById(R.id.gorjeta5EditText)
        gorjeta10EditText = findViewById(R.id.gorjeta10EditText)
        gorjeta15EditText = findViewById(R.id.gorjeta15EditText)
        percentualSeekBar = findViewById(R.id.percentualSeekBar)
        percentualEditText = findViewById(R.id.percentualEditText)
        gorjetaEditText = findViewById(R.id.gorjetaEditText)

        contaEditText.addTextChangedListener(OuvinteContaEditText())
        percentualSeekBar.setOnSeekBarChangeListener(OuvintePercentualSeekBar())

        if (savedInstanceState == null) {
            conta = 0.0
            percentual = 7.0
        } else {
            conta = savedInstanceState.getDouble(CONTA)
            percentual = savedInstanceState.getDouble(PERCENTUAL)
        }

        contaEditText.setText(String.format("%.2f", conta))
        percentualSeekBar.setProgress(percentual.toInt())
    }

    private fun atualizaGorjetas() {
        val gorjetas = Calculadora().gorjetas(conta)
        gorjeta5EditText.setText(String.format("%.1f", gorjetas[0]))
        gorjeta10EditText.setText(String.format("%.1f", gorjetas[1]))
        gorjeta15EditText.setText(String.format("%.1f", gorjetas[2]))
    }

    private fun atualizaGorjetaPersonalizada() {
        val gorjeta = Calculadora().gorjeta(conta, percentual)
        gorjetaEditText.setText(String.format("%.1f", gorjeta))
    }

    inner class OuvinteContaEditText: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            try {
                conta = contaEditText.getText().toString().toDouble()
            } catch (e: NumberFormatException) {
                conta = 0.0
            }
            atualizaGorjetas()
            atualizaGorjetaPersonalizada()
        }
    }

    inner class OuvintePercentualSeekBar: SeekBar.OnSeekBarChangeListener {
        override fun onStartTrackingTouch(p0: SeekBar?) {}
        override fun onStopTrackingTouch(p0: SeekBar?) {}

        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            percentual = percentualSeekBar.progress.toDouble()
            percentualEditText.setText(String.format("%.1f", percentual))
            atualizaGorjetaPersonalizada()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putDouble(CONTA, conta)
        outState.putDouble(PERCENTUAL, percentual)
    }
}
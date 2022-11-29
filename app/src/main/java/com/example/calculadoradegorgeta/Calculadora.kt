package com.example.calculadoradegorgeta

class Calculadora {

    fun gorjeta(valor:Double, percentual:Double): Double {
        return valor * percentual / 100.0
    }

    fun gorjetas(valor:Double): Array<Double> {
        val saida: Array<Double> = Array(size = 3) {
            i -> gorjeta(valor, percentual = (i + 1) * 5.00)
        }

        return saida
    }
}
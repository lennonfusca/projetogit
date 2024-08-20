package com.example.aplicativo_lennon

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class MainActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private var nomes = listOf(
        "Gabriel" to R.drawable.gabriel,
        "Raul" to R.drawable.raul,
        "Lennon" to R.drawable.lennon,

    )

    data class Particle(
        var x: Float,
        var y: Float,
        var velocityX: Float,
        var velocityY: Float,
        var life: Int
    )

    private var running = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         findViewById<View>(R.id.name)

        handler = Handler(Looper.getMainLooper())

        val button = findViewById<Button>(R.id.button2)
        val showButton = false

        val ganhou = findViewById<View>(R.id.ganhou2)
        val showGanhou = false



        if (showButton) {
            button.visibility = View.VISIBLE
        } else {
            button.visibility = View.GONE
        }
        if (showGanhou) {
            ganhou.visibility = View.VISIBLE
        } else {
            ganhou.visibility = View.GONE
        }

    }

    fun sortear(view: View) {
        if (running) return // Impede múltiplos cliques
        val textoResultado = findViewById<TextView>(R.id.text_resultado)
        val textoCarregando = findViewById<Button>(R.id.button)

        val imageView = findViewById<ImageView>(R.id.imageView4) // Adicionando ImageView
        val ganhou = findViewById<View>(R.id.ganhou2)
        ganhou.visibility = View.GONE

        imageView.layoutParams.width = 500 // Define a largura da imagem em pixels
        imageView.layoutParams.height = 500 // Define a altura da imagem em pixels

        running = true
        val delay = 100L // Velocidade de rotação

        // Função de rotação
        val roletaRunnable = object : Runnable {
            var index = 0

            override fun run() {
                val (nome, imagem) = nomes[index]
                textoResultado.text = nome
                imageView.setImageResource(imagem)
                textoCarregando.text = "CARREGANDO..."
                textoCarregando.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.green))
                imageView.visibility = View.VISIBLE // Mostra a imagem
                index = (index + 1) % nomes.size
                if (running) {

                    handler.postDelayed(this, delay)
                } else {
                    // Quando a rotação parar, mostra o nome final
                    val (nomeSorteado, imagemSorteada) = nomes.random()
                    textoResultado.text = nomeSorteado
                    imageView.setImageResource(imagemSorteada)
                    imageView.visibility = View.VISIBLE

                    // Exibe o botão de reiniciar
                    val botaoReiniciar = findViewById<Button>(R.id.button2)
                    textoCarregando.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.lilas))
                    textoCarregando.text = "SORTEAR"

                    botaoReiniciar.visibility = View.VISIBLE
                }
            }
        }

        // Inicia a rotação
        handler.post(roletaRunnable)


        // Para a rotação após um tempo
        handler.postDelayed({
            running = false
            ganhou.visibility = View.VISIBLE
        }, 3000L) // Tempo de rotação (3 segundos)

    }

    fun reiniciar(view: View) {
        val textoResultado = findViewById<TextView>(R.id.text_resultado)
        val ganhou = findViewById<View>(R.id.ganhou2)
        val imageView = findViewById<ImageView>(R.id.imageView4) // Adicionando ImageView

        textoResultado.text = "Vazio"
        imageView.visibility = View.GONE // Esconde a imagem

        // Esconde o botão de reiniciar
        val botaoReiniciar = findViewById<Button>(R.id.button2)
        ganhou.visibility = View.GONE
        botaoReiniciar.visibility = View.GONE
    }

}


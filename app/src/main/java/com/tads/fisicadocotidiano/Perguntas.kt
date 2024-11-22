package com.tads.fisicadocotidiano

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.RadioButton
import com.tads.fisicadocotidiano.databinding.ActivityPerguntasBinding
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge


class Perguntas : AppCompatActivity() {

    private lateinit var binding: ActivityPerguntasBinding
    private var currentQuestionIndex = 0
    private var score = 0


    private val questions = arrayOf(
        "Porque é mais difícil fechar a porta do carro com as janelas fechadas?",
        "Porque quando encostamos na maçaneta de metal ela parece ser mais fria que a de madeira?",
        "De que maneira a água contribui para apagar o fogo em um incêndio?",
        "As brisas marítimas são causadas por diferenças de temperatura entre a terra e o mar. Qual processo de transferência de calor explica a formação das brisas?",
        "Qual fenômeno ocorre quando a luz solar entra em uma gota de água e cria o arco-íris?"
    )

    private val answers = arrayOf(
        arrayOf(
            "A janela quando está fechada deixa a porta mais pesada, precisando de mais força para fechá-la.",
            "A pressão interna do carro aumenta ao fechar a porta com os vidros fechados, criando uma força de resistência.",
            "Quando as janelas estão levantadas o centro de gravidade da porta muda, deixando a porta mais difícil de fechar do que quando estão abertas",
            "Tanto a janela aberta quanto fechado não mudarão a massa da porta, ou seja não haverá diferença na hora de fechá-la."
        ),
        arrayOf(
            "O metal é um condutor térmico melhor que a madeira, então a transferência de energia para o metal ocorre mais rapidamente, dando a sensação de frio.",
            "A madeira e o metal, estando na mesma temperatura, não terão diferença na transferencia de energia, o que ocorre é um fenômeno psicológico, o cérebro associa o metal sendo mais frio que a madeira.",
            "Devido a melhor condução de energia do metal, este tende a ficar mais frio que a madeira, pelo simples fato da circulação do ar, dando essa sensação de estar mais frio ao encostar.",
            "A madeira, devido suas fibras naturais, absorve mais o calor do que o metal, por isso que ao encostarmos no metal temos a sensação dele ser mais frio que a madeira, porque isso é de fato verdade."
        ),
        arrayOf(
            "Ela alimenta o fogo, aumentando a combustão e liberando vapor que consome o oxigênio",
            "Ela aumenta a quantidade de combustível, fazendo o fogo se espalhar menos",
            "Ela resfria o material em combustão, retirando o calor necessário para manter o fogo e o vapor de água evita a renovação do oxigênio",
            "Ela causa uma explosão que extingue o fogo"
        ),
        arrayOf(
            "Irradiação, causada pela absorção de calor pelo solo e pela água",
            "Condução, onde o calor é transferido diretamente da terra para o mar",
            "Difusão, que espalha o calor igualmente entre a terra e o mar",
            "Convecção, onde o ar quente sobre a terra sobe e o ar frio do mar se desloca para ocupar seu lugar"
        ),
        arrayOf(
            "Reflexão e dispersão",
            "Refração e reflexão interna",
            "Difração e absorção",
            "Absorção e dispersão"
        )
    )

    private val correctAnswers = arrayOf(
        "A pressão interna do carro aumenta ao fechar a porta com os vidros fechados, criando uma força de resistência.",
        "O metal é um condutor térmico melhor que a madeira, então a transferência de energia para o metal ocorre mais rapidamente, dando a sensação de frio.",
        "Ela resfria o material em combustão, retirando o calor necessário para manter o fogo e o vapor de água evita a renovação do oxigênio",
        "Convecção, onde o ar quente sobre a terra sobe e o ar frio do mar se desloca para ocupar seu lugar",
        "Refração e reflexão interna"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityPerguntasBinding.inflate(layoutInflater)
        setContentView(binding.root)


        showQuestion()


        binding.buttonPerguntaProximo.setOnClickListener {

            val selectedId = binding.respostasGrupo.checkedRadioButtonId
            val selectedAnswer = findViewById<RadioButton>(selectedId)

            if (selectedAnswer != null) {

                if (selectedAnswer.text.toString() == correctAnswers[currentQuestionIndex]) {
                    score += 5
                }


                currentQuestionIndex++

                if (currentQuestionIndex < questions.size) {

                    showQuestion()
                } else {

                    showRanking()
                }
            } else {
                Toast.makeText(this, "Escolha uma resposta!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showQuestion() {

        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        binding.textoPergunta.startAnimation(fadeOut)
        binding.respostasGrupo.startAnimation(fadeOut)


        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {

                binding.textoPergunta.text = questions[currentQuestionIndex]
                binding.resposta1.text = answers[currentQuestionIndex][0]
                binding.resposta2.text = answers[currentQuestionIndex][1]
                binding.resposta3.text = answers[currentQuestionIndex][2]
                binding.resposta4.text = answers[currentQuestionIndex][3]


                binding.respostasGrupo.clearCheck()


                val fadeIn = AnimationUtils.loadAnimation(this@Perguntas, R.anim.fade_in)
                binding.textoPergunta.startAnimation(fadeIn)
                binding.respostasGrupo.startAnimation(fadeIn)
            }
        })
    }

    private fun showRanking() {
        val usuario = intent.getStringExtra("login")
        binding.tituloPerguntas.setText("Parabéns, $usuario!")
        binding.textoPergunta.visibility = View.GONE
        binding.respostasGrupo.visibility = View.GONE
        binding.buttonPerguntaProximo.visibility = View.GONE
        binding.rankingText.visibility = View.VISIBLE
        binding.rankingText.text = "Sua pontuação final: $score pontos"
        binding.buttonPerguntaRanking.visibility = View.VISIBLE
        binding.buttonPerguntaVoltar.visibility = View.VISIBLE

        binding.buttonPerguntaRanking.setOnClickListener {
            val intent = Intent(this, Ranking::class.java)
            intent.putExtra("login", usuario)
            intent.putExtra("score", score)
            startActivity(intent)
            finish()
        }
        binding.buttonPerguntaVoltar.setOnClickListener {
            var intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }
}

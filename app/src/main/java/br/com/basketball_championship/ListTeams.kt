@file:Suppress("DEPRECATION")

package br.com.basketball_championship

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.list_teams.*


@Suppress("DEPRECATION")
class ListTeams : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_teams)
        val times = arrayListOf<Time>()
        val arrTeam = intent.getStringArrayListExtra("arrTeam")
        val myPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        var initializeNewRound = 0
        var position: Int


        var index = 0
        if (arrTeam!!.size != 0) {
            for (i in 0 until arrTeam.size step 2) {
                times.add(
                    index,
                    Time(arrTeam[i].toString(), arrTeam[i + 1].toString())
                )
                index += 1
            }
        }

        times.map { time -> creatRow(
            time.getTime1(),
           "0",
            time.getTime2(),
           "0"
        ) }


        button_list.setOnClickListener {
            position = myPreferences.getInt("index", 0)
            if (position - 1 >= 0) {
                times[position-1].setPoints1(myPreferences.getString("valueTeam1", "0").toString())
                times[position-1].setPoints2(myPreferences.getString("valueTeam2", "0").toString())
                tabela_campeonato.removeAllViews()
                times.map { time ->
                    creatRow(
                        time.getTime1(),
                        time.getPoints1(),
                        time.getTime2(),
                        time.getPoints2()
                    )
                }
            }
//Verifica se todos os times jogarm
            if (position < times.size) {
                nextPage2(times[position].getTime1(), times[position].getTime2())
            } else {
                val count: Int = times.size
//verifica se ja finalizou todas as rodadas
                if (initializeNewRound < (times.size - 1)) {
//inicia nova rodada
                    while (initializeNewRound < count) {
                        var time1: String
                        var time2: String
//Verifica o time vencedor da partida
                        if (times[initializeNewRound].getPoints1()
                                .toInt() > times[initializeNewRound].getPoints2().toInt()
                        ) {
                            time1 = times[initializeNewRound].getTime1()
                        } else {
                            time1 = times[initializeNewRound].getTime2()
                        }
//Verifica o time vencedor da partida
                        if (times[initializeNewRound + 1].getPoints1().toInt() > times[initializeNewRound + 1].getPoints2().toInt()) {
                            time2 = times[initializeNewRound + 1].getTime1()
                        } else {
                            time2 = times[initializeNewRound + 1].getTime2()
                        }

                        initializeNewRound += 2
//Preenche o array das partidas com a nova rodada
                        times.add(
                            index,
                            Time(time1, time2)
                        )
                        index += 1
                    }
//Limpa e implementa na view a nova lista
                    tabela_campeonato.removeAllViews()
                    times.map { time ->
                        creatRow(
                            time.getTime1(),
                            time.getPoints1(),
                            time.getTime2(),
                            time.getPoints2()
                        )
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Partida finalizada",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }

    class Time(private var time1: String, private var time2: String) {
        private  var points1: String = "0"
        private  var points2: String = "0"

        fun setTime1(time1: String) {
            this.time1 = time1
        }

        fun getTime1(): String {
            return this.time1
        }

        fun setTime2(time2: String) {
            this.time2 = time2
        }

        fun getTime2(): String {
            return this.time2
        }

        fun setPoints1(time1: String) {
            this.points1 = time1
        }

        fun getPoints1(): String {
            return this.points1
        }

        fun setPoints2(time2: String) {
            this.points2 = time2
        }

        fun getPoints2(): String {
            return this.points2
        }
    }

    @SuppressLint("RtlHardcoded")
    fun creatRow(time1: String, value1: String,time2: String, value2: String) {
        val row = TableRow(this)
        val image = ImageView(this)
        val tvTime1 = TextView(this)
        val tvTime2 = TextView(this)
        val tvValueTime1 = TextView(this)
        val tvValueTime2 = TextView(this)
        tabela_campeonato.addView(
            row,
            TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT
            )
        )

        row.addView(tvTime1, 0)
        row.addView(tvValueTime1, 1)
        row.addView(image, 2)
        row.addView(tvValueTime2, 3)
        row.addView(tvTime2, 4)
        row.gravity = Gravity.CENTER


        tvTime1.text = time1
        tvTime1.textSize = 25F
        tvTime1.setTextColor(Color.parseColor("#4919FF"))

        tvTime2.text = time2
        tvTime2.textSize = 25F
        tvTime2.setTextColor(Color.parseColor("#4919FF"))
        tvTime2.gravity = Gravity.RIGHT

        tvValueTime1.text = value1
        tvValueTime1.textSize = 25F
        tvValueTime1.setTextColor(Color.parseColor("#4919FF"))
        tvValueTime2.gravity = Gravity.CENTER

        tvValueTime2.text = value2
        tvValueTime2.textSize = 25F
        tvValueTime2.setTextColor(Color.parseColor("#4919FF"))
        tvValueTime2.gravity = Gravity.CENTER


        image.layoutParams.height = 80
        image.layoutParams.width = 80
        image.setImageResource(R.drawable.ic_match)

    }


    //Troca de páginas
    fun nextPage2(time1: String, time2: String) {
        val intent = Intent(this, Game::class.java)
        intent.putExtra("team1", time1)
        intent.putExtra("team2", time2)
        startActivity(intent)
    }

    fun back(view: View) {
        finish()
    }

    fun nextPage2(view: View) {}
}
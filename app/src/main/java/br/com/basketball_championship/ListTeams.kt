package br.com.basketball_championship

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.list_teams.*


class ListTeams : AppCompatActivity() {
    lateinit var team1: String
    lateinit var team2: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_teams)
        val arrTeam = intent.getStringArrayListExtra("arrTeam")

        team1 = arrTeam?.get(0)!!
        team2 = arrTeam.get(1)!!

        val tvTitle = TextView(this)
        val row = TableRow(this)
        tabela_campeonato.addView(
            row,
            TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT
            )
        )

        row.addView(tvTitle, 0)
        row.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.MATCH_PARENT
        )
        row.gravity = Gravity.CENTER

        tvTitle.text = "Rodada 1"
        tvTitle.gravity = Gravity.CENTER
        tvTitle.textSize = 28F
        tvTitle.setTextColor(Color.parseColor("#4919FF"))


        var i = 0
        while (i < arrTeam.size) {
            creatRow(arrTeam, i)
            i += 2
        }
    }

    @SuppressLint("RtlHardcoded")
    fun creatRow(list : ArrayList<String>, index : Int){
        val row = TableRow(this)
        val image = ImageView(this)
        val tvTime1 = TextView(this)
        val tvTime2 = TextView(this)

        tabela_campeonato.addView(
            row,
            TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT
            )
        )

        row.addView(tvTime1, 0)
        row.addView(image, 1)
        row.addView(tvTime2, 2)
        row.gravity = Gravity.CENTER


        tvTime1.text = list.get(index)
        tvTime1.textSize = 25F
        tvTime1.setTextColor(Color.parseColor("#4919FF"))

        tvTime2.text = list.get(index+1)
        tvTime2.textSize = 25F
        tvTime2.setTextColor(Color.parseColor("#4919FF"))
        tvTime2.gravity = Gravity.RIGHT


        image.layoutParams.height = 80
        image.layoutParams.width = 80
        image.setImageResource(R.drawable.ic_match)

    }


    //Troca de páginas
    fun nextPage2(view: View) {

        val intent = Intent(this, Game::class.java)
        intent.putExtra("team1", team1)
        intent.putExtra("team2", team2)
        startActivity(intent)
    }

    fun back(view: View) {
        finish()
    }
}
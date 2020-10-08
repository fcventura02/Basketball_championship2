package br.com.basketball_championship

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.game.*

class Game : AppCompatActivity() {
    var pointsTeam1 = 0
    var pointsTeam2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        val team1 = intent.getStringExtra("team1")
        val team2 = intent.getStringExtra("team2")
        game_nameTeam1.text = team1
        game_nameTeam2.text = team2
        game_pointsTeam1.text = pointsTeam1.toString()
        game_pointsTeam2.text = pointsTeam2.toString()

        game_point1.setOnClickListener {
            actionPointPositive(1, team1.toString(), team2.toString())
        }
        game_point3.setOnClickListener {
            actionPointPositive(3, team1.toString(), team2.toString())
        }
        game_pointNegative1.setOnClickListener {
            actionPointNegative(1, team1.toString(), team2.toString())
        }
        game_pointNegative3.setOnClickListener {
            actionPointNegative(3, team1.toString(), team2.toString())
        }

    }

    fun actionPointPositive(int: Int, team1: String, team2: String) {
        AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_check)
            .setTitle("Adicionar Pontos")
            .setMessage("Qual time fez pontos?")
            .setNegativeButton(
                team1
            ) { dialogInterface, i ->
                pointsTeam1 = sun(int, pointsTeam1)
                game_pointsTeam1.text = pointsTeam1.toString()
            }
            .setPositiveButton(
                team2
            ) { dialogInterface, i ->
                pointsTeam2 = sun(int, pointsTeam2)
                game_pointsTeam2.text = pointsTeam2.toString()

            }
            .show()
    }

    fun actionPointNegative(int: Int, team1: String, team2: String) {
        if (pointsTeam1 >= int || pointsTeam2 >= int) {
            AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_cancel)
                .setTitle("Pontos Anulados")
                .setMessage("Qual time o ponto foi anulado?")
                .setNegativeButton(
                    team1
                ) { dialogInterface, i ->
                    if (pointsTeam1 >= int) {
                        pointsTeam1 = sub(int, pointsTeam1)
                        game_pointsTeam1.text = pointsTeam1.toString()
                    }
                }
                .setPositiveButton(
                    team2
                ) { dialogInterface, i ->
                    if (pointsTeam2 >= int) {
                        pointsTeam2 = sub(int, pointsTeam2)
                        game_pointsTeam2.text = pointsTeam2.toString()
                    }
                }
                .show()
        }
    }

    fun sun(int: Int, value: Int): Int {
        val valor = int + value
        return valor
    }

    fun sub(int: Int, value: Int): Int {
        return value - int
    }

    fun nextPage2(view: View) {
        val intent = Intent(this, ListTeams::class.java)
        startActivity(intent)
    }

    fun back(view: View) {
        finish()
    }
}
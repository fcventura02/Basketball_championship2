@file:Suppress("DEPRECATION")

package br.com.basketball_championship

import android.app.AlertDialog
import android.widget.ArrayAdapter
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var listView: ListView
    var arrTeam = ArrayList<String>()
    lateinit var adapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val mayEditor = myPreferences.edit()
        mayEditor.putInt("index", 0)
        mayEditor.putString("valueTeam1", "0")
        mayEditor.putString("valueTeam2", "0")
        mayEditor.apply()
        //Identificando a lista
        listView = findViewById(R.id.list_teams)



        //adicionando um novo time
        btm_confirm.setOnClickListener {
            addTeam()
        }
        //verifica o estado dos valores
        if(savedInstanceState != null){
            arrTeam = savedInstanceState.getStringArrayList("teams_list" )as ArrayList<String>
        }

        //Vincula a váriavel ao adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arrTeam)
        //adicionando estado a lista
        listView.adapter = adapter

        //adicionando ação ao clicar em um item
        listView.setOnItemClickListener { parent, view, position, id ->
            //Caixa de dialogo para deletar um item
            AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_cancel)
                .setTitle("Deletar time")
                .setMessage("deseja deletar o time ${arrTeam[position]}")
                .setPositiveButton("Sim") { _, _ ->
                    arrTeam.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("Não", null)
                .show()
        }
    }

    fun addTeam() {
        arrTeam.add(name_team.text.toString())
        adapter.notifyDataSetChanged()
        name_team.text.clear()
    }

    fun nextPage(view: View) {
        if (arrTeam.size == 4 || arrTeam.size == 8 || arrTeam.size == 16) {
            val intent = Intent(this, ListTeams::class.java)
            intent.putExtra("arrTeam", arrTeam)
            startActivity(intent)
        } else {
            Toast.makeText(
                this,
                "Entre com pelomenos 4, 8 ou 16 times. Você inseriu ${arrTeam.size} times.",
                Toast.LENGTH_LONG
            )
                .show()
        }

    }

    //salva o estado das variaveis
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("teams_list", arrTeam)
    }

}


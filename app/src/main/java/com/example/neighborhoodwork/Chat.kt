package com.example.neighborhoodwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_chat.*

class Chat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        img5Menu.setOnClickListener {
            val fastMenu = PopupMenu(this, it)
            fastMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.Menu_Start -> {
                        var nowa = Intent(applicationContext, Home::class.java)
                        startActivity(nowa)
                        true
                    }
                    R.id.Menu_Kreator -> {
                        var nowa = Intent(applicationContext, Home::class.java)
                        startActivity(nowa)
                        true
                    }
                    R.id.Menu_Zadania -> {
                        var nowa = Intent(applicationContext, Home::class.java)
                        startActivity(nowa)
                        true
                    }
                    R.id.Menu_Ustawienia -> {
                        var nowa = Intent(applicationContext, Home::class.java)
                        startActivity(nowa)
                        true
                    }
                    else -> false
                }
            }
            fastMenu.inflate(R.menu.main_menu)
            fastMenu.show()
        }

        img5Mapa.setOnClickListener {
            val mapa = Intent(applicationContext, Home::class.java)
            startActivity(mapa)
        }


        rc5.apply {
            layoutManager = LinearLayoutManager(this@Chat)
            addItemDecoration(TopSpacingItemDecoration(30))
            adapter = AdapterChat()
        }
    }
}

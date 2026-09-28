package com.example.hariak_bancua

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hariak_bancua.ui.theme.Hariak_BancuaTheme
class Kontua(var saldoa: Int) {

    @Synchronized
    fun ateraDirua(kopurua: Int, izena: String) {

        if (saldoa >= kopurua) {

            saldoa -= kopurua

            println("$izena-k $kopurua € atera ditu.")
            println("Geratzen den saldoa: $saldoa €")

        } else {

            println("$izena-k ezin du $kopurua € atera.")
            println("Ez dago nahikoa dirurik kontuan.")
        }
    }
}
class Pertsona(
    private val kontua: Kontua,
    private val izena: String
) : Thread() {

    override fun run() {

        // 4 aldiz 10 € ateratzen saiatzen da
        repeat(4) {
            kontua.ateraDirua(10, izena)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Hariak_BancuaTheme {
                Banco()
            }
        }
    }
}

@Composable
fun Banco() {

    var resultado by remember {
        mutableStateOf("Pulsa el botón para empezar")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(30.dp)
        ) {

            Text(
                text = resultado
            )

            Button(
                onClick = {

                    // 1. Crear cuenta con 40 €
                    val kontua = Kontua(40)

                    // 2. Crear los dos hilos
                    val ana = Pertsona(kontua, "Ana")
                    val mikel = Pertsona(kontua, "Mikel")

                    // 3. Ejecutar los dos hilos
                    ana.start()
                    mikel.start()

                    resultado = "Ana eta Mikel dirua ateratzen ari dira..."
                }
            ) {
                Text("Hasi")
            }
        }
    }
}
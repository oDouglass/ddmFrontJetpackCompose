package up.ddm

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val atributos: Atributos? = intent.getParcelableExtra("atributos")

        setContent {
            if (atributos != null) {
                RaceSelectionScreen(atributos = atributos){ selectedRace ->}
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                // Botão para voltar à MainActivity
                Button(onClick = {
                    finish()
                }) {
                    Text(text = "Voltar para Distribuição de Pontos")
                }

            }
        }
    }

    @Composable
    fun RaceSelectionScreen(atributos: Atributos, onCharacterCreated: (String) -> Unit) {
        // Lista de raças
        val races = listOf(
            "AltoElfo", "Anao", "AnaoColina", "AnaoMontanha", "Draconato", "Drow",
            "Elfo", "ElfoFloresta", "Gnomo", "GnomoFloresta", "GnomoRochas",
            "Halfling", "HalflingPesLeves", "HalflingRobusto", "Humano",
            "MeioElfo", "MeioOrc", "Tiefling"
        )

        // Estado para controlar a raça selecionada
        var selectedRace by remember { mutableStateOf(races[0]) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(text = "Selecione a Raça", fontSize = 18.sp)

            // Exibir a raça selecionada
            Text(text = "Raça Selecionada: $selectedRace", fontSize = 16.sp)

            // Botão para criar o personagem
            Button(onClick = {
                onCharacterCreated(selectedRace)
                // Aqui está abrindo a ThirdActivityT
                val intent = Intent(this@SecondActivity, ThirdActivityT::class.java)
                intent.putExtra("nomeDaRaca", selectedRace)
                intent.putExtra("atributos", atributos)
                startActivity(intent)
            }) {
                Text("Criar Personagem")
            }

            // Retângulo com a lista de raças no canto inferior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(BorderStroke(1.dp, Color.Gray))
                    .padding(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Coluna da esquerda
                    Column(modifier = Modifier.weight(1f)) {
                        for (i in 0 until races.size / 2) {
                            RaceItem(races[i]) { selectedRace = races[i] }
                        }
                    }
                    // E Coluna da direita
                    Column(modifier = Modifier.weight(1f)) {
                        for (i in races.size / 2 until races.size) {
                            RaceItem(races[i]) { selectedRace = races[i] }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun RaceItem(race: String, onClick: () -> Unit) {
        Text(
            text = race,
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(8.dp),
            fontSize = 16.sp
        )
    }

}
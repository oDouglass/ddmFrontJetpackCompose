package up.ddm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

     var atributos = Atributos()
     var distribuidor = DistribuidorDePontos(atributos, 27)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AtributosScreen(distribuidor)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Botão que chama a segunda activity
                Button(onClick = {
                    // Criar o Intent para chamar a segunda activity
                    val intent = Intent(this@MainActivity, SecondActivity::class.java)
                    intent.putExtra("atributos", atributos)
                    startActivity(intent)
                }) {
                    Text(text = "Abrir Escolha de Raça")
                }
            }
        }
    }
}

@Composable
fun AtributosScreen(atributos: DistribuidorDePontos) {
    var pontosRestantes by remember { mutableStateOf(atributos.getPontosDisponiveis()) }
    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Criação de Personagem", fontSize = 18.sp)

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Pontos restantes:", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "$pontosRestantes", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Coluna
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Atributo", modifier = Modifier.weight(1f))
            Text(text = "Valor", modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Atributos com colunas adicionais
        AtributoInputRow("Força", atributos, { pontosRestantes = atributos.getPontosDisponiveis() }, { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })
        Spacer(modifier = Modifier.height(8.dp))
        AtributoInputRow("Destreza", atributos, { pontosRestantes = atributos.getPontosDisponiveis() }, { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })
        Spacer(modifier = Modifier.height(8.dp))
        AtributoInputRow("Constituição", atributos, { pontosRestantes = atributos.getPontosDisponiveis() }, { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })
        Spacer(modifier = Modifier.height(8.dp))
        AtributoInputRow("Sabedoria", atributos, { pontosRestantes = atributos.getPontosDisponiveis() }, { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })
        Spacer(modifier = Modifier.height(8.dp))
        AtributoInputRow("Inteligência", atributos, { pontosRestantes = atributos.getPontosDisponiveis() }, { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })
        Spacer(modifier = Modifier.height(8.dp))
        AtributoInputRow("Carisma", atributos, { pontosRestantes = atributos.getPontosDisponiveis() }, { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })

        if (snackbarVisible) {
            Snackbar(
                action = {
                    Button(onClick = { snackbarVisible = false }) {
                        Text("Fechar")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(snackbarMessage)
            }
        }
    }
}

@Composable
fun AtributoInputRow(label: String, atributos: DistribuidorDePontos, updatePontos: () -> Unit, onError: (String) -> Unit) {
    var textValue by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Coluna de Atributos
        Text(text = label, modifier = Modifier.weight(1f))

        // Campo de entrada de número inteiro
        OutlinedTextField(
            value = textValue,
            onValueChange = { newValue ->
                // Permitir apenas números
                if (newValue.all { it.isDigit() }) {
                    textValue = newValue
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .weight(1f)
                .width(50.dp)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused && textValue.isNotEmpty()) {
                        try {
                            val valor = textValue.toInt()
                            atributos.aumentarAtributo(label, valor) // Envia o valor para setAtributo
                            updatePontos() // Atualiza os pontos
                        } catch (e: IllegalArgumentException) {
                            onError(e.message ?: "Erro desconhecido")
                            textValue = "" // Limpa o campo em caso de erro
                        } catch (e: NumberFormatException) {
                            onError("Por favor, insira um número válido.")
                            textValue = "" // Limpa o campo em caso de erro
                        }
                    }
                }
        )
    }
}

fun showMessage(message: String, callback: (String) -> Unit) {
    callback(message)
}
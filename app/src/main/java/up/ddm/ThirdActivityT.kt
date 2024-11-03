package up.ddm

import AltoElfo
import Anao
import AnaoColina
import AnaoMontanha
import Draconato
import Drow
import Elfo
import ElfoFloresta
import Gnomo
import GnomoFloresta
import GnomoRochas
import Halfling
import HalflingPesLeves
import HalflingRobusto
import Humano
import MeioElfo
import MeioOrc
import Racas.iRaca
import Tiefling
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.Personagem
import up.ddm.database.DatabaseHelper
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

class ThirdActivityT : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val atributos: Atributos? = intent.getParcelableExtra("atributos")
        val nomeDaRaca = intent.getStringExtra("nomeDaRaca")
        val racaEscolhida = escolherRaca(nomeDaRaca.toString())
        val personagem = Personagem(atributos!!, racaEscolhida)

        setContent {

            MostrarAtributoComNome(personagem)

        }
    }

    @Composable
    fun SalvarPersonagemButton(personagem: Personagem) {
        // OBTER O CONTEXTO ATUAL USANDO LOCALCONTEXT
        val context = LocalContext.current
        // INSTÂNCIA DO DATABASEHELPER
        val dbHelper = DatabaseHelper(context)

        // SNACKBAR
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Ajusta o padding com base no Scaffold
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        try {
                            // SALVAR O PERSONAGEM NO BD
                            val personagemId = dbHelper.addPersonagem(personagem)

                            // MOSTRAR A MENSAGEM DE SUCESSO
                            scope.launch {
                                snackbarHostState.showSnackbar("Personagem adicionado com ID: $personagemId")
                                val intent = Intent(this@ThirdActivityT, FourthActivity::class.java)
                                startActivity(intent)
                            }
                        } catch (e: Exception) {
                            // MOSTRAR A MENSAGEM DE ERRO
                            scope.launch {
                                snackbarHostState.showSnackbar("Erro: ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(text = "Salvar Personagem")
                }
            }
        }
    }

    fun escolherRaca(race: String): iRaca {
        val racaSelecionada: iRaca = when(race){
            "AltoElfo" -> AltoElfo()
            "Anao" -> Anao()
            "AnaoColina" -> AnaoColina()
            "AnaoMontanha" -> AnaoMontanha()
            "Draconato" -> Draconato()
            "Drow" -> Drow()
            "Elfo" -> Elfo()
            "ElfoFloresta" -> ElfoFloresta()
            "Gnomo" -> Gnomo()
            "GnomoFloresta" -> GnomoFloresta()
            "GnomoRochas" -> GnomoRochas()
            "Halfling" -> Halfling()
            "HalflingPesLeves" -> HalflingPesLeves()
            "HalflingRobusto" -> HalflingRobusto()
            "Humano" -> Humano()
            "MeioElfo" -> MeioElfo()
            "MeioOrc" -> MeioOrc()
            "Tiefling" -> Tiefling()
            else -> Humano()
        }
        return racaSelecionada
    }

// NÃO ESTÁ SENDO USADO
    @Composable
    fun MostrarAtributosFeio(personagem: Personagem) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Cabeçalho
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Atributo", fontSize = 18.sp)
                Text("Valor", fontSize = 18.sp)
                Text("Mod", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Exibir os atributos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Força")
                Text(personagem.atributos.forca.toString())
                Text(calcularModificador(personagem.atributos.forca).toString())
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Destreza")
                Text(personagem.atributos.destreza.toString())
                Text(calcularModificador(personagem.atributos.destreza).toString())
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Constituição")
                Text(personagem.atributos.constituicao.toString())
                Text(calcularModificador(personagem.atributos.constituicao).toString())
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Inteligência")
                Text(personagem.atributos.inteligencia.toString())
                Text(calcularModificador(personagem.atributos.inteligencia).toString())
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Sabedoria")
                Text(personagem.atributos.sabedoria.toString())
                Text(calcularModificador(personagem.atributos.sabedoria).toString())
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Carisma")
                Text(personagem.atributos.carisma.toString())
                Text(calcularModificador(personagem.atributos.carisma).toString())
            }

            val vidaCalculada = 10 + calcularModificador(personagem.atributos.constituicao)
            personagem.pontosDeVida = vidaCalculada

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Vida")
                Text(personagem.pontosDeVida.toString())
                Text("")
            }
        }
    }
// NÃO ESTÁ MAIS SENDO USADO

    fun calcularModificador(valor: Int): Int {
        return (valor - 10) / 2
    }

// NÃO ESTÁ MAIS SENDO USADO
    @Composable
    fun MostrarAtributoBonito(personagem: Personagem) {

        val vidaCalculada = 10 + calcularModificador(personagem.atributos.constituicao)
        personagem.pontosDeVida = vidaCalculada

        // Usando Card para encapsular os atributos
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Cabeçalho
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Atributo", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Valor", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Mod", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Exibir os atributos
                AtributoRow("Força", personagem.atributos.forca, calcularModificador(personagem.atributos.forca))
                Divider(color = Color.Gray, thickness = 1.dp)
                AtributoRow("Destreza", personagem.atributos.destreza, calcularModificador(personagem.atributos.destreza))
                Divider(color = Color.Gray, thickness = 1.dp)
                AtributoRow("Constituição", personagem.atributos.constituicao, calcularModificador(personagem.atributos.constituicao))
                Divider(color = Color.Gray, thickness = 1.dp)
                AtributoRow("Inteligência", personagem.atributos.inteligencia, calcularModificador(personagem.atributos.inteligencia))
                Divider(color = Color.Gray, thickness = 1.dp)
                AtributoRow("Sabedoria", personagem.atributos.sabedoria, calcularModificador(personagem.atributos.sabedoria))
                Divider(color = Color.Gray, thickness = 1.dp)
                AtributoRow("Carisma", personagem.atributos.carisma, calcularModificador(personagem.atributos.carisma))

                Spacer(modifier = Modifier.height(8.dp))

                AtributoRow("Vida", personagem.pontosDeVida, "")

                // SALVAR O PERSONAGEM NO BANCO DE DADOS
                SalvarPersonagemButton(personagem)
            }
        }
    }
// NÃO ESTÁ MAIS SENDO USADO

// FUNÇÃO PARA EXIBIR UMA LINHA DE ATRIBUTO
    @Composable
    fun AtributoRow(nome: String, valor: Int, modificador: Any) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = nome, fontSize = 16.sp)
            Text(text = valor.toString(), fontSize = 16.sp)
            Text(text = modificador.toString(), fontSize = 16.sp)
        }
    }

    @Composable
    fun MostrarAtributoComNome(personagem: Personagem) {
        val vidaCalculada = 10 + calcularModificador(personagem.atributos.constituicao)
        personagem.pontosDeVida = vidaCalculada

        // ESTADO PARA O CAMPO DE NOME
        var nomePersonagem by remember { mutableStateOf(personagem.nome) }
        var nomeInvalido by remember { mutableStateOf(false) } // Estado para validação

        // CARD PARA OS ATRIBUTOS
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // CABEÇALHO
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Atributo", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Valor", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Mod", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // EXIBIR OS ATRIBUTOS
                AtributoRow("Força", personagem.atributos.forca, calcularModificador(personagem.atributos.forca))
                Divider(color = Color.Gray, thickness = 1.dp)
                AtributoRow("Destreza", personagem.atributos.destreza, calcularModificador(personagem.atributos.destreza))
                Divider(color = Color.Gray, thickness = 1.dp)
                AtributoRow("Constituição", personagem.atributos.constituicao, calcularModificador(personagem.atributos.constituicao))
                Divider(color = Color.Gray, thickness = 1.dp)
                AtributoRow("Inteligência", personagem.atributos.inteligencia, calcularModificador(personagem.atributos.inteligencia))
                Divider(color = Color.Gray, thickness = 1.dp)
                AtributoRow("Sabedoria", personagem.atributos.sabedoria, calcularModificador(personagem.atributos.sabedoria))
                Divider(color = Color.Gray, thickness = 1.dp)
                AtributoRow("Carisma", personagem.atributos.carisma, calcularModificador(personagem.atributos.carisma))

                Spacer(modifier = Modifier.height(8.dp))

                AtributoRow("Vida", personagem.pontosDeVida, "")

                Spacer(modifier = Modifier.height(16.dp))

                // CAMPO DE TEXTO PARA NOME DO PERSONAGEM
                TextField(
                    value = nomePersonagem,
                    onValueChange = {
                        nomePersonagem = it
                        personagem.nome = it // ARMAZENA O NOME NO PERSONAGEM
                        nomeInvalido = false
                    },
                    label = { Text("Nome do Personagem") },
                    isError = nomeInvalido,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                if (nomeInvalido) {
                    Text(
                        text = "Nome é obrigatório",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // SALVAR O PERSONAGEM NO BANCO DE DADOS
                SalvarPersonagemButton(personagem = personagem)
            }
        }
    }

}


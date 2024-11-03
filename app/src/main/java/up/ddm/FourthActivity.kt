package up.ddm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.Personagem
import up.ddm.database.DatabaseHelper

class FourthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConsultaEExclusaoPersonagem()
        }
    }
}

@Composable
fun ConsultaEExclusaoPersonagem() {
    val context = LocalContext.current
    val dbHelper = DatabaseHelper(context)

    var nomeConsulta by remember { mutableStateOf("") }
    var nomeExcluir by remember { mutableStateOf("") }
    var personagemEncontrado by remember { mutableStateOf<Personagem?>(null) }
    var mensagemStatus by remember { mutableStateOf("") }
    var listaPersonagens by remember { mutableStateOf<List<Pair<Long, String>>>(emptyList()) }

    // VARIAVEIS PARA ALTERAÇÃO
    var idAlterar by remember { mutableStateOf("") }
    var novoNome by remember { mutableStateOf("") }

    // CARREGA A LISTA DE PERSONAGENS AO INICIAR
    LaunchedEffect(Unit) {
        listaPersonagens = dbHelper.listarPersonagens()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Consultar Personagem(Em Desenvolvimento)", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        TextField(
            value = nomeConsulta,
            onValueChange = { nomeConsulta = it },
            label = { Text("Nome do Personagem") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                //personagemEncontrado = dbHelper.consultarPersonagemPorNome(nomeConsulta)
                mensagemStatus = if (personagemEncontrado != null) {
                    "Personagem encontrado!"
                } else {
                    "Personagem não encontrado."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Consultar")
        }

        personagemEncontrado?.let { personagem ->
            Text("Nome: ${personagem.nome}")
            Text("Força: ${personagem.atributos.forca}")
            Text("Destreza: ${personagem.atributos.destreza}")
            Text("Constituição: ${personagem.atributos.constituicao}")
            Text("Inteligência: ${personagem.atributos.inteligencia}")
            Text("Sabedoria: ${personagem.atributos.sabedoria}")
            Text("Carisma: ${personagem.atributos.carisma}")
            Text("Vida: ${personagem.pontosDeVida}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Excluir Personagem", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        TextField(
            value = nomeExcluir,
            onValueChange = { nomeExcluir = it },
            label = { Text("Nome do Personagem para Excluir") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val sucesso = dbHelper.excluirPersonagem(nomeExcluir)
                mensagemStatus = if (sucesso) {
                    "Personagem excluído com sucesso."
                } else {
                    "Erro ao excluir personagem."
                }
                nomeExcluir = ""
                personagemEncontrado = null
                listaPersonagens = dbHelper.listarPersonagens()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Excluir")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(mensagemStatus, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(16.dp))

        // ALTERAR NOME DO PERSONAGEM
        Text("Alterar Nome do Personagem", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        TextField(
            value = idAlterar,
            onValueChange = { idAlterar = it },
            label = { Text("ID do Personagem") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = novoNome,
            onValueChange = { novoNome = it },
            label = { Text("Novo Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val idLong = idAlterar.toLongOrNull()
                if (idLong != null && novoNome.isNotBlank()) {
                    val sucesso = dbHelper.alterarNomePersonagem(idLong, novoNome)
                    mensagemStatus = if (sucesso) {
                        "Nome alterado com sucesso."
                    } else {
                        "Erro ao alterar o nome."
                    }
                    listaPersonagens = dbHelper.listarPersonagens()
                    idAlterar = ""
                    novoNome = ""
                } else {
                    mensagemStatus = "ID inválido ou nome vazio."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Alterar Nome")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LISTA DE PERSONAGENS CRIADOS
        Text("Lista de Personagens", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        listaPersonagens.forEach { (id, nome) ->
            Text("ID: $id, Nome: $nome")
        }
    }
}

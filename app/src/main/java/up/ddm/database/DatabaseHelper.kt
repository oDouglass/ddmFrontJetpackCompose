package up.ddm.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.example.Personagem
import up.ddm.Atributos

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "app_database.db"
        private const val DATABASE_VERSION = 3

        // SÃO AS CONSTATNES PARA A TABELA DE ATRIBUTOS
        const val TABLE_ATRIBUTOS = "atributos"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_FORCA = "forca"
        const val COLUMN_DESTREZA = "destreza"
        const val COLUMN_CONSTITUICAO = "constituicao"
        const val COLUMN_INTELIGENCIA = "inteligencia"
        const val COLUMN_SABEDORIA = "sabedoria"
        const val COLUMN_CARISMA = "carisma"
        const val COLUMN_VIDA = "vida"

        // COMANDO SQL PARA CRIAR A TABELA DE ATRIBUTOS
        private const val CREATE_TABLE_ATRIBUTOS = """
            CREATE TABLE $TABLE_ATRIBUTOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_FORCA INTEGER,
                $COLUMN_DESTREZA INTEGER,
                $COLUMN_CONSTITUICAO INTEGER,
                $COLUMN_INTELIGENCIA INTEGER,
                $COLUMN_SABEDORIA INTEGER,
                $COLUMN_CARISMA INTEGER,
                $COLUMN_VIDA INTEGER
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableAtributos = """
        CREATE TABLE $TABLE_ATRIBUTOS (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME TEXT NOT NULL,
            $COLUMN_FORCA INTEGER NOT NULL,
            $COLUMN_DESTREZA INTEGER NOT NULL,
            $COLUMN_CONSTITUICAO INTEGER NOT NULL,
            $COLUMN_INTELIGENCIA INTEGER NOT NULL,
            $COLUMN_SABEDORIA INTEGER NOT NULL,
            $COLUMN_CARISMA INTEGER NOT NULL,
            $COLUMN_VIDA INTEGER NOT NULL
        )
    """
        db.execSQL(createTableAtributos)
    }

// FUNÇÃO QUE IRÁ ADD NOSSO PERSONAGEM NO BD
    fun addPersonagem(personagem: Personagem): Long {
        val db = this.writableDatabase

        // CRIAR UM CONJUNTO DE VALORES PARA A INSERÇÃO DOS DADOS
        val values = ContentValues().apply {
            put(COLUMN_NAME, personagem.nome)
            put(COLUMN_FORCA, personagem.atributos.forca)
            put(COLUMN_DESTREZA, personagem.atributos.destreza)
            put(COLUMN_CONSTITUICAO, personagem.atributos.constituicao)
            put(COLUMN_INTELIGENCIA, personagem.atributos.inteligencia)
            put(COLUMN_SABEDORIA, personagem.atributos.sabedoria)
            put(COLUMN_CARISMA, personagem.atributos.carisma)
            put(COLUMN_VIDA, personagem.pontosDeVida)
        }

        // INSERIR A LINHA E RETORNAR O ID DA NOVA LINHA INSERIDA
        return db.insert(TABLE_ATRIBUTOS, null, values).also {
            db.close()
        }
    }

//FUNÇÃO PARA SER USADA QUANDO FOR CRIAR UM NOVO BD COM BASE NA VERSÃO
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ATRIBUTOS")
        onCreate(db)
    }

//FUNÇÃO PARA VERIFICAR SE O PERSONAGEM JÁ EXISTE NO BD
    fun verificarPersonagemExiste(nome: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ATRIBUTOS WHERE $COLUMN_NAME = ?"
        val cursor = db.rawQuery(query, arrayOf(nome))
        val existe = cursor.count > 0
        cursor.close()
        db.close() // Fechar o banco após a consulta
        return existe
    }

/* //FUNÇÃO PARA CONSULTAR PERSONAGEM PELO NOME
    fun consultarPersonagemPorNome(nome: String): Personagem? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_ATRIBUTOS,
            null,
            "$COLUMN_NAME = ?",
            arrayOf(nome),
            null,
            null,
            null
        )

        // Usando a let para evitar o uso do if-else
        val personagem = cursor.use { c ->
            if (c.moveToFirst()) {
                Personagem(
                    nome = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME)),
                    atributos = Atributos(
                        forca = c.getInt(c.getColumnIndexOrThrow(COLUMN_FORCA)),
                        destreza = c.getInt(c.getColumnIndexOrThrow(COLUMN_DESTREZA)),
                        constituicao = c.getInt(c.getColumnIndexOrThrow(COLUMN_CONSTITUICAO)),
                        inteligencia = c.getInt(c.getColumnIndexOrThrow(COLUMN_INTELIGENCIA)),
                        sabedoria = c.getInt(c.getColumnIndexOrThrow(COLUMN_SABEDORIA)),
                        carisma = c.getInt(c.getColumnIndexOrThrow(COLUMN_CARISMA))
                    ),
                    pontosDeVida = c.getInt(c.getColumnIndexOrThrow(COLUMN_VIDA))
                )
            } else {
                null
            }
        }

        db.close() // Fecha o banco após a consulta
        return personagem
    }
*/

//FUNÇÃO PARA EXCLUIR O PERSONAGEM, COMPROVANDO SUA CRIAÇÃO NO BD E EXCLUSÃO
fun excluirPersonagem(nome: String): Boolean {
        val db = this.writableDatabase
        val linhasAfetadas = db.delete(TABLE_ATRIBUTOS, "$COLUMN_NAME = ?", arrayOf(nome))
        return linhasAfetadas > 0
    }

//FUNÇÃO PARA LISTAR OS PERSONAGENS, COMPROVANDO SUA CRIAÇÃO NO BD
    fun listarPersonagens(): List<Pair<Long, String>> {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_ATRIBUTOS,
            arrayOf(COLUMN_ID, COLUMN_NAME),
            null,
            null,
            null,
            null,
            null
        )

        val listaPersonagens = mutableListOf<Pair<Long, String>>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            listaPersonagens.add(Pair(id, nome))
        }

        cursor.close()
        db.close()
        return listaPersonagens
    }

    fun alterarNomePersonagem(id: Long, novoNome: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, novoNome)
        }
        val sucesso = db.update(TABLE_ATRIBUTOS, values, "$COLUMN_ID = ?", arrayOf(id.toString())) > 0
        db.close()
        return sucesso
    }

}

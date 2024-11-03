import Racas.iRaca
import up.ddm.Atributos

class MeioOrc : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            forca += 2
            constituicao += 1
        }
    }
}

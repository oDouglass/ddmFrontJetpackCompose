import Racas.iRaca
import up.ddm.Atributos

class AnaoMontanha : iRaca {

    override fun aplicarAprimoramento(atributos: Atributos): Atributos {
        return atributos.apply {
            forca += 2
        }
    }
}


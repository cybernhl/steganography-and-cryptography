import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.experimental.xor

//public  fun transformBits(message: List<Byte>, password: String) = getBits(password).let { keyBits ->
//    message.mapIndexed { index, byte -> byte xor keyBits[index % keyBits.size] }
//}

public fun transformBits(message: List<Byte>, password: String): List<Byte> {
    return getBits(password).let { keyBits ->
        message.mapIndexed { index, byte ->
            byte xor keyBits[index % keyBits.size]
        }
    }
}

public fun getBufferedImage(file: File): BufferedImage? {
    return try {
        ImageIO.read(file)
    } catch (e: Exception) {
        null
    }
}

public  fun decodeMessage(message: List<Byte>, password: String) =
    transformBits(message, password).joinToString("").chunked(8).map { it.toInt(2).toChar() }.joinToString("")
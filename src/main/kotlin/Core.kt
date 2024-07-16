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

public fun steganographyEncoder(image: BufferedImage, message_with_pwd_and_end_bits:  List<Byte>): BufferedImage {
//    val copyImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB)//FIXME can not use copy will let Encoder image fail !!
    var index = 0
    start@ for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            if (index == message_with_pwd_and_end_bits.size) break@start
            val bit = message_with_pwd_and_end_bits[index++].toInt()
            val color = Color(image.getRGB(x, y)).let {
                Color(
                    it.red,
                    it.green,
                    it.blue.and(254).or(bit)
                )
            }
            image.setRGB(x, y, color.rgb)
        }
    }
    return image
}

public fun steganographyDecoder(image: BufferedImage,password: String) :String {
    val message = mutableListOf<Byte>()
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            message.add((Color(image.getRGB(x, y)).blue % 2).toByte())
            if (message.size >= END.size && message.size % 8 == 0 && message.takeLast(END.size) == END) {
                return "Message:\n" + decodeMessage(message.dropLast(END.size), password)
            }
        }
    }
    return "No message was hidden!"
}
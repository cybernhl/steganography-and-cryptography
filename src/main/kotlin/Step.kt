import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

public fun hide(): String {
    val input = getFile("Input")
    val output = getFile("Output")
    val userMessage = getString("Message to hide:").let { it.ifEmpty { null } }
        ?: return "Message can't be empty!"
    val password = getPassword() ?: return PASSWORD_ERROR
    val image = getBufferedImage(input) ?: return INPUT_ERROR
    val enoughSpace = image.width * image.height >= userMessage.length * 8 + END.size
    val notLargeEnough = "The input image is not large enough to hold this message."
    val message =
        if (enoughSpace) "Message saved in ${output.name} image." else return notLargeEnough
    val bits = transformBits(getBits(userMessage), password) + END
    val newImage = steganographyEncoder(image, bits)
    return if (saveImage(newImage, output)) message else "Can't write to output file!"
}

public fun saveImage(image: BufferedImage, file: File): Boolean {
    return try {
        ImageIO.write(image, "png", file)
    } catch (e: Exception) {
        false
    }
}

public fun show(): String {
    val input = getFile("Input")
    val password = getPassword() ?: return PASSWORD_ERROR
    val image = getBufferedImage(input) ?: return INPUT_ERROR
    return steganographyDecoder(image, password)
}


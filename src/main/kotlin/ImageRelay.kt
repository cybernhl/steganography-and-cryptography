import org.apache.commons.io.FilenameUtils
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO

public class ImageRelay(byteArray: ByteArray) {
    private val bytes: ByteArray = byteArray
    private val image: BufferedImage
    private val _height: Int
    private val _width: Int
    public val height: Int get() = _height
    public val width: Int get() = _width

    init {
        image = ImageIO.read(ByteArrayInputStream(bytes))
        _width = image.width
        _height = image.height
    }

    public fun getRGB(x: Int, y: Int): Int {
        return image.getRGB(x, y)
    }

    public fun setRGB(x: Int, y: Int, rgb: Int) {
        image.setRGB(x, y, rgb)
    }

    public fun export(imagePath: String = "image") {
        val imageType = FilenameUtils.getExtension(imagePath)
        require(imageType in listOf("tiff", "bmp", "gif", "wbmp", "png"))
        val outputfile = File("$imagePath")
        ImageIO.write(image, imageType, outputfile)
    }

    public fun copy(): ImageRelay = ImageRelay(bytes)
}
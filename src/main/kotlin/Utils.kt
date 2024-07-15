import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO


public fun getPassword() = getString("Password:").let { it.ifEmpty { null } }
public fun getFile(type: String) = File(getString("$type image file:"))
public fun getString(message: String): String {
    println(message)
    return readln()
}

public fun getBits(message: String) = message.map { it.code }.map {
    it.toString(2).padStart(8, '0').map { char -> char.digitToInt().toByte() }
}.flatten()

//Ref : https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
public fun BufferedImage.deepCopy(image: BufferedImage): BufferedImage {
    val cm = image.colorModel
    val isAlphaPremultiplied = cm.isAlphaPremultiplied
//    val raster = image.copyData(null)
    val raster = image.copyData(image.raster.createCompatibleWritableRaster())
    return BufferedImage(cm, raster, isAlphaPremultiplied, null)
}

public fun BufferedImage.clone(image: BufferedImage): BufferedImage {
    val clone = BufferedImage(
        image.width,
        image.height, image.type
    )
    val g2d = clone.createGraphics()
    g2d.drawImage(image, 0, 0, null)
    g2d.dispose()
    return clone
}

public fun fileToBufferedImage(file: File): BufferedImage? {
    return try {
        val byteArray = file.readBytes()
        val inputStream = ByteArrayInputStream(byteArray)
        ImageIO.read(inputStream)
    } catch (e: Exception) {
        null // 處理可能的例外，例如檔案不存在或無法讀取
    }
}

public fun fileByteArrayToBufferedImage(byteArray: ByteArray): BufferedImage? {
    return try {
        val inputStream = ByteArrayInputStream(byteArray)
        ImageIO.read(inputStream)
    } catch (e: Exception) {
        null
    }
}

public fun ByteArray.toBufferedImage(): BufferedImage? {
    return try {
        val inputStream = ByteArrayInputStream(this)
        ImageIO.read(inputStream)
//        ImageIO.read(ByteArrayInputStream(this))
    } catch (e: Exception) {
        null // 處理可能的例外，例如格式不支援
    }
}
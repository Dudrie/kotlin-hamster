package de.github.dudrie.hamster.ui.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAwtImage
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skija.Image
import org.jetbrains.skiko.toImage

class ImageColorChanger private constructor(private val image: Image) {

    companion object {
        @Composable
        fun rememberReplaceColor(image: Image, original: Color, replacement: Color): Image =
            remember { ImageColorChanger(image).replaceColor(original, replacement) }
    }

    private fun replaceColor(original: Color, replacement: Color): Image {
        val adjustedImage = image.asImageBitmap().asAwtImage()
        val originalARGB = original.toArgb()

        for (x in 0 until adjustedImage.width) {
            for (y in 0 until adjustedImage.height) {
                val oldPixel = adjustedImage.getRGB(x, y)

                if (oldPixel == originalARGB) {
                    adjustedImage.setRGB(x, y, replacement.toArgb())
                }
            }
        }

        return adjustedImage.toImage()
    }
}

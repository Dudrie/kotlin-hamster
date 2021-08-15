package de.github.dudrie.hamster.ui.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAwtImage
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import de.github.dudrie.hamster.ui.helpers.ImageColorChanger.Companion.rememberReplaceColor
import org.jetbrains.skija.Image
import org.jetbrains.skiko.toImage

/**
 * Helper class which allows to replace one color of an [Image] with a different one.
 *
 * Should be instantiated with [rememberReplaceColor] which is a [Composable] itself and remembers the color change so the replacement is only done once and not on every re-render.
 *
 * @param image [Image] to replace the color.
 */
class ImageColorChanger private constructor(private val image: Image) {

    /**
     * Provides a function to instantiate an [ImageColorChanger].
     */
    companion object {
        /**
         * Instantiate an [ImageColorChanger] and applies the [replaceColor] function. The result is [remembered][remember].
         */
        @Composable
        fun rememberReplaceColor(image: Image, original: Color, replacement: Color): Image =
            remember { ImageColorChanger(image).replaceColor(original, replacement) }
    }

    /**
     * Replaces the [original] color with the given [replacement] color.
     *
     * This is done pixel per pixel.
     */
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

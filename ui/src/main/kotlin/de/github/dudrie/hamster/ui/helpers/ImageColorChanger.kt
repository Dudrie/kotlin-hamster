package de.github.dudrie.hamster.ui.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.*
import org.jetbrains.skija.Image
import org.jetbrains.skiko.toImage

/**
 * Helper class which allows to replace one color of an [Image] with a different one.
 *
 * Should be instantiated with [rememberReplaceColor] which is a [Composable] itself and remembers the color change so the replacement is only done once and not on every re-render.
 *
 * @param image [Image] to replace the color.
 */
class ImageColorChanger(image: Image) {

    /**
     * Image which gets replaced by new images if any replacement function of this object is called.
     *
     * @see replaceAllColors
     * @see replaceColor
     */
    var image: Image = image
        private set

    /**
     * Replaces all colors in the [image] according to the given [colorMap].
     *
     * @param colorMap Map which indicates which color should be converted into which other color. The keys represent the original colors and their values the color into which they should be converted.
     */
    fun replaceAllColors(colorMap: Map<Color, Color>) {
        for ((original, replacement) in colorMap) {
            replaceColor(original, replacement)
        }
    }

    /**
     * Replaces the [original] color with the given [replacement] color.
     *
     * This is done pixel per pixel.
     */
    private fun replaceColor(original: Color, replacement: Color) {
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

        image = adjustedImage.toImage()
    }
}

/**
 * Changes the colors of the [image] according to the given [colorMap].
 *
 * The result is [remembered][remember] before it is returned.
 */
@Composable
fun rememberReplaceColor(image: () -> Image, colorMap: Map<Color, Color>): ImageBitmap =
    remember {
        val changer = ImageColorChanger(image())
        changer.replaceAllColors(colorMap)
        changer.image.asImageBitmap()
    }

//
///**
// * Instantiate an [ImageColorChanger] and applies the [replaceColor] function. The result is [remembered][remember].
// */
//@Composable
//fun rememberReplaceColor(image: () -> Image, original: Color, replacement: Color): Image =
//    remember { ImageColorChanger(image()).replaceColor(original, replacement) }

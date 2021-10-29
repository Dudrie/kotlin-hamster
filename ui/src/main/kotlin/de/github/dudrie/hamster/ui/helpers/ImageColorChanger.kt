package de.github.dudrie.hamster.ui.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.*
import org.jetbrains.skia.Image
import org.jetbrains.skiko.toImage

/**
 * Helper class which allows to replace one color of an [Image] with a different one.
 *
 * Should be instantiated with [rememberReplaceColor] which is a [Composable] itself and remembers the color change so the replacement is only done once and not on every re-render.
 *
 * @param image [Image] to replace the color.
 */
class ImageColorChanger(image: Image) {
    private val originalImage: Image = image

    private val colorInfoMap: MutableMap<Color, Color> = mutableMapOf()

    /**
     * Image which gets replaced by new images if any replacement function of this object is called.
     *
     * @see replaceAllColors
     * @see replaceColor
     */
    private var adjustedImage: Image = image

    /**
     * The [adjustedImage] as [ImageBitmap].
     */
    val imageAsBitmap: ImageBitmap
        get() = adjustedImage.toComposeImageBitmap()

    /**
     * Adds the information that on a replacement call the [original] color should get replaced with the [replacement] color.
     *
     * @see replaceAllColors
     */
    fun addColorReplacement(original: Color, replacement: Color) {
        colorInfoMap[original] = replacement
    }

    /**
     * Replaces all colors in the [adjustedImage] according information given by the configuration functions like [addColorReplacement].
     *
     * @see addColorReplacement
     */
    fun replaceAllColors() {
        adjustedImage = originalImage
        for ((original, replacement) in colorInfoMap) {
            replaceColor(original, replacement)
        }
    }

    /**
     * Replaces the [original] color with the given [replacement] color.
     *
     * This is done pixel per pixel.
     */
    private fun replaceColor(original: Color, replacement: Color) {
        val adjustedImage = adjustedImage.toComposeImageBitmap().toAwtImage()
        val originalARGB = original.toArgb()

        for (x in 0 until adjustedImage.width) {
            for (y in 0 until adjustedImage.height) {
                val oldPixel = adjustedImage.getRGB(x, y)

                if (oldPixel == originalARGB) {
                    adjustedImage.setRGB(x, y, replacement.toArgb())
                }
            }
        }

        this.adjustedImage = adjustedImage.toImage()
    }
}

/**
 * Changes the colors of the [image] according to the given [configuration][configure].
 *
 * The result is [remembered][remember] before it is returned.
 */
@Composable
fun rememberReplaceColor(image: () -> Image, configure: ImageColorChanger.() -> Unit): ImageBitmap =
    remember {
        val changer = ImageColorChanger(image())
        configure(changer)
        changer.replaceAllColors()
        changer.imageAsBitmap
    }

/**
 * Replaces the [current] color of the [image] with the [replacement] color.
 *
 * The result is [remembered][remember] before it is returned.
 */
@Composable
fun rememberReplaceColor(current: Color, replacement: Color, image: () -> Image): ImageBitmap =
    rememberReplaceColor(image) { addColorReplacement(current, replacement) }

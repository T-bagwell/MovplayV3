package com.example.movplayv3.utils

import android.util.Size
import com.example.movplayv3.data.model.ImagesConfig

class ImageUrlParser(private val imagesConfig: ImagesConfig) {
    private val secureBaseUrl = imagesConfig.secureBaseUrl

    private val backdropDimensions = convertCodes(imagesConfig.backdropSizes).distinct()
    private val logoDimensions = convertCodes(imagesConfig.logoSizes).distinct()
    private val posterDimensions = convertCodes(imagesConfig.posterSizes).distinct()
    private val profileDimensions = convertCodes(imagesConfig.profileSizes).distinct()
    private val stillDimensions = convertCodes(imagesConfig.stillSizes).distinct()

    fun getImageUrl(
        path: String?,
        type: ImageType,
        preferredSize: Size? = null,
        strategy: MatchingStrategy = MatchingStrategy.FirstBiggerWidth
    ): String? {
        val source = when (type) {
            ImageType.Backdrop -> backdropDimensions
            ImageType.Logo -> logoDimensions
            ImageType.Poster -> posterDimensions
            ImageType.Profile -> profileDimensions
            ImageType.Still -> stillDimensions
        }

        return urlFromSource(source, path, preferredSize, strategy)
    }

    private sealed class Dimension(val code: String) {
        object Original : Dimension(code = "original")
        data class Width(val value: Int) : Dimension(code = code) {
            companion object {
                const val code = "w"
            }
        }

        data class Height(val value: Int) : Dimension(code = code) {
            companion object {
                const val code = "h"
            }
        }

        fun asCode() = when (this) {
            is Width -> "${Width.code}${this.value}"
            is Height -> "${Height.code}${this.value}"
            is Original -> "original"
        }
    }

    private fun convertCodes(codes: List<String>): List<Dimension> {
        return codes.mapNotNull { code ->
            when {
                code.contains(Dimension.Original.code) -> Dimension.Original
                code.contains(Dimension.Width.code) -> getValueFromCode(code)?.let { value ->
                    Dimension.Width(value)
                }
                code.contains(Dimension.Height.code) -> getValueFromCode(code)?.let { value ->
                    Dimension.Height(value)
                }
                else -> null
            }
        }
    }

    private fun getValueFromCode(code: String): Int? {
        return code.filter { char -> char.isDigit() }.toIntOrNull()
    }

    enum class MatchingStrategy {
        FirstBiggerWidth, FirstBiggerHeight, LowestWidthDiff, LowestHeightDiff
    }

    enum class ImageType {
        Backdrop, Logo, Poster, Profile, Still
    }
}
package com.suhas.todoapplication.ui.ui.placeHolders

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

private var emptyListPlaceholder: ImageVector? = null

@Composable
fun getEmptyListPlaceholder(): ImageVector {
    if (emptyListPlaceholder == null) {
        emptyListPlaceholder = Builder(
            name = "Empty List Placeholder Icon",
            defaultWidth = 200.dp,
            defaultHeight = 240.dp,
            viewportWidth = 200f,
            viewportHeight = 240f
        ).apply {

            // --- Clipboard Clip ---
            path(
                fill = SolidColor(MaterialTheme.colors.primary),
                stroke = SolidColor(MaterialTheme.colors.onBackground),
                strokeLineWidth = 2f
            ) {
                moveTo(x = 85f, y = 32f)
                arcToRelative(
                    a = 15f,
                    b = 10f,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 30f,
                    dy1 = 1f
                )
                verticalLineToRelative(dy = 12f)
                horizontalLineToRelative(dx = -30f)
                close()
            }

            // --- clip center circle ---
            path(
                fill = SolidColor(MaterialTheme.colors.background),
                stroke = SolidColor(MaterialTheme.colors.onBackground),
                strokeLineWidth = 2f
            ) {
                moveTo(x = 100f, y = 32f)
                moveToRelative(dx = -3f, dy = 0f)
                arcToRelative(
                    a = 4f,
                    b = 3f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 6f,
                    dy1 = 0f
                )
                arcToRelative(
                    a = 3f,
                    b = 3f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -6f,
                    dy1 = 0f
                )
            }

            // --- Clipboard Base ---
            path(
                fill = SolidColor(MaterialTheme.colors.primary),
                stroke = SolidColor(MaterialTheme.colors.onBackground),
                strokeLineWidth = 3f
            ) {
                moveTo(x = 40f, y = 40f)
                horizontalLineToRelative(dx = 120f)
                arcToRelative(
                    a = 12f,
                    b = 12f,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 12f,
                    dy1 = 12f
                )
                verticalLineToRelative(dy = 160f)
                arcToRelative(
                    a = 12f,
                    b = 12f,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -12f,
                    dy1 = 12f
                )
                horizontalLineToRelative(dx = -120f)
                arcToRelative(
                    a = 12f,
                    b = 12f,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -12f,
                    dy1 = -12f
                )
                verticalLineToRelative(dy = -160f)
                arcToRelative(
                    a = 12f,
                    b = 12f,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 12f,
                    dy1 = -12f
                )
                close()
            }

            // --- Paper Sheet ---
            path(
                fill = SolidColor(MaterialTheme.colors.surface),
                stroke = SolidColor(MaterialTheme.colors.onBackground),
                strokeLineWidth = 2f
            ) {
                moveTo(x = 50f, y = 55f)
                horizontalLineToRelative(dx = 100f)
                arcToRelative(
                    a = 6f,
                    b = 6f,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 6f,
                    dy1 = 6f
                )
                verticalLineToRelative(dy = 140f)
                arcToRelative(
                    a = 6f,
                    b = 6f,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -6f,
                    dy1 = 6f
                )
                horizontalLineToRelative(dx = -100f)
                arcToRelative(
                    a = 6f,
                    b = 6f,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -6f,
                    dy1 = -6f
                )
                verticalLineToRelative(dy = -140f)
                arcToRelative(
                    a = 6f,
                    b = 6f,
                    theta = 0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 6f,
                    dy1 = -6f
                )
                close()
            }

            // --- Placeholder Lines ---
            path(
                stroke = SolidColor(Color(0xFFFFF176)), // No direct MaterialTheme.colors mapping
                strokeLineWidth = 4f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(x = 60f, y = 75f)
                horizontalLineToRelative(dx = 80f)
            }
            path(
                stroke = SolidColor(Color(0xFFFFF176)), // No direct MaterialTheme.colors mapping
                strokeLineWidth = 4f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(x = 60f, y = 95f)
                horizontalLineToRelative(dx = 40f)
            }
            path(
                stroke = SolidColor(Color(0xFFFFF176)), // No direct MaterialTheme.colors mapping
                strokeLineWidth = 4f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(x = 60f, y = 115f)
                horizontalLineToRelative(dx = 70f)
            }
            path(
                stroke = SolidColor(Color(0xFFFFF176)), // No direct MaterialTheme.colors mapping
                strokeLineWidth = 4f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(x = 60f, y = 135f)
                horizontalLineToRelative(dx = 70f)
            }

            // --- Bee Face ---
            path(
                fill = SolidColor(MaterialTheme.colors.primary),
                stroke = SolidColor(Color(0xFF424242)), // No direct MaterialTheme.colors mapping
                strokeLineWidth = 2f
            ) {
                moveTo(x = 100f, y = 200f)
                moveToRelative(dx = -30f, dy = 0f)
                arcToRelative(
                    a = 30f,
                    b = 30f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 60f,
                    dy1 = 0f
                )
                arcToRelative(
                    a = 30f,
                    b = 30f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -60f,
                    dy1 = 0f
                )
            }

            // --- Eyes ---
            path(
                fill = SolidColor(Color(0xFF424242))
            ) {
                moveTo(x = 90f, y = 195f)
                moveToRelative(dx = -4f, dy = 0f)
                arcToRelative(
                    a = 4f,
                    b = 4f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 8f,
                    dy1 = 0f
                )
                arcToRelative(
                    a = 4f,
                    b = 4f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -8f,
                    dy1 = 0f
                )
            }
            path(
                fill = SolidColor(Color(0xFF424242))
            ) {
                moveTo(x = 110f, y = 195f)
                moveToRelative(dx = -4f, dy = 0f)
                arcToRelative(
                    a = 4f,
                    b = 4f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 8f,
                    dy1 = 0f
                )
                arcToRelative(
                    a = 4f,
                    b = 4f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -8f,
                    dy1 = 0f
                )
            }

            // --- Smile ---
            path(
                stroke = SolidColor(Color(0xFF424242)),
                strokeLineWidth = 2f
            ) {
                moveTo(x = 85f, y = 210f)
                quadTo(x1 = 99f, y1 = 220f, x2 = 115f, y2 = 210f)
            }

            // --- Antennae ---
            path(
                stroke = SolidColor(MaterialTheme.colors.secondaryVariant),
                strokeLineWidth = 1f,
                fill = SolidColor(Color.Transparent)
            ) {
                moveTo(x = 90f, y = 173f)
                quadTo(x1 = 85f, y1 = 140f, x2 = 73f, y2 = 145f)
            }
            path(
                stroke = SolidColor(MaterialTheme.colors.secondaryVariant),
                strokeLineWidth = 1f,
                fill = SolidColor(Color.Transparent)
            ) {
                moveTo(x = 110f, y = 173f)
                quadTo(x1 = 112f, y1 = 140f, x2 = 125f, y2 = 145f)
            }

            // --- Antenna Tips ---
            path(
                fill = SolidColor(MaterialTheme.colors.secondaryVariant)
            ) {
                moveTo(x = 70f, y = 145f)
                moveToRelative(dx = -3f, dy = 0f)
                arcToRelative(
                    a = 3f,
                    b = 3f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 6f,
                    dy1 = 0f
                )
                arcToRelative(
                    a = 3f,
                    b = 3f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -6f,
                    dy1 = 0f
                )
                close()
            }
            path(
                fill = SolidColor(MaterialTheme.colors.secondaryVariant)
            ) {
                moveTo(x = 125f, y = 145f)
                moveToRelative(dx = -3f, dy = 0f)
                arcToRelative(
                    a = 3f,
                    b = 3f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 6f,
                    dy1 = 0f
                )
                arcToRelative(
                    a = 3f,
                    b = 3f,
                    theta = 0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = -6f,
                    dy1 = 0f
                )
                close()
            }
        }.build()
    }
    return emptyListPlaceholder!!
}
package com.sotsap.apps.gymmanagement.core.framework.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection

/**
 * Represents the different sides for applying insets or padding.
 * This enum is used to specify which sides of a Composable should receive padding.
 */
enum class Insets { Top, Start, End, Bottom }

/**
 * Adds padding to a Composable, allowing for selective application of insets and additional padding.
 *
 * This function provides flexibility in controlling which sides of the Composable receive inset-based padding
 * and which receive standard padding. It's useful for scenarios where you need to respect system insets
 * on certain sides while applying custom padding on others.
 *
 * @param insetType A list of [Insets] defining which sides (Top, Start, End, Bottom) should consider the `insets` parameter.
 *                  Defaults to applying insets to all sides.
 * @param insets The [PaddingValues] to be applied to the sides specified in `insetType`. Typically, this would be
 *               system window insets or a similar overall padding. Defaults to `PaddingValues()`.
 * @param padding The [PaddingValues] to be applied to all sides. This padding is always applied, and if a side is also
 *                specified in `insetType`, the `insets` for that side will be added to this `padding`.
 *                Defaults to `PaddingValues()`.
 * @return A [Modifier] that applies the calculated padding.
 */
fun Modifier.paddingInsets(
    insetType: List<Insets> = listOf(Insets.Top, Insets.Start, Insets.End, Insets.Bottom),
    insets: PaddingValues = PaddingValues(),
    padding: PaddingValues = PaddingValues()
) = then(
    Modifier.padding(
        start = when {
            Insets.Start in insetType -> insets.calculateStartPadding(LayoutDirection.Ltr) + padding.calculateStartPadding(
                LayoutDirection.Ltr
            )
            else -> padding.calculateStartPadding(LayoutDirection.Ltr)
        },
        top = when {
            Insets.Top in insetType -> insets.calculateTopPadding() + padding.calculateTopPadding()
            else -> padding.calculateTopPadding()
        },
        end = when {
            Insets.End in insetType -> insets.calculateEndPadding(LayoutDirection.Ltr) + padding.calculateEndPadding(
                LayoutDirection.Ltr
            )
            else -> padding.calculateEndPadding(LayoutDirection.Ltr)
        },
        bottom = when {
            Insets.Bottom in insetType -> insets.calculateBottomPadding() + padding.calculateBottomPadding()
            else -> padding.calculateBottomPadding()
        }
    )
)
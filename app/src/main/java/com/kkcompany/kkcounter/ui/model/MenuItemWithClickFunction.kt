package com.kkcompany.kkcounter.ui.model

import androidx.annotation.StringRes

data class MenuItemWithClickFunction(
    @StringRes val titleRes: Int,
    val enabled: Boolean = true,
    val onClick: () -> Unit
)
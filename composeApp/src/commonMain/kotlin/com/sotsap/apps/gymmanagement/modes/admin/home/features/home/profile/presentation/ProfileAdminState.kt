package com.sotsap.apps.gymmanagement.modes.admin.home.features.home.profile.presentation

import com.sotsap.apps.gymmanagement.features.login.domain.ProfileModel

data class ProfileAdminState(
    val progress: Boolean = false,
    val profile: ProfileModel? = null
)

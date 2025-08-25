package com.sotsap.apps.gymmanagement.features.login.data

import com.sotsap.apps.gymmanagement.features.login.domain.ProfileModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the profile data of a user.
 *
 * @property name The name of the user.
 * @property email The email address of the user.
 * @property isAdmin A boolean indicating whether the user is an administrator.
 *                  The `@SerialName("is_admin")` annotation is used by Kotlinx Serialization
 *                  to map this property to the "is_admin" field in the JSON representation.
 */
@Serializable
data class ProfileEntity(
    val name: String,
    val email: String,
    @SerialName("is_admin") val isAdmin: Boolean
) {

    /**
     * Converts this [ProfileModel] data class to a [ProfileEntity] data class.
     *
     * @return A new [ProfileEntity] instance with the same data as this [ProfileModel].
     */
    fun toModel() = ProfileModel(
        name = name,
        email = email,
        isAdmin = isAdmin
    )

}

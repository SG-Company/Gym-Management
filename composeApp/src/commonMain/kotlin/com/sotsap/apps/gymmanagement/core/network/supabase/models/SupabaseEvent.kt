package com.sotsap.apps.gymmanagement.core.network.supabase.models

import com.sotsap.apps.gymmanagement.core.network.supabase.extensions.SupabaseEventTypes
import kotlinx.serialization.Serializable

/**
 * Represents an event that can be sent to or received from Supabase.
 * This interface defines the basic structure of a Supabase event.
 */
interface SupabaseEvent {
    val type: SupabaseEventTypes
        get() = SupabaseEventTypes.Event
}

/**
 * Represents an event that can be posted to Supabase.
 * This class is used internally for serialization purposes.
 * @param count The count of the event.
 * @param type The type of the event.
 * @param name The name of the event.
 * @param property1 The first property of the event.
 * @param property2 The second property of the event.
 * @param property3 The third property of the event.
 * @param property4 The fourth property of the event.
 * @param property5 The fifth property of the event.
 */
@Serializable
data class SupabasePostableEvent(
    val count: Int,
    val type: String,
    val name: String,
    val property1: String,
    val property2: String,
    val property3: String,
    val property4: String,
    val property5: String,
)


package com.sotsap.apps.gymmanagement.core.network.supabase.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sotsap.apps.gymmanagement.core.network.supabase.models.SupabaseEvent
import com.sotsap.apps.gymmanagement.core.network.supabase.models.SupabasePostableEvent
import com.sotsap.apps.gymmanagement.core.network.supabase.supabaseClient
import com.sotsap.apps.gymmanagement.core.utilities.AppConfig
import com.sotsap.apps.gymmanagement.core.utilities.Logger
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

private const val TAG_SEND_EVENT = "sendEvent"

/**
 * Represents the different types of events that can be tracked in Supabase.
 *
 * Each event type has a corresponding string representation that is used when interacting with the Supabase API.
 */
enum class SupabaseEventTypes(val type: String) {

    /**
     * Represents the different types of events that can be tracked in Supabase.
     * Each enum entry has a corresponding `type` string that is used in the database.
     */
    Event(type = "Event"),

    /**
     * User action events.
     *
     * For example, button clicks, navigation, etc.
     */
    UserAction(type = "User action"),

    /**
     * Represents a Trigger event in Supabase.
     * This event type is used to track the execution of database triggers.
     */
    Trigger(type = "Trigger")

}

/**
 * Converts a [SupabaseEvent] to a [SupabasePostableEvent].
 *
 * This function determines the name of the event. If the event is an enum, its name is used.
 * Otherwise, the simple name of the event's class is used. If the class name is not available,
 * an empty string is used as the name.
 *
 * The type of the event is derived from the `name` property of the `type` field of the input event.
 *
 * @param event The [SupabaseEvent] to be converted.
 * @param count The [SupabaseEvent] count value.
 * @param property1 The first property of the event.
 * @param property2 The second property of the event.
 * @param property3 The third property of the event.
 * @param property4 The fourth property of the event.
 * @param property5 The fifth property of the event.
 * @return A [SupabasePostableEvent] object with the determined type and name.
 */
private fun toPostable(
    event: SupabaseEvent,
    count: Int = 1,
    property1: String = "",
    property2: String = "",
    property3: String = "",
    property4: String = "",
    property5: String = ""
): SupabasePostableEvent {
    val name = when(event) {
        is Enum<*> -> event.name
        else -> event::class.simpleName ?: ""
    }
    return SupabasePostableEvent(
        type = event.type.name,
        name = name,
        count = count,
        property1 = property1,
        property2 = property2,
        property3 = property3,
        property4 = property4,
        property5 = property5
    )
}


/**
 * Sends a [SupabaseEvent] to the "events" table in Supabase.
 *
 * This function is an extension function for [ViewModel].
 * It ensures that the event name is not empty before attempting to send the event.
 * The event is sent asynchronously on an IO dispatcher.
 * Any exceptions during the insertion process are caught and logged.
 *
 * @param event The [SupabaseEvent] to be sent.
 * @param property1 The first property of the event.
 * @param property2 The second property of the event.
 * @param property3 The third property of the event.
 * @param property4 The fourth property of the event.
 * @param property5 The fifth property of the event.
 */
fun ViewModel.sendEvent(
    event: SupabaseEvent,
    property1: String = "",
    property2: String = "",
    property3: String = "",
    property4: String = "",
    property5: String = "",
) {
    if (AppConfig.DEBUG) {
        Logger.logDebug("Application is in debug mode...event will not be sent", TAG_SEND_EVENT)
        return
    }
    // convert to postable event in order to match the database field names
    val postableEvent = toPostable(
        event = event,
        property1 = property1,
        property2 = property2,
        property3 = property3,
        property4 = property4,
        property5 = property5
    )
    if (postableEvent.name.isEmpty()) {
        Logger.logError("Event name should not be empty", TAG_SEND_EVENT)
        return
    }
    val client = supabaseClient.postgrest
    viewModelScope.launch(context = Dispatchers.IO) {
        try {
            // first we have to fetch if the given event is already in database. If is, then we have
            // to update only the counter number. If not, insert it with 1 as counter number
            val eventFound = client
                .from(table = "events")
                .select {
                    filter {
                        eq("name", postableEvent.name)
                        eq("property1", postableEvent.property1)
                        eq("property2", postableEvent.property2)
                        eq("property3", postableEvent.property3)
                        eq("property4", postableEvent.property4)
                        eq("property5", postableEvent.property5)
                    }
                }
                .decodeSingleOrNull<SupabasePostableEvent>()
            // check if event found is not null
            if (eventFound == null) {
                // insert the event
                client.from(table = "events").insert(postableEvent)
            } else {
                // update the fetched event count + 1 and update it
                client
                    .from(table = "events")
                    .update(
                        request = {
                            filter {
                                eq(column = "name", value = eventFound.name)
                                eq(column = "type", value = eventFound.type)
                                eq("property1", postableEvent.property1)
                                eq("property2", postableEvent.property2)
                                eq("property3", postableEvent.property3)
                                eq("property4", postableEvent.property4)
                                eq("property5", postableEvent.property5)
                            }
                        },
                        update = {
                            set(column = "count", value = eventFound.count + 1)
                        }
                    )
            }

            Logger.logDebug("Event [${postableEvent.name}] sent successfully", TAG_SEND_EVENT)

        } catch (ex: Exception) {
            Logger.logError(message = ex.message ?: "", tag = TAG_SEND_EVENT)
        }

    }
}
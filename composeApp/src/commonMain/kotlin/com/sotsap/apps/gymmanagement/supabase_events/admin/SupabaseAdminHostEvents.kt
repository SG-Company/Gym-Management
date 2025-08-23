package com.sotsap.apps.gymmanagement.supabase_events.admin

import com.sotsap.apps.gymmanagement.core.network.supabase.extensions.SupabaseEventTypes
import com.sotsap.apps.gymmanagement.core.network.supabase.models.SupabaseEvent

/**
 * Enum class representing Supabase events specific to the Admin Host screen.
 * These events are used for tracking user interactions and system events within the admin interface.
 *
 * Each enum constant represents a specific event that can occur on the Admin Host screen.
 * The `type` property, inherited from `SupabaseEvent`, categorizes the event
 * (e.g., `UserAction` for events triggered by direct user interaction).
 */
enum class SupabaseAdminHostEvents: SupabaseEvent {
    UserSelectDashboardOption {
        override val type: SupabaseEventTypes get() = SupabaseEventTypes.UserAction
    },
    UserSelectReceiptsOption {
        override val type: SupabaseEventTypes get() = SupabaseEventTypes.UserAction
    },
    UserSelectMessagesOption {
        override val type: SupabaseEventTypes get() = SupabaseEventTypes.UserAction
    },
    UserSelectSchedulesOption {
        override val type: SupabaseEventTypes get() = SupabaseEventTypes.UserAction
    },
    UserSelectProfileOption {
        override val type: SupabaseEventTypes get() = SupabaseEventTypes.UserAction
    }
}
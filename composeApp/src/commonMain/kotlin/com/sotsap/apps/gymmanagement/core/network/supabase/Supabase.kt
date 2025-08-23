package com.sotsap.apps.gymmanagement.core.network.supabase

import com.sotsap.apps.gymmanagement.BuildKonfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.supabaseJson
import kotlinx.serialization.json.Json

/**
 * Supabase client for interacting with the Supabase backend.
 *
 * This client is configured with the Supabase URL and key from BuildKonfig
 * and has the Auth plugin installed for handling authentication.
 */
val supabaseClient = createSupabaseClient(
    supabaseUrl = BuildKonfig.supabaseUrl,
    supabaseKey = BuildKonfig.supabaseToken
) {
    install(Auth)
    install(Postgrest) {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
    }
}
package com.sotsap.apps.gymmanagement.core.network.supabase.models

import com.sotsap.apps.gymmanagement.core.models.ResponseError

enum class SupabaseErrors: ResponseError {
    PostgrestException,
    RequestTimeout
}
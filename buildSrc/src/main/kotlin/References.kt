import org.gradle.api.JavaVersion
import java.util.Calendar

/**
 * The package name of the application.
 */
const val mPackageName = "com.sotsap.apps.gymmanagement"

/**
 * The package name of the desktop application.
 */
const val packageNameDesktop = "$mPackageName.Main"

/**
 * The version of the application.
 */
const val appVersion = "1.0.0"

/**
 * The version of the desktop application.
 */
const val desktopVersion = "1.0.0"

/**
 * The build number for the release version of the application.
 *
 * This constant is used to differentiate between different builds of the same release version.
 * It is typically a two-digit string, incremented for each new build.
 */
const val releaseBuildNumber = "01"

/**
 * Converts a string representation of a Java version to its corresponding [JavaVersion] enum.
 *
 * This function takes a string (e.g., "21", "22") and returns the matching [JavaVersion]
 * constant. If the string does not match any known version, it defaults to [JavaVersion.VERSION_21].
 *
 * @return The [JavaVersion] corresponding to the input string, or [JavaVersion.VERSION_21] if no match is found.
 */
fun String.toJavaVersion() = when (this) {
    "21" -> JavaVersion.VERSION_21
    "22" -> JavaVersion.VERSION_22
    "23" -> JavaVersion.VERSION_23
    "24" -> JavaVersion.VERSION_24
    "25" -> JavaVersion.VERSION_25
    "26" -> JavaVersion.VERSION_26
    "27" -> JavaVersion.VERSION_27
    else -> JavaVersion.VERSION_21
}

/**
 * The version code of the application.
 *
 * This property generates a version code by concatenating the current year, month, and day.
 * For example, if the current date is October 25, 2023, the `versionCode` will be "20231025".
 * This format ensures a unique and chronologically increasing version code.
 */
val mVersionCode = "${getYear()}${getMonth()}${getDay()}"


/**
 * The full version of the application, combining the [appVersion], [mVersionCode], and a potential release build number.
 *
 * This property creates a string that includes the human-readable application version,
 * the unique version code, and an optional release build number, formatted as
 * "appVersion (versionCode[releaseBuildNumber])".
 * For example, if [appVersion] is "1.0.0", [mVersionCode] is "20231025", and `releaseBuildNumber` (if present) is "01",
 * this will be "1.0.0 (2023102501)". If `releaseBuildNumber` is empty, it will be "1.0.0 (20231025)".
 */
val appFullVersion = "$appVersion ($mVersionCode$releaseBuildNumber)"

/**
 * Enum class representing Supabase properties.
 *
 * This enum defines the keys for various Supabase configuration settings,
 * such as the project URL and public authentication key. Each property
 * is associated with a unique string key used for accessing its value.
 *
 * @property key The string key associated with the Supabase property.
 */
enum class SupabaseProperties(val key: String) {

    /**
     * The URL of your Supabase project.
     *
     * This is the unique endpoint for accessing your Supabase backend services,
     * such as the database, authentication, and storage.
     * It typically looks like `https://<your-project-id>.supabase.co`.
     */
    SUPABASE_URL("SUPABASE_URL"),

    /**
     * The public key for Supabase authentication.
     * This key is used to identify the application when interacting with Supabase services.
     */
    SUPABASE_PUBLIC_KEY("SUPABASE_PUBLIC_KEY")

}

/**
 * Retrieves the current year.
 *
 * This function uses the [Calendar] instance to get the current year.
 *
 * @return An integer representing the current year.
 */
private fun getYear(): String {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.YEAR).toString()
}

/**
 * Returns the current month as an integer.
 *
 * This function retrieves the current month from the system's calendar.
 * The value returned is 1-indexed (January = 1, February = 2, etc.),
 * as [Calendar.MONTH] is 0-indexed.
 *
 * @return An integer representing the current month (01-12).
 */
private fun getMonth(): String {
    val calendar = Calendar.getInstance()
    val month = calendar.get(Calendar.MONTH) + 1
    val formattedMonth = if (month < 10) "0$month" else month.toString()
    return formattedMonth
}

/**
 * Returns the current day of the month.
 *
 * This function uses the default time zone and locale to determine the current day.
 *
 * @return An integer representing the current day of the month (01-31).
 */
private fun getDay(): String {
    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val formattedDay = if (day < 10) "0$day" else day.toString()
    return formattedDay
}
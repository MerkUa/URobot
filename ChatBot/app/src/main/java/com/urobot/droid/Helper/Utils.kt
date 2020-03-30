package com.urobot.droid.Helper

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.CalendarContract
import android.provider.CalendarContract.Calendars
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.loader.content.CursorLoader
import com.urobot.droid.R
import com.urobot.droid.data.model.CalendarItemModel
import java.util.*


object Utils {

    fun isNetworkConected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    fun getRealPath(context: Context, fileUri: Uri): String? {
        // SDK >= 11 && SDK < 19
        return if (Build.VERSION.SDK_INT < 19) {
            getRealPathFromURIAPI11to18(context, fileUri)
        } else {
            getRealPathFromURIAPI19(context, fileUri)
        }// SDK > 19 (Android 4.4) and up
    }

    @SuppressLint("NewApi")
    fun getRealPathFromURIAPI11to18(context: Context, contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        var result: String? = null

        val cursorLoader = CursorLoader(context, contentUri, proj, null, null, null)
        val cursor = cursorLoader.loadInBackground()

        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(columnIndex)
            cursor.close()
        }
        return result
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author Niks
     */
    @SuppressLint("NewApi")
    fun getRealPathFromURIAPI19(context: Context, uri: Uri): String? {

        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                var cursor: Cursor? = null
                try {
                    cursor = context.contentResolver.query(
                        uri,
                        arrayOf(MediaStore.MediaColumns.DISPLAY_NAME),
                        null,
                        null,
                        null
                    )
                    cursor!!.moveToNext()
                    val fileName = cursor.getString(0)
                    val path =
                        Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
                    if (!TextUtils.isEmpty(path)) {
                        return path
                    }
                } finally {
                    cursor?.close()
                }
                val id = DocumentsContract.getDocumentId(uri)
                if (id.startsWith("raw:")) {
                    return id.replaceFirst("raw:".toRegex(), "")
                }
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads"),
                    java.lang.Long.valueOf(id)
                )

                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                when (type) {
                    "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri, selection, selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )
        } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
            return uri.path
        }// File
        // MediaStore (and general)

        return null
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     * @author Niks
     */
    private fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor =
                context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }


    fun isAppRunning(
        context: Context,
        packageName: String
    ): Boolean {
        val activityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val procInfos =
            activityManager.runningAppProcesses
        if (procInfos != null) {
            for (processInfo in procInfos) {
                if (processInfo.processName == packageName) {
                    return true
                }
            }
        }
        return false
    }


    fun ensureCalendarExists(context: Context): Long {
        Log.d("Merk", "ensureCalendarExists ")

        val resolver = context.contentResolver
        // Lookup the calendar
        var calendarId: Long = 0
        val cursor = resolver.query(
            Calendars.CONTENT_URI,
            arrayOf(
                Calendars._ID
            ),
            Calendars.ACCOUNT_NAME + "=? AND " + Calendars.ACCOUNT_TYPE + "=?",
            arrayOf("Urobot", "Urobot calendar"),
            null
        )
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    calendarId = cursor.getLong(0)
                }
            } finally {
                cursor.close()
            }
        }
        if (calendarId == 0L) { // Calendar doesn't exist yet, so create it
            val contentValues = ContentValues()
            contentValues.put(Calendars.ACCOUNT_NAME, "Urobot")
            contentValues.put(Calendars.ACCOUNT_TYPE, "Urobot calendar")
            contentValues.put(Calendars.NAME, "Calendar")
            contentValues.put(
                Calendars.CALENDAR_DISPLAY_NAME,
                context.getString(R.string.app_name)
            )
//            contentValues.put(
//                Calendars.CALENDAR_COLOR,
//                ContextCompat.getColor(context, R.color.)
//            )
            contentValues.put(
                Calendars.CALENDAR_ACCESS_LEVEL,
                Calendars.CAL_ACCESS_OWNER
            )
            contentValues.put(Calendars.CAN_ORGANIZER_RESPOND, 0)
            contentValues.put(Calendars.OWNER_ACCOUNT, "Urobot")
            contentValues.put(Calendars.SYNC_EVENTS, 1)
            contentValues.put(
                Calendars.CALENDAR_TIME_ZONE,
                TimeZone.getDefault().id
            )
            contentValues.put(Calendars.ALLOWED_REMINDERS, "0,1,2")
            contentValues.put(Calendars.ALLOWED_AVAILABILITY, "0,1")
            contentValues.put(Calendars.ALLOWED_ATTENDEE_TYPES, "0")
            contentValues.put(
                Calendars.MAX_REMINDERS,
                Integer.toString(Int.MAX_VALUE)
            )
            contentValues.put(Calendars.VISIBLE, 1)
            var uri = Calendars.CONTENT_URI
            uri = uri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(Calendars.ACCOUNT_NAME, "Urobot")
                .appendQueryParameter(Calendars.ACCOUNT_TYPE, "Urobot calendar")
                .build()
            val newCalendarUri = resolver.insert(uri, contentValues) ?: return -1
            calendarId = ContentUris.parseId(newCalendarUri)
            Log.d("Merk", "Merk " + calendarId)
        }
        return calendarId
    }


    fun addCalendarEvent(context: Context, item: CalendarItemModel) {
        Log.d("Merk", "ensureCalendarExists ")

        val resolver = context.contentResolver
        // Lookup the calendar
        var calendarId: Long = 0
        val cursor = resolver.query(
            Calendars.CONTENT_URI,
            arrayOf(
                Calendars._ID
            ),
            Calendars.ACCOUNT_NAME + "=? AND " + Calendars.ACCOUNT_TYPE + "=?",
            arrayOf("Urobot", "Urobot calendar"),
            null
        )
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    calendarId = cursor.getLong(0)
                    val contentValues = ContentValues()
                    contentValues.put(CalendarContract.Events.DTSTART, item.timeInMilis);
                    contentValues.put(CalendarContract.Events.DURATION, 1);

                    contentValues.put(CalendarContract.Events.TITLE, item.title);
                    contentValues.put(CalendarContract.Events.DESCRIPTION, item.description);
                    contentValues.put(CalendarContract.Events.CALENDAR_ID, calendarId);
                    contentValues.put(CalendarContract.Events.EVENT_TIMEZONE, "Ukraine");
                    resolver.insert(CalendarContract.Events.CONTENT_URI, contentValues);
                }
            } finally {
                cursor.close()
            }
        }
    }
}
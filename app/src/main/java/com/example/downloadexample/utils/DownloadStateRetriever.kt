package com.example.downloadexample.utils

import android.app.DownloadManager
import android.database.Cursor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

//my class using rx java for download files
class DownloadStateRetriever (private val downloadManager: DownloadManager) {
    fun retrieve(id: Long) {
        var downloading = AtomicBoolean(true)

        val disposable = Observable.fromCallable {
            val query = DownloadManager.Query().setFilterById(id)
            val cursor = downloadManager.query(query)

            cursor.moveToFirst()

            val bytesDownloaded = cursor.intValue(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
            val bytesTotal = cursor.intValue(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)

            if (isSuccessful(cursor)) downloading.set(false)
            cursor.close()

            if (bytesTotal == 0) 0.toInt() else ((bytesDownloaded * 100F) / bytesTotal).toInt()
        }
            .subscribeOn(Schedulers.newThread())
            .delay(1, TimeUnit.SECONDS)
            .repeatUntil { !downloading.get() }
            .subscribe {
            }
    }

    private fun isSuccessful(cursor: Cursor) = status(cursor) == DownloadManager.STATUS_SUCCESSFUL

    private fun status(cursor: Cursor) = cursor.intValue(DownloadManager.COLUMN_STATUS)
}
fun Cursor.column(which: String) = this.getColumnIndex(which)
fun Cursor.intValue(which: String): Int = this.getInt(column(which))
fun Cursor.floatValue(which: String): Float = this.getFloat(column(which))
fun Cursor.stringValue(which: String): String = this.getString(column(which))
fun Cursor.doubleValue(which: String): Double = this.getDouble(column(which))
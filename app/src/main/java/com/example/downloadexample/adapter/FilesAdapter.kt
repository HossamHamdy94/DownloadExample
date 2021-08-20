package com.example.downloadexample.adapter

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.downloadexample.R
import com.example.downloadexample.data.FileModel
import kotlinx.android.synthetic.main.downloadfileitem.view.*


class FilesAdapter (var actvity : Activity,var context: Context, var files : List<FileModel>): RecyclerView.Adapter<FilesAdapter.FilesAdapterViewHolder>() {
    private val TAG = "ReviewAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesAdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.downloadfileitem, parent, false)
        return FilesAdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: FilesAdapterViewHolder, position: Int) {
        val file= (files[position])

        if (file.type.equals("pdf")){
            Glide.with(context).load(R.drawable.ic_pdf_icon).into(holder.filimage)

        }else
        Glide.with(context).load(file.url).into(holder.filimage)

        holder.filename.text = file.name
        holder.downloadicon.setOnClickListener {
         //   downloadFile(file)



            val urlDownload =
                file.url
            val request = DownloadManager.Request(Uri.parse(urlDownload))

            request.setDescription("Nagwa Task")
            request.setTitle("Download")
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file.name)

            val   manager =  context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            val downloadId = manager!!.enqueue(request)

            holder.downloadicon.visibility = View.GONE
            holder.prgress.visibility = View.VISIBLE


            Thread {

                var downloading = true



                while (downloading) {
                    val q = DownloadManager.Query()
                    q.setFilterById(downloadId)
                    val cursor: Cursor = manager!!.query(q)
                    cursor.moveToFirst()
                    val bytes_downloaded: Int = cursor.getInt(
                        cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                    )
                    val bytes_total: Int =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) === DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false
                    }
                    Log.d("hossam",bytes_downloaded.toString())
                    Log.d("hossam",cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES).toString())
                    val dl_progress =
                        (bytes_downloaded.toDouble() / bytes_total.toDouble() * 100f).toInt()
                    actvity.runOnUiThread(Runnable {

                        if (bytes_total==-1){
                            holder.prgress.setText("Downloading")
                        }
                        else{
                            holder.prgress.setText(dl_progress.toString()+ " % ")
                            if (dl_progress == 100) {
                                holder.markAscomplete.visibility = View.VISIBLE
                            }

                        }
                    })
                    cursor.close()
                }

            }.start()


        }










    }


    fun downloadFile(file:FileModel) {



        val urlDownload =
           file.url
        val request = DownloadManager.Request(Uri.parse(urlDownload))

        request.setDescription("Nagwa Task")
        request.setTitle("Download")
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file.name)

        val   manager =  context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadId = manager!!.enqueue(request)


        Thread {
            var downloading = true
            while (downloading) {
                val q = DownloadManager.Query()
                q.setFilterById(downloadId)
                val cursor: Cursor = manager!!.query(q)
                cursor.moveToFirst()
                val bytes_downloaded: Int = cursor.getInt(
                    cursor
                        .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                )
                val bytes_total: Int =
                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) === DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val dl_progress =
                    (bytes_downloaded.toDouble() / bytes_total.toDouble() * 100f).toInt()
                actvity.runOnUiThread(Runnable {
                //    progressBar.setProgress(dl_progress)
                })

                cursor.close()
            }
        }.start()



    }

    override fun getItemCount(): Int {
        return files.size
    }

    inner class FilesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var filimage = itemView.fileIV
        var filename = itemView.fileNameTv
        var downloadicon = itemView.downloadIcon
        var prgress = itemView.progresstext
        var markAscomplete = itemView.comleteIcon



    } }